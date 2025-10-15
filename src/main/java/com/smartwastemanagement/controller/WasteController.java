package com.smartwastemanagement.controller;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.service.BinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/waste")
@RequiredArgsConstructor
public class WasteController {

    private final BinService binService;

    @PostMapping("/sensor-reading/{binId}")
    public ResponseEntity<ApiResponse> getSensorReading(@PathVariable("binId") String binId) {
        return binService.readBinData(binId);
    }


}
