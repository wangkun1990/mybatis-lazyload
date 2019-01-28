package com.mybatis.util;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class HttpClientUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientUtil.class);

    private HttpClientUtil() {

    }


    public static String get(String url) throws IOException {
        String body;
        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
        //默认20
        //clientConnectionManager.setMaxTotal(20);
        //默认2
        //clientConnectionManager.setDefaultMaxPerRoute(5);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).build();

        HttpGet httpGet = new HttpGet(url);
        /**
         * setConnectTimeout：设置连接超时时间，单位毫秒。
         *
         * setConnectionRequestTimeout：设置从connect Manager(连接池)获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。
         *
         * setSocketTimeout：请求获取数据的超时时间(即响应时间)，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
         */
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000).setConnectTimeout(2000).setSocketTimeout(3000).build();
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        //The underlying HTTP connection is still held by the response object to allow the response content to be streamed directly from the network socket.
        // In order to ensure correct deallocation of system resources the user MUST call CloseableHttpResponse#close() from a finally clause.
        // Please note that if response content is not fully consumed the underlying connection cannot be safely re-used and will be shut down and discarded by the connection manager.
        try {
            HttpEntity entity = response.getEntity();
            body = EntityUtils.toString(entity, "utf-8");
            // do something useful with the response body and ensure it is fully consumed
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
        return body;
    }



    public static String post(String url, Map<String, String> param) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost post = new HttpPost(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000).setConnectTimeout(20000).setSocketTimeout(100000).build();
        post.setConfig(requestConfig);
        if (MapUtils.isNotEmpty(param)) {
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : param.entrySet()) {
                // 给参数赋值
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(formparams, Consts.UTF_8);
            post.setEntity(urlEncodedFormEntity);
        }

        //执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(post);
        //获取结果实体
        HttpEntity entity = response.getEntity();
        String body = StringUtils.EMPTY;
        if (entity != null) {
            //按指定编码转换结果实体为String类型
            body = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        //释放链接
        response.close();
        return body;
    }


    public static boolean isSuccessful(int code) {
        return code >= 200 && code < 300;
    }

    public static HttpResponse getUrlInputStream(String url) throws IOException {
        PoolingHttpClientConnectionManager clientConnectionManager = new PoolingHttpClientConnectionManager();
        clientConnectionManager.setMaxTotal(100);
        clientConnectionManager.setDefaultMaxPerRoute(50);
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(clientConnectionManager).build();
        HttpGet httpGet = new HttpGet(url);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(2000).setConnectTimeout(2000).setSocketTimeout(3000).build();
        httpGet.setConfig(requestConfig);
        return httpClient.execute(httpGet);
    }

    public static void consume(HttpResponse response) throws IOException {
        if (response != null) {
            HttpEntity httpEntity = response.getEntity();
            EntityUtils.consume(httpEntity);
            ((CloseableHttpResponse) response).close();
        }
    }
}
