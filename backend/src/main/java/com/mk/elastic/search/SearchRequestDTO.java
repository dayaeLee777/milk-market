package com.mk.elastic.search;

import java.util.List;

import org.elasticsearch.search.sort.SortOrder;

import com.mk.db.code.Code;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchRequestDTO extends PagedRequestDTO {

    private List<String> fields;

    private String searchTerm;
    
    private String sortBy;
    
    private SortOrder order;
    
    private Code division;
    
    private Code category;
    
    private String bcode;
    
    private Code status;
    
}
