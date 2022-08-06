package com.camargo.scrapermicroservie.elasticsearch;

import com.camargo.scrapermicroservie.webscraper.HeadlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ElasticSearchController {

    @Autowired
    private ElasticSearchQuery elasticSearchQuery;

    @PostMapping("/createOrUpdateDocument")
    public ResponseEntity<Object> createOrUpdateDocument(@RequestParam HeadlineDTO headlineDTO){

        String response = null;
        try {
            response = elasticSearchQuery.createOrUpdateDocument(headlineDTO);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            //TODO: work on this section
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getDocument")
    public ResponseEntity<Object> getDocumentById(@RequestParam String newsId){
        try {
            HeadlineDTO news = elasticSearchQuery.getDocumentById(newsId);
            return new ResponseEntity<>(news, HttpStatus.OK);
        } catch (IOException e) {
            //TODO: work on this section
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteDocument")
    public ResponseEntity<Object> deleteDocumentById(@RequestParam String newsId){
        try {
            String response = elasticSearchQuery.deleteDocumentById(newsId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IOException e) {
            //TODO: work on this section
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getAllDocuments")
    public ResponseEntity<Object> getAllDocuments(){
        try {
            List<HeadlineDTO> newsList = elasticSearchQuery.getAllDocuments();
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        } catch (IOException e) {
            //TODO: work on this section
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/searchTerm")
    public ResponseEntity<Object> searchDocuments(@RequestParam("term") String searchTerm){
        try {
            List<HeadlineDTO> newsList = elasticSearchQuery.searchDocuments(searchTerm);
            System.out.println(newsList);
            return new ResponseEntity<>(newsList, HttpStatus.OK);
        } catch (IOException e) {
            //TODO: work on this section
            return new ResponseEntity<>(e.getMessage() ,HttpStatus.BAD_REQUEST);
        }
    }
}
