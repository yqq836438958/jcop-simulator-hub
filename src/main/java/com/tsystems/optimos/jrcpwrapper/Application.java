package com.tsystems.optimos.jrcpwrapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Application {
    public static final Logger logger = (LogManager.getLogger("com.tsystems.optimos.jrcpwrapper"));


    public static void main(String[] args) {
        final JCOPSimulatorServer server = new JCOPSimulatorServer();
        try {
            server.start();
            server.blockUntilShutdown();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}

// sub MTY, NAD, header, payload
/*
    ControllerConfigurationRequestMessage controllerConfigurationRequestListMessage = new ControllerConfigurationRequestMessage((short) 1, (byte) 0xFF, null, null);
    ControllerConfigurationResponseMessage controllerConfigurationResponseMessage = jrcPoverTCPChannelClient.request(controllerConfigurationRequestListMessage);
    System.out.println("Controller Configuration List Resp: " + HexUtil.byteArrayToHexString(controllerConfigurationResponseMessage.getPayload()));
    System.out.println("Connected Terminal Data: " + HexUtil.byteArrayToHexString(controllerConfigurationResponseMessage.getTerminalData()));


    ControllerConfigurationRequestMessage controllerConfigurationRequestConnectMessage = new ControllerConfigurationRequestMessage((short) 4, (byte) 0xFF, null, Arrays.copyOfRange(controllerConfigurationResponseMessage.getTerminalData(), 2, controllerConfigurationResponseMessage.getTerminalData().length));
    controllerConfigurationResponseMessage = jrcPoverTCPChannelClient.request(controllerConfigurationRequestConnectMessage);
    System.out.println("Controller Configuration connect to terminal Resp: " + HexUtil.byteArrayToHexString(controllerConfigurationResponseMessage.getPayload()));
    System.out.println("Controller Configuration connected NAD: " + controllerConfigurationResponseMessage.getConnectedNAD());

    for (int i = 0; i < 10; i++) {
        TerminalInformationRequestMessage terminalInformationRequestMessage = new TerminalInformationRequestMessage((byte) i, null, null);
        TerminalInformationResponseMessage terminalInformationResponseMessage = jrcPoverTCPChannelClient.request(terminalInformationRequestMessage);
        System.out.println("Terminal Information " + i + ": " + terminalInformationResponseMessage.getInformation());
    }

    ServerStatusRequestMessage  serverStatusRequestMessage = new ServerStatusRequestMessage((byte) 0xFF, null, null);
    ServerStatusResponseMessage serverStatusResponseMessage = jrcPoverTCPChannelClient.request(serverStatusRequestMessage);
    System.out.println("Server Status Resp: " +serverStatusResponseMessage.getStatus().data + " Description: " + serverStatusResponseMessage.getDescription());
 */

