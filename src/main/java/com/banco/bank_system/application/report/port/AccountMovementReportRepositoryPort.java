
package com.banco.bank_system.application.report.port;

import com.banco.bank_system.application.report.dto.AccountMovementReport;
import com.banco.bank_system.domain.valueobject.AccountId;

public interface AccountMovementReportRepositoryPort {

    AccountMovementReport findMovementReportByAccountId(AccountId accountId);
}
