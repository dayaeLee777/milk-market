package com.mk.elastic.search.util;

import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.util.CollectionUtils;

import com.mk.db.code.Code;
import com.mk.elastic.search.SearchRequestDTO;

public class SearchUtil {

	private SearchUtil() {
	}

	public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto) {
		try {
			final int page = dto.getPage();
			final int size = dto.getSize();
			final int from = page <= 0 ? 0 : page * size;
			
//			SearchSourceBuilder builder = new SearchSourceBuilder().from(from).size(size)
//					.postFilter(getQueryBuilder(dto));
			
			SearchSourceBuilder builder = new SearchSourceBuilder()
					.postFilter(getQueryBuilder(dto));
			
			if(dto.getDivision() != null) {
				builder = builder.postFilter(getQueryBuilder("division", dto.getDivision()));
			}
			
			if(dto.getCategory() != null) {
				builder = builder.postFilter(getQueryBuilder("category", dto.getCategory()));
			}
			
			if(dto.getBcode() != null) {
				builder = builder.postFilter(getQueryBuilder("bcode", dto.getBcode()));
			}

			if (dto.getSortBy() != null) {
				builder = builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			}
			
			builder = builder.from(from).size(size);

			final SearchRequest request = new SearchRequest(indexName);
			request.source(builder);

			return request;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SearchRequest buildSearchRequest(final String indexName, final String field, final Date date) {
		try {
			final SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(getQueryBuilder(field, date));

			final SearchRequest request = new SearchRequest(indexName);
			request.source(builder);

			return request;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto,
			final Date date) {
		try {
			final QueryBuilder searchQuery = getQueryBuilder(dto);
			final QueryBuilder dateQuery = getQueryBuilder("regDate", date);

			final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().mustNot(searchQuery).must(dateQuery);

			SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(boolQuery);

			if (dto.getSortBy() != null) {
				builder = builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			}

			final SearchRequest request = new SearchRequest(indexName);
			request.source(builder);

			return request;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static SearchRequest buildSearchRequest(final String indexName, final SearchRequestDTO dto,
			final Code division) {
		try {
			final QueryBuilder searchQuery = getQueryBuilder(dto);
			final QueryBuilder divisionQuery = getQueryBuilder("division", division);

//			final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery().must(searchQuery).must(divisionQuery);

			SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(searchQuery).postFilter(divisionQuery);

			if (dto.getSortBy() != null) {
				builder = builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.ASC);
			}

			final SearchRequest request = new SearchRequest(indexName);
			request.source(builder);

			return request;
		} catch (final Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	private static QueryBuilder getQueryBuilder(final SearchRequestDTO dto) {
		if (dto == null) {
			return null;
		}
		
		final List<String> fields = dto.getFields();
		if (CollectionUtils.isEmpty(fields)) {
			return null;
		}

		if (fields.size() > 1) {
			final MultiMatchQueryBuilder queryBuilder = QueryBuilders.multiMatchQuery(dto.getSearchTerm())
					.type(MultiMatchQueryBuilder.Type.CROSS_FIELDS).operator(Operator.AND);
			fields.forEach(queryBuilder::field);

			return queryBuilder;
		}

		return fields.stream().findFirst()
				.map(field -> QueryBuilders.matchQuery(field, dto.getSearchTerm()).operator(Operator.AND)).orElse(null);
	}

	private static QueryBuilder getQueryBuilder(final String field, final Date date) {
		return QueryBuilders.rangeQuery(field).gte(date);
	}
	
	private static QueryBuilder getQueryBuilder(final String field, final Code code) {
		return QueryBuilders.matchQuery(field, code).operator(Operator.AND);
	}
	private static QueryBuilder getQueryBuilder(final String field, final String bcode) {
		return QueryBuilders.matchQuery(field, bcode).operator(Operator.AND);
	}
}
