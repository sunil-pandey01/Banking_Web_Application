package com.demo_bank_v1.repository;

import com.demo_bank_v1.models.Account;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    @Query(value = "SELECT * FROM account WHERE user_id = :user_id", nativeQuery = true)
    List<Account> getUserAccountsById(@Param("user_id")int user_id);

    @Query(value = "SELECT sum(balance) FROM account WHERE user_id = :user_id", nativeQuery = true)
    BigDecimal getTotalBalance(@Param("user_id")int user_id);

    @Query(value = "SELECT balance FROM account WHERE user_id = :user_id AND account_id = :account_id", nativeQuery = true)
    double getAccountBalance(@Param("user_id") int user_id, @Param("account_id") int account_id);

    @Modifying
    @Query(value ="UPDATE account SET balance = :new_balance WHERE account_id = :account_id" , nativeQuery = true)
    @Transactional
    void changeAccountBalanceById(@Param("new_balance") double new_balance, @Param("account_id") int account_id);

    @Modifying
    @Query(value = "INSERT INTO account(user_id, account_number, account_name, account_type, created_at, balance) VALUES" +
            "(:user_id, :account_number, :account_name,:account_type, :created_at, :balance )", nativeQuery = true)
    @Transactional
    void createBankAccount(@Param("user_id") int user_id,
                           @Param("account_number") String account_number,
                           @Param("account_name") String account_name,
                           @Param("account_type")String account_type,
                           @Param("created_at") LocalDateTime created_at,
                           @Param("balance") double balance);
}
