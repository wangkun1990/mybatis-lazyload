package com.squareup.okhttp3.example.recipes;

import okhttp3.CertificatePinner;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.security.cert.Certificate;
import java.util.Collections;
import java.util.Set;

public final class CheckHandshake {
    /**
     * Rejects otherwise-trusted certificates.
     */
    private static final Interceptor CHECK_HANDSHAKE_INTERCEPTOR = new Interceptor() {
        Set<String> blacklist = Collections.singleton(
                "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=");

        @Override
        public Response intercept(Chain chain) throws IOException {
            for (Certificate certificate : chain.connection().handshake().peerCertificates()) {
                String pin = CertificatePinner.pin(certificate);
                if (blacklist.contains(pin)) {
                    throw new IOException("Blacklisted peer certificate: " + pin);
                }
            }
            return chain.proceed(chain.request());
        }
    };

    private final OkHttpClient client = new OkHttpClient.Builder()
            .addNetworkInterceptor(CHECK_HANDSHAKE_INTERCEPTOR)
            .build();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://publicobject.com/helloworld.txt")
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());
        }
    }

    public static void main(String... args) throws Exception {
        new CheckHandshake().run();
    }
}
