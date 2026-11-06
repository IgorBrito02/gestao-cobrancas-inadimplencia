package br.com.cobranca.gestaocobrancasinadimplencia.cobranca;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;

@Service
public class CobrancaService {
    
    private final CobrancaRepository repository;

    public CobrancaService(CobrancaRepository repository){
        this.repository = repository;
    }

    public List<Cobranca> listar() {
        return repository.findAll();
    }

    public Optional<Cobranca> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Cobranca salvar(Cobranca cobranca){
        
        // Só executa a lógica de pagamento se o status enviado for "PAGO"
        if ("PAGO".equalsIgnoreCase(cobranca.getStatus())) {
            LocalDate dataAtual = LocalDate.now();
            
            if (dataAtual.isAfter(cobranca.getDataVencimento())) {
                long diasAtraso = ChronoUnit.DAYS.between(cobranca.getDataVencimento(), dataAtual);
                BigDecimal multa = cobranca.getValor().multiply(new BigDecimal("0.02"));
                BigDecimal taxaDiaria = new BigDecimal("0.01").divide(new BigDecimal("30"), 4, RoundingMode.HALF_UP);
                BigDecimal juros = cobranca.getValor().multiply(taxaDiaria).multiply(BigDecimal.valueOf(diasAtraso));
                BigDecimal valorAtualizado = cobranca.getValor().add(multa).add(juros);

                cobranca.setValor(valorAtualizado);
            }
        }
        
        return repository.save(cobranca);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
