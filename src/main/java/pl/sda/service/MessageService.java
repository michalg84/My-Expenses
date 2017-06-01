package pl.sda.service;

import pl.sda.dto.MessageDto;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
public interface MessageService {
    void addInfoMessage(String msg);

    void addSuccessMessage(String msg);

    void addWarnMessage(String msg);

    void addErrorMessage(String msg);

    void addNotificationMessage(MessageDto.MessageDtoType type, String msg);

}

