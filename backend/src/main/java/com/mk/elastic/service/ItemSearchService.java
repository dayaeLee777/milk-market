package com.mk.elastic.service;

import java.util.List;

import com.mk.api.dto.request.ItemModifyRequestDto;
import com.mk.elastic.document.Itemsearch;
import com.mk.elastic.search.SearchRequestDTO;

public interface ItemSearchService {
	
	public Boolean index(final Itemsearch itemSearch);
	
	public List<Itemsearch> search(final SearchRequestDTO dto);
	
	public int getSearchPages(final SearchRequestDTO dto);
	
	public Itemsearch getById(final String itemSearchId);
	
	Boolean registerItemSearch(String itemId);
	
	public Boolean deleteItemsearch(final String itemSearchId);
	
	public Boolean modifyItemsearch(final ItemModifyRequestDto itemModifyRequestDto);
	
}
