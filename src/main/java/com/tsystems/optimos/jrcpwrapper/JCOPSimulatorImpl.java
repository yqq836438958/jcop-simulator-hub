package com.tsystems.optimos.jrcpwrapper;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.rpc.Code;
import com.google.rpc.Status;
import com.googlecode.protobuf.format.JsonFormat;
import com.nxp.id.jrcp.HexUtil;
import io.grpc.examples.jcop.*;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JCOPSimulatorImpl extends JCOPSimulatorGrpc.JCOPSimulatorImplBase {
    private static final Logger logger = (LogManager.getLogger("com.tsystems.optimos.jrcpwrapper"));

    private static JsonFormat jsonFormat = new JsonFormat();
    private static Map<String, JRCPClient> clientMap = new HashMap<>();
    private static Map<String, Simulator> simulatorMap = new HashMap<>();
    private static Map<Version, File> fileMapping = new HashMap<>();

    JCOPSimulatorImpl() {
        super();

        File file = new File("JCOP_Simulator-JCOP4.7-R1.00.4-RC17/win32/jcop.exe");
        if (!file.exists()) {
            //TODO err
        }

        fileMapping.put(Version.JCOP4_7_R1_00_4_RC17, file);

    }

    @Override
    public void open(OpenRequest request, StreamObserver<OpenReply> responseObserver) {
        logger.info("received open request: " + jsonFormat.printToString(request));

        File file = fileMapping.get(request.getVersion());
        if (file == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("simulator doesn't exist")
                    .build();

            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        try {
            // find open port
            int port = Utils.findOpenPort();
            // generate uniqueId for simulator
            String simulatorId = UUID.randomUUID().toString();
            // start simulator instance
            Simulator simulator = new Simulator(simulatorId,port,file);
            simulator.startInstance(exitCode -> logger.info("process exit with code: " + exitCode));

            JRCPClient client = new JRCPClient();
            // wait for simulator port to be reachable
            client.waitForSimulator(simulator);
            simulatorMap.put(simulatorId, simulator);

            // connect to card
            byte[] atr = client.waitForCard(simulator.getId());

            String uuid;
            if (atr != null) {
                uuid = UUID.randomUUID().toString();
                clientMap.put(uuid, client);
                logger.info("opened session " + uuid + " to simulator " + simulator.getId() + " on port " + simulator.getPort() );
            } else {
                Status status = Status.newBuilder()
                        .setCode(Code.INTERNAL.getNumber())
                        .setMessage("couldn't retrieve ATR from simulator")
                        .build();

                responseObserver.onError(StatusProto.toStatusRuntimeException(status));
                return;
            }

            OpenReply reply = OpenReply.newBuilder()
                    .setAtr(ByteString.copyFrom(atr))
                    .setSimulatorId(simulator.getId())
                    .setSessionId(uuid)
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (IOException e) {
            e.printStackTrace();
            Status status = Status.newBuilder()
                    .setCode(Code.INTERNAL.getNumber())
                    .setMessage("unknown")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        }
    }

    @Override
    public void transmitAPDU(Capdu request, StreamObserver<Rapdu> responseObserver) {
        logger.info("session : " + request.getSessionId() + " Capdu -> " + HexUtil.byteArrayToHexString(request.getApdu().toByteArray()));

        JRCPClient client = clientMap.get(request.getSessionId());
        if (client == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("no JRCP client found for " + request.getSessionId())
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        byte[] resp= client.sendData(request.getSimulatorId(), request.getApdu().toByteArray());

        if (resp == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNKNOWN.getNumber())
                    .setMessage("received null response for sendData")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        logger.info("session : " + request.getSessionId() + " Rapdu <- " + HexUtil.byteArrayToHexString(resp));

        Rapdu rapdu = Rapdu.newBuilder()
                .setSessionId(request.getSessionId())
                .setApdu(ByteString.copyFrom(resp))
                .build();

        responseObserver.onNext(rapdu);
        responseObserver.onCompleted();
    }

    @Override
    public void reset(ResetRequest request, StreamObserver<ResetReply> responseObserver) {
        logger.info("simulator: " + request.getSimulatorId() + " session : " + request.getSessionId() + " request reset " + (request.getWarm() ? "warm" : "cold"));

        JRCPClient client = clientMap.get(request.getSessionId());
        if (client == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("no JRCP client found for " + request.getSessionId())
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        byte[] atr;

        if (request.getWarm()) {
            atr = client.warmReset(request.getSimulatorId());
        } else {
            atr = client.coldReset(request.getSimulatorId());
        }

        logger.info("session : " + request.getSessionId() + " reset success: " + HexUtil.byteArrayToHexString(atr));

        ResetReply reply = ResetReply.newBuilder()
                .setSimulatorId(request.getSimulatorId())
                .setSessionId(request.getSessionId())
                .setAtr(ByteString.copyFrom(atr))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void stop(StopRequest request, StreamObserver<StopReply> responseObserver) {
        logger.info("simulator: " + request.getSimulatorId() + " session : " + request.getSessionId() + " stop request");

        Simulator simulator = simulatorMap.get(request.getSimulatorId());
        Process process = simulator.getProcess();
        process.destroy();
        try {
            process.waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("couldn't stop process for simulator")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        StopReply reply = StopReply.newBuilder()
                .setSimulatorId(request.getSimulatorId())
                .setSessionId(request.getSessionId())
                .setSuccess(true)
                .build();

        clientMap.remove(request.getSessionId());
        simulatorMap.remove(simulator.getId());

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
