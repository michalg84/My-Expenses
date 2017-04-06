package pl.sda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.sda.repo.PlayerRepo;

/**
 * Created by Michał Gałka on 2017-04-06.
 */
@Service
public class PalyerService {

    @Autowired
    private PlayerRepo playerRepo;



}
