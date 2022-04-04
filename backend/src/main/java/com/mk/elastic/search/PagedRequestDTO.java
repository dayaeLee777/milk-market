package com.mk.elastic.search;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagedRequestDTO {
	
	private static final int DEFAULT_SIZE = 1000;

    private int page;
    private int size = DEFAULT_SIZE;
    
    public int getDefaultSize() {
    	return DEFAULT_SIZE;
    }
}
