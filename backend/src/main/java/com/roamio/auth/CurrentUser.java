package com.roamio.auth;

import com.roamio.user.*;
import org.springframework.stereotype.Component;
import java.security.Principal;

@Component
public class CurrentUser {
    private final UserRepository users;
    public CurrentUser(UserRepository users){this.users=users;}
    public User get(Principal principal){
        if(principal==null) throw new IllegalStateException("Authentication required");
        return users.findByEmailIgnoreCase(principal.getName()).orElseThrow();
    }
}
