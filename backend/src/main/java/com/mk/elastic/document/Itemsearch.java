package com.mk.elastic.document;

import java.util.Date;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mk.db.code.Code;
import com.mk.elastic.helper.Indices;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = Indices.ITEMSEARCH_INDEX)
@Setting(settingPath = "static/es-settings.json")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Itemsearch {
	
	private String id;
	
	@Enumerated(EnumType.STRING)
	private Code division;

	private String itemName;

	@Enumerated(EnumType.STRING)
	private Code category;
	
	private int price;
	
	private String description;
	
	private String bcode;
	
	private String bname;

    @JsonFormat(pattern = "yyyy-MM-dd")
	private Date regDate;

	private String rentStartDate;

	private String rentEndDate;
    
    private Map<String, String> fileNameList;

	public void setRentDate(String rentStartDate, String rentEndDate) {
		this.rentStartDate = rentStartDate;
		this.rentEndDate = rentEndDate;
	}
	
}
