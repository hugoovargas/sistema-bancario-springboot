
package com.banco.bank_system.infrastructure.database.sql;

import com.banco.bank_system.infrastructure.database.entities.TransactionEntity;
import com.banco.bank_system.infrastructure.database.projection.AccountMovementReportProjection;
import org.springframework.data.repository.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface JpaAccountMovementReportRepository
        extends Repository<TransactionEntity, UUID> {

    @Query(value =
            """
            SELECT
                CAST(:accountId AS uuid) AS "accountId",

                COUNT(*) FILTER (
                    WHERE type = 'DEPOSIT'
                ) AS "quantidadeDepositos",

                COALESCE(
                    SUM(amount) FILTER (
                        WHERE type = 'DEPOSIT'
                    ), 0
                ) AS "totalDepositado",

                COUNT(*) FILTER (
                    WHERE type = 'WITHDRAW'
                ) AS "quantidadeSaques",

                COALESCE(
                    SUM(amount) FILTER (
                        WHERE type = 'WITHDRAW'
                    ), 0
                ) AS "totalSacado",

                COUNT(*) FILTER (
                    WHERE type = 'TRANSFER_SENT'
                ) AS "quantidadeTransferenciasEnviadas",

                COALESCE(
                    SUM(amount) FILTER (
                        WHERE type = 'TRANSFER_SENT'
                    ), 0
                ) AS "totalTransferido",

                COUNT(*) FILTER (
                    WHERE type = 'TRANSFER_RECEIVED'
                ) AS "quantidadeTransferenciasRecebidas",

                COALESCE(
                    SUM(amount) FILTER (
                        WHERE type = 'TRANSFER_RECEIVED'
                    ), 0
                ) AS "totalRecebido"

            FROM tb_transactions
            WHERE account_id = :accountId""", nativeQuery = true)
    AccountMovementReportProjection findMovementReportByAccountId(
            @Param("accountId") UUID accountId
    );
}
