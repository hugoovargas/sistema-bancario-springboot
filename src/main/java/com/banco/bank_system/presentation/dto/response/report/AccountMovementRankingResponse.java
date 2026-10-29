package com.banco.bank_system.presentation.dto.response.report;

import com.banco.bank_system.application.report.dto.AccountMovementRanking;

import java.math.BigDecimal;
import java.util.UUID;

public record AccountMovementRankingResponse(
        UUID accountId,
        String branch,
        String accountNumber,
        long quantidadeOperacoes,
        BigDecimal volumeMovimentado
) {

    public static AccountMovementRankingResponse from(
            AccountMovementRanking ranking
    ) {
        return new AccountMovementRankingResponse(
                ranking.accountId(),
                ranking.branch(),
                ranking.accountNumber(),
                ranking.quantidadeOperacoes(),
                ranking.volumeMovimentado()
        );
    }
}