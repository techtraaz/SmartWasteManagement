package com.smartwastemanagement.service;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.WasteCollectionDto;
import org.springframework.http.ResponseEntity;

public interface WasteCollectionService {

    ResponseEntity<ApiResponse> WasteCollection(WasteCollectionDto wasteCollectionDto);

}
