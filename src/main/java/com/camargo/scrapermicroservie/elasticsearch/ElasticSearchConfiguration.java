package com.camargo.scrapermicroservie.elasticsearch;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class ElasticSearchConfiguration {

    @Value("${elasticsearch.host}")
    private String host;
    @Value("${elasticsearch.port}")
    private int port;

    @Bean
    public ElasticsearchClient getElasticsearchClient(){
        RestClient restClient = RestClient.builder(
            new HttpHost(host,  port)).build();

        RestClientTransport restClientTransport =  new RestClientTransport(
                restClient, new JacksonJsonpMapper()
        );

        ElasticsearchClient client = new ElasticsearchClient(
                restClientTransport
        );
        return client;
    }
}
