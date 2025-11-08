package com.vahabvahabov.PDFTextSearch.config;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;

@Configuration
public class ElasticSearchConfig  extends ElasticsearchConfiguration {

    @Value("${spring.elasticsearch.uris}")
    private String elasticSearchUrl;

    @Override
    public ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder().connectedTo(elasticSearchUrl.replace("http://", "").replace("https://", "")).build();
    }
}
