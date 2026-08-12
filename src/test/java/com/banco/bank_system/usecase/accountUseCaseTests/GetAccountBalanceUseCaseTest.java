package com.banco.bank_system.usecase.accountUseCaseTests;

import com.banco.bank_system.application.account.dto.GetBalanceOutput;
import com.banco.bank_system.application.account.usecases.GetAccountBalanceUseCase;
import com.banco.bank_system.application.account.util.AccountFinder;
import com.banco.bank_system.application.exception.AccountNotFoundException;
import com.banco.bank_system.domain.entities.Account;
import com.banco.bank_system.domain.valueobject.AccountIdentity;
import com.banco.bank_system.domain.valueobject.Money;
import com.banco.bank_system.entities.helper.AccountFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class GetAccountBalanceUseCaseTest {

    @Mock
    private AccountFinder finder;

    @InjectMocks
    private GetAccountBalanceUseCase useCase;

    @Test
    void shouldReturnBalance() {

        Account account =
                AccountFactory.checking(Clock.systemUTC());

        account.deposit(Money.of("500"));

        when(finder.byIdentity(
                account.getAccountIdentity()))
                .thenReturn(account);

        GetBalanceOutput output =
                useCase.execute(account.getAccountIdentity());

        assertEquals(
                Money.of("500"),
                output.balance()
        );
    }
}