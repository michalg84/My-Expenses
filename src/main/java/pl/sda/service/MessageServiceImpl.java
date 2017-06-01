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
@Service
public class MessageServiceImpl implements MessageService{
    @Autowired
    private HttpSession httpSession;
    public static final String NOTIFY_MSG_SESSION_KEY = "notificationMessages";

    /**
     * Show info message on page.
     * @param msg
     */
    public void addInfoMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.INFO, msg);
    }

    /**
     * Show success message on page.
     * @param msg
     */
    public void addSuccessMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.SUCCESS, msg);
    }

    /**
     * Show warn message on page.
     * @param msg
     */
    public void addWarnMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.WARN, msg);
    }

    /**
     * Show error message on page.
     * @param msg
     */
    public void addErrorMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.ERROR, msg);
    }

    /**
     * Adds message to layout view HttpSession.
     * @param type
     * @param msg
     */
    public void addNotificationMessage(MessageDto.MessageDtoType type, String msg) {
        List<MessageDto> notifyMessages = (List<MessageDto>) httpSession.getAttribute(NOTIFY_MSG_SESSION_KEY);
        if (notifyMessages == null) {
            notifyMessages = new ArrayList<MessageDto>();
        }
        notifyMessages.add(new MessageDto(type, msg));
        httpSession.setAttribute(NOTIFY_MSG_SESSION_KEY, notifyMessages);
    }
}

