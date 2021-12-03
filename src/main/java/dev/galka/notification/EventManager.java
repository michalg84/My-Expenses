package dev.galka.notification;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    List<Subscriber> subscriberList = new ArrayList<>();

    public void subscribe(Subscriber subscriber) {
        subscriberList.add(subscriber);
    }
}
