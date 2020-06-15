package com.soypita.battle.repository;

import com.soypita.battle.entities.ContestUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContestUsersRepository extends MongoRepository<ContestUser, String> {
    Optional<ContestUser> findByLoginId(String loginId);
    void deleteByLoginId(String loginId);
}
