package com.hubo.gillajabi.location.domain.service.constant;

import lombok.Getter;

@Getter
public enum LocationCollectionType {

    GPS("GPS");

    private final String description;

    LocationCollectionType(String description){
        this.description = description;
    }
}