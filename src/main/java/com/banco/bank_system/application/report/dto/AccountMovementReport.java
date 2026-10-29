
package com.banco.bank_system.application.report.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountMovementReport(
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
}
