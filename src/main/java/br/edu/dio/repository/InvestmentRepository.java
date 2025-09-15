package br.edu.dio.repository;

import br.edu.dio.exception.AccountWithInvestmentException;
import br.edu.dio.exception.InvestmentNotFoundException;
import br.edu.dio.exception.PixInUseException;
import br.edu.dio.exception.WalletNotFoundException;
import br.edu.dio.model.AccountWallet;
import br.edu.dio.model.Investment;
import br.edu.dio.model.InvestmentWallet;

import java.util.ArrayList;
import java.util.List;

import static br.edu.dio.repository.CommonsRepository.checkFundsForTransaction;

public class InvestmentRepository {

    private final List<Investment> investments = new ArrayList<>();
    private final List<InvestmentWallet> wallets = new ArrayList<>();
    private long nextId;


    public Investment create(final long tax, final long initialFunds){
        this.nextId++;
        var investment = new Investment(this.nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestmentWallet initInvestment(final AccountWallet account, final long id){
        var accountsInUse = wallets.stream().map(InvestmentWallet::getAccount).toList();
        if(accountsInUse.contains(account)){
            throw new AccountWithInvestmentException("A conta '" + account + "' já possui um investimento.");
        }


        var investment = findById(id);
        checkFundsForTransaction(account, investment.initialFunds());
        var wallet = new InvestmentWallet(investment, account, investment.initialFunds());
        return wallet;
    }

    public InvestmentWallet deposit(final String pix,final long funds){
        var wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getServiceType(), "Investimento");
        return wallet;
    }

    public InvestmentWallet withdraw(final String pix, final long funds){
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getServiceType(), "saque de investimentos");
        if(wallet.getFunds()== 0){
            wallets.remove(wallet);
        }
        return wallet;
    }

    public void updateAmount(){
        wallets.forEach(w -> w.updateAmount(w.getInvestment().tax()));
    }

    public Investment findById(final long id){
        return investments.stream().filter(a -> a.id() == id)
                .findFirst()
                .orElseThrow(
                        () -> new InvestmentNotFoundException("O investimento '" + id + "' não foi encontrado.")
                );
    }


    public InvestmentWallet findWalletByAccountPix(final String pix) {
        return wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(
                        () -> new WalletNotFoundException("A carteira não foi encontrada.")
                );
    }

    public List<InvestmentWallet> listWallets(){
        return this.wallets;
    }

    public List<Investment> list(){
        return this.investments;
    }
}
