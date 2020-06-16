package com.tsystems.optimos.jrcpwrapper;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

class JCOPSimulatorServer {
    private static final Logger logger = (LogManager.getLogger("com.tsystems.optimos.jrcpwrapper"));
    private Server server;

    JCOPSimulatorServer() {
    }

    void start() throws IOException {
        int port = 8080;

        logger.info("Server started, listening on " + port);
        JCOPSimulatorImpl impl = null;
        try {
            impl = new JCOPSimulatorImpl();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        server = ServerBuilder.forPort(port)
                .addService(impl)
                .build()
                .start();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // Use stderr here since the logger may have been reset by its JVM shutdown hook.
            System.err.println("*** shutting down gRPC server since JVM is shutting down");
            try {
                JCOPSimulatorServer.this.stop();
            } catch (InterruptedException e) {
                e.printStackTrace(System.err);
            }
            System.err.println("*** server shut down");
        }));
    }

    private void stop() throws InterruptedException {
        if (server != null) {
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }
}
