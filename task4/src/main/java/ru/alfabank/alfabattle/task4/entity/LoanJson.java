package ru.alfabank.alfabattle.task4.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlSchemaType;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
