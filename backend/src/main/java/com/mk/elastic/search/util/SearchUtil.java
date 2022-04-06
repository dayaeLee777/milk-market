package com.mk.elastic.search.util;

import java.util.Date;
import java.util.List;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.core.CountRequest;
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

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SearchUtil {

	private SearchUtil() {
	}

	public static SearchRequest buildSearchRequest(String indexName, SearchRequestDTO dto) {
		try {

			final int page = 0;
			final int size = dto.getSize() <= 0 ? dto.getDefaultSize() : dto.getSize();
			final int from = page <= 0 ? 0 : page * size;

			QueryBuilder searchQuery = getQueryBuilder(dto);
			QueryBuilder divisionQuery = null;
			QueryBuilder categoryQuery = null;
			QueryBuilder bcodeQuery = null;
			QueryBuilder statusQuery = null;

			if (dto.getStatus() == null)
				statusQuery = getQueryBuilder("status", Code.C01);
			else
				statusQuery = getQueryBuilder("status", dto.getStatus());

			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
			boolQuery.must(statusQuery);

			if (searchQuery != null) {
				boolQuery.must(searchQuery);
			}
			
			if (dto.getDivision() != null) {
				divisionQuery = getQueryBuilder("division", dto.getDivision());
				boolQuery.must(divisionQuery);
			}

			if (dto.getCategory() != null) {
				categoryQuery = getQueryBuilder("category", dto.getCategory());
				boolQuery.must(categoryQuery);
			}

			if (dto.getBcode() != null) {
				bcodeQuery = getQueryBuilder("bcode", dto.getBcode());
				boolQuery.must(bcodeQuery);
			}

			SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(boolQuery);
			if (dto.getSortBy() != null) {
				SortOrder sortOrder = SortOrder.DESC;
				if(dto.getOrder() != null && dto.getOrder().toString().toUpperCase().equals("ASC"))
					sortOrder = SortOrder.ASC;
				builder.sort(dto.getSortBy().toString(), sortOrder);
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

	public static CountRequest buildSearchRequestCount(String indexName, SearchRequestDTO dto) {
		try {

			QueryBuilder searchQuery = getQueryBuilder(dto);
			QueryBuilder divisionQuery = null;
			QueryBuilder categoryQuery = null;
			QueryBuilder bcodeQuery = null;

			BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

			if (searchQuery != null) {
				boolQuery.must(searchQuery);
			}

			if (dto.getDivision() != null) {
				divisionQuery = getQueryBuilder("division", dto.getDivision());
				boolQuery.must(divisionQuery);
			}

			if (dto.getCategory() != null) {
				categoryQuery = getQueryBuilder("category", dto.getCategory());
				boolQuery.must(categoryQuery);
			}

			if (dto.getBcode() != null) {
				bcodeQuery = getQueryBuilder("bcode", dto.getBcode());
				boolQuery.must(bcodeQuery);
			}

			SearchSourceBuilder builder = new SearchSourceBuilder().postFilter(boolQuery);

			builder.query(QueryBuilders.matchAllQuery());
			CountRequest countRequest = new CountRequest(indexName);
			countRequest.source(builder);

			return countRequest;
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
				builder = builder.sort(dto.getSortBy(), dto.getOrder() != null ? dto.getOrder() : SortOrder.DESC);
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

	private static QueryBuilder getQueryBuilder(String field, Code code) {
		return QueryBuilders.matchQuery(field, code).operator(Operator.AND);
	}

	private static QueryBuilder getQueryBuilder(String field, String bcode) {
		return QueryBuilders.matchQuery(field, bcode).operator(Operator.AND);
	}
}
