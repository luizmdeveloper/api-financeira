CREATE TABLE perfil_autorizacoes(
	codigo_perfil BIGINT NOT NULL,
	codigo_autorizacao BIGINT NOT NULL,

	FOREIGN KEY (codigo_perfil) REFERENCES perfis (codigo),
	FOREIGN KEY (codigo_autorizacao) REFERENCES autorizacoes (codigo)
);