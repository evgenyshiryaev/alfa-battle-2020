package ru.alfabank.alfabattle2020sample;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("calculator")
public class CalculatorController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> calculate(
            @RequestParam int arg0,
            @RequestParam int arg1,
            @RequestParam CalculatorOperation operation) {
        int result;

        switch (operation) {
            case PLUS:
                result = arg0 + arg1;
                break;

            case MINUS:
                result = arg0 - arg1;
                break;

            default:
                return ResponseEntity.badRequest().body("Unsupported operation");
        }

        return ResponseEntity.ok(result);
    }

}
