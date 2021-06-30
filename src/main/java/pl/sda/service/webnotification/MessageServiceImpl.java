package pl.sda.service.webnotification;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Michał Gałka on 2017-05-22.
 */
@Service
class MessageServiceImpl implements MessageService {
    public static final String NOTIFY_MSG_SESSION_KEY = "notificationMessages";
    private static final Logger logger_ = Logger.getLogger(MessageServiceImpl.class);
    @Autowired
    private HttpSession httpSession;

    public void addInfoMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.INFO, msg);
        logger_.debug(msg);
    }

    public void addSuccessMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.SUCCESS, msg);
        logger_.debug(msg);
    }

    public void addWarnMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.WARN, msg);
        logger_.warn(msg);
    }

    public void addErrorMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.ERROR, msg);
        logger_.error(msg);
    }

    @SuppressWarnings("unchecked")
    private void addNotificationMessage(MessageDto.MessageDtoType type, String msg) {
        List<MessageDto> notifyMessages = (List<MessageDto>) httpSession.getAttribute(NOTIFY_MSG_SESSION_KEY);
        if (notifyMessages == null) {
            notifyMessages = new ArrayList<>();
        }
        notifyMessages.add(new MessageDto(type, msg));
        httpSession.setAttribute(NOTIFY_MSG_SESSION_KEY, notifyMessages);
    }
}

