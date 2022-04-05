package com.mk.db.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.mk.db.code.Code;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@Entity
@NoArgsConstructor
public class Purchase extends BaseEntity{


    private String itemId;
    private String orderItemName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Purchase(String itemId, String orderItemName, User user) {
        this.itemId = itemId;
        this.orderItemName = orderItemName;
        this.user = user;
    }
}
