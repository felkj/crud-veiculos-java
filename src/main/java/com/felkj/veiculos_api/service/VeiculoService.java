package com.felkj.veiculos_api.service;

import com.felkj.veiculos_api.exception.ResourceNotFoundException;
import com.felkj.veiculos_api.exception.VeiculoNotFoundException;
import com.felkj.veiculos_api.model.Veiculo;
import com.felkj.veiculos_api.repository.VeiculoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VeiculoService {

    private final VeiculoRepository repo;

    public List<Veiculo> listeTodos(){
        return repo.findAll();
    }

    public Veiculo findById(Long id){
        return repo.findById(id).orElseThrow(() -> new VeiculoNotFoundException(id));
    }
    public Veiculo save(Veiculo veiculo) {
        return repo.save(veiculo);
    }

    public Veiculo update(Long id, Veiculo veiculoAtualizado) {
        Veiculo veiculo = findById(id);
        System.out.println("DEBUG: Veículo Atualizado recebido do frontend: " + veiculoAtualizado); // <<< Adicione esta linha
        BeanUtils.copyProperties(veiculoAtualizado, veiculo, "id");
        veiculo.setVendido(veiculoAtualizado.getVendido());
        return repo.save(veiculo);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
