package com.ABCLab.ABCLab.Controller;

import com.ABCLab.ABCLab.Model.LabService;
import com.ABCLab.ABCLab.Repository.LabServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins = "http://localhost:63342")
public class LabServiceController {

    @Autowired
    LabServiceRepo labServiceRepo;

    /*@PostMapping("/addService")
    public void addService(@RequestBody LabService labService){
        // Find the maximum sid from the database
        Integer maxSid = labServiceRepo.findMaxSid();

        // If no records in the database, set sid to 1, otherwise increment the maximum sid
        labService.setSid(maxSid != null ? maxSid + 1 : 1);

        // Save the LabService with the updated sid
        labServiceRepo.save(labService);
    }*/

    @PostMapping("/addService")
    public void addService(@RequestBody LabService labService){

        labServiceRepo.save(labService);
    }

    /*@PostMapping("/addService")
    public void addService(@RequestBody LabService labService) {
        // Find the maximum sid from the database
        Integer maxSid = labServiceRepo.findMaxSid().getSid();

        // If no records in the database, set sid to 1, otherwise increment the maximum sid
        labService.setSid(maxSid != null ? maxSid + 1 : 1);

        // Save the LabService with the updated structure
        labServiceRepo.save(labService);
    }*/

    @GetMapping("/Service/all")
    public ResponseEntity<List<LabService>> getAllServices(){
        List<LabService> labServices = labServiceRepo.findAll();

        if(labServices.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(labServices, HttpStatus.OK);
    }


    @PutMapping("/updateService")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateService(@RequestBody LabService labService) {
        // Fetch the LabService by id
        LabService existingService = labServiceRepo.findById(labService.getSid()).orElse(null);

        // Check if LabService exists
        if (existingService != null) {
            // Update with the required data
            if (labService.getSname() != null) {
                existingService.setSname(labService.getSname());
            }

            if (labService.getDoctors() != null) {
                existingService.setDoctors(labService.getDoctors());
            }

            if (labService.getDates() != null) {
                existingService.setDates(labService.getDates());
            }

            // Save the updated LabService
            labServiceRepo.save(existingService);
        }
        // You may add an else block if you want to handle the case when LabService is not found
    }

    @DeleteMapping("/deleteService/{sid}")
    public void deleteService(@PathVariable Integer sid){
        labServiceRepo.deleteById(sid);
    }

}




