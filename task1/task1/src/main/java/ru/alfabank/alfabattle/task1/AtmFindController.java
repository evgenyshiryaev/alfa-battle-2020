package ru.alfabank.alfabattle.task1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


@RestController("/")
public class AtmFindController {

    @GetMapping
    public String getNearestAtm() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(
                    "https://apiws.alfabank.ru/alfabank/alfadevportal/atm-service/atms", String.class);
        } catch (Exception e) {
            
        }

        return "go to hell";
    }

}
