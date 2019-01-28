package com.squareup.okhttp3.example.recipes;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.tls.HandshakeCertificates;
import okhttp3.tls.HeldCertificate;


import java.net.InetAddress;

/**
 * Create an HTTPS server with a self-signed certificate that OkHttp trusts.
 */
public class HttpsServer {
    public void run() throws Exception {
        String localhost = InetAddress.getByName("localhost").getCanonicalHostName();
        HeldCertificate localhostCertificate = new HeldCertificate.Builder()
                .addSubjectAlternativeName(localhost)
                .build();

        HandshakeCertificates serverCertificates = new HandshakeCertificates.Builder()
                .heldCertificate(localhostCertificate)
                .build();
        MockWebServer server = new MockWebServer();
        server.useHttps(serverCertificates.sslSocketFactory(), false);
        server.enqueue(new MockResponse());

        HandshakeCertificates clientCertificates = new HandshakeCertificates.Builder()
                .addTrustedCertificate(localhostCertificate.certificate())
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .sslSocketFactory(clientCertificates.sslSocketFactory(), clientCertificates.trustManager())
                .build();

        Call call = client.newCall(new Request.Builder()
                .url(server.url("/"))
                .build());
        Response response = call.execute();
        System.out.println(response.handshake().tlsVersion());
    }

    public static void main(String... args) throws Exception {
        new HttpsServer().run();
    }
}
