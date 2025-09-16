package br.edu.dio.model;

import lombok.Getter;
import lombok.ToString;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;

import static br.edu.dio.model.BankService.INVESTMENT;

@ToString
@Getter
public class InvestmentWallet extends Wallet {

    private final Investment investment;
    private final AccountWallet  account;

    public InvestmentWallet(final Investment investment, final AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investment = investment;
        this.account = account;
        addMoney(account.reduceMoney(amount, "investimento"), getServiceType(), "Investimento");
    }

    public void updateAmount(final long percent){
        var amount = getFunds() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getServiceType(), "Rendimentos", OffsetDateTime.now(), amount);
        var money = Stream.generate(() -> new Money(history)).limit(amount).toList();
        this.money.addAll(money);
    }
}
