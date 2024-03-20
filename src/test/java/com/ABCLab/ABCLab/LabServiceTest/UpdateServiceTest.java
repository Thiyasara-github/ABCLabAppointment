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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UpdateServiceTest {

    @InjectMocks
    private LabServiceController labServiceController;

    @Mock
    private LabServiceRepo labServiceRepo;

    @Test
    void testUpdateService() {
        // Mock data
        LabService existingService = new LabService();
        existingService.setSid(1);
        existingService.setSname("Service 1");
        existingService.setDoctors(new ArrayList<>());
        existingService.setDates(new ArrayList<>());

        LabService updatedService = new LabService();
        updatedService.setSid(1);
        updatedService.setSname("Updated Service");
        updatedService.setDoctors(List.of("Doctor 1", "Doctor 2"));
        updatedService.setDates(List.of("Date 1", "Date 2"));

        // Mock behavior of labServiceRepo.findById()
        when(labServiceRepo.findById(1)).thenReturn(Optional.of(existingService));

        // Call the method to test
        labServiceController.updateService(updatedService);

        // Verify that labServiceRepo.save() is called with the updated LabService
        verify(labServiceRepo, times(1)).save(updatedService);

        // Print a message indicating test success
        System.out.println("Test successful: testUpdateService() updated the LabService successfully.");
    }
}
