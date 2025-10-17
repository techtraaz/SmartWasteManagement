package com.smartwastemanagement;


import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.entity.SensorReading;
import com.smartwastemanagement.service.impl.BinServiceImpl;
import com.smartwastemanagement.util.SensorDataUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BinServiceTest {

    @Mock
    private SensorDataUtil sensorDataUtil;

    @InjectMocks
    private BinServiceImpl binService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testReadBinData_WithSensorReading() {

        String binId = "BIN123";
        SensorReading mockReading = new SensorReading();
        mockReading.setBinId(binId);
        mockReading.setReadingId("001");
        mockReading.setWeight(1.0);

        when(sensorDataUtil.readSensorData(binId)).thenReturn(mockReading);


        ResponseEntity<ApiResponse> response = binService.readBinData(binId);


        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("02", response.getBody().getStatusCode());
        assertEquals("Garbage Bin Sensor Reading", response.getBody().getMessage());
        assertEquals(mockReading, response.getBody().getContent());

        verify(sensorDataUtil, times(1)).readSensorData(binId);
    }

    @Test
    void testReadBinData_NoReadingFound() {

        String binId = "BIN404";
        when(sensorDataUtil.readSensorData(binId))
                .thenReturn("No readings found for the given binId");


        ResponseEntity<ApiResponse> response = binService.readBinData(binId);


        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("02", response.getBody().getStatusCode());
        assertEquals("Garbage Bin Sensor Reading", response.getBody().getMessage());
        assertEquals("No readings found for the given binId", response.getBody().getContent());

        verify(sensorDataUtil, times(1)).readSensorData(binId);
    }
}
