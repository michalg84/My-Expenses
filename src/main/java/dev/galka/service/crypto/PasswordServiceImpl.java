package dev.galka.service.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
class PasswordServiceImpl implements PasswordService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public String encode(String input) {
        return bCryptPasswordEncoder.encode(input);
    }
}
