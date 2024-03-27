package ru.nsu.concertsmate.backend;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello() {

        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                new UsernamePasswordCredentials("elastic", "123321"));

        RestClientBuilder builder = RestClient.builder(
                        new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider);
                });

// Create the transport with a Jackson mapper
        ElasticsearchTransport transport = new RestClientTransport(
                builder.build(), new JacksonJsonpMapper());

// And create the API client
        ElasticsearchClient esClient = new ElasticsearchClient(transport);

        String searchText = "москва";

        try {
            SearchResponse<ElasticCity> response = esClient.search(s -> s
                            .index("russian_cities")
                            .query(q -> q
                                    .match(t -> t
                                            .field("name")
                                            .query(searchText)
                                            .fuzziness("AUTO")
                                    )
                            ),
                    ElasticCity.class
            );
            return response.hits().hits().get(0).source().toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
