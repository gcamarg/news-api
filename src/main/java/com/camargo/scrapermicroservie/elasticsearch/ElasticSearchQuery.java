package com.camargo.scrapermicroservie.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.TextQueryType;
import co.elastic.clients.elasticsearch.core.*;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.camargo.scrapermicroservie.webscraper.HeadlineDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ElasticSearchQuery {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "news";

    public String createOrUpdateDocument(HeadlineDTO news) throws IOException {

        if(!checkIfDocumentExists(news)) {
            IndexResponse response = elasticsearchClient.index(doc -> doc
                    .index(indexName)
                    .id(news.getId())
                    .document(news)
            );

            if (response.result().name().equals("Created")) {
                return new StringBuilder("Document has been successfully created.").toString();
            } else if (response.result().name().equals("Updated")) {
                return new StringBuilder("Document has been successfully updated.").toString();
            }
            return new StringBuilder("Couldn't perform the operation.").toString();
        }
        return new StringBuilder("Document already exists.").toString();

    }

    public boolean checkIfDocumentExists(HeadlineDTO news){

        CountResponse response = null;
        try {
            response = elasticsearchClient.count(doc->doc
                    .index("news")
                    .query(q->q.match(m->m.field("headline").query(news.getHeadline()))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e){
            if(e.getMessage().contains("index_not_found_exception"))return false;
        }

        if(response.count() > 0){
            return true;
        }
        return false;
    }

    public HeadlineDTO getDocumentById(String newsId) throws IOException {
        HeadlineDTO news = null;

        GetResponse<HeadlineDTO> response = elasticsearchClient.get(doc -> doc
                .index(indexName)
                .id(newsId),
                HeadlineDTO.class
        );

        if(response.found()) {
            news = response.source();
            System.out.println("News headline: " + news.getHeadline());
            // TODO save log
        } else {
            System.out.println("No news found.");
            // TODO save log
        }
        return news;
    }

    public String deleteDocumentById(String newsID) throws IOException {
        DeleteRequest request = DeleteRequest.of(doc -> doc
                .index(indexName)
                .id(newsID)
        );

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);

        if(!deleteResponse.result().name().equals("NotFound") && Objects.nonNull(deleteResponse.result())){
            return new StringBuilder("News of id " + deleteResponse.id() + " has been deleted.").toString();
        } else {
            System.out.println("News not found.");
            return new StringBuilder("Can't find news that matches the id " + deleteResponse.id() + ".").toString();
        }
    }

    public List<HeadlineDTO> getAllDocuments() throws IOException {

        SearchRequest searchRequest = SearchRequest.of(doc -> doc.index(indexName));

        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, HeadlineDTO.class);

        List<Hit> hits = searchResponse.hits().hits();

        List<HeadlineDTO> news = new ArrayList<>();

        for(Hit hit : hits){
            System.out.println((HeadlineDTO) hit.source());
            news.add((HeadlineDTO) hit.source());
        }
        return news;
    }

    public List<HeadlineDTO> searchDocuments(String searchTerm) throws IOException {

        SearchRequest searchRequest = SearchRequest.of(doc -> doc
                .index(indexName)
                .query(q -> q
                        .multiMatch(m->m
                                .query(searchTerm)
                                .type(TextQueryType.BoolPrefix)
                                .fields("headline"))
                )
        );
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, HeadlineDTO.class);

        List<Hit> hits = searchResponse.hits().hits();

        List<HeadlineDTO> news = new ArrayList<>();

        for(Hit hit : hits){
            System.out.println((HeadlineDTO) hit.source());
            news.add((HeadlineDTO) hit.source());
        }
        return news;
    }
}
