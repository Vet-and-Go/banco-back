package com.grupo4.VetAndGo.spring;

import com.grupo4.VetAndGo.domain.repository.BankAccountRepository;
import com.grupo4.VetAndGo.domain.repository.BankTransactionRepository;
import com.grupo4.VetAndGo.domain.repository.ClientRepository;
import com.grupo4.VetAndGo.domain.repository.CreditCardRepository;
import com.grupo4.VetAndGo.domain.service.BankAccountService;
import com.grupo4.VetAndGo.domain.service.BankTransactionService;
import com.grupo4.VetAndGo.domain.service.ClientService;
import com.grupo4.VetAndGo.domain.service.CreditCardService;
import com.grupo4.VetAndGo.domain.service.impl.BankAccountServiceImpl;
import com.grupo4.VetAndGo.domain.service.impl.BankTransactionServiceImpl;
import com.grupo4.VetAndGo.domain.service.impl.ClientServiceImpl;
import com.grupo4.VetAndGo.domain.service.impl.CreditCardServiceImpl;
import com.grupo4.VetAndGo.domain.service.CardPaymentService;
import com.grupo4.VetAndGo.domain.service.impl.CardPaymentServiceImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.BankAccountJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.BankTransactionJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.ClientJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.CreditCardJpaDao;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.BankAccountJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.BankTransactionJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.ClientJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.dao.jpa.impl.CreditCardJpaDaoImpl;
import com.grupo4.VetAndGo.persistence.repository.BankAccountRepositoryImpl;
import com.grupo4.VetAndGo.persistence.repository.BankTransactionRepositoryImpl;
import com.grupo4.VetAndGo.persistence.repository.ClientRepositoryImpl;
import com.grupo4.VetAndGo.persistence.repository.CreditCardRepositoryImpl;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@Profile("!test")
@EnableJpaRepositories(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa")
@EntityScan(basePackages = "com.grupo4.VetAndGo.persistence.dao.jpa.entity")
public class SpringConfig {

    // Repositories
    @Bean
    public BankAccountRepository bankAccountRepository(BankAccountJpaDao jpaDao) {
        return new BankAccountRepositoryImpl(jpaDao);
    }

    @Bean
    public BankTransactionRepository bankTransactionRepository(BankTransactionJpaDao jpaDao) {
        return new BankTransactionRepositoryImpl(jpaDao);
    }

    @Bean
    public ClientRepository clientRepository(ClientJpaDao jpaDao) {
        return new ClientRepositoryImpl(jpaDao);
    }

    @Bean
    public CreditCardRepository creditCardRepository(CreditCardJpaDao jpaDao) {
        return new CreditCardRepositoryImpl(jpaDao);
    }

    // Services
    @Bean
    public BankAccountService bankAccountService(BankAccountRepository accountRepo, BankTransactionRepository transactionRepo) {
        return new BankAccountServiceImpl(accountRepo, transactionRepo);
    }

    @Bean
    public BankTransactionService bankTransactionService(BankTransactionRepository transactionRepo) {
        return new BankTransactionServiceImpl(transactionRepo);
    }

    @Bean
    public ClientService clientService(ClientRepository clientRepo) {
        return new ClientServiceImpl(clientRepo);
    }

    @Bean
    public CreditCardService creditCardService(CreditCardRepository creditCardRepo) {
        return new CreditCardServiceImpl(creditCardRepo);
    }

    @Bean
    public CardPaymentService cardPaymentService(CreditCardRepository creditCardRepo, BankAccountRepository bankAccountRepo, BankTransactionRepository transactionRepo) {
        return new CardPaymentServiceImpl(creditCardRepo, bankAccountRepo, transactionRepo);
    }

    // JPA DAOs
    @Bean
    public BankAccountJpaDao bankAccountJpaDao() {
        return new BankAccountJpaDaoImpl();
    }

    @Bean
    public BankTransactionJpaDao bankTransactionJpaDao() {
        return new BankTransactionJpaDaoImpl();
    }

    @Bean
    public ClientJpaDao clientJpaDao() {
        return new ClientJpaDaoImpl();
    }

    @Bean
    public CreditCardJpaDao creditCardJpaDao() {
        return new CreditCardJpaDaoImpl();
    }
}
