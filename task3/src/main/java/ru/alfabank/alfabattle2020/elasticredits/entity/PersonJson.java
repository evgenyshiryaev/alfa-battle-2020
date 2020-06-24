package ru.alfabank.alfabattle2020.elasticredits.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.elasticsearch.search.DocValueFormat;

import javax.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PersonJson {

    @JsonProperty("ID")
    private String id;
    @JsonProperty("DocId")
    private String docId;
    @JsonProperty("FIO")
    private String fio;
    @JsonProperty("Birthday")
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date birthday;
    @JsonProperty("Salary")
    private BigDecimal salary;
    @JsonProperty("Gender")
    private String gender;

}
