package com.ABCLab.ABCLab.Repository;

import com.ABCLab.ABCLab.Model.LabService;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface LabServiceRepo extends MongoRepository<LabService, Integer> {

    
}
