package com.squareup.okhttp3.example.recipes;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.TimeUnit;

public final class ConfigureTimeouts {
    private final OkHttpClient client;

    public ConfigureTimeouts() throws Exception {
        client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://httpbin.org/delay/2") // This URL is served with a 2 second delay.
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println("Response completed: " + response);
        }
    }

    public static void main(String... args) throws Exception {
        new ConfigureTimeouts().run();
    }
}
