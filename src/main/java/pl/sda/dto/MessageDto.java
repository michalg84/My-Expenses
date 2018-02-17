package pl.sda.dto;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
public class MessageDto {

    private MessageDtoType type;
    private String text;

    public MessageDto(MessageDtoType type, String text) {
        this.type = type;
        this.text = text;
    }

    public MessageDto() {
    }

    public MessageDtoType getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public enum MessageDtoType {
        INFO("alert alert-info"),
        ERROR("alert alert-warning"),
        WARN("alert alert-danger"),
        SUCCESS("alert alert-success");

        private String cssClass;

        MessageDtoType(String cssClass) {
            this.cssClass = cssClass;
        }

        @Override
        public String toString() {
            return this.cssClass;
        }
    }
}
