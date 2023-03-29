package com.a304.wildworker.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.a304.wildworker.domain.station.Station;
import com.a304.wildworker.domain.station.StationRepository;
import com.a304.wildworker.domain.user.User;
import com.a304.wildworker.domain.user.UserRepository;
import com.a304.wildworker.ethereum.contract.Bank;
import com.a304.wildworker.exception.UserNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.web3j.crypto.CipherException;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

@ExtendWith(MockitoExtension.class)
public class InvestServiceTest {

    private final StationRepository stationRepository;
    private final UserRepository userRepository;
    private final Bank bank;
    private final InvestService investService;

    public InvestServiceTest(@Mock StationRepository stationRepository,
            @Mock UserRepository userRepository, @Mock Bank bank) {
        this.stationRepository = stationRepository;
        this.userRepository = userRepository;
        this.bank = bank;
        this.investService = new InvestService(stationRepository, userRepository, bank);
    }

    @Test
    @DisplayName("investToStation 메소드가 정상적으로 작동하는지 확인")
    public void testInvestToStation() throws CipherException, IOException {
        Long stationId = 1L;
        Long userId = 2L;
        Long amount = 100L;

        Station station = new Station();
        User user = new User("Test");
        user.addBalance(amount);
        when(stationRepository.findById(stationId)).thenReturn(Optional.of(station));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        CompletableFuture<TransactionReceipt> receiptFuture = new CompletableFuture<>();
        TransactionReceipt receipt = new TransactionReceipt();
        receiptFuture.complete(receipt);
        when(bank.invest(station, user, amount)).thenReturn(receiptFuture);

        investService.investToStation(stationId, userId, amount);

        verify(bank, times(1)).invest(station, user, amount);
        assertThat(user.getBalance()).isEqualTo(0L);
    }

    @Test
    @DisplayName("UserNotFoundException이 발생하는 경우 확인")
    public void testInvestToStationWithUserNotFoundException() {
        Long stationId = 1L;
        Long userId = 2L;
        Long amount = 100L;

        when(stationRepository.findById(stationId)).thenReturn(Optional.of(new Station()));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> investService.investToStation(stationId, userId, amount));
    }

}
