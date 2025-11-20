package com.grankhan.loan_service.infrastructure.config;

import com.grankhan.loan_service.domain.model.Loan;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.model.UserRole;
import com.grankhan.loan_service.domain.port.LoanRepositoryPort;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public ApplicationRunner initData(UserRepositoryPort userRepository,
                                      LoanRepositoryPort loanRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // Usuarios
            User user = userRepository.findByEmail("usuario@test.com")
                    .orElseGet(() -> {
                        User u = new User(
                                null,
                                "Usuario",
                                "usuario@test.com",
                                passwordEncoder.encode("123"),
                                UserRole.USER
                        );
                        return userRepository.save(u);
                    });

            User admin = userRepository.findByEmail("admin@test.com")
                    .orElseGet(() -> {
                        User a = new User(
                                null,
                                "Administrador",
                                "admin@test.com",
                                passwordEncoder.encode("123"),
                                UserRole.ADMIN
                        );
                        return userRepository.save(a);
                    });

            // Préstamos demo solo si no hay ninguno
            List<Loan> existing = loanRepository.findAll();
            if (existing.isEmpty()) {
                // Pending
                Loan loan1 = Loan.newPending(user.getId(), new BigDecimal("50000"), 24);
                loanRepository.save(loan1);

                // Approved
                Loan loan2 = Loan.newPending(user.getId(), new BigDecimal("100000"), 36);
                loan2.approve(admin.getId());
                loanRepository.save(loan2);
            }
        };
    }
}
