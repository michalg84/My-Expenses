package pl.sda.dto;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
public class PlayerDto {

    private Integer id;
    private String name;
    private Integer age;
    private Integer teamId;

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

    @Override
    public String toString() {
        return "PlayerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", teamId=" + teamId +
                '}';
    }
}
