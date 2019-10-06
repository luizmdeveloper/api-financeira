CREATE SEQUENCE sequence_autorizacoes
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807;
    
CREATE TABLE autorizacoes(
	codigo BIGINT PRIMARY KEY NOT NULL,
	nome VARCHAR(100) NOT NULL
);