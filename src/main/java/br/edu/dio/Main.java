package br.edu.dio;

import br.edu.dio.exception.*;
import br.edu.dio.model.AccountWallet;
import br.edu.dio.model.MoneyAudit;
import br.edu.dio.repository.AccountRepository;
import br.edu.dio.repository.InvestmentRepository;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;
import static java.time.temporal.ChronoUnit.SECONDS;

public class Main {

    private final static AccountRepository accountRepository = new AccountRepository();
    private final static InvestmentRepository investmentRepository = new InvestmentRepository();


    static Scanner scanner = new Scanner(System.in);


    public static void main(String[] args) {
        System.out.println("Olá, seja bem vindo ao DIO Bank");
        while (true) {
            System.out.println("Selecione a operação desejada:");
            System.out.println("1 - Criar uma conta");
            System.out.println("2 - Criar um investimento");
            System.out.println("3 - Criar uma carteira de investimento");
            System.out.println("4 - Depositar na conta");
            System.out.println("5 - Sacar da conta");
            System.out.println("6 - Transferencia entre contas");
            System.out.println("7 - Investir");
            System.out.println("8 - Sacar investimento");
            System.out.println("9 - Listar contas");
            System.out.println("10 - Listar investimentos");
            System.out.println("11 - Listar carteiras de investimento");
            System.out.println("12 - Atualizar investimentos");
            System.out.println("13 - Histórico de contas");
            System.out.println("14 - Checar saldo da conta");
            System.out.println("15 - Sair");

            var option = scanner.nextInt();
            switch(option) {
                case 1 -> createAccount();
                case 2 -> createInvestment();
                case 3 -> createWalletInvestment();
                case 4 -> deposit();
                case 5 -> withdraw();
                case 6 -> transferToAccount();
                case 7 -> inicInvestment();
                case 8 -> rescueInvestment();
                case 9 -> accountRepository.list().forEach(System.out::println);
                case 10 -> investmentRepository.list().forEach(System.out::println);
                case 11 -> investmentRepository.listWallets().forEach(System.out::println);
                case 12 -> {
                    investmentRepository.updateAmount();
                    System.out.println("Investimentos reajustados");
                }
                case 13 -> checkHistory();
                case 14 -> checkBalance();
                case 15 ->  System.exit(0);
                default -> System.out.println("Opção inválida");
            }
        }
    }

    private static void checkBalance() {
        System.out.println("Informe a chave pix da conta para verificar extrato:");
        var pix = scanner.next();
        try {
            var balance = accountRepository.getBalance(pix);
            System.out.println("Saldo em conta e de: " + balance);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void checkHistory() {
        System.out.println("Informe a chave pix da conta para verificar extrato:");
        var pix = scanner.next();
        try {
            var sortedHistory = accountRepository.getHistory(pix);
            sortedHistory.forEach((k, v) -> {
                System.out.println("Data/Hora: " + k.format(ISO_DATE_TIME));
                v.forEach(e -> {
                    System.out.println("ID: " + e.transactionId());
                    System.out.println("Descricao: " + e.description());
                    System.out.println("Valor movimentado: R$" + (e.amount() / 100) + "," + (e.amount() % 100));
                    System.out.println("--------- ||| --------");
                });
            });
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }


    }

    private static void rescueInvestment() {
        System.out.println("Informe a chave pix da conta para resgate do investimento:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será resgatado: ");
        var amount = scanner.nextLong();
        try {
            investmentRepository.withdraw(pix, amount);
            System.out.println("O valor de: " + amount + " foi resgatado.");
            System.out.println("Saldo atual: R$" + accountRepository.getBalance(pix));
        } catch (AccountNotFoundException | NoFundsEngoughException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void inicInvestment() {
        System.out.println("Informe a chave pix da conta para investimento:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será investido: ");
        var amount = scanner.nextLong();
        try {
            investmentRepository.deposit(pix, amount);
        } catch (WalletNotFoundException | AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("O valor de: " + amount + " foi investido.");
    }

    private static void createWalletInvestment(){
        System.out.println("Informe a chave pix da conta: ");
        var pix  = scanner.next();
        var account = accountRepository.findByPix(pix);
        System.out.println("Informe o identificador do investimento");
        System.out.println(investmentRepository.listInvestments());
        var investmentId = scanner.nextInt();
        try {
            var investmentWallet = investmentRepository.initInvestment(account, investmentId);
            System.out.println("Conta de investimento criada: " + investmentWallet);
        } catch (InvestmentNotFoundException | AccountWithInvestmentException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void transferToAccount() {
        System.out.println("Informe a chave pix da conta de origem: ");
        var source = scanner.next();
        System.out.println("Informe a chave pix da conta de destino: ");
        var target = scanner.next();
        System.out.println("Informe o valor para transferência");
        var amount = scanner.nextLong();
        try {
            accountRepository.transferMoney(source, target, amount);
            System.out.println("O valor de: " + amount + " foi retirado da conta.");
            System.out.println("Saldo atual: R$" + accountRepository.getBalance(source));
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("O valor de: " + amount + " foi transferido.");
    }

    private static void withdraw() {
        System.out.println("Informe a chave pix da conta para sacar:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será retirado da conta: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.withdraw(pix, amount);
            System.out.println("O valor de: " + amount + " foi retirado da conta.");
            System.out.println("Saldo atual: R$" + accountRepository.getBalance(pix));
        } catch (AccountNotFoundException | NoFundsEngoughException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void deposit() {
        System.out.println("Informe a chave pix da conta para depósito:");
        var pix = scanner.next();
        System.out.println("Informe o valor que será depositado: ");
        var amount = scanner.nextLong();
        try {
            accountRepository.deposit(pix, amount);
        } catch (AccountNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println("O valor de: " + amount + " foi depositado.");
    }

    private static void createInvestment() {
        System.out.println("Informe a taxa do investimento");
        var tax = scanner.nextInt();
        System.out.println("informe o valor inicial de depósito");
        var initialFunds = scanner.nextLong();
        var investment = investmentRepository.create(tax, initialFunds);
        System.out.println("Investimento criado: " + investment);
    }

    private static void createAccount() {
        System.out.println("Informe as chaves pix (separadas por ';'");
        var pix = Arrays.stream(scanner.next().split(";"))
                        .collect(Collectors.toSet());
        System.out.println("informe o valor inicial de depósito");
        var amount = scanner.nextLong();
        var wallet = accountRepository.create(pix, amount);
        System.out.println("Conta criada: " + wallet);
    }
}
