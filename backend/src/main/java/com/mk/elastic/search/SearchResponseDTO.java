package com.mk.elastic.search;

import java.util.List;

import com.mk.elastic.document.Itemsearch;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class SearchResponseDTO {
	
	public List<Itemsearch> itemsearchList;
	
	public int totalPages;
	
}
