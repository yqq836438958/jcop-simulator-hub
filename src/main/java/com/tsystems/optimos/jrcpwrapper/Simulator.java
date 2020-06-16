package com.tsystems.optimos.jrcpwrapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Simulator {
    private String id;
    private int port;
    private File file;
    private Process process;

    public Simulator(String id, int port, File file) {
        this.id = id;
        this.port = port;
        this.file = file;
    }

    public void startInstance(SimulatorProcessListener listener) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/c " + file.getAbsolutePath() + " -portc=" + port);
        this.process = processBuilder.start();
        CompletableFuture<Process> future = process.onExit();
        future.thenRun(() -> {
            Process p;
            try {
                p = future.get();
                listener.onProcessExit(p.exitValue());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

    public String getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    public File getFile() {
        return file;
    }

    public Process getProcess() {
        return process;
    }
}
