package com.mybatis.util;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.collections4.MapUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public final class OkHttp3Util {

    private OkHttp3Util() {

    }

    /**
     * OkHttpClient线程安全
     */
    private static OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(2, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS).build();

    /**
     * get请求
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(response.message());
        }
    }

    /**
     *
     * @param url
     * @param header
     * @return
     * @throws IOException
     */
    public static String get(String url, Map<String, String> header) throws IOException {
        Request.Builder requestBuilder = (new Request.Builder()).url(url);
        if (MapUtils.isNotEmpty(header)) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }

        Response response = client.newCall(requestBuilder.build()).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(response.message());
        }
    }

    /**
     * json格式入参
     *
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(response.message());
        }
    }


    /**
     * 表单格式入参
     *
     * @param url
     * @param param key1=value1&key2=value2...
     * @return
     * @throws IOException
     */
    public static String postFormUrlencoded(String url, String param) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), param);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(response.message());
        }
    }

    public static String post(String url, String param) throws IOException {
        RequestBody body = RequestBody.create(null, param);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(response.message());
        }
    }


    /**
     * 根据url获取流
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static Response getUrlInputStream(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        return client.newCall(request).execute();
    }

    public static void closeResponse(Response response) {
        if (response != null) {
            response.close();
        }
    }

    public static String postFormUrlencodedWithHeader(String url, String param, Map<String, String> header) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), param);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url)
                .post(body);
        if (MapUtils.isNotEmpty(header)) {
            for (Map.Entry<String, String> entry : header.entrySet()) {
                requestBuilder.header(entry.getKey(), entry.getValue());
            }
        }
        Response response = client.newCall(requestBuilder.build()).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new RuntimeException(response.message());
        }
    }
}
