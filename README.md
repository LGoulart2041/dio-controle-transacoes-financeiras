# 💰 DIO - Controle de Transações Financeiras

[![Java](https://img.shields.io/badge/java-17-blue)]()
[![Gradle](https://img.shields.io/badge/build-Gradle-green)]()
[![Coverage](https://img.shields.io/badge/coverage-80%25-yellowgreen)]()
[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)

Projeto desenvolvido em **Java 17** durante o bootcamp da **Digital Innovation One (DIO)**.  
Simula um sistema de **controle de contas bancárias e investimentos**, com suporte a **PIX, depósitos, saques, transferências** e **histórico de transações (audit log)**.

---

## 🚀 Funcionalidades

- Criar contas bancárias com **chaves PIX únicas**.  
- Realizar **depósitos, saques e transferências** entre contas.  
- Criar e gerenciar **investimentos**.  
- Obter **saldo em tempo real**.  
- Consultar **histórico detalhado de transações** (com valor, descrição, timestamp).  

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**  
- **Gradle** (build e execução)  
- **Lombok** (para reduzir boilerplate)  

---

## 📂 Estrutura do Projeto

```text
src/main/java/br/edu/dio/
│
├── model/          # Entidades do domínio (Wallet, AccountWallet, Money, MoneyAudit, etc.)
├── repository/     # Repositórios de contas e investimentos
├── exception/      # Exceções customizadas
└── Main.java       # Ponto de entrada (menu interativo via console)
```

---

## ⚙️ Como Executar

### Pré-requisitos
- Java 17 instalado
- Gradle (ou use o Gradle Wrapper incluso)

### Passos
```bash
# Clone o repositório
git clone https://github.com/LGoulart2041/dio-controle-transacoes-financeiras.git
cd dio-controle-transacoes-financeiras

# Compile e rode
./gradlew clean build
./gradlew run
```

---

## 🧪 Exemplo de Uso

### Criar conta
```text
Olá, seja bem-vindo ao DIO Bank
Selecione a operação desejada:
1 - Criar uma conta
...
Informe as chaves pix (separadas por ';'): chave1;chave2
Informe o valor inicial de depósito: 1000
Conta criada: Wallet{service=ACCOUNT, money= R$10,00} AccountWallet{pix=[chave1, chave2]}
```

### Histórico
```text
13 - Histórico de contas
Informe a chave pix da conta para verificar extrato: chave1
2025-09-17T15:20:10
ID: xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx
Descrição: Valor de criacao da conta
Valor movimentado: R$10,00
```

---

## 📊 Histórico de Transações

Cada operação gera um registro no **audit log** contendo:
- `transactionId`  
- `BankService` (tipo de serviço)  
- `Descrição`  
- `Data/Hora (OffsetDateTime)`  
- `Valor movimentado`  

---

## 🔮 Melhorias Futuras

- Persistência em banco de dados (PostgreSQL, MongoDB).  
- API REST usando **Spring Boot**.  
- Relatórios financeiros por período.  
- Integração com serviços externos (ex.: simulação de taxa de câmbio).  

---

## 🤝 Contribuindo

Contribuições são bem-vindas!  
Faça um **fork**, crie uma branch (`feature/nova-funcionalidade`), e abra um **pull request** 🚀.

---

## 📄 Licença

Distribuído sob a licença MIT. Consulte o arquivo [LICENSE](LICENSE) para mais detalhes.
