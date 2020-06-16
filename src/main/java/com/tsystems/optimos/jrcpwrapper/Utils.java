package com.tsystems.optimos.jrcpwrapper;

import java.io.IOException;
import java.net.ServerSocket;

public class Utils {

    public static int findOpenPort() throws IOException {
        ServerSocket ss = new ServerSocket(0);
        ss.setReuseAddress(true);
        ss.close();
        return ss.getLocalPort();
    }
}
