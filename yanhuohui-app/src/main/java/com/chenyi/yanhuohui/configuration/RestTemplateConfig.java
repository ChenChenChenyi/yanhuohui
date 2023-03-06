package com.chenyi.yanhuohui.configuration;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * @author 陈义
 */
@Configuration
public class RestTemplateConfig {
    /**
     * 使用ssl对证书进行验证，需要将证书导出到jdk管理仓库，
     * 命令：keytool -import -v -trustcacerts -alias mytest -file "D:/tmp/mytest.cer"  -keystore "C:/Program Files/Java/jdk1.8.0_131/jre/lib/security/cacerts"
     */
    @Bean("restTemplate")
    @Primary
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyManagementException {
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new
                HttpComponentsClientHttpRequestFactory();
        //获取连接池连接的超时时间（毫秒）
        httpRequestFactory.setConnectionRequestTimeout(6 * 1000);
        //连接上服务器(握手成功)的时间（毫秒）
        httpRequestFactory.setConnectTimeout(6 * 1000);
        //返回数据时间（毫秒）
        httpRequestFactory.setReadTimeout(60 * 1000);
        httpRequestFactory.setHttpClient(httpClient());
        RestTemplate restTemplate = new RestTemplate(httpRequestFactory);
        return restTemplate;
    }

    /**
     * 绕过ssl对证书进行验证，无需将证书导出到jdk管理仓库
     */
    @Bean("restTemplateIgnoreSSL")
    public RestTemplate restTemplateIgnoreSSL() throws NoSuchAlgorithmException, KeyManagementException {
        IgnoreSSLRequestFactory requestFactory = new IgnoreSSLRequestFactory(httpClient());
        requestFactory.setConnectTimeout(60000); //连接上服务器(握手成功)的时间
        requestFactory.setReadTimeout(60000); //返回数据时间
        RestTemplate restTemplate = new RestTemplate(requestFactory);
        return restTemplate;
    }

    @Bean
    public HttpClient httpClient() throws KeyManagementException, NoSuchAlgorithmException {
        SSLContextBuilder contextBuilder = new SSLContextBuilder();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(contextBuilder.build(), NoopHostnameVerifier.INSTANCE);
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", new PlainConnectionSocketFactory())
                .register("https", socketFactory).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(200);
        connectionManager.setDefaultMaxPerRoute(100);
        CloseableHttpClient httpClient = HttpClientBuilder.create().setConnectionManager(connectionManager).build();
        return httpClient;
    }
}
