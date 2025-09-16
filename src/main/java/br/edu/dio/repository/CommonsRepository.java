package br.edu.dio.repository;

import br.edu.dio.exception.NoFundsEngoughException;
import br.edu.dio.model.AccountWallet;
import br.edu.dio.model.Money;
import br.edu.dio.model.MoneyAudit;
import br.edu.dio.model.Wallet;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static br.edu.dio.model.BankService.ACCOUNT;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CommonsRepository {

    public static void  checkFundsForTransaction(final Wallet source, final long amount) {
        if(source.getFunds()< amount) {
            throw new NoFundsEngoughException("Sua conta não tem dinheiro o suficiente para realizar essa transação.");
        }
    }
}
