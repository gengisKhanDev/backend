package com.grankhan.loan_service.infrastructure.persistence.adapter;

import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.model.UserRole;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import com.grankhan.loan_service.infrastructure.persistence.entity.UserEntity;
import com.grankhan.loan_service.infrastructure.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;

    public UserRepositoryAdapter(UserJpaRepository userJpaRepository) {
        this.userJpaRepository = userJpaRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(this::toDomain);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public User save(User user) {
        UserEntity e = toEntity(user);
        UserEntity saved = userJpaRepository.save(e);
        return toDomain(saved);
    }

    private User toDomain(UserEntity e) {
        return new User(
                e.getId(),
                e.getEmail(),
                e.getPasswordHash(),
                e.getRole()
        );
    }

    private UserEntity toEntity(User u) {
        UserEntity e = new UserEntity();
        e.setId(u.getId());
        e.setEmail(u.getEmail());
        e.setPasswordHash(u.getPasswordHash());
        e.setRole(u.getRole() != null ? u.getRole() : UserRole.USER);
        return e;
    }
}
