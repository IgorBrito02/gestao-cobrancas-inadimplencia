package br.com.cobranca.gestaocobrancasinadimplencia.cobranca;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CobrancaRepository extends JpaRepository<Cobranca, Long> {
    List<Cobranca> findByStatus(String status);
    List<Cobranca> findByDataVencimento(LocalDate dataVencimento);
}