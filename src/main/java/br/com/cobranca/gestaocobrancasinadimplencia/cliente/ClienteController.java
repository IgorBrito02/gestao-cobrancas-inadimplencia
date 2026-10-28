package br.com.cobranca.gestaocobrancasinadimplencia.cliente;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.cobranca.gestaocobrancasinadimplencia.cliente.dto.ClienteRequestDTO;
import br.com.cobranca.gestaocobrancasinadimplencia.cliente.dto.ClienteResponseDTO;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        List<ClienteResponseDTO> clientes = clienteService.listar()
                .stream()
                .map(ClienteResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarPorId(@PathVariable Long id) {
        return clienteService.buscarPorId(id)
                .map(cliente -> ResponseEntity.ok(ClienteResponseDTO.fromEntity(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ClienteResponseDTO> cadastrar(@RequestBody @Valid ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setCpf(dto.cpf());
        cliente.setTelefone(dto.telefone());

        Cliente clienteSalvo = clienteService.salvar(cliente);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ClienteResponseDTO.fromEntity(clienteSalvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid ClienteRequestDTO dto) {
        return clienteService.buscarPorId(id)
                .map(clienteExistente -> {
                    clienteExistente.setNome(dto.nome());
                    clienteExistente.setEmail(dto.email());
                    clienteExistente.setCpf(dto.cpf());
                    clienteExistente.setTelefone(dto.telefone());
                    
                    Cliente salvo = clienteService.salvar(clienteExistente);
                    return ResponseEntity.ok(ClienteResponseDTO.fromEntity(salvo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (clienteService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}