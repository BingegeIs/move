package bingege.movie.common.service.impl;

import bingege.movie.common.config.property.AppProperties;
import bingege.movie.common.dao.UserRepository;
import bingege.movie.common.model.User;
import bingege.movie.common.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).get();
    }

    @Override
    public Optional<User> initAdmin() {
        if (!existAdmin()) {
            User admin = User.builder()
                    .nickname(appProperties.getNickname())
                    .password(passwordEncoder.encode(appProperties.getPassword()))
                    .username(appProperties.getUsername())
                    .latest(LocalDateTime.now()).build();
            return Optional.of(userRepository.save(admin));
        }
        return Optional.empty();
    }

    @Override
    public void fresh(String username) {
        User user = getUserByUsername(username);
        user.setLatest(LocalDateTime.now());
        userRepository.saveAndFlush(user);
    }

    private boolean existAdmin() {
        Optional<User> byUsername = userRepository.findByUsername(appProperties.getUsername());
        return byUsername.isPresent();
    }
}
