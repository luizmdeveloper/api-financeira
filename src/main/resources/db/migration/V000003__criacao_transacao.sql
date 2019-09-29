CREATE SEQUENCE sequence_transacoes
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807;
    
CREATE TABLE transacoes(
	codigo BIGINT PRIMARY KEY NOT NULL,
	codigo_categoria BIGINT NOT NULL,
	codigo_conta BIGINT NOT NULL,
	codigo_conta_transferencia BIGINT,
	data_emissao DATE NOT NULL,
	valor NUMERIC(15,2) NOT NULL,
	tipo CHAR(1) NOT NULL,
	observacao VARCHAR(255),
	conciliado BOOLEAN NOT NULL,
	
	FOREIGN KEY (codigo_categoria) REFERENCES categorias (codigo),
	FOREIGN KEY (codigo_conta) REFERENCES contas (codigo),
	FOREIGN KEY (codigo_conta_transferencia) REFERENCES contas (codigo)
);