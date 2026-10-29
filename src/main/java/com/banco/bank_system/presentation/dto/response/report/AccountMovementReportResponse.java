package com.banco.bank_system.presentation.dto.response.report;

import com.banco.bank_system.application.report.dto.AccountMovementReport;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountMovementReportResponse(
        UUID accountId,
        long quantidadeDepositos,
        BigDecimal totalDepositado,
        long quantidadeSaques,
        BigDecimal totalSacado,
        long quantidadeTransferenciasEnviadas,
        BigDecimal totalTransferido,
        long quantidadeTransferenciasRecebidas,
        BigDecimal totalRecebido
) {

    public static AccountMovementReportResponse from(
            AccountMovementReport report
    ) {
        return new AccountMovementReportResponse(
                report.accountId(),
                report.quantidadeDepositos(),
                report.totalDepositado(),
                report.quantidadeSaques(),
                report.totalSacado(),
                report.quantidadeTransferenciasEnviadas(),
                report.totalTransferido(),
                report.quantidadeTransferenciasRecebidas(),
                report.totalRecebido()
        );
    }
}