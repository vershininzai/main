package ru.mku.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.mku.MkuApplication;
import ru.mku.domain.user.User;
import ru.mku.domain.user.UserRepository;

import javax.annotation.PostConstruct;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(MkuApplication.class);

    @Autowired
    UserRepository userRepository;


}
