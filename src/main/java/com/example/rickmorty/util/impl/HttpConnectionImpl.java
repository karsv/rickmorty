package com.example.rickmorty.util.impl;

import com.example.rickmorty.exceptions.HttpDataException;
import com.example.rickmorty.util.HttpConnection;
import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

@Component
public class HttpConnectionImpl implements HttpConnection {

    @Override
    public String sendGet(String url) {
        String result = "";
        try (CloseableHttpClient httpClient = HttpClients.createDefault();) {
            HttpGet request = new HttpGet(url);
            try (CloseableHttpResponse response = httpClient.execute(request);) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    result = EntityUtils.toString(entity);
                }
            } catch (IOException e) {
                throw new HttpDataException("Something wrong while getting response", e);
            }
        } catch (IOException e) {
            throw new HttpDataException("Something wrong while http connection", e);
        }
        return result;
    }
}
