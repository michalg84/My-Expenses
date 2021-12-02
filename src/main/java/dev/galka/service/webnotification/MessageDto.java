package dev.galka.service.webnotification;

import java.io.Serializable;

final class MessageDto implements Serializable {
    public static final long serialVersionUID = 1L;
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

    enum MessageDtoType {
        INFO("alert alert-info"),
        ERROR("alert alert-warning"),
        WARN("alert alert-danger"),
        SUCCESS("alert alert-success");

        private final String cssClass;

        MessageDtoType(String cssClass) {
            this.cssClass = cssClass;
        }

        @Override
        public String toString() {
            return this.cssClass;
        }
    }
}
