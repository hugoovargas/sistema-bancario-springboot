package com.banco.bank_system.infrastructure.database.sql;

import com.banco.bank_system.infrastructure.database.entities.TransactionEntity;
import com.banco.bank_system.infrastructure.database.projection.AccountMovementRankingProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.UUID;

public interface JpaAccountMovementRankingRepository
        extends Repository<TransactionEntity, UUID> {

    @Query(value = """
        SELECT
            t.account_id AS "accountId",
            a.agency_number AS "branch",
            a.account_number AS "accountNumber",
            COUNT(*) AS "quantidadeOperacoes",
            COALESCE(SUM(t.amount), 0) AS "volumeMovimentado"
        FROM tb_transactions t
        INNER JOIN tb_accounts a
            ON t.account_id = a.id
        GROUP BY
            t.account_id,
            a.agency_number,
            a.account_number
        ORDER BY "volumeMovimentado" DESC
        """, nativeQuery = true)
    List<AccountMovementRankingProjection> findAccountMovementRanking();
}