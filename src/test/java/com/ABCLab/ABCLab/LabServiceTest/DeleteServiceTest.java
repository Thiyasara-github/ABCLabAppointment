package com.ABCLab.ABCLab.LabServiceTest;

import com.ABCLab.ABCLab.Controller.LabServiceController;
import com.ABCLab.ABCLab.Repository.LabServiceRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class DeleteServiceTest {

    @InjectMocks
    private LabServiceController labServiceController;

    @Mock
    private LabServiceRepo labServiceRepo;

    @Test
    void testDeleteService() {
        // Mock the ID of the LabService to be deleted
        Integer serviceId = 1;

        // Call the method to test
        labServiceController.deleteService(serviceId);

        // Verify that labServiceRepo.deleteById() is called with the correct ID
        verify(labServiceRepo).deleteById(serviceId);

        // Print a message indicating test success
        System.out.println("Test successful: testDeleteService() deleted the LabService with ID " + serviceId + " successfully.");
    }
}
