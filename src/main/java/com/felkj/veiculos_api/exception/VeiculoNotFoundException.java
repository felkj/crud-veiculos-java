package com.felkj.veiculos_api.exception;

public class VeiculoNotFoundException extends RuntimeException {
    public VeiculoNotFoundException(Long id) {
        super("Veículo com ID " + id + " não encontrado.");
    }
}
