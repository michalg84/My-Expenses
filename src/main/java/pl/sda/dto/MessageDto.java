package pl.sda.dto;

/**
 * Created by Michał Gałka on 2017-04-10.
 */
public class MessageDto {
    private String msg;

    public MessageDto() { }

    public MessageDto(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
