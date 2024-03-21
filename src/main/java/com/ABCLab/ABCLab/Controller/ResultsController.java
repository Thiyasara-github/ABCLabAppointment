package com.ABCLab.ABCLab.Controller;

import com.ABCLab.ABCLab.Model.Results;
import com.ABCLab.ABCLab.Repository.ResultsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
public class ResultsController {

    private final ResultsRepo resultsRepo;

    @Autowired
    public ResultsController(ResultsRepo resultsRepo) {
        this.resultsRepo = resultsRepo;
    }

    @PostMapping("/addresult")
    public ResponseEntity<String> addResult(@RequestBody Results result) {
        // Generate test result content
        String testResultContent = generateTestResultContent(result);

        // Convert test result content to PDF and save it to the database
        byte[] pdfData = generatePdf(testResultContent);

        // Save test result data to database
        result.setPdfFileData(pdfData);
        resultsRepo.save(result);

        return new ResponseEntity<>("Test result added successfully", HttpStatus.OK);
    }

    private String generateTestResultContent(Results result) {
        StringBuilder content = new StringBuilder();

        content.append("Medical Test Result\n\n");
        content.append("Patient Information:\n");
        content.append("Name: ").append(result.getName()).append("\n");
        content.append("Email: ").append(result.getEmail()).append("\n\n");
        content.append("Test Details:\n");
        content.append("Test Name: ").append(result.getTest()).append("\n");
        content.append("Doctor: ").append(result.getDoctor()).append("\n");
        // You can add more test details if needed
        content.append("Test Result: ").append(result.getTest()).append("\n\n");
        content.append("Interpretation:\n");
        // Add interpretation here if available
        content.append("Recommendations:\n");
        content.append(result.getRecommendations()).append("\n");

        return content.toString();
    }

    private byte[] generatePdf(String content) {
        // Here you would use a PDF library like iText or Apache PDFBox to generate the PDF
        // For this example, let's assume a simple conversion from String to byte[]
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // Convert String content to PDF bytes
            outputStream.write(content.getBytes());
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping("/getpdf/{resultId}")
    public ResponseEntity<ByteArrayResource> getPdf(@PathVariable String resultId) {
        // Find the test result by its ID
        Optional<Results> optionalResult = resultsRepo.findById(resultId);

        // If the result is found, retrieve the PDF file data
        if (optionalResult.isPresent()) {
            Results result = optionalResult.get();

            // Prepare the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "test_result.pdf");

            // Return the PDF file data as a byte array resource
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(result.getPdfFileData().length)
                    .body(new ByteArrayResource(result.getPdfFileData()));
        } else {
            // If the result is not found, return a 404 Not Found response
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
