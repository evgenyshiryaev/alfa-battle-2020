package ru.alfa.task3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alfa.task3.entity.Branches;

import java.util.List;

public interface BranchesRepository extends JpaRepository<Branches, Long> {
    Branches findById(long id);
    List<Branches> findAll();
}
