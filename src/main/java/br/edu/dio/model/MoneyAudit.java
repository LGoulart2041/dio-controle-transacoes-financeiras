package br.edu.dio.model;

import java.time.OffsetDateTime;
import java.util.UUID;

public record MoneyAudit(
        UUID transactionId,
        BankService targetService,
        String description,
        OffsetDateTime createdAt,
        long amount
) {
}
