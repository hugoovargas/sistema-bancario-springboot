
package com.banco.bank_system.infrastructure.database.adapters;

import com.banco.bank_system.application.report.dto.AccountMovementReport;
import com.banco.bank_system.application.report.port.AccountMovementReportRepositoryPort;
import com.banco.bank_system.domain.valueobject.AccountId;
import com.banco.bank_system.infrastructure.database.projection.AccountMovementReportProjection;
import com.banco.bank_system.infrastructure.database.sql.JpaAccountMovementReportRepository;
import org.springframework.stereotype.Repository;

@Repository
public class AccountMovementReportAdapter
        implements AccountMovementReportRepositoryPort {

    private final JpaAccountMovementReportRepository repository;

    public AccountMovementReportAdapter(
            JpaAccountMovementReportRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public AccountMovementReport findMovementReportByAccountId(
            AccountId accountId
    ) {
        AccountMovementReportProjection projection = repository.findMovementReportByAccountId(accountId.id());

        return new AccountMovementReport(
                projection.getAccountId(),
                projection.getQuantidadeDepositos(),
                projection.getTotalDepositado(),
                projection.getQuantidadeSaques(),
                projection.getTotalSacado(),
                projection.getQuantidadeTransferenciasEnviadas(),
                projection.getTotalTransferido(),
                projection.getQuantidadeTransferenciasRecebidas(),
                projection.getTotalRecebido()
        );
    }
}
