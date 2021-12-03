package dev.galka.service.webnotification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
class MessageServiceImpl implements MessageService {
    public static final String NOTIFY_MSG_SESSION_KEY = "notificationMessages";
    private final HttpSession httpSession;


    public void addInfoMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.INFO, msg);
        log.debug(msg);
    }

    public void addSuccessMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.SUCCESS, msg);
        log.debug(msg);
    }

    public void addWarnMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.WARN, msg);
        log.warn(msg);
    }

    public void addErrorMessage(String msg) {
        addNotificationMessage(MessageDto.MessageDtoType.ERROR, msg);
        log.error(msg);
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

