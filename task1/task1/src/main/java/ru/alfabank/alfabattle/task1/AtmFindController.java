package ru.alfabank.alfabattle.task1;

import java.io.FileInputStream;
import java.net.URI;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;
import ru.alfabank.alfabattle.task1.modelalfa.ATMDetails;
import ru.alfabank.alfabattle.task1.modelalfa.ATMStatus;
import ru.alfabank.alfabattle.task1.modelalfa.JSONResponseBankATMDetails;
import ru.alfabank.alfabattle.task1.modelalfa.JSONResponseBankATMStatus;
import ru.alfabank.alfabattle.task1.modeltask.AtmResponse;
import ru.alfabank.alfabattle.task1.modeltask.AtmResponseConverter;


@Slf4j
@RestController("/")
public class AtmFindController {

    private static final String API_ROOT = "https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service";
    private static final String ATMS_API_PATH = API_ROOT + "/atms";
    private static final String ATMS_STATUS_API_PATH = API_ROOT + "/atms/status";


    @Autowired
    private AtmResponseConverter converter;


    private RestTemplate restTemplate;
    private HttpEntity<?> httpEntity;

    private final  Map<Integer, ATMDetails> atmById = new HashMap<>();


    @PostConstruct
    void init() throws Exception {
        restTemplate = getRestTemplate();
        httpEntity = getHttpEntity();

        initAtms();
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<AtmResponse> getById(@PathVariable("id") int id) {
        ATMDetails atm = atmById.get(id);
        return atm != null
                ? ResponseEntity.ok(converter.convert(atm))
                : ResponseEntity.notFound().build();
    }


    @GetMapping(value = "/nearest")
    public ResponseEntity<ATMDetails> getNearestAtm(String latitude, String longitude) {
        // TODO
        return null;
    }


    private static RestTemplate getRestTemplate() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        char[] password = "password".toCharArray();
        keyStore.load(new FileInputStream(ResourceUtils.getFile("classpath:alfabattle.jks")), password);

        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, password)
//                .loadTrustMaterial(null, new TrustSelfSignedStrategy())
                .build();

        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
//                .setSSLHostnameVerifier(new NoopHostnameVerifier())
                .build();

        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);

        return new RestTemplate(requestFactory);
    }


    private static HttpEntity<?> getHttpEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");
        headers.add("x-ibm-client-id", "d79fc084-69ec-4d8e-9dfd-daf8b5d04ef9");

        return new HttpEntity<>(headers);
    }


    private void initAtms() {
        log.info("Initing ATMs...");

        log.info("Loading data...");
        ResponseEntity<JSONResponseBankATMDetails> atmsResponse = restTemplate.exchange(
                URI.create(ATMS_API_PATH),
                HttpMethod.GET, httpEntity, JSONResponseBankATMDetails.class);
        ResponseEntity<JSONResponseBankATMStatus> atmsStatusResponse = restTemplate.exchange(
                URI.create(ATMS_STATUS_API_PATH),
                HttpMethod.GET, httpEntity, JSONResponseBankATMStatus.class);

        List<ATMDetails> atms = atmsResponse.getBody().getData().getAtms();
        List<ATMStatus> atmsStatuses = atmsStatusResponse.getBody().getData().getAtms();
        log.info("Data is loaded");

        atms.forEach(atm -> atmById.put(atm.getDeviceId(), atm));
    }

}
