package com.smartwastemanagement;


import com.smartwastemanagement.entity.UserCoins;
import com.smartwastemanagement.repository.UserCoinRepo;
import com.smartwastemanagement.service.UserCoinsService;
import com.smartwastemanagement.service.impl.UserCoinsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserCoinServiceTest {

    @Mock
    private UserCoinRepo userCoinRepo;

    @InjectMocks
    private UserCoinsServiceImpl userCoinsService;

    private UserCoins mockUserCoins;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUserCoins = UserCoins.builder()
                .userCoinsId(1)
                .userId(1001)
                .coins(50)
                .build();
    }

    @Test
    void testGetUserCoins_Success() {

        when(userCoinRepo.findUserCoinsByUserId(1001)).thenReturn(mockUserCoins);


        UserCoins result = userCoinsService.getUserCoins(1001);


        assertNotNull(result);
        assertEquals(1001, result.getUserId());
        assertEquals(50, result.getCoins());
        verify(userCoinRepo, times(1)).findUserCoinsByUserId(1001);
    }

    @Test
    void testGetUserCoins_NotFound() {

        when(userCoinRepo.findUserCoinsByUserId(9999)).thenReturn(null);


        UserCoins result = userCoinsService.getUserCoins(9999);


        assertNull(result);
        verify(userCoinRepo, times(1)).findUserCoinsByUserId(9999);
    }

    @Test
    void testAddCoins_Success() {

        int coinsToAdd = 30;
        UserCoins updatedCoins = UserCoins.builder()
                .userCoinsId(1)
                .userId(1001)
                .coins(mockUserCoins.getCoins() + coinsToAdd)
                .build();

        when(userCoinRepo.save(any(UserCoins.class))).thenReturn(updatedCoins);


        userCoinsService.addCoins(mockUserCoins, coinsToAdd);


        assertEquals(80, mockUserCoins.getCoins()); // 50 + 30
        verify(userCoinRepo, times(1)).save(mockUserCoins);
    }

    @Test
    void testAddCoins_AddZero() {

        when(userCoinRepo.save(any(UserCoins.class))).thenReturn(mockUserCoins);


        userCoinsService.addCoins(mockUserCoins, 0);


        assertEquals(50, mockUserCoins.getCoins());
        verify(userCoinRepo, times(1)).save(mockUserCoins);
    }
}
