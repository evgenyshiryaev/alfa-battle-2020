package ru.alfabank.alfabattle.task4.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PersonJsonList {

    private List<PersonJson> persons;

}
