package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.service.BinService;
import com.smartwastemanagement.util.SensorDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BinServiceImpl implements BinService {

    private final SensorDataUtil sensorDataUtil;


    @Override
    public ResponseEntity<ApiResponse> readBinData(String binId) {

        Object sensorReading = sensorDataUtil.readSensorData(binId);

        ApiResponse apiResponse = new ApiResponse("02", "Garbage Bin Sensor Reading", sensorReading);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }
}
