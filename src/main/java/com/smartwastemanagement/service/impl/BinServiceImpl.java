package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.SensorReading;
import com.smartwastemanagement.repository.SensorRecordRepo;
import com.smartwastemanagement.service.BinService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BinServiceImpl implements BinService {

    private final SensorRecordRepo sensorrepo;

    @Override
    public ResponseEntity<ApiResponse> addBin() {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> getBinsByPropertyId(Integer propertyId) {
        return null;
    }

    @Override
    public ResponseEntity<ApiResponse> readBinData(String binId){

        SensorReading sensorReading = sensorrepo.getSensorReadingByBinId(binId);

        if(sensorReading == null){
            ApiResponse apiResponse = new ApiResponse("00","no such bin exist",null);
            return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
        }

        ApiResponse apiResponse = new ApiResponse("02","Garbage Bin Sensor Reading",sensorReading);
        return ResponseEntity.status(HttpStatus.OK).body(apiResponse);
    }

}
