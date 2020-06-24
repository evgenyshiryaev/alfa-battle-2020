package ru.alfa.task3.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alfa.task3.entity.QueueLog;

import java.util.List;

public interface QueueLogRepository extends JpaRepository<QueueLog, Long> {
    QueueLog findById(long id);
    List<QueueLog> findAllByBranchesId(long id);
    List<QueueLog> findAll();
}
