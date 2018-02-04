package pl.sda.model;

import javax.persistence.*;

/**
 * Created by Michał Gałka on 2017-05-18.
 */
@Entity
@Table(name = "account_types")
public class AccountType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
