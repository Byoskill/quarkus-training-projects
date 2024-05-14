package com.byoskill.adapters.adoption.h2;


import io.quarkus.arc.profile.IfBuildProfile;
import io.quarkus.hibernate.search.orm.elasticsearch.SearchExtension;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurationContext;
import org.hibernate.search.backend.elasticsearch.analysis.ElasticsearchAnalysisConfigurer;


/**
 *
 */
@IfBuildProfile(anyOf = "h2")
@SearchExtension
public class AnalysisConfigurer implements ElasticsearchAnalysisConfigurer {

    @Override
    public void configure(final ElasticsearchAnalysisConfigurationContext context) {
        context.analyzer("name").custom()
                .tokenizer("standard")
                .tokenFilters("asciifolding", "lowercase");

        context.analyzer("english").custom()
                .tokenizer("standard")
                .tokenFilters("asciifolding", "lowercase", "porter_stem");

    }
}