package com.ABCLab.ABCLab.LabServiceTest;

import com.ABCLab.ABCLab.Controller.LabServiceController;
import com.ABCLab.ABCLab.Model.LabService;
import com.ABCLab.ABCLab.Repository.LabServiceRepo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class testGetAllTests {

    @InjectMocks
    private LabServiceController labServiceController;

    @Mock
    private LabServiceRepo labServiceRepo;

    private final PrintStream originalErr = System.err;
    private ByteArrayOutputStream errContent;

    @BeforeEach
    void redirectErrStream() {
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreErrStream() {
        System.setErr(originalErr);
    }

    @Test
    void testGetAllServices() {
        // Mock data
        List<LabService> labServices = new ArrayList<>();
        labServices.add(new LabService(1, "Service 1", new ArrayList<>(), new ArrayList<>()));

        // Mock behavior of labServiceRepo.findAll()
        when(labServiceRepo.findAll()).thenReturn(labServices);

        // Call the method to test
        ResponseEntity<List<LabService>> responseEntity = labServiceController.getAllServices();

        // Verify the response status
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify the returned data
        List<LabService> returnedLabServices = responseEntity.getBody();
        assertEquals(1, returnedLabServices.size());
        assertEquals("Service 1", returnedLabServices.get(0).getSname());

        // Print a message indicating test success
        System.out.println("Test successful: getAllServices() returned expected data.");
    }


}
