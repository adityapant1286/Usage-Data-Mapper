package com.zuora.usagedatamapper.springConfig;

import com.zuora.usagedatamapper.model.configs.dao.AuthoritiesDao;
import com.zuora.usagedatamapper.model.configs.dao.UserDao;
import com.zuora.usagedatamapper.model.configs.vo.AppUserDetails;
import com.zuora.usagedatamapper.repositories.AuthorityRepository;
import com.zuora.usagedatamapper.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zuora.usagedatamapper.util.AppUtil.formatStr;
import static com.zuora.usagedatamapper.util.Constants.RECORD_NOT_FOUND_BY;

@Slf4j
//@Service("jpaUserDetailsService")
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public JpaUserDetailsService(UserRepository userRepository,
                                 AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug(">--> username=" + username);
        final UserDao user = userRepository.findByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException(formatStr(RECORD_NOT_FOUND_BY,
                                    "User", "username=" + username)));
        log.debug(">--> username=" + user.toString());
        final List<AuthoritiesDao> authorities = authorityRepository.findAllByUsername(username)
                            .orElseThrow(() -> new UsernameNotFoundException(formatStr(RECORD_NOT_FOUND_BY,
                                    "Authority", "username=" + username)));
        log.debug(">--> authorities=" + authorities.toString());

        return new AppUserDetails(user, authorities);
    }
}
