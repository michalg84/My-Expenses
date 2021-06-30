package pl.sda.service.webnotification;


/**
 * Created by Michał Gałka on 2017-05-22.
 */
public interface MessageService {
    void addInfoMessage(String msg);

    void addSuccessMessage(String msg);

    void addWarnMessage(String msg);

    void addErrorMessage(String msg);
}

