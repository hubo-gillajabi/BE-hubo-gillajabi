package com.hubo.gillajabi;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.NodeStatistics;
import co.elastic.clients.elasticsearch.nodes.Stats;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import co.elastic.clients.elasticsearch.ElasticsearchClient;

@SpringBootTest
@ActiveProfiles("test")
class HuboApplicationTests {

    private static final Logger logger = LoggerFactory.getLogger(HuboApplicationTests.class);

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    @Test
    public void testElasticsearchConnection() {
        logger.info("Starting Elasticsearch connection test");
        try {
            var response = elasticsearchClient.info();
            logger.info("Elasticsearch info: {}", response);
        } catch (Exception e) {
            logger.error("Error connecting to Elasticsearch", e);
            throw new RuntimeException("Failed to connect to Elasticsearch", e);
        }
    }
}