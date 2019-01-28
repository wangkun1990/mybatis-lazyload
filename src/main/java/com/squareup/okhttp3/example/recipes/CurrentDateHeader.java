package com.squareup.okhttp3.example.recipes;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.Date;

public final class CurrentDateHeader {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new CurrentDateInterceptor())
            .build();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            System.out.println(response.request().header("Date"));
        }
    }

    static class CurrentDateInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Headers newHeaders = request.headers()
                    .newBuilder()
                    .add("Date", new Date())
                    .build();
            Request newRequest = request.newBuilder()
                    .headers(newHeaders)
                    .build();
            return chain.proceed(newRequest);
        }
    }

    public static void main(String... args) throws Exception {
        new CurrentDateHeader().run();
    }
}
