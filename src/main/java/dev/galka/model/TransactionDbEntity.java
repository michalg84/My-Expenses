package dev.galka.model;

import dev.galka.account.domain.User;
import dev.galka.account.inout.AccountDbEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "transactionn")
public class TransactionDbEntity {

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
    private AccountDbEntity account;
    @Column
    private String comment;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date transDate;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id")
    private Category category;
}
