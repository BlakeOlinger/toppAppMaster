package com.practice;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

class InternetCheck {
    static boolean isConnected() {
        try {
            var webSite = "www.google.com";
            var port = 80;
            var timeOutMS = 5000;
            var socket = new Socket();
            socket.connect(new InetSocketAddress(webSite, port),
                    timeOutMS);

            return true;
        } catch (IOException ignore) {
            return false;
        }
    }
}
