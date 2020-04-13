package com.example.rickmorty.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;

public interface HttpConnection {
    String sendGet(String url) throws MalformedURLException, ProtocolException, IOException;
}
