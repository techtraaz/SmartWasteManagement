package com.smartwastemanagement.util;

import com.smartwastemanagement.entity.SensorReading;
import com.smartwastemanagement.repository.SensorRecordRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SensorDataUtil {

    private final SensorRecordRepo sensorRecordRepo;


    public Object readSensorData(String binId) {
        SensorReading sensorReading = sensorRecordRepo.getSensorReadingByBinId(binId);

        if (sensorReading == null) {
            return "No readings found for the given binId";
        }
        return sensorReading;
    }
}
