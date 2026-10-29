package com.banco.bank_system.presentation.controller;

import com.banco.bank_system.application.report.dto.AccountMovementReport;
import com.banco.bank_system.application.report.usecase.GetAccountMovementReportUseCase;
import com.banco.bank_system.domain.valueobject.AccountIdentity;
import com.banco.bank_system.presentation.dto.response.report.AccountMovementReportResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final GetAccountMovementReportUseCase getAccountMovementReportUseCase;

    public ReportController(
            GetAccountMovementReportUseCase getAccountMovementReportUseCase
    ) {
        this.getAccountMovementReportUseCase = getAccountMovementReportUseCase;
    }

    @GetMapping("/accounts/{branch}/{accountNumber}/movements")
    public ResponseEntity<AccountMovementReportResponse> getAccountMovementReport(
            @PathVariable String branch,
            @PathVariable String accountNumber
    ) {
        AccountIdentity accountIdentity = new AccountIdentity(branch, accountNumber);

        AccountMovementReport report =
                getAccountMovementReportUseCase.execute(accountIdentity);

        AccountMovementReportResponse response =
                AccountMovementReportResponse.from(report);

        return ResponseEntity.ok(response);
    }
}