package com.felkj.veiculos_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "veiculo")
@Table(name = "veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String marca;
    private String modelo;
    private String url_image;
    private Integer ano;
    private Double preco;
    private Boolean vendido = false;

    @Column(name = "data_cadastro", updatable = false)
    private LocalDateTime dataCadastro;

    @Column(name = "data_venda")
    private LocalDateTime dataVenda;

    @PrePersist
    protected void onCreate() {
        this.dataCadastro = LocalDateTime.now();
    }

    // Set vendido and update dataVenda
    public void setVendido(Boolean vendido) {
        this.vendido = vendido;

        if (Boolean.TRUE.equals(vendido) && this.dataVenda == null) {
            this.dataVenda = LocalDateTime.now();
        }
    }
}

