package ru.nntu.avito.application;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nntu.avito.domain.UserEntity;
import ru.nntu.avito.exception.NotFoundException;
import ru.nntu.avito.repo.UserRepo;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwdEnc;

    public UserService(UserRepo userRepo, PasswordEncoder passwdEnc) {
        this.userRepo = userRepo;
        this.passwdEnc = passwdEnc;
    }

    public UserEntity get(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserEntity get(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND, "User not found"));
    }

    @Transactional
    public void add(String username, String password, int wallet) {
        UserEntity user = UserEntity.create(username, passwdEnc.encode(password), wallet);
        userRepo.save(user);
    }

    @Transactional
    public String remove(Long id) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepo.deleteById(id);
        return "User successfully deleted.";
    }

    @Transactional
    public void edit(Long id, UserEntity user) {
        if (!userRepo.existsById(id)) {
            throw new NotFoundException(HttpStatus.NOT_FOUND, "User not found");
        }
        userRepo.save(user);
    }
    
    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }
}

