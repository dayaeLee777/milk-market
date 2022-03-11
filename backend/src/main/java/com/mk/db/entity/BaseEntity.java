package com.mk.db.entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
public class BaseEntity {
	
    @Id
    @GeneratedValue(generator = "uuid2") // uuid2 => uuid로 생성 자동으로,
    @GenericGenerator(name = "uuid2" , strategy = "uuid2")
    @Column(length = 36)
    private String id;
    
	public String getId() {
		return id;
	}

}
