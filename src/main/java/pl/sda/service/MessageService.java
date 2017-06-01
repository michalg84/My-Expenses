package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.dto.MessageDto;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

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

