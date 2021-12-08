package dev.galka.model;

import dev.galka.account.domain.model.Account;
import dev.galka.account.domain.model.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transactionn")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private BigDecimal amount;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "account_id")
    private Account account;
    @Column
    private String comment;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;
}
