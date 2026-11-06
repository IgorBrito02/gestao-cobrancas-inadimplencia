package br.com.cobranca.gestaocobrancasinadimplencia.cobranca.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CobrancaRequestDTO(
    @NotNull(message = "O ID do cliente é obrigatório")
    Long clienteId,

    @NotNull(message = "O valor é obrigatório")
    @Positive(message = "O valor da cobrança deve ser maior que zero")
    BigDecimal valor,

    @NotNull(message = "A data de vencimento é obrigatória")
    LocalDate dataVencimento,

    String status
) {}
