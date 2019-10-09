/* Inserindo perfis */
INSERT INTO perfis (codigo, nome) VALUES (1, 'Administrador');
INSERT INTO perfis (codigo, nome) VALUES (2, 'Consultor');

/* Inserindo autorizações */
INSERT INTO autorizacoes (codigo, nome) VALUES (1, 'ROLE_PESQUISAR_CATEGORIA');
INSERT INTO autorizacoes (codigo, nome) VALUES (2, 'ROLE_SALVAR_CATEGORIA');
INSERT INTO autorizacoes (codigo, nome) VALUES (3, 'ROLE_EXCLUIR_CATEGORIA');

INSERT INTO autorizacoes (codigo, nome) VALUES (4, 'ROLE_PESQUISAR_CONTA');
INSERT INTO autorizacoes (codigo, nome) VALUES (5, 'ROLE_SALVAR_CONTA');
INSERT INTO autorizacoes (codigo, nome) VALUES (6, 'ROLE_EXCLUIR_CATEGORIA');

INSERT INTO autorizacoes (codigo, nome) VALUES (7, 'ROLE_PESQUISAR_TRANSACAO');
INSERT INTO autorizacoes (codigo, nome) VALUES (8, 'ROLE_SALVAR_TRANSACAO');
INSERT INTO autorizacoes (codigo, nome) VALUES (9, 'ROLE_EXCLUIR_TRANSACAO');

INSERT INTO autorizacoes (codigo, nome) VALUES (10, 'ROLE_PESQUISAR_DASHBOARD');

/* Inserindo perfil administrador */
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 1);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 2);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 3);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 4);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 5);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 6);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 7);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 8);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 9);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (1, 10);

/* Inserindo perfil consultor */
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (2, 1);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (2, 4);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (2, 7);
INSERT INTO perfil_autorizacoes (codigo_perfil, codigo_autorizacao) VALUES (2, 10);

/* Inserindo perfil consultor */
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (1, 'Luiz Mário', 'luizmario@inforio.com.br', '$2a$10$ATUcntVEe3BiyNEQwKcxOeA20xlSuj6QlFHo9mrVRJgac4/tqH5gC', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (2, 'Consultor', 'consultor@inforio.com.br', '$2a$10$ifNbxr9wfAUMYkdXQfRYIO2CvvusJQdm9qqrjddiFrtobERrAjWM2', 2);
