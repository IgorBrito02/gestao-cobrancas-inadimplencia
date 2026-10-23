package br.com.cobranca.gestaocobrancasinadimplencia.cobranca;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cobrancas")
public class CobrancaController {

    private final CobrancaService cobrancaService;

    public CobrancaController(CobrancaService cobrancaService) {
        this.cobrancaService = cobrancaService;
    }

    @GetMapping
    public List<Cobranca> listar() {
        return cobrancaService.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cobranca> buscarPorId(@PathVariable Long id) {
        return cobrancaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cobranca criar(@RequestBody Cobranca cobranca) {
        return cobrancaService.salvar(cobranca);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        if (cobrancaService.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        cobrancaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
