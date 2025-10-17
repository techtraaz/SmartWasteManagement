package com.smartwastemanagement.repository;

import com.smartwastemanagement.entity.SensorReading;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SensorRecordRepo extends JpaRepository<SensorReading,String> {

    SensorReading getSensorReadingByBinId(String binId);

}
