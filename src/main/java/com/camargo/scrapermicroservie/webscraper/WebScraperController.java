package com.camargo.scrapermicroservie.webscraper;

import com.camargo.scrapermicroservie.elasticsearch.ElasticSearchQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "/api/news")
public class WebScraperController {

    /*@Autowired
    private WebScraperService webScraperService;
    @Autowired
    private ElasticSearchQuery elasticSearchQuery;

    @GetMapping(path = "/nytimes")
    public ResponseEntity getNYTNews() throws IOException {
        List<HeadlineDTO> headlineDTOList = webScraperService.getNYTNews();

        for(HeadlineDTO news : headlineDTOList){
            String response = elasticSearchQuery.createOrUpdateDocument(news);
        }

        return new ResponseEntity(headlineDTOList, HttpStatus.OK);

    }

    @GetMapping(path = "/yahoo")
    public ResponseEntity getYahooNews(){
        List<HeadlineDTO> headlineDTOList = webScraperService.getYahooNews();

        return new ResponseEntity(headlineDTOList, HttpStatus.OK);

    }
*/
}
