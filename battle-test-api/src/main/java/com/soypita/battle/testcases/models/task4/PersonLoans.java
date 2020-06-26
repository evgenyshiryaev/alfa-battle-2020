package com.soypita.battle.testcases.models.task4;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PersonLoans {
    private Integer countLoan = 0;
    private BigDecimal sumAmountLoans = BigDecimal.valueOf(0.0);
    private List<Loan> loans;

    public void increaseCountLoan(){
        countLoan++;
    }
    public void increaseCountAmountLoan(BigDecimal amount){

        sumAmountLoans = sumAmountLoans.add(amount);
    }

}
