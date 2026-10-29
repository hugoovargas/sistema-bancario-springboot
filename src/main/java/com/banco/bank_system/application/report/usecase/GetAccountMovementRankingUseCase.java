package com.banco.bank_system.application.report.usecase;

import com.banco.bank_system.application.report.dto.AccountMovementRanking;
import com.banco.bank_system.application.report.port.AccountMovementRankingRepositoryPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAccountMovementRankingUseCase {

    private final AccountMovementRankingRepositoryPort rankingRepository;

    public GetAccountMovementRankingUseCase(
            AccountMovementRankingRepositoryPort rankingRepository
    ) {
        this.rankingRepository = rankingRepository;
    }

    public List<AccountMovementRanking> execute() {
        return rankingRepository.findAccountMovementRanking();
    }
}