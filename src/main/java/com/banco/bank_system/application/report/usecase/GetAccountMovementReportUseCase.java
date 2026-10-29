package com.banco.bank_system.application.report.usecase;


import com.banco.bank_system.application.account.util.AccountFinder;
import com.banco.bank_system.application.report.dto.AccountMovementReport;
import com.banco.bank_system.application.report.port.AccountMovementReportRepositoryPort;
import com.banco.bank_system.domain.entities.Account;
import com.banco.bank_system.domain.valueobject.AccountIdentity;
import org.springframework.stereotype.Service;

@Service
public class GetAccountMovementReportUseCase {

    private final AccountMovementReportRepositoryPort reportRepository;
    private final AccountFinder accountFinder;

    public GetAccountMovementReportUseCase(
            AccountMovementReportRepositoryPort reportRepository,
            AccountFinder accountFinder
    ) {
        this.reportRepository = reportRepository;
        this.accountFinder = accountFinder;
    }

    public AccountMovementReport execute(AccountIdentity accountIdentity) {
        Account account = accountFinder.byIdentity(accountIdentity);

        return reportRepository.findMovementReportByAccountId(account.getId());
    }
}
