package br.com.cobranca.gestaocobrancasinadimplencia.cobranca;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return repository.save(cobranca);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}
