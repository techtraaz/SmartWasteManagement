package com.smartwastemanagement.service.impl;

import com.smartwastemanagement.dto.ApiResponse;
import com.smartwastemanagement.dto.WasteCollectionDto;
import com.smartwastemanagement.entity.*;
import com.smartwastemanagement.repository.*;
import com.smartwastemanagement.service.WasteCollectionService;
import com.smartwastemanagement.util.strategy.CoinStrategyFactory;
import com.smartwastemanagement.util.SensorDataUtil;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Builder
public class WasteCollectionServiceImpl implements WasteCollectionService {

    private final SensorRecordRepo sensorRecordRepo;
    private final SensorDataUtil sensorDataUtil;
    private final AccountRepo accountRepo;
    private final UserCoinRepo userCoinRepo;
    private final BinRepo binRepo;
    private final ResidentRepo residentRepo;
    private final BusinessRepo businessRepo;
    private final CoinStrategyFactory coinStrategyFactory;
    private final WasteCollectionRepo  wasteCollectionRepo;

    @Override
    public ResponseEntity<ApiResponse> WasteCollection(WasteCollectionDto dto) {
        try {
            Bin bin = getBin(dto.getBinId());
            Integer ownerId = getOwnerId(bin);

            double weight = getWeightFromSensor(dto.getBinId());
            int requiredCoins = calculateCoinDeduction(dto.getWasteType(), weight);

            UserCoins userCoins = userCoinRepo.findUserCoinsByUserId(ownerId);
            if (userCoins.getCoins() < requiredCoins) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse("01", "Insufficient coins", null));
            }

            deductUserCoins(userCoins, requiredCoins);
            WasteCollection wc = recordCollection(dto, bin, ownerId, weight);

            return ResponseEntity.ok(new ApiResponse("00", "Collection successful", wc));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("99", "Error: " + e.getMessage(), null));
        }
    }

    private Bin getBin(String binId) {
        return binRepo.findBinByBinId(binId);
    }

    private Integer getOwnerId(Bin bin) {
        if (bin == null) throw new IllegalArgumentException("bin must not be null");

        String type = bin.getPropertyType();
        Integer propId = bin.getPropertyId();
        if (type == null || propId == null) throw new IllegalStateException("bin missing propertyType/propertyId");

        if ("RESIDENT".equalsIgnoreCase(type)) {
            Resident resident = residentRepo.findResidentByResidentId(propId);
            if (resident == null) throw new EntityNotFoundException("Resident not found: " + propId);
            return resident.getOwnerId();
        } else {
            Business business = businessRepo.findBusinessByBusinessId(propId);
            if (business == null) throw new EntityNotFoundException("Business not found: " + propId);
            return business.getOwnerId();
        }
    }


    private double getWeightFromSensor(String binId) {
        SensorReading reading = (SensorReading) sensorDataUtil.readSensorData(binId);
        return reading.getWeight();
    }

    private int calculateCoinDeduction(String wasteType, double weight) {
        return coinStrategyFactory.getStrategy(wasteType).calculateCoins(weight);
    }

    private void deductUserCoins(UserCoins userCoins, int coinsToDeduct) {
        userCoins.setCoins(userCoins.getCoins() - coinsToDeduct);
        userCoinRepo.save(userCoins);
    }

    private WasteCollection recordCollection(WasteCollectionDto dto, Bin bin, Integer ownerId, double weight) {
        WasteCollection wc = WasteCollection.builder()
                .collectionId(UUID.randomUUID().toString())
                .binId(bin.getBinId())
                .collectionDate(LocalDate.now())
                .wasteType(dto.getWasteType())
                .quantity(weight)
                .ownerId(ownerId)
                .build();
        wasteCollectionRepo.save(wc);
        return wc;

    }
}
