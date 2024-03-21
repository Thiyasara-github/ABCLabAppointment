package com.ABCLab.ABCLab.Repository;

import com.ABCLab.ABCLab.Model.Appointment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface AppointmentRepo extends MongoRepository<Appointment, String> {

}
