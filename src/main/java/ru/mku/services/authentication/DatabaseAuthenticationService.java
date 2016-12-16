package ru.mku.services.authentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import ru.mku.domain.user.User;
import ru.mku.domain.user.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class DatabaseAuthenticationService implements AuthenticationManager {

    protected static final Logger log = LoggerFactory.getLogger(DatabaseAuthenticationService.class);

    @Autowired
    private ShaPasswordEncoder encoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        log.info("ZZZZZZZZZZZZZZ authenticate");
        log.info("ZZZZZZZZZZZZZZ authenticate p:{}",auth.getPrincipal().toString());
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (auth.getPrincipal() == null || auth.getCredentials() == null) {
            throw new DatabaseAuthenticationException("auth.password_empty", null);
        }

        String details = "";
        User user = userRepository.findByLogin(auth.getPrincipal().toString());
        if (user == null) {
            throw new DatabaseAuthenticationException("auth.user_not_found", new NullPointerException());
        }

        if (!encoder.isPasswordValid(user.getPassword(), auth.getCredentials().toString(), user.salt())) {
            throw new DatabaseAuthenticationException("auth.user_not_found", null);
        }

        Collection<GrantedAuthority> roles = new ArrayList<>();

        roles.add(new SimpleGrantedAuthority("User"));
        if (user.getAdmin())
            roles.add(new SimpleGrantedAuthority("Admin"));

        return new ExtendedAuth(user, auth.getPrincipal(), auth.getCredentials(), roles, details);
    }

    /**
     * Добавляем дефолтного пользователя
     */
    @PostConstruct
    public void createDefaultAdmin() {
        Iterable<User> users = userRepository.findAll();
        if (!users.iterator().hasNext()) {
            User user = User.defaultUser();
            user.setPassword(encoder.encodePassword(user.getPassword(), user.salt()));
            userRepository.save(user);
        }
    }

    public static class ExtendedAuth extends UsernamePasswordAuthenticationToken {

        private final User user;

        public ExtendedAuth(User user, Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, String details) {
            super(principal, credentials, authorities);
            this.user = user;
            setDetails(details);
        }

        public User getUser() {
            return user;
        }
    }

}
