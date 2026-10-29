package com.banco.bank_system.infrastructure.database.adapters;

import com.banco.bank_system.application.report.dto.AccountMovementRanking;
import com.banco.bank_system.application.report.port.AccountMovementRankingRepositoryPort;
import com.banco.bank_system.infrastructure.database.projection.AccountMovementRankingProjection;
import com.banco.bank_system.infrastructure.database.sql.JpaAccountMovementRankingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AccountMovementRankingAdapter
        implements AccountMovementRankingRepositoryPort {

    private final JpaAccountMovementRankingRepository repository;

    public AccountMovementRankingAdapter(
            JpaAccountMovementRankingRepository repository
    ) {
        this.repository = repository;
    }

    @Override
    public List<AccountMovementRanking> findAccountMovementRanking() {
        List<AccountMovementRankingProjection> projections =
                repository.findAccountMovementRanking();

        return projections.stream()
                .map(projection -> new AccountMovementRanking(
                        projection.getAccountId(),
                        projection.getBranch(),
                        projection.getAccountNumber(),
                        projection.getQuantidadeOperacoes(),
                        projection.getVolumeMovimentado()
                ))
                .toList();
    }
}