package com.smartwastemanagement;


import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.WasteCollectionDto;
import com.smartwastemanagement.entity.*;
import com.smartwastemanagement.repository.*;
import com.smartwastemanagement.service.impl.WasteCollectionServiceImpl;
import com.smartwastemanagement.util.SensorDataUtil;
import com.smartwastemanagement.util.strategy.CoinStrategyFactory;
import com.smartwastemanagement.util.strategy.CoinCalculationStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WasteCollectionServiceTest {

    @Mock private SensorRecordRepo sensorRecordRepo;
    @Mock private SensorDataUtil sensorDataUtil;
    @Mock private AccountRepo accountRepo;
    @Mock private UserCoinRepo userCoinRepo;
    @Mock private BinRepo binRepo;
    @Mock private ResidentRepo residentRepo;
    @Mock private BusinessRepo businessRepo;
    @Mock private CoinStrategyFactory coinStrategyFactory;
    @Mock private WasteCollectionRepo wasteCollectionRepo;
    @Mock private CoinCalculationStrategy mockCoinStrategy;

    @InjectMocks
    private WasteCollectionServiceImpl wasteCollectionService;

    private WasteCollectionDto dto;
    private Bin bin;
    private Resident resident;
    private UserCoins userCoins;
    private SensorReading sensorReading;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        dto = new WasteCollectionDto();
        dto.setBinId("BIN001");
        dto.setWasteType("PLASTIC");

        bin = Bin.builder()
                .binId("BIN001")
                .propertyType("RESIDENT")
                .propertyId(10)
                .build();

        resident = Resident.builder()
                .residentId(10)
                .ownerId(101)
                .build();

        userCoins = UserCoins.builder()
                .userCoinsId(1)
                .userId(101)
                .coins(100)
                .build();

        sensorReading = SensorReading.builder()
                .binId("BIN001")
                .weight(5.0)
                .build();
    }


    @Test
    void testWasteCollection_Success() {

        when(binRepo.findBinByBinId("BIN001")).thenReturn(bin);
        when(residentRepo.findResidentByResidentId(10)).thenReturn(resident);
        when(sensorDataUtil.readSensorData("BIN001")).thenReturn(sensorReading);
        when(coinStrategyFactory.getStrategy("PLASTIC")).thenReturn(mockCoinStrategy);
        when(mockCoinStrategy.calculateCoins(5.0)).thenReturn(20);
        when(userCoinRepo.findUserCoinsByUserId(101)).thenReturn(userCoins);
        when(wasteCollectionRepo.save(any(WasteCollection.class))).thenAnswer(i -> i.getArgument(0));


        ResponseEntity<ApiResponse> response = wasteCollectionService.WasteCollection(dto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("00", response.getBody().getStatusCode());
        assertEquals("Collection successful", response.getBody().getMessage());

        WasteCollection wc = (WasteCollection) response.getBody().getContent();
        assertNotNull(wc);
        assertEquals("BIN001", wc.getBinId());
        assertEquals("PLASTIC", wc.getWasteType());
        assertEquals(LocalDate.now(), wc.getCollectionDate());


        verify(userCoinRepo, times(1)).save(userCoins);
        verify(wasteCollectionRepo, times(1)).save(any(WasteCollection.class));
    }


    @Test
    void testWasteCollection_BinNotFound() {
        when(binRepo.findBinByBinId("INVALID")).thenReturn(null);
        dto.setBinId("INVALID");

        ResponseEntity<ApiResponse> response = wasteCollectionService.WasteCollection(dto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("bin must not be null"));
    }


    @Test
    void testWasteCollection_ResidentNotFound() {
        when(binRepo.findBinByBinId("BIN001")).thenReturn(bin);
        when(residentRepo.findResidentByResidentId(10)).thenReturn(null);

        ResponseEntity<ApiResponse> response = wasteCollectionService.WasteCollection(dto);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().getMessage().contains("Resident not found"));
    }


    @Test
    void testWasteCollection_InsufficientCoins() {
        userCoins.setCoins(5);
        when(binRepo.findBinByBinId("BIN001")).thenReturn(bin);
        when(residentRepo.findResidentByResidentId(10)).thenReturn(resident);
        when(sensorDataUtil.readSensorData("BIN001")).thenReturn(sensorReading);
        when(coinStrategyFactory.getStrategy("PLASTIC")).thenReturn(mockCoinStrategy);
        when(mockCoinStrategy.calculateCoins(5.0)).thenReturn(20);
        when(userCoinRepo.findUserCoinsByUserId(101)).thenReturn(userCoins);

        ResponseEntity<ApiResponse> response = wasteCollectionService.WasteCollection(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("01", response.getBody().getStatusCode());
        assertEquals("Insufficient coins", response.getBody().getMessage());
        verify(userCoinRepo, never()).save(any());
        verify(wasteCollectionRepo, never()).save(any());
    }


    @Test
    void testWasteCollection_BusinessOwnerSuccess() {

        bin.setPropertyType("BUSINESS");
        bin.setPropertyId(55);

        Business business = Business.builder()
                .businessId(55)
                .ownerId(202)
                .build();

        userCoins.setUserId(202);
        when(binRepo.findBinByBinId("BIN001")).thenReturn(bin);
        when(businessRepo.findBusinessByBusinessId(55)).thenReturn(business);
        when(sensorDataUtil.readSensorData("BIN001")).thenReturn(sensorReading);
        when(coinStrategyFactory.getStrategy("PLASTIC")).thenReturn(mockCoinStrategy);
        when(mockCoinStrategy.calculateCoins(5.0)).thenReturn(10);
        when(userCoinRepo.findUserCoinsByUserId(202)).thenReturn(userCoins);
        when(wasteCollectionRepo.save(any(WasteCollection.class))).thenAnswer(i -> i.getArgument(0));


        ResponseEntity<ApiResponse> response = wasteCollectionService.WasteCollection(dto);


        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("00", response.getBody().getStatusCode());
        verify(userCoinRepo, times(1)).save(userCoins);
        verify(wasteCollectionRepo, times(1)).save(any(WasteCollection.class));
    }
}
