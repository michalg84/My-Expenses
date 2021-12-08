package dev.galka.account.adapters.out;

import dev.galka.account.domain.AccountType;
import dev.galka.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "account")
@Getter
@Setter
@NoArgsConstructor
public class AccountDbEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;
    @Column
    private String name;
    @Column
    private Integer accountNumber;
    @ManyToOne
    @JoinColumn(name = "account_type")
    private AccountType accountType;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @Column
    private BigDecimal balance;
}

