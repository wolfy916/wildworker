package com.a304.wildworker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.exception.PaperTooLowException;
import java.io.IOException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.crypto.CipherException;

@ExtendWith(MockitoExtension.class)
public class MiningServiceTest {

    @Mock
    private Bank bank;

    @Mock
    private UserRepository userRepository;

    @Mock
    private StationRepository stationRepository;

    @InjectMocks
    private MiningService miningService;

    @Test
    @DisplayName("종이가 충분히 모이지 않을 때 sellPaper를 호출하면 예외가 발생한다")
    public void sellPaper_shouldThrowExceptionWhenNumberOfCollectedPaperIsTooLow() {
        // Arrange
        Long userId = 1L;
        User user = new User("test");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Act & Assert
        assertThrows(PaperTooLowException.class, () -> miningService.sellPaper(userId));
    }

    @Test
    @DisplayName("sellPaper 호출 시 내부적으로 bank.manualMine이 호출된다")
    public void sellPaper_shouldCallBankAndUserRepositoryWhenNumberOfCollectedPaperIsSufficient()
            throws CipherException, IOException {
        // Arrange
        Long userId = 1L;
        User user = new User("test");
        for (int i = 0; i < 100; i++) {
            user.collectPaper();
        }
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Act
        miningService.sellPaper(userId);

        // Assert
        verify(userRepository).findById(userId);
        verify(bank).manualMine(user);
        assertEquals(0, user.getNumberOfCollectedPaper());
    }

    @Test
    @DisplayName("giveWonFromStationToUser 호출 시 bank.autoMine이 호출된다.")
    public void giveWonFromStationToUser_shouldCallBankAndStationRepository()
            throws CipherException, IOException {
        // Arrange
        Long stationId = 1L;
        Long userId = 2L;
        Station station = new Station();
        when(stationRepository.findById(stationId)).thenReturn(java.util.Optional.of(station));
        User user = new User("test");
        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        // Act
        miningService.giveWonFromStationToUser(stationId, userId);

        // Assert
        verify(stationRepository).findById(stationId);
        verify(userRepository).findById(userId);
        verify(bank).autoMine(station, user);
    }

}
