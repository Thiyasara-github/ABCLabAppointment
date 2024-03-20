package com.ABCLab.ABCLab.LabServiceTest;

import com.ABCLab.ABCLab.Controller.LabServiceController;
import com.ABCLab.ABCLab.Model.LabService;
import com.ABCLab.ABCLab.Repository.LabServiceRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AddServiceTest {

    @InjectMocks
    private LabServiceController labServiceController;

    @Mock
    private LabServiceRepo labServiceRepo;

    @Test
    void testAddService() {
        // Mock data
        LabService labServiceToAdd = new LabService(1, "New Service", new ArrayList<>(), new ArrayList<>());

        // Call the method to test
        labServiceController.addService(labServiceToAdd);

        // Verify that labServiceRepo.save() was called with the correct argument
        verify(labServiceRepo).save(labServiceToAdd);

        // Print a message indicating test success
        System.out.println("Test successful: testAddService() method successfully added a LabService.");
    }
}
