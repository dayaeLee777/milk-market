package com.mk.elastic.document;

import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mk.api.dto.request.ItemModifyRequestDto;
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

	@Enumerated(EnumType.STRING)
	private Code status;
	
	private int price;
	
	private String description;
	
	private String bcode;
	
	private String bname;

    @JsonFormat(pattern = "yyyy-MM-dd")
	private Date regDate;

	private String regDateTime;

	private String rentStartDate;

	private String rentEndDate;
    
    private Map<String, String> fileNameList;

	public void setRentDate(String rentStartDate, String rentEndDate) {
		this.rentStartDate = rentStartDate;
		this.rentEndDate = rentEndDate;
	}
	
	public void modifyItemSearch(ItemModifyRequestDto itemModifyRequestDto) {
		DateTimeFormatter rentDateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		
		this.division = itemModifyRequestDto.getDivision();
		this.itemName = itemModifyRequestDto.getItemName();
		this.category = itemModifyRequestDto.getCategory();
		this.price = itemModifyRequestDto.getPrice();
		this.description = itemModifyRequestDto.getDescription();
		
		if(itemModifyRequestDto.getDivision() == Code.A01) {
			this.rentStartDate = itemModifyRequestDto.getRentStartDate().format(rentDateTimeFormatter);
			this.rentEndDate = itemModifyRequestDto.getRentEndDate().format(rentDateTimeFormatter);			
		} else {
			this.rentStartDate = null;
			this.rentEndDate = null;			
		}
	}
	
	public void modifyItemSearchImages(Map<String, String> fileNameList) {
		this.fileNameList = fileNameList;
	}
	
}
