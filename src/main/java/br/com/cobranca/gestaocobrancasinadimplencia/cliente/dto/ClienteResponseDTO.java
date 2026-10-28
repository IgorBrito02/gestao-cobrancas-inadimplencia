package br.com.cobranca.gestaocobrancasinadimplencia.cliente.dto;

import br.com.cobranca.gestaocobrancasinadimplencia.cliente.Cliente;

public record ClienteResponseDTO(
    Long id,
    String nome,
    String email,
    String cpf,
    String telefone
) {
    public static ClienteResponseDTO fromEntity(Cliente cliente) {
        return new ClienteResponseDTO(
            cliente.getId(),
            cliente.getNome(),
            cliente.getEmail(),
            cliente.getCpf(),
            cliente.getTelefone()
        );
    }
}
