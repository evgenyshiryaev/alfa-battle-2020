package ru.alfa.task3.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.alfa.task3.entity.Branches;
import ru.alfa.task3.repositories.BranchesRepository;

import java.util.List;


@Service
public class BranchesService {

    private BranchesRepository branchesRepository;

    @Autowired
    public BranchesService(BranchesRepository branchesRepository) {
        this.branchesRepository = branchesRepository;
    }

    public Branches findById(long id){
        return branchesRepository.findById(id);
    }

    public List<Branches> findAll(){
        return branchesRepository.findAll();
    }

}
