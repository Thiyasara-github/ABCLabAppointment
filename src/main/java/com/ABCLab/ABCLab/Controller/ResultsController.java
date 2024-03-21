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

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:63342")
public class ResultsController {

    private final ResultsRepo resultsRepo;

    @Autowired
    public ResultsController(ResultsRepo resultsRepo) {
        this.resultsRepo = resultsRepo;
    }

    @PostMapping("/addData")
    public ResponseEntity<String> addData(@RequestBody Map<String, String> requestData) {
        try {
            // Extract data from the request body
            String name = requestData.get("name");
            String email = requestData.get("email");
            String test = requestData.get("test");
            String doctor = requestData.get("doctor");
            String recommendations = requestData.get("recommendations");

            // Validate the data (if needed)

            // Create a new Results object
            Results result = new Results();
            result.setName(name);
            result.setEmail(email);
            result.setTest(test);
            result.setDoctor(doctor);
            result.setRecommendations(recommendations);

            // Save the Results object to the database
            resultsRepo.save(result);

            return new ResponseEntity<>("Data added successfully", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Failed to add data", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /*@PostMapping("/addresult")
    public ResponseEntity<String> addResult(@RequestBody Results result) {
        // Generate test result content
        String testResultContent = generateTestResultContent(result);

        // Convert test result content to PDF and save it to the database
        byte[] pdfData = generatePdf(testResultContent);

        // Save test result data to database
        result.setPdfFileData(pdfData);
        resultsRepo.save(result);

        return new ResponseEntity<>("Test result added successfully", HttpStatus.OK);
    }*/

    /*private String generateTestResultContent(Results result) {
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
    }*/

    /*private byte[] generatePdf(String content) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Add content to the PDF document
            document.add(new Paragraph(content));

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
    }*/

    private byte[] generatePdf(String content) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try (PdfWriter writer = new PdfWriter(outputStream);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf)) {

            // Create a paragraph with proper formatting
            Paragraph paragraph = new Paragraph(content);

            // Add the paragraph to the document
            document.add(paragraph);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return outputStream.toByteArray();
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

    @GetMapping("/getpdf2/{resultId}")
    public ResponseEntity<Map<String, String>> getPdf2(@PathVariable String resultId) {
        // Find the test result by its ID
        Optional<Results> optionalResult = resultsRepo.findById(resultId);

        // If the result is found, retrieve the data
        if (optionalResult.isPresent()) {
            Results result = optionalResult.get();

            // Create a map to store the result data
            Map<String, String> resultMap = new LinkedHashMap<>();
            resultMap.put("Name", result.getName());
            resultMap.put("Email", result.getEmail());
            resultMap.put("Test", result.getTest());
            resultMap.put("Doctor", result.getDoctor());
            resultMap.put("Recommendations", result.getRecommendations());

            return ResponseEntity.ok().body(resultMap);
        } else {
            // If the result is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }

    /**@GetMapping("/getresult/{resultId}")
    public ResponseEntity<Object> getResult(@PathVariable String resultId, @RequestParam(required = false) String format) {
        // Find the test result by its ID
        Optional<Results> optionalResult = resultsRepo.findById(resultId);

        // If the result is found, retrieve the result data
        if (optionalResult.isPresent()) {
            Results result = optionalResult.get();

            if ("pdf".equalsIgnoreCase(format)) {
                // If the requested format is PDF, return the PDF file data
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("filename", "test_result.pdf");

                return ResponseEntity
                        .ok()
                        .headers(headers)
                        .contentLength(result.getPdfFileData().length)
                        .body(new ByteArrayResource(result.getPdfFileData()));
            } else {
                // If the requested format is not PDF, return the result data as JSON
                Map<String, String> resultMap = new LinkedHashMap<>();
                resultMap.put("Name", result.getName());
                resultMap.put("Email", result.getEmail());
                resultMap.put("Test", result.getTest());
                resultMap.put("Doctor", result.getDoctor());

                return ResponseEntity.ok().body(resultMap);
            }
        } else {
            // If the result is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }**/

    /*@GetMapping("/gettxt/{resultId}")
    public ResponseEntity<byte[]> getText(@PathVariable String resultId) {
        // Find the test result by its ID
        Optional<Results> optionalResult = resultsRepo.findById(resultId);

        // If the result is found, retrieve the test result content
        if (optionalResult.isPresent()) {
            Results result = optionalResult.get();

            // Generate the test result content as plain text
            String testResultContent = generateTestResultContent(result);

            // Convert the text content to bytes
            byte[] textBytes = testResultContent.getBytes(StandardCharsets.UTF_8);

            // Set response headers to specify content type and attachment
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("filename", "test_result.txt");

            // Return the text file content as a byte array
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(textBytes);
        } else {
            // If the result is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
    }*/

    @GetMapping("/gettxt/{resultId}")
    public ResponseEntity<byte[]> getText(@PathVariable String resultId) {
        // Find the test result by its ID
        Optional<Results> optionalResult = resultsRepo.findById(resultId);

        // If the result is found, retrieve the test result content
        if (optionalResult.isPresent()) {
            Results result = optionalResult.get();

            // Generate the test result content as plain text
            String testResultContent = generateTestResultContent(result);

            // Convert the text content to bytes
            byte[] textBytes = testResultContent.getBytes(StandardCharsets.UTF_8);

            // Set response headers to specify content type and attachment
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("filename", "test_result.txt");

            // Return the text file content as a byte array
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(textBytes);
        } else {
            // If the result is not found, return a 404 Not Found response
            return ResponseEntity.notFound().build();
        }
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


}
