package com.banco.bank_system.usecase.accountUseCaseTests;

import com.banco.bank_system.application.account.port.AccountRepositoryPort;
import com.banco.bank_system.application.account.usecases.RemoveAccountUseCase;
import com.banco.bank_system.application.account.util.AccountFinder;
import com.banco.bank_system.application.exception.CannotRemoveAccountException;
import com.banco.bank_system.domain.entities.Account;
import com.banco.bank_system.domain.valueobject.Money;
import com.banco.bank_system.entities.helper.AccountFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RemoveAccountUseCaseTest {

    @Mock
    private AccountRepositoryPort accountRepository;

    @Mock
    private AccountFinder finder;

    @InjectMocks
    private RemoveAccountUseCase useCase;

    @Test
    void shouldRemoveAccount() {

        Account account = AccountFactory.checking(Clock.systemUTC());

        when(finder.byIdentity(
                account.getAccountIdentity()))
                .thenReturn(account);

        useCase.execute(account.getAccountIdentity());

        verify(finder)
                .byIdentity(account.getAccountIdentity());
    }

    @Test
    void shouldNotRemoveAccountWithActiveBalance() {

        Account account =
                AccountFactory.checking(Clock.systemUTC());

        account.deposit(Money.of("100"));

        when(finder.byIdentity(
                account.getAccountIdentity()))
                .thenReturn(account);

        assertThrows(
                CannotRemoveAccountException.class,
                () -> useCase.execute(account.getAccountIdentity())
        );
    }
}