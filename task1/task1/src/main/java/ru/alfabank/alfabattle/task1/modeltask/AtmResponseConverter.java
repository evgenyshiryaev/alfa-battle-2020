package ru.alfabank.alfabattle.task1.modeltask;

import org.mapstruct.Mapper;

import ru.alfabank.alfabattle.task1.modelalfa.ATMDetails;


@Mapper(componentModel = "spring")
public interface AtmResponseConverter {

    AtmResponse convert(ATMDetails atmDetails);

}
