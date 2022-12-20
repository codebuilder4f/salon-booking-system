package com.mpb.salon.bookig.system.security.services;


import com.mpb.salon.bookig.system.entity.auth.User;
import com.mpb.salon.bookig.system.repository.UserRepository;
import com.mpb.salon.bookig.system.web.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        User user = userRepository
                .findByUserName(email)
                .orElseThrow(() -> new EntityNotFoundException(User.class, "User Not Found with username = ", email));
        return UserDetailsImpl.build(user);
    }
}
