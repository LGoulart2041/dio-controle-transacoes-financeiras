package br.edu.dio.model;

import lombok.Getter;

import java.util.Set;

import static br.edu.dio.model.BankService.ACCOUNT;

@Getter
public class AccountWallet extends Wallet {

    private final Set<String> pix;

    public AccountWallet(final Set<String> pix) {
        super(ACCOUNT);
        this.pix = pix;

    }

    public AccountWallet(final long amount, Set<String> pix) {
        super(ACCOUNT);
        this.pix = pix;
        addMoney(amount, "Valor de criacao da conta: ");
    }

    public void addMoney(final long amount, final String description){
        var money = generateMoney(amount, description);
        super.addMoney(money, getServiceType(), description);
    }

    @Override
    public String toString() {
        return super.toString() + "AccountWallet{" +
                "pix=" + pix +
                '}';
    }
}
