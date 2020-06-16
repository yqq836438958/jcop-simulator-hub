package com.tsystems.optimos.jrcpwrapper;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Simulator {
    private String id;
    private int port;
    private File file;
    private Process process;
    private Instant created;
    private byte[] lastCommand;
    private byte[] lastResp;
    private Instant lastInteraction;

    public Simulator(String id, int port, File file) {
        this.id = id;
        this.port = port;
        this.file = file;
        this.created = Instant.now();
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

    public boolean stopInstance() {
        process.destroy();
        try {
            process.waitFor();
            return true;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return false;
        }
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

    public byte[] getLastCommand() {
        return lastCommand;
    }

    public void setLastCommand(byte[] lastCommand) {
        this.lastCommand = lastCommand;
    }

    public Instant getLastInteraction() {
        return lastInteraction;
    }

    public void setLastInteraction(Instant lastInteraction) {
        this.lastInteraction = lastInteraction;
    }

    public byte[] getLastResp() {
        return lastResp;
    }

    public void setLastResp(byte[] lastResp) {
        this.lastResp = lastResp;
    }

    public Instant getCreated() {
        return created;
    }
}
