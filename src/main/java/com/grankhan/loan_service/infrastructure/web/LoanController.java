package com.grankhan.loan_service.infrastructure.web;

import com.grankhan.loan_service.application.dto.ApproveLoanCommand;
import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.application.dto.RequestLoanCommand;
import com.grankhan.loan_service.application.usecase.ApproveLoanUseCase;
import com.grankhan.loan_service.application.usecase.GetAllLoansUseCase;
import com.grankhan.loan_service.application.usecase.GetLoansForUserUseCase;
import com.grankhan.loan_service.application.usecase.RequestLoanUseCase;
import com.grankhan.loan_service.domain.model.User;
import com.grankhan.loan_service.domain.port.UserRepositoryPort;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final RequestLoanUseCase requestLoanUseCase;
    private final ApproveLoanUseCase approveLoanUseCase;
    private final GetLoansForUserUseCase getLoansForUserUseCase;
    private final GetAllLoansUseCase getAllLoansUseCase;
    private final UserRepositoryPort userRepository;

    public LoanController(RequestLoanUseCase requestLoanUseCase,
                          ApproveLoanUseCase approveLoanUseCase,
                          GetLoansForUserUseCase getLoansForUserUseCase,
                          GetAllLoansUseCase getAllLoansUseCase,
                          UserRepositoryPort userRepository) {
        this.requestLoanUseCase = requestLoanUseCase;
        this.approveLoanUseCase = approveLoanUseCase;
        this.getLoansForUserUseCase = getLoansForUserUseCase;
        this.getAllLoansUseCase = getAllLoansUseCase;
        this.userRepository = userRepository;
    }

    // 1) Solicitar préstamo (usuario autenticado)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LoanView> requestLoan(@Valid @RequestBody LoanRequestDto body,
                                      Authentication auth) {

        String email = auth.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario autenticado no encontrado"));

        RequestLoanCommand command = new RequestLoanCommand(
                currentUser.getId(),
                body.amount(),
                body.term()
        );

        return Mono.fromCallable(() -> requestLoanUseCase.execute(command))
                .subscribeOn(Schedulers.boundedElastic());
    }

    // 2) Aprobar / rechazar préstamo (admin)
    @PostMapping("/{loanId}/approval")
    public Mono<LoanView> approveLoan(@PathVariable Long loanId,
                                      @Valid @RequestBody ApproveLoanRequestDto body,
                                      Authentication auth) {

        String email = auth.getName();
        User admin = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario autenticado no encontrado"));

        ApproveLoanCommand command = new ApproveLoanCommand(
                loanId,
                admin.getId(),
                body.approved()
        );

        return Mono.fromCallable(() -> approveLoanUseCase.execute(command))
                .subscribeOn(Schedulers.boundedElastic());
    }

    // 3) Listar préstamos
    //    - USER: solo sus préstamos
    //    - ADMIN: todos los préstamos
    @GetMapping
    public Flux<LoanView> getLoans(Authentication auth) {

        String email = auth.getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuario autenticado no encontrado"));

        boolean isAdmin = currentUser.isAdmin();

        Mono<List<LoanView>> source = Mono.fromCallable(() ->
                        isAdmin
                                ? getAllLoansUseCase.execute()
                                : getLoansForUserUseCase.execute(currentUser.getId())
                )
                .subscribeOn(Schedulers.boundedElastic());

        return source.flatMapMany(Flux::fromIterable);
    }
}
