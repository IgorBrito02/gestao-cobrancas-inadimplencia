package br.com.cobranca.gestaocobrancasinadimplencia.cobranca.dto;

import br.com.cobranca.gestaocobrancasinadimplencia.cobranca.Cobranca;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CobrancaResponseDTO(
    Long id,
    Long clienteId,
    String nomeCliente,
    BigDecimal valor,
    LocalDate dataVencimento,
    String status
) {
    public static CobrancaResponseDTO fromEntity(Cobranca cobranca) {
        return new CobrancaResponseDTO(
            cobranca.getId(),
            cobranca.getCliente() != null ? cobranca.getCliente().getId() : null,
            cobranca.getCliente() != null ? cobranca.getCliente().getNome() : null,
            cobranca.getValor(),
            cobranca.getDataVencimento(),
            cobranca.getStatus()
        );
    }
}