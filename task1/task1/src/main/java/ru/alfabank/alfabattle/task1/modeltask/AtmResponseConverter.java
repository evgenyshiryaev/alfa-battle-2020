package ru.alfabank.alfabattle.task1.modeltask;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ru.alfabank.alfabattle.task1.modelalfa.ATMDetails;


@Mapper(componentModel = "spring")
public interface AtmResponseConverter {

    @Mapping(source = "coordinates.latitude", target = "latitude")
    @Mapping(source = "coordinates.longitude", target = "longitude")
    @Mapping(source = "address.city", target = "city")
    @Mapping(source = "address.location", target = "location")
    AtmResponse convert(ATMDetails atmDetails);

}
