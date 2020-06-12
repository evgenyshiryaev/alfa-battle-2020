package com.soypita.battle.entities;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Data
@NoArgsConstructor
@Document(collection = "users")
public class ContestUser {
    @Id
    private String loginId;

    @NotBlank(message = "host is mandatory")
    private String host;

    private Map<Integer, Float> testResults;
}
