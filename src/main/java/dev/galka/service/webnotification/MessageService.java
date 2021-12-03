package dev.galka.service.webnotification;

public interface MessageService {
    void addInfoMessage(String msg);

    void addSuccessMessage(String msg);

    void addWarnMessage(String msg);

    void addErrorMessage(String msg);
}

