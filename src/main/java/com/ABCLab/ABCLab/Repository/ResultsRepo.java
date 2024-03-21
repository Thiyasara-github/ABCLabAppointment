package com.ABCLab.ABCLab.Repository;

import com.ABCLab.ABCLab.Model.Results;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ResultsRepo extends MongoRepository<Results, String> {
}
