package com.banco.bank_system.application.report.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountMovementRanking(
        UUID accountId,
        String branch,
        String accountNumber,
        long quantidadeOperacoes,
        BigDecimal volumeMovimentado
) {
}