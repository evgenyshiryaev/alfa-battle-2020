package com.soypita.battle.converters;

import com.soypita.battle.dto.UserResult;
import com.soypita.battle.entities.ContestUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserResultConverter {
    UserResult convert(ContestUser user);
}
