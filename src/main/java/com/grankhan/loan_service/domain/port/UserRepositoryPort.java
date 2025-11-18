package com.grankhan.loan_service.domain.port;

import com.grankhan.loan_service.domain.model.User;

import java.util.Optional;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    User save(User user);
}
