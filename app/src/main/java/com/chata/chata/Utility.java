package com.chata.chata;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Utility {
    private static Utility instance;

    public static Utility getObject() {
        if (instance == null)
            instance = new Utility();

        return instance;
    }

    private Utility() {
    }

    public int Post(String url, final String body) {
        AtomicInteger returnCode = new AtomicInteger();

        Thread thread = new Thread(() -> {
            try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setRequestProperty("Content-Type", "application/text");
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            try (OutputStream os = connection.getOutputStream()) {
                os.write(body.getBytes("UTF-8"));
                os.flush();
            }

            returnCode.set(connection.getResponseCode());
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
            returnCode.set(500);
        }
        });

        thread.start();
        try {
            thread.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return returnCode.get();
    }

    public boolean isGetResponseSuccessful(String response) {
        return !(response == null || response.startsWith("ERROR"));
    }

    public String Get(String url) {
        String[] responseBody = new String[1];

        Thread thread = new Thread(() -> {
            try {
                URL u = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();
                connection.setRequestMethod("GET");

                InputStream response = connection.getInputStream();
                try (Scanner scanner = new Scanner(response).useDelimiter("\\A")) {
                    responseBody[0] = scanner.hasNext() ? scanner.next() : "";
                }
            } catch (IOException e) {
                e.printStackTrace();
                responseBody[0] = "ERROR: " + e.getMessage();
            }
        });

        thread.start();
        try {
            thread.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return responseBody[0];
    }
}
