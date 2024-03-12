package com.ABCLab.ABCLab.Repository;

import com.ABCLab.ABCLab.Model.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentRepo extends MongoRepository<Payment, Integer> {

}
