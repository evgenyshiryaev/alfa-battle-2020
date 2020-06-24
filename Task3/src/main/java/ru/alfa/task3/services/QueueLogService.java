package ru.alfa.task3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alfa.task3.entity.QueueLog;
import ru.alfa.task3.repositories.QueueLogRepository;

import java.util.List;

@Service
public class QueueLogService {

    private QueueLogRepository queueLogRepository;

    @Autowired
    public QueueLogService(QueueLogRepository queueLogRepository) {
        this.queueLogRepository = queueLogRepository;
    }

    public QueueLog findById(long id){
        return queueLogRepository.findById(id);
    }
    public List<QueueLog> findAllByBranchesId(long branchesId){
        return queueLogRepository.findAllByBranchesId(branchesId);
    }
    public List<QueueLog> findAll(){
        return queueLogRepository.findAll();
    }

}
