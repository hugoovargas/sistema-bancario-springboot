package com.banco.bank_system.infrastructure.database.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountMovementRankingProjection {

    UUID getAccountId();

    String getBranch();

    String getAccountNumber();

    Long getQuantidadeOperacoes();

    BigDecimal getVolumeMovimentado();
}