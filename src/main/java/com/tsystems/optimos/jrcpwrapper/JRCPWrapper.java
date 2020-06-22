package com.tsystems.optimos.jrcpwrapper;

import com.nxp.id.jrcp.HexUtil;
import com.nxp.id.jrcp.client.JRCPoverTCPChannelClient;
import com.nxp.id.jrcp.messages.cold_reset.ColdResetRequestMessage;
import com.nxp.id.jrcp.messages.cold_reset.ColdResetResponseMessage;
import com.nxp.id.jrcp.messages.send_data.SendDataRequestMessage;
import com.nxp.id.jrcp.messages.send_data.SendDataResponseMessage;
import com.nxp.id.jrcp.messages.wait_for_card.WaitForCardRequestMessage;
import com.nxp.id.jrcp.messages.wait_for_card.WaitForCardResponseMessage;
import com.nxp.id.jrcp.messages.warm_reset.WarmResetRequestMessage;
import com.nxp.id.jrcp.messages.warm_reset.WarmResetResponseMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

class JRCPWrapper {
    private static final Logger logger = (LogManager.getLogger("com.tsystems.optimos.jrcpwrapper"));
    private HashMap<String, JRCPoverTCPChannelClient> clientMap = new HashMap<>();

    JRCPWrapper() {}

    Future<Boolean> connect (Simulator simulator) {
        CompletableFuture<Boolean> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            boolean scanning=true;
            while(scanning) {
                Socket socket = new Socket();

                try {
                    socket.connect(new InetSocketAddress("localhost", simulator.getPort()));
                    JRCPoverTCPChannelClient jrcPoverTCPChannelClient = new JRCPoverTCPChannelClient(socket, eventResponseMessage -> {
                        logger.info("STATUS: " + eventResponseMessage.getStatus().getData());
                        logger.info("DATA: " + HexUtil.byteArrayToHexString(eventResponseMessage.getEventData()));
                        return null;
                    });
                    logger.info("connected to Simulator on localhost" + ":" + simulator.getPort());
                    scanning = false;

                    // map simulator id to socket
                    clientMap.put(simulator.getId(), jrcPoverTCPChannelClient);

                    // finish
                    completableFuture.complete(true);

                } catch (IOException e) {
                    e.printStackTrace();
                    logger.error("Connect failed, waiting and trying again");
                    try {
                        Thread.sleep(2000);//2 seconds
                    }
                    catch(InterruptedException ie) {
                        ie.printStackTrace();
                        completableFuture.complete(false);
                    }
                }
            }
        }).start();
        return completableFuture;
    }

    Future<byte[]> waitForCard(String simulatorId) {
        CompletableFuture<byte[]> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            // UART INTERFACE = NAD 6
            //1 == nad, 2 = header, 3 = payload
            JRCPoverTCPChannelClient client = clientMap.get(simulatorId);
            if (client == null) {
                //TODO
                completableFuture.complete( null);
                return;
            }
            try {
                WaitForCardRequestMessage waitForCard = new WaitForCardRequestMessage((byte) 0x06, null, new byte[]{(byte) 0xFF, 0x00, 0x00, 0x00});
                WaitForCardResponseMessage resp = client.request(waitForCard);
                logger.info("Wait for card resp: " + HexUtil.byteArrayToHexString(resp.getATR()));
                logger.info("Ready to communicate with card");
                completableFuture.complete(resp.getATR());
            } catch (IOException e) {
                e.printStackTrace();
                completableFuture.complete( null);
            }
        }).start();
        return completableFuture;
    }

    Future<byte[]> coldReset(String simulatorId) {
        CompletableFuture<byte[]> completableFuture = new CompletableFuture<>();

        new Thread(() -> {

            JRCPoverTCPChannelClient client = clientMap.get(simulatorId);
            if (client == null) {
                //TODO
                completableFuture.complete(null);
                return;
            }

            try {
                ColdResetRequestMessage coldResetRequestMessage = new ColdResetRequestMessage((byte) 0x06, null, new byte[]{0x00, 0x00, 0x01, 0x00});
                ColdResetResponseMessage coldResetResponseMessage = client.request(coldResetRequestMessage);
                logger.info("Cold Reset resp: " + HexUtil.byteArrayToHexString(coldResetResponseMessage.getATR()));
                completableFuture.complete(coldResetResponseMessage.getATR());
            } catch (IOException e) {
                e.printStackTrace();
                completableFuture.complete(null);
            }
        }).start();

        return completableFuture;
    }

    Future<byte[]> warmReset(String simulatorId) {
        CompletableFuture<byte[]> completableFuture = new CompletableFuture<>();

        new Thread(() -> {

            JRCPoverTCPChannelClient client = clientMap.get(simulatorId);
            if (client == null) {
                //TODO
                completableFuture.complete( null);
                return;
            }

            try {
                WarmResetRequestMessage warmResetRequestMessage = new WarmResetRequestMessage((byte) 0x06, null, new byte[]{0x00, 0x00, 0x01, 0x00});
                WarmResetResponseMessage warmResetResponseMessage = client.request(warmResetRequestMessage);
                logger.info("Warm Reset resp: " + HexUtil.byteArrayToHexString(warmResetResponseMessage.getATR()));
                completableFuture.complete(warmResetResponseMessage.getATR());
            } catch (IOException e) {
                e.printStackTrace();
                completableFuture.complete( null);
            }
        }).start();

        return completableFuture;
    }

    Future<byte[]> sendData(String simulatorId, byte[] data) {
        CompletableFuture<byte[]> completableFuture = new CompletableFuture<>();

        new Thread(() -> {
            JRCPoverTCPChannelClient client = clientMap.get(simulatorId);
            if (client == null) {
                //TODO
                completableFuture.complete( null);
                return;
            }

            SendDataRequestMessage sendDataRequestMessage;
            try {
                sendDataRequestMessage = new SendDataRequestMessage((byte) 0x06, null, data);
                SendDataResponseMessage sendDataResponseMessage = client.request(sendDataRequestMessage);
                completableFuture.complete(sendDataResponseMessage.getAPDU());
            } catch (IOException e) {
                e.printStackTrace();
                completableFuture.complete( null);
            }
        }).start();

        return completableFuture;
    }
}
