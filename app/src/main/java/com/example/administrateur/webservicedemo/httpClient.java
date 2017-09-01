package com.example.administrateur.webservicedemo;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class httpClient extends Thread {

    private String adr;
    private String response;

    public String getAdr() {
        return adr;
    }
    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getResponse() {
        return response;
    }

    @Override
    public void run() {
        URL url;
        HttpURLConnection cnt = null;
        try {
            url = new URL(adr);
            cnt = (HttpURLConnection) url.openConnection();
            InputStream stream = cnt.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            response = "";
            String line = "";
            while ((line = reader.readLine()) != null) {
                response+= line;
            }
            stream.close();
            reader.close();
        } catch (Exception ex) {
            response += "\nErreur : " + ex.getMessage();
        }
        finally {
            cnt.disconnect();
        }
    }
}
