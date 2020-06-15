package ru.alfabank.alfabattle.task1;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
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
//            RestTemplate restTemplate = new RestTemplate();
            RestTemplate restTemplate = getRestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service/atms", String.class);
            log.info(response.toString());
        } catch (Exception e) {
            log.error("Error", e);
        }

        return "go to hell";
    }


    private RestTemplate getRestTemplate() throws Exception {
        char[] password = "fuckoff".toCharArray();

        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore("classpath:apidevelopers.jks", password), password)
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()).build();

        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();
        return builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }

     private KeyStore keyStore(String file, char[] password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        File key = ResourceUtils.getFile(file);
        try (InputStream in = new FileInputStream(key)) {
            keyStore.load(in, password);
        }
        return keyStore;
    }


//    private RestTemplate restTemplate() {
//        KeyStore clientStore = KeyStore.getInstance("PKCS12");
//        clientStore.load(new FileInputStream("/path/to/certfile"),
//                "certpassword".toCharArray());
//
//        SSLContextBuilder sslContextBuilder = new SSLContextBuilder();
//        sslContextBuilder.useProtocol("TLS");
//        sslContextBuilder.loadKeyMaterial(clientStore, "certpassword".toCharArray());
//        sslContextBuilder.loadTrustMaterial(new TrustSelfSignedStrategy());
//     
//        SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContextBuilder.build());
//        CloseableHttpClient httpClient = HttpClients.custom()
//                .setSSLSocketFactory(sslConnectionSocketFactory)
//                .build();
//        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
//        requestFactory.setConnectTimeout(10000); // 10 seconds
//        requestFactory.setReadTimeout(10000); // 10 seconds
//        return new RestTemplate(requestFactory);
//    }

}
