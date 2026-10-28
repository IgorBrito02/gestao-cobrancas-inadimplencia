package br.com.cobranca.gestaocobrancasinadimplencia.cobranca.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CobrancaRequestDTO(
    @NotNull(message = "O ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor da cobrança deve ser maior que zero")
    BigDecimal valor,

    @NotNull(message = "A data de vencimento é obrigatória")
    @FutureOrPresent(message = "A data de vencimento não pode ser no passado")
    LocalDate dataVencimento
) {}
