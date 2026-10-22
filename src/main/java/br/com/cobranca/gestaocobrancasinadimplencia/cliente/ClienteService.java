package br.com.cobranca.gestaocobrancasinadimplencia.cliente;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {
    
    private final ClienteRepository repository;

    public ClienteService(ClienteRepository repository){
        this.repository = repository;
    }

    public List<Cliente> listar() {
        return repository.findAll();
    }

    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Cliente salvar(Cliente cliente){
        return repository.save(cliente);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }
}