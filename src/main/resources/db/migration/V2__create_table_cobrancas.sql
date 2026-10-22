CREATE TABLE cobrancas (
    id BIGSERIAL PRIMARY KEY,
    cliente_id BIGINT NOT NULL,
    valor NUMERIC(10, 2) NOT NULL,
    data_vencimento DATE NOT NULL,
    status VARCHAR(50) NOT NULL, -- PENDENTE, PAGO, ATRASADO
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);