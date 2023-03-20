package com.techacademy.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.techacademy.entity.Authentication;
import com.techacademy.repository.AuthenticationRepository;

@Service
public class AuthenticationService implements UserDetailsService  {
    private final AuthenticationRepository authenticationRepository;

    public AuthenticationService(AuthenticationRepository repository) {
        this.authenticationRepository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Authentication> authentication = authenticationRepository.findById(username);

        if (!authentication.isPresent()) {
            throw new UsernameNotFoundException("Exception:Code Not Found");
        }
        return new UserDetail(authentication.get().getEmployee());
    }
    /** 全件を検索して返す */
    public List<Authentication> getAuthenticationList() {
        // リポジトリのfindAllメソッドを呼び出す
        return authenticationRepository.findAll();
    }


}
