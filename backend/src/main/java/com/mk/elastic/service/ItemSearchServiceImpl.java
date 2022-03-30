package com.mk.elastic.service;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.api.service.ItemImageService;
import com.mk.db.code.Code;
import com.mk.db.entity.Item;
import com.mk.db.repository.ItemImageRepository;
import com.mk.db.repository.ItemRepository;
import com.mk.elastic.document.Itemsearch;
import com.mk.elastic.helper.Indices;
import com.mk.elastic.search.SearchRequestDTO;
import com.mk.elastic.search.util.SearchUtil;

import lombok.RequiredArgsConstructor;

@Service("ItemSearchService")
@RequiredArgsConstructor
public class ItemSearchServiceImpl implements ItemSearchService {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private static final Logger LOG = LoggerFactory.getLogger(ItemSearchService.class);

	private final RestHighLevelClient client;

	private final ItemRepository itemRepository;

	private final ItemImageRepository itemImageRepository;

	private final ItemImageService itemImageService;

	public List<Itemsearch> search(final SearchRequestDTO dto) {
		final SearchRequest request = SearchUtil.buildSearchRequest(Indices.ITEMSEARCH_INDEX, dto);

		return searchInternal(request);
	}

	private List<Itemsearch> searchInternal(final SearchRequest request) {
		if (request == null) {
			LOG.error("Failed to build search request");
			return Collections.emptyList();
		}

		try {
			final SearchResponse response = client.search(request, RequestOptions.DEFAULT);

			final SearchHit[] searchHits = response.getHits().getHits();
			final List<Itemsearch> itemsearches = new ArrayList<>(searchHits.length);
			for (SearchHit hit : searchHits) {
				itemsearches.add(MAPPER.readValue(hit.getSourceAsString(), Itemsearch.class));
			}

			return itemsearches;
		} catch (Exception e) {
			LOG.error(e.getMessage(), e);
			return Collections.emptyList();
		}
	}

	@Override
	public Boolean index(Itemsearch itemSearch) {
		try {
			final String itemSearchAsString = MAPPER.writeValueAsString(itemSearch);

			final IndexRequest request = new IndexRequest(Indices.ITEMSEARCH_INDEX);
			request.id(itemSearch.getId());
			request.source(itemSearchAsString, XContentType.JSON);

			final IndexResponse response = client.index(request, RequestOptions.DEFAULT);

			return response != null && response.status().equals(RestStatus.OK);
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			return false;
		}
	}

	@Override
	public Itemsearch getById(String itemSearchId) {
		try {
			final GetResponse documentFields = client.get(new GetRequest(Indices.ITEMSEARCH_INDEX, itemSearchId),
					RequestOptions.DEFAULT);
			if (documentFields == null || documentFields.isSourceEmpty()) {
				return null;
			}

			return MAPPER.readValue(documentFields.getSourceAsString(), Itemsearch.class);
		} catch (final Exception e) {
			LOG.error(e.getMessage(), e);
			return null;
		}
	}

	@Override
	public Boolean registerItemSearch(String itemId) {
		Item item = itemRepository.findById(itemId).orElse(null);

		if (item == null)
			return false;

		Map<String, String> itemImageList = new HashMap<String, String>();

		itemImageRepository.findByItem(item).forEach(file -> {
			String originFilename = file.getOriginFileName();
			String newFilename = file.getNewFileName();
			itemImageList.put(originFilename, itemImageService.getImagePath(newFilename));
		});

		DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd KK:mm");
		
		Date date = Date.from(item.getRegDate().atZone(ZoneId.systemDefault()).toInstant());
		
		System.out.println(item.getRegDate());
		Itemsearch itemSearch = Itemsearch.builder()
				.id(itemId)
				.division(item.getDivision())
				.itemName(item.getItemName())
				.category(item.getCategory())
				.price(item.getPrice())
				.description(item.getDescription())
				.bcode(item.getUser().getBcode())
				.bname(item.getUser().getBname())
				.regDate(date)
				.fileNameList(itemImageList)
				.build();

		if (item.getDivision() == Code.A01)
			itemSearch.setRentDate(item.getRentStartDate().format(rentDateTimeFormatter),
					item.getRentEndDate().format(rentDateTimeFormatter));

		return index(itemSearch);
	}

}