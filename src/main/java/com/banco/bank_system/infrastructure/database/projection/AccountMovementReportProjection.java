package com.banco.bank_system.infrastructure.database.projection;

import java.math.BigDecimal;
import java.util.UUID;

public interface AccountMovementReportProjection {

    UUID getAccountId();

    Long getQuantidadeDepositos();

    BigDecimal getTotalDepositado();

    Long getQuantidadeSaques();

    BigDecimal getTotalSacado();

    Long getQuantidadeTransferenciasEnviadas();

    BigDecimal getTotalTransferido();

    Long getQuantidadeTransferenciasRecebidas();

    BigDecimal getTotalRecebido();
}
