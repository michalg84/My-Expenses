package dev.galka.model;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "account_types")
@NoArgsConstructor
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String type;

}
