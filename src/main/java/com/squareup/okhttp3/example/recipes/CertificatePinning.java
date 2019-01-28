package com.squareup.okhttp3.example.recipes;

import okhttp3.CertificatePinner;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.security.cert.Certificate;

public final class CertificatePinning {
    private final OkHttpClient client;

    public CertificatePinning() {
        client = new OkHttpClient.Builder()
                .certificatePinner(
                        new CertificatePinner.Builder()
                                .add("publicobject.com", "sha256/afwiKY3RxoMmLkuRW1l7QsPZTJPwDS2pdDROQjXw8ig=")
                                .build())
                .build();
    }

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("https://publicobject.com/robots.txt")
                .build();

        /*try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            for (Certificate certificate : response.handshake().peerCertificates()) {
                System.out.println(CertificatePinner.pin(certificate));
            }
        }*/
    }

    public static void main(String... args) throws Exception {
        new CertificatePinning().run();
    }
}
