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
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (3, 'Leonardo', 'leonardo@inforio.com.br', '$2a$10$0.PsS3i68Y8QLVVPSqqXcO.w10bJzM2BFeWoaWpN.iXvJ4G0oe9Ri', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (4, 'Pablo', 'pablo@inforio.com.br', '$2a$10$GFzTefTb3f.pSjAgurZfKOBdAiKd0Ds4Y.l7GL37HLK9xDUCI30yu', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (5, 'Lucas', 'lucas@inforio.com.br', '$2a$10$1j6zf4nyU4hi8piaGjzm1eOj9zom0p8HAsOqfMieExxdtYOM2e9Te', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (6, 'João Manoel', 'joao@inforio.com.br', '$2a$10$5dbxA6XTr8uYc.1Enqr7puZYnLUlQMBdi.Mmm9xa0D/FHdbXnvR.y', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (7, 'Onofre', 'onofre@inforio.com.br', '$2a$10$diJZnBy13aB8nlw.zFWtWuMHRN.FjrsLi6onvh41NyjPbp1ahfJyW', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (8, 'Antonio', 'antonio@inforio.com.br', '$2a$10$rlLoKPWCoD8XTrSIF6GV/ul8JqNiFwe4JqnAS3x1KONcCS/Ep8Ea2', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (9, 'Washington', 'washington@inforio.com.br', '$2a$10$PSS82.bj5DVzaZOb0hFLmeec2vJRW/mYQWILiSwq1soRMYjtDTwGy', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (10, 'Mateus', 'mateus@inforio.com.br', '$2a$10$itefl.BGjmcQ2y6AWIOm3.cwOPuZ1ATSU471EMudE.aFvD0KGoG5.', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (11, 'Brenda', 'brenda@inforio.com.br', '$2a$10$2iEL1vHReC/.WqibZ/bA3un4t8Y6c6k7G1zUp6S/qJWD5tT/2nKC.', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (12, 'Fábio', 'fabio@inforio.com.br', '$2a$10$R.4PcLcAHlCjS0S/u07.2..L8azRDvBUkaflWH2aPuDmVHfwKPGuS', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (13, 'André', 'andre@inforio.com.br', '$2a$10$eeHvsRNAPPHiQqH5LZIR9um2BtYSbJ8SHQTFKOi.PR0HcSyJJLuYy', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (14, 'Ana carla', 'andre@inforio.com.br', '$2a$10$a85XHB.mrG76v1Mjm7JPgunEQrIZzfdOOP07pt17DKCg/I/bvz3.G', 1);
INSERT INTO usuarios (codigo, nome, email, senha, codigo_perfil) VALUES (15, 'Julian', 'julian@inforio.com.br', '$2a$10$mLSeqdFrAAj9yV0tRRDtHO6MgUPvESq.Vv0lsjxIdrRaU2plOkRVe', 1);