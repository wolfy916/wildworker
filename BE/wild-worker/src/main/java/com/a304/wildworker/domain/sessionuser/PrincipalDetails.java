package com.a304.wildworker.domain.sessionuser;

import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

@Getter
public class PrincipalDetails extends DefaultOAuth2User {

    private SessionUser sessionUser;

    /**
     * Constructs a {@code DefaultOAuth2User} using the provided parameters.
     *
     * @param authorities      the authorities granted to the user
     * @param attributes       the attributes about the user
     * @param nameAttributeKey the key used to access the user's &quot;name&quot; from
     *                         {@link #getAttributes()}
     */
    public PrincipalDetails(Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> attributes, String nameAttributeKey, SessionUser user) {
        super(authorities, attributes, nameAttributeKey);
        this.sessionUser = user;
    }
}
