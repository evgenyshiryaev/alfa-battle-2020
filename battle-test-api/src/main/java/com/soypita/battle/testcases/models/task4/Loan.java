package com.soypita.battle.testcases.models.task4;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.util.Date;


@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Loan {
    private String loan;
    private BigDecimal amount;
    private String document;
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date startdate;
    private Integer period;
}
