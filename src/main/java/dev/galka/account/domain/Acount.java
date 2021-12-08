package dev.galka.account.domain;

import java.math.BigDecimal;
import java.util.Date;

class Account {

    private Integer id;
    private Date creationDate;
    private String name;
    private Integer accountNumber;
    private AccountType accountType;
    private User user;
    private BigDecimal balance;

}

