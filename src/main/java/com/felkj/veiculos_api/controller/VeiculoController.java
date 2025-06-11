package com.felkj.veiculos_api.controller;

import com.felkj.veiculos_api.model.Veiculo;
import com.felkj.veiculos_api.service.VeiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Veiculo>> getAll() {
        return ResponseEntity.ok(service.listeTodos());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Veiculo> getById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Veiculo> criar(@RequestBody Veiculo veiculo) {
        System.out.println("Recebido: " + veiculo); // debug
        Veiculo salvo = service.save(veiculo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Veiculo> veiculoAtualizado(@PathVariable Long id, @RequestBody Veiculo updated) {
        Veiculo veiculoAtualizado = service.update(id, updated);
        return ResponseEntity.ok(veiculoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.findById(id);
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
