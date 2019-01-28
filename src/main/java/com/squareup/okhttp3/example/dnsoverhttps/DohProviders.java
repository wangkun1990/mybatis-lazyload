

package com.squareup.okhttp3.example.dnsoverhttps;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.dnsoverhttps.DnsOverHttps;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Temporary registry of known DNS over HTTPS providers.
 * <p>
 * https://github.com/curl/curl/wiki/DNS-over-HTTPS
 */
public class DohProviders {
    static DnsOverHttps buildGoogle(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://dns.google.com/experimental"))
                .bootstrapDnsHosts(getByIp("216.58.204.78"), getByIp("2a00:1450:4009:814:0:0:0:200e"))
                .build();
    }

    static DnsOverHttps buildGooglePost(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://dns.google.com/experimental"))
                .bootstrapDnsHosts(getByIp("216.58.204.78"), getByIp("2a00:1450:4009:814:0:0:0:200e"))
                .post(true)
                .build();
    }

    static DnsOverHttps buildCloudflareIp(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://1.1.1.1/dns-query"))
                .includeIPv6(false)
                .build();
    }

    static DnsOverHttps buildCloudflare(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://cloudflare-dns.com/dns-query"))
                .bootstrapDnsHosts(getByIp("1.1.1.1"))
                .includeIPv6(false)
                .build();
    }

    static DnsOverHttps buildCloudflarePost(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://cloudflare-dns.com/dns-query"))
                .bootstrapDnsHosts(getByIp("104.16.111.25"), getByIp("104.16.112.25"),
                        getByIp("2400:cb00:2048:1:0:0:6810:7019"), getByIp("2400:cb00:2048:1:0:0:6810:6f19"))
                .includeIPv6(false)
                .post(true)
                .build();
    }

    static DnsOverHttps buildCleanBrowsing(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://doh.cleanbrowsing.org/doh/family-filter/"))
                .includeIPv6(false)
                .build();
    }

    static DnsOverHttps buildChantra(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://dns.dnsoverhttps.net/dns-query"))
                .includeIPv6(false)
                .build();
    }

    static DnsOverHttps buildCryptoSx(OkHttpClient bootstrapClient) {
        return new DnsOverHttps.Builder().client(bootstrapClient)
                .url(HttpUrl.get("https://doh.crypto.sx/dns-query"))
                .includeIPv6(false)
                .build();
    }

    public static List<DnsOverHttps> providers(OkHttpClient client, boolean http2Only,
                                               boolean workingOnly, boolean getOnly) {

        List<DnsOverHttps> result = new ArrayList<>();

        result.add(buildGoogle(client));
        if (!getOnly) {
            result.add(buildGooglePost(client));
        }
        result.add(buildCloudflare(client));
        result.add(buildCloudflareIp(client));
        if (!getOnly) {
            result.add(buildCloudflarePost(client));
        }
        if (!workingOnly) {
            //result.add(buildCleanBrowsing(client)); // timeouts
            result.add(buildCryptoSx(client)); // 521 - server down
        }
        result.add(buildChantra(client));

        return result;
    }

    private static InetAddress getByIp(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            // unlikely
            throw new RuntimeException(e);
        }
    }
}
