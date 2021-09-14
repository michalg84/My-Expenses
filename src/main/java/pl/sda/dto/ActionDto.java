package pl.sda.dto;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
public class ActionDto {
    private Integer id;
    private boolean delete;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }
}
