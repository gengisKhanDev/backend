package com.grankhan.loan_service.infrastructure.web;

import com.grankhan.loan_service.application.dto.LoanView;
import com.grankhan.loan_service.application.dto.RequestLoanCommand;
import com.grankhan.loan_service.application.dto.ApproveLoanCommand;
import com.grankhan.loan_service.application.usecase.ApproveLoanUseCase;
import com.grankhan.loan_service.application.usecase.GetLoansForUserUseCase;
import com.grankhan.loan_service.application.usecase.RequestLoanUseCase;
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

    public LoanController(RequestLoanUseCase requestLoanUseCase,
                          ApproveLoanUseCase approveLoanUseCase,
                          GetLoansForUserUseCase getLoansForUserUseCase) {
        this.requestLoanUseCase = requestLoanUseCase;
        this.approveLoanUseCase = approveLoanUseCase;
        this.getLoansForUserUseCase = getLoansForUserUseCase;
    }

    // 1) Solicitar préstamo (usuario autenticado)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<LoanView> requestLoan(@Valid @RequestBody LoanRequestDto body,
                                      Authentication auth) {

        // auth.getName() -> "1", "2", etc. Lo convertimos a Long
        Long userId = Long.parseLong(auth.getName());

        RequestLoanCommand command = new RequestLoanCommand(
                userId,
                body.amount(),
                body.termInMonths()
        );

        // Usas JPA (bloqueante), así que envolvemos el execute(...) en boundedElastic
        return Mono.fromCallable(() -> requestLoanUseCase.execute(command))
                .subscribeOn(Schedulers.boundedElastic());
    }

    // 2) Aprobar / rechazar préstamo (admin)
    @PostMapping("/{loanId}/approval")
    public Mono<LoanView> approveLoan(@PathVariable Long loanId,
                                      @Valid @RequestBody ApproveLoanRequestDto body,
                                      Authentication auth) {

        Long adminId = Long.parseLong(auth.getName());

        ApproveLoanCommand command = new ApproveLoanCommand(
                loanId,
                adminId,
                body.approved()
        );

        return Mono.fromCallable(() -> approveLoanUseCase.execute(command))
                .subscribeOn(Schedulers.boundedElastic());
    }


    // 3) Listar préstamos del usuario autenticado
    @GetMapping
    public Flux<LoanView> getLoansForCurrentUser(Authentication auth) {

        Long userId = Long.parseLong(auth.getName());

        return Mono.fromCallable(() -> getLoansForUserUseCase.execute(userId))
                .flatMapMany(list -> Flux.fromIterable(list))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
