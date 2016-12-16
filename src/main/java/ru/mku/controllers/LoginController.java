package ru.mku.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import ru.mku.domain.user.User;
import ru.mku.services.authentication.DatabaseAuthenticationService;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Response;
import java.util.Map;

@Controller
@RequestMapping("/api/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private AuthenticationManager authenticationManager;

    private User currentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = ((DatabaseAuthenticationService.ExtendedAuth) authentication).getUser();
        return currentUser;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public Response post(@RequestBody Map<String, String> cred, HttpServletRequest request) {
        log.info("DDDDDDDDD post");
        log.info("DDDDDDDDD post cred:{}",cred);
        try {
            DatabaseAuthenticationService.ExtendedAuth auth =
                    (DatabaseAuthenticationService.ExtendedAuth) authenticationManager
                            .authenticate(new UsernamePasswordAuthenticationToken(cred.get("login"), cred.get("password")));
            if (auth.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(auth);
                return Response.ok(currentUser()).build();
            }
        } catch (AuthenticationException e) {
            return Response.ok().status(Response.Status.UNAUTHORIZED).build();
        }
        return Response.ok().status(Response.Status.UNAUTHORIZED).build();
    }

}
