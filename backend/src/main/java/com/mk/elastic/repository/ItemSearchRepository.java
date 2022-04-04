package com.mk.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.mk.elastic.document.Itemsearch;

public interface ItemSearchRepository extends ElasticsearchRepository<Itemsearch, String> {

}
