package br.com.cobranca.gestaocobrancasinadimplencia.cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Optional<Cliente> buscarPorCpf(String cpf);
    Optional<Cliente> buscarPorEmail(String email);
}