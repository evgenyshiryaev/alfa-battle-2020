package com.soypita.battle.converters;


import com.soypita.battle.dto.AddUserRequest;
import com.soypita.battle.entities.ContestUser;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ContestUserConverter {
    ContestUser convert(AddUserRequest req);
}
