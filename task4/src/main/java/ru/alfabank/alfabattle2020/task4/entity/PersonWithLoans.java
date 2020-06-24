package ru.alfabank.alfabattle2020.task4.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PersonWithLoans {
    private String id;
    private String docid;
    private String fio;
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private Date birthday;
    private BigDecimal salary;
    private String gender;
    private List<Loan> loans;
}
