package ru.nsu.concertsmate.backend.services.impl;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.ElasticCity;
import ru.nsu.concertsmate.backend.services.CityService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class CityServiceImpl implements CityService {
    private ElasticsearchClient elasticsearchClient;


    private void createElasticsearchClient(){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "password"));

        RestClientBuilder builder = RestClient.builder( new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder
                            .setDefaultCredentialsProvider(credentialsProvider);
                });

        ElasticsearchTransport transport = new RestClientTransport( builder.build(), new JacksonJsonpMapper());

        elasticsearchClient = new ElasticsearchClient(transport);
    }

    @Autowired
    public CityServiceImpl(){
        createElasticsearchClient();
    }


    @Override
    public List<ElasticCity> getCityByName(String name) {
        try {
            SearchResponse<ElasticCity> response = elasticsearchClient.search(s -> s
                            .index("russian_cities")
                            .query(q -> q
                                    .fuzzy(t -> t
                                            .field("name")
                                            .value(name)
                                            .fuzziness("AUTO")
                                    )
                            ),
                    ElasticCity.class
            );
            List<ElasticCity> res = new ArrayList<>();
            for (var hit: response.hits().hits()){
                res.add(hit.source());
            }
            return res;
        } catch (IOException e) {
            throw new RuntimeException(e); //add unique exception
        }
    }
}
