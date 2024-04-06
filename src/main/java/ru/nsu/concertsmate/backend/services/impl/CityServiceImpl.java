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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.nsu.concertsmate.backend.ElasticCity;
import ru.nsu.concertsmate.backend.services.CityService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Component
public class CityServiceImpl implements CityService {

    @Value("${spring.application.elasticLogin}")
    private String elasticLogin;

    @Value("${spring.application.elasticPassword}")
    private String elasticPassword;


    @Value("${spring.application.elasticHost}")
    private String elasticHost;


    @Value("${spring.application.elasticPort}")
    private int elasticPort;


    private ElasticsearchClient elasticsearchClient;


    private boolean init = false;


    private void createElasticsearchClient(){
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticLogin, elasticPassword));

        RestClientBuilder builder = RestClient.builder( new HttpHost(elasticHost, elasticPort))
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

    }


    @Override
    public List<ElasticCity> getCityByName(String name) {

        if (!init){
            createElasticsearchClient();
            init = true;
        }

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
