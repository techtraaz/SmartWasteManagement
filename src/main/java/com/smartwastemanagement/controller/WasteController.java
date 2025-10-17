package com.smartwastemanagement.controller;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.WasteCollectionDto;
import com.smartwastemanagement.entity.Bin;
import com.smartwastemanagement.service.BinService;
import com.smartwastemanagement.service.WasteCollectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/waste")
@RequiredArgsConstructor
public class WasteController {

    private final BinService binService;
    private final WasteCollectionService  wasteCollectionService;

    @PostMapping("/sensor-reading/{binId}")
    public ResponseEntity<ApiResponse> getSensorReading(@PathVariable("binId") String binId) {
        return binService.readBinData(binId);
    }

    @PostMapping("/collect")
    public ResponseEntity<ApiResponse> collect(@RequestBody WasteCollectionDto  wasteCollectionDto) {
        return wasteCollectionService.WasteCollection(wasteCollectionDto);
    }


}
