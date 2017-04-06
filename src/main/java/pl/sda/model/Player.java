package pl.sda.model;

import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Entity
@Table(name = "player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer age;
    @Column
    private Integer teamId; //na razie bez klasy

    public Player() {
    }

    public Player(Integer id, String name, Integer age, Integer teamId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.teamId = teamId;
    }

    public Integer getId() {
        return id;

    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}
