package ru.alfabank.alfabattle.task1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.util.stream.Collectors;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController("/")
public class AtmFindController {

    @Autowired
    private RestTemplateBuilder builder;


    @GetMapping
    public String getNearestAtm() {
        try {
            test1();
        } catch (Exception e) {
            log.error("Error", e);
        }

        return "go to hell";
    }


    private void test1() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        char[] password = "password".toCharArray();
        keyStore.load(new FileInputStream(ResourceUtils.getFile("classpath:alfabattle.jks")), password);

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, "password".toCharArray())
                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        HttpUriRequest request = RequestBuilder.get()
                .setUri(URI.create("https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service/atms"))
                .setHeader("accept", "application/json")
                .setHeader("x-ibm-client-id", "d79fc084-69ec-4d8e-9dfd-daf8b5d04ef9")
                .build();
        CloseableHttpResponse response = httpClient.execute(request);
        log.info(response.toString());
        String text = new BufferedReader(
                new InputStreamReader(response.getEntity().getContent(), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        log.info(text);

//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
//        requestFactory.setHttpClient(httpClient);
//
//        ResponseEntity<String> response = new RestTemplate(requestFactory).exchange(
//                URI.create("https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service/atms"),
//                HttpMethod.GET, null, String.class);
//        log.info(response.toString());
    }

}
