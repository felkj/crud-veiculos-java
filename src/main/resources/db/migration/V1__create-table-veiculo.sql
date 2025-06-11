CREATE TABLE veiculo (
    id SERIAL PRIMARY KEY,
    marca VARCHAR(255),
    modelo VARCHAR(255),
    ano INTEGER,
    preco DOUBLE PRECISION,
    vendido BOOLEAN,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_venda TIMESTAMP
);