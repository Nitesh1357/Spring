package com.nkm.bankapp.repository;

import com.nkm.bankapp.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long>{

}
