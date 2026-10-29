package com.banco.bank_system.application.report.port;

import com.banco.bank_system.application.report.dto.AccountMovementRanking;

import java.util.List;

public interface AccountMovementRankingRepositoryPort {

    List<AccountMovementRanking> findAccountMovementRanking();
}