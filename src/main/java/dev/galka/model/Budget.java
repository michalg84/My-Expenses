package dev.galka.model;

import dev.galka.account.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Column
    private BigDecimal sum;
    @Column
    private Integer month;
    @Column
    private Integer year;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
