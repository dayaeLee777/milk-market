package com.mk.elastic.service;

public interface IndexService {

	public void tryToCreateIndices();
	
	public void recreateIndices(final boolean deleteExisting);

}
