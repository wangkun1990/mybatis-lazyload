package com.squareup.okhttp3.example.recipes;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public final class PreemptiveAuth {
    private final OkHttpClient client;

    public PreemptiveAuth() {
        client = new OkHttpClient.Builder()
                .addInterceptor(
                        new BasicAuthInterceptor("publicobject.com", "jesse", "password1"))
                .build();
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://publicobject.com/secrets/hellosecret.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    public static void main(String... args) throws Exception {
        new PreemptiveAuth().run();
    }

    static final class BasicAuthInterceptor implements Interceptor {
        private final String credentials;
        private final String host;

        BasicAuthInterceptor(String host, String username, String password) {
            this.credentials = Credentials.basic(username, password);
            this.host = host;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (request.url().host().equals(host)) {
                request = request.newBuilder()
                        .header("Authorization", credentials)
                        .build();
            }
            return chain.proceed(request);
        }
    }
}
