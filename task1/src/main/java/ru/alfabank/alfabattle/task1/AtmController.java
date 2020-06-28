package ru.alfabank.alfabattle.task1;

import java.io.InputStream;
import java.net.URI;
import java.security.KeyStore;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import ru.alfabank.alfabattle.task1.modelalfa.ATMDetails;
import ru.alfabank.alfabattle.task1.modelalfa.JSONResponseBankATMDetails;
import ru.alfabank.alfabattle.task1.modeltask.AtmResponse;
import ru.alfabank.alfabattle.task1.modeltask.AtmResponseConverter;
import ru.alfabank.alfabattle.task1.modeltask.ErrorResponse;


@Slf4j
@RestController
@RequestMapping("/atms")
public class AtmController {

    private static final String API_ROOT = "https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service";
    private static final String ATMS_API_PATH = API_ROOT + "/atms";
    //private static final String ATMS_STATUS_API_PATH = API_ROOT + "/atms/status";

    private static final String CLIENT_ID = "d79fc084-69ec-4d8e-9dfd-daf8b5d04ef9";


    @Autowired
    private AtmService atmService;

    @Autowired
    private AtmResponseConverter converter;


    @PostConstruct
    void init() throws Exception {
        initAtms();
    }


    @GetMapping(value = "/{id}")
    @ApiResponses({
        @ApiResponse(code = 200, message = "OK", response = AtmResponse.class),
        @ApiResponse(code = 404, message = "NOT FOUND", response = ErrorResponse.class)})
    public ResponseEntity<?> getById(@PathVariable("id") int id) {
        ATMDetails atm = atmService.getById(id);
        return atm != null
                ? ResponseEntity.ok(converter.convert(atm))
                : new ResponseEntity<>(new ErrorResponse("atm not found"), HttpStatus.NOT_FOUND);
    }


    @GetMapping(value = "/nearest")
    public ResponseEntity<AtmResponse> getNearestAtm(
            String latitude, String longitude,
            @RequestParam(required = false, defaultValue = "false") boolean payments) {
        int id = atmService.getNearest(latitude, longitude, payments);
        ATMDetails atm = atmService.getById(id);
        return ResponseEntity.ok(converter.convert(atm));
    }


    @GetMapping(value = "/nearest-with-alfik")
    public ResponseEntity<List<AtmResponse>> getNearestAtmsWithMoney(
            String latitude, String longitude, int alfik) {
        List<Integer> ids = atmService.getNearestWithAlfik(latitude, longitude, alfik);

        List<AtmResponse> response = ids.stream()
                .map(atmService::getById)
                .map(converter::convert)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }


    private static RestTemplate getRestTemplate() throws Exception {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        char[] password = "password".toCharArray();
        InputStream jksInputStream = AtmController.class.getClassLoader().getResourceAsStream("alfabattle.jks");
        keyStore.load(jksInputStream, password);

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
        headers.add("x-ibm-client-id", CLIENT_ID);

        return new HttpEntity<>(headers);
    }


    private void initAtms() throws Exception {
        log.info("Initing ATMs...");

        log.info("Loading data...");
        RestTemplate restTemplate = getRestTemplate();
        HttpEntity<?> httpEntity = getHttpEntity();
        ResponseEntity<JSONResponseBankATMDetails> atmsResponse = restTemplate.exchange(
                URI.create(ATMS_API_PATH),
                HttpMethod.GET, httpEntity, JSONResponseBankATMDetails.class);
        //ResponseEntity<JSONResponseBankATMStatus> atmsStatusResponse = restTemplate.exchange(
        //        URI.create(ATMS_STATUS_API_PATH),
        //        HttpMethod.GET, httpEntity, JSONResponseBankATMStatus.class);
        log.info("Data is loaded");

        List<ATMDetails> atms = atmsResponse.getBody().getData().getAtms();
        atmService.setAtms(atms);

        //List<ATMStatus> atmStatuses = atmsStatusResponse.getBody().getData().getAtms();
        //atmService.setAtmStatuses(atmStatuses);

        log.info("ATMs are inited");
    }

}
