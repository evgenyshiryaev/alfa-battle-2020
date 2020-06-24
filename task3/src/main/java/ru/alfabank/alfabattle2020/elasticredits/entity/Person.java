package ru.alfabank.alfabattle2020.elasticredits.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlSchemaType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Person {

   // private String id;
    private String docid;
    private String fio;
    @XmlSchemaType(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date birthday;
    private BigDecimal salary;
    private String gender;

}
