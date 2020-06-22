package com.tsystems.optimos.jrcpwrapper;

import com.google.protobuf.ByteString;
import com.google.protobuf.Timestamp;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class JCOPSimulatorImpl extends JCOPSimulatorGrpc.JCOPSimulatorImplBase {
    private static final Logger logger = (LogManager.getLogger("com.tsystems.optimos.jrcpwrapper"));

    private static JsonFormat jsonFormat = new JsonFormat();
    private static Map<String, Simulator> simulatorMap = new HashMap<>();
    private static Map<Version, File> fileMapping = new HashMap<>();
    private JRCPWrapper jrcpWrapper;


    JCOPSimulatorImpl() throws FileNotFoundException {
        super();

        jrcpWrapper = new JRCPWrapper();
        File file = new File("JCOP_Simulator-JCOP4.7-R1.00.4-RC17/win32/jcop.exe");
        if (!file.exists()) {
            throw new FileNotFoundException("file with path '" + file.getAbsolutePath() + "' not found");
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

            // wait for simulator port to be reachable
            if (!jrcpWrapper.connect(simulator).get()) {
                Status status = Status.newBuilder()
                        .setCode(Code.INTERNAL.getNumber())
                        .setMessage("couldn't connect to simulator")
                        .build();

                responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            }

            simulatorMap.put(simulatorId, simulator);
            simulator.setLastInteraction(Instant.now());

            // connect to card
            byte[] atr = jrcpWrapper.waitForCard(simulator.getId()).get();

            if (atr != null) {
                logger.info("opened session to simulator " + simulator.getId() + " on port " + simulator.getPort() );
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
                    .build();

            responseObserver.onNext(reply);
            responseObserver.onCompleted();
        } catch (InterruptedException | ExecutionException | IOException e) {
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
        logger.info("Simulator: " + request.getSimulatorId() + " Capdu -> " + HexUtil.byteArrayToHexString(request.getApdu().toByteArray()));

        Simulator simulator = simulatorMap.get(request.getSimulatorId());

        if (simulator == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("no simulator found for " + request.getSimulatorId())
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        byte[] cmd = request.getApdu().toByteArray();
        byte[] resp = new byte[0];
        try {
            resp = jrcpWrapper.sendData(request.getSimulatorId(), cmd).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            Status status = Status.newBuilder()
                    .setCode(Code.INTERNAL.getNumber())
                    .setMessage("unknown")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
        }

        simulator.setLastInteraction(Instant.now());

        if (resp == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNKNOWN.getNumber())
                    .setMessage("received null response for sendData")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        simulator.setLastCommand(cmd);
        simulator.setLastResp(resp);

        logger.info("Simulator: " + request.getSimulatorId() + " Rapdu <- " + HexUtil.byteArrayToHexString(resp));

        Rapdu rapdu = Rapdu.newBuilder()
                .setSimulatorId(request.getSimulatorId())
                .setApdu(ByteString.copyFrom(resp))
                .build();

        responseObserver.onNext(rapdu);
        responseObserver.onCompleted();
    }

    @Override
    public void reset(ResetRequest request, StreamObserver<ResetReply> responseObserver) {
        logger.info("Simulator: " + request.getSimulatorId() + " request reset " + (request.getWarm() ? "warm" : "cold"));
        Simulator simulator = simulatorMap.get(request.getSimulatorId());

        if (simulator == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("no simulator found for " + request.getSimulatorId())
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        byte[] atr;

        try {
            if (request.getWarm()) {
                atr = jrcpWrapper.warmReset(request.getSimulatorId()).get();
            } else {
                atr = jrcpWrapper.coldReset(request.getSimulatorId()).get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();

            Status status = Status.newBuilder()
                    .setCode(Code.INTERNAL.getNumber())
                    .setMessage("unknown")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        simulator.setLastInteraction(Instant.now());
        simulator.setLastCommand(null);
        simulator.setLastResp(null);

        logger.info("Simulator: " + request.getSimulatorId() + " reset success: " + HexUtil.byteArrayToHexString(atr));

        ResetReply reply = ResetReply.newBuilder()
                .setSimulatorId(request.getSimulatorId())
                .setAtr(ByteString.copyFrom(atr))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void stop(StopRequest request, StreamObserver<StopReply> responseObserver) {
        logger.info("Simulator: " + request.getSimulatorId() + " stop request");

        Simulator simulator = simulatorMap.get(request.getSimulatorId());
        if (simulator == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("no simulator found for " + request.getSimulatorId())
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        if (!simulator.stopInstance()) {
            simulator.setLastInteraction(Instant.now());
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("couldn't stop process for simulator")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        StopReply reply = StopReply.newBuilder()
                .setSimulatorId(simulator.getId())
                .setSuccess(true)
                .build();

        simulatorMap.remove(simulator.getId());

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

    @Override
    public void getStatus(GetStatusRequest request, StreamObserver<GetStatusReply> responseObserver) {
        logger.info("Simulator: " + request.getSimulatorId() + " received getStatus request");

        Simulator simulator = simulatorMap.get(request.getSimulatorId());
        if (simulator == null) {
            Status status = Status.newBuilder()
                    .setCode(Code.UNAVAILABLE.getNumber())
                    .setMessage("no simulator found for " + request.getSimulatorId())
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            return;
        }

        GetStatusReply reply = GetStatusReply.newBuilder()
                .setSimulatorId(simulator.getId())
                .setCreated(Timestamp.newBuilder().setSeconds(simulator.getCreated().getEpochSecond()))
                .setLastInteraction(Timestamp.newBuilder().setSeconds(simulator.getLastInteraction().getEpochSecond()).build())
                .setLastCommand(ByteString.copyFrom(simulator.getLastCommand()))
                .build();

        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }
}
