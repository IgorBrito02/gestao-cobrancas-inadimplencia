package br.com.cobranca.gestaocobrancasinadimplencia.cobranca;

import br.com.cobranca.gestaocobrancasinadimplencia.cliente.Cliente;
import br.com.cobranca.gestaocobrancasinadimplencia.cliente.ClienteRepository;
import br.com.cobranca.gestaocobrancasinadimplencia.cobranca.dto.CobrancaRequestDTO;
import br.com.cobranca.gestaocobrancasinadimplencia.cobranca.dto.CobrancaResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cobrancas")
public class CobrancaController {

    private final CobrancaRepository cobrancaRepository;
    private final ClienteRepository clienteRepository;
    private final CobrancaService cobrancaService;

    public CobrancaController(CobrancaRepository cobrancaRepository, 
                              ClienteRepository clienteRepository, 
                              CobrancaService cobrancaService) {
        this.cobrancaRepository = cobrancaRepository;
        this.clienteRepository = clienteRepository;
        this.cobrancaService = cobrancaService;
    }

    @PostMapping
    public ResponseEntity<CobrancaResponseDTO> cadastrar(@RequestBody @Valid CobrancaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + dto.clienteId()));

        Cobranca cobranca = new Cobranca();
        cobranca.setCliente(cliente);
        cobranca.setValor(dto.valor());
        cobranca.setDataVencimento(dto.dataVencimento());
        cobranca.setStatus("PENDENTE");

        Cobranca cobrancaSalva = cobrancaRepository.save(cobranca);
        return ResponseEntity.status(HttpStatus.CREATED).body(CobrancaResponseDTO.fromEntity(cobrancaSalva));
    }

    @GetMapping
    public ResponseEntity<List<CobrancaResponseDTO>> listarTodas() {
        List<CobrancaResponseDTO> cobrancas = cobrancaRepository.findAll()
                .stream()
                .map(CobrancaResponseDTO::fromEntity)
                .toList();
        return ResponseEntity.ok(cobrancas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CobrancaResponseDTO> buscarPorId(@PathVariable Long id) {
        return cobrancaRepository.findById(id)
                .map(cobranca -> ResponseEntity.ok(CobrancaResponseDTO.fromEntity(cobranca)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CobrancaResponseDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CobrancaRequestDTO dto) {
        return cobrancaRepository.findById(id)
                .map(cobranca -> {
                    Cliente cliente = clienteRepository.findById(dto.clienteId())
                            .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o ID: " + dto.clienteId()));

                    cobranca.setCliente(cliente);
                    cobranca.setValor(dto.valor());
                    cobranca.setDataVencimento(dto.dataVencimento());
                    cobranca.setStatus(dto.status());
                    
                    // Opcional: caso queira permitir atualizar o status enviando por DTO ou definir padrão
                    // cobranca.setStatus("PAGO"); // Se o seu DTO tiver status, você pode pegar de lá.

                    // Passa pelo Service para executar a lógica de cálculo de juros/multa e status "PAGO"
                    Cobranca atualizada = cobrancaService.salvar(cobranca);
                    return ResponseEntity.ok(CobrancaResponseDTO.fromEntity(atualizada));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (!cobrancaRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        cobrancaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}