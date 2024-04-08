package ru.nsu.concertsmate.backend.services.impl;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
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

    private void createElasticsearchClient() {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(elasticLogin, elasticPassword));

        final RestClientBuilder builder = RestClient.builder(new HttpHost(elasticHost, elasticPort))
                .setHttpClientConfigCallback(httpClientBuilder -> {
                    httpClientBuilder.disableAuthCaching();
                    return httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                });

        final ElasticsearchTransport transport = new RestClientTransport(builder.build(), new JacksonJsonpMapper());

        elasticsearchClient = new ElasticsearchClient(transport);
    }

    @Autowired
    public CityServiceImpl() {

    }

    @Override
    public List<ElasticCity> getCityByName(String name) {
        if (!init) {
            createElasticsearchClient();
            init = true;
        }

        try {
            final SearchResponse<ElasticCity> response = elasticsearchClient.search(s -> s
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

            return response.hits().hits().stream().map(Hit::source).toList();
        } catch (IOException e) {
            throw new RuntimeException(e); // TODO: add unique exception
        }
    }
}
