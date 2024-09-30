package com.hubo.gillajabi;

import org.elasticsearch.client.Request;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class HuboApplicationTests {

    @Autowired
    private RestClient restClient;

    @Test
    public void testElasticsearchConnection() throws IOException {
        assertNotNull(restClient, "RestClient is null");

        Request request = new Request("GET", "/");
        Response response = restClient.performRequest(request);

        assertEquals(200, response.getStatusLine().getStatusCode(), "success");
    }

}
