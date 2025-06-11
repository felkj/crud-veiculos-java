package com.felkj.veiculos_api.repository;

import com.felkj.veiculos_api.model.Veiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long> {
}
