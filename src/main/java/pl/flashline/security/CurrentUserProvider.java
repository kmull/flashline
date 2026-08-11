package pl.flashline.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import pl.flashline.user.User;

@Component
public class CurrentUserProvider {

    public Long getCurrentUserId() {
        return getCurrentUser().getId();
    }

    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
