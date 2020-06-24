package ru.alfabank.alfabattle2020.task4.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import javax.xml.bind.annotation.XmlSchemaType;
import java.util.Date;

@Data
@Builder
public class LoanJson {

    @JsonProperty("Loan")
    private String loan;
    @JsonProperty("PersonId")
    private String document;
    @JsonProperty("Amount")
    private Integer amount;
    @JsonProperty("StartDate")
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date openDate;
    @JsonProperty("Period")
    private Integer period;

}
