CREATE TABLE pessoa (
	codigo BIGINT(20) PRIMARY KEY AUTO_INCREMENT,
	nome VARCHAR(100) NOT NULL,
	logradouro VARCHAR(100),
	numero VARCHAR(5),
	complemento VARCHAR(50),
	bairro VARCHAR(50),
	cep VARCHAR(10),
	cidade VARCHAR(100),
	estado VARCHAR(2),
	ativo BOOLEAN
) ENGINE=InnoDB DEFAULT charset=utf8;

INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('André Luiz de Paula Britto', 'Avenida Afonso Pena', '704', 'apto 11', 'Aparecida', '11.020-002', 'Santos', 'SP', true);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Elizangela Alves Lima Britto', 'Avenida Afonso Pena', '704', null, 'Aparecida', '11.020-002', 'Santos', 'SP', true);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Felipe Britto', 'Rua Caramuru', '25', null, 'Aparecida', '11.333-002', 'Paranaguá', 'PR', false);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Maria Rosa Britto', null, null, null, 'Aparecida', '11.020-002', 'Santos', 'SP', false);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Vera Britto', 'Rua Padre Ernesto Odino', '902', null, 'Fortunato Minguini', '58.999-366', 'Tatuí', 'ES', true);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Cerise Costa', 'Rua Jurubatuba', '35', 'apto 108', 'Aparecida', '11.035-101', 'Santos', 'SP', false);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Anderson Moura', 'Avenida Zucri Zaidan', '4.699', 'bloco A', 'Morumbi', '00.885-002', 'São Paulo', 'SP', true);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Levi Britto', 'Chácara Recanto do Sol', 's/n', null, null, null, 'Pindamonhangaba', 'RJ', true);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Paulo Stefanini', 'Avenida Eusébio Matoso', '913', null, 'Butantã', '00.020-002', 'São Paulo', 'SP', true);
 
INSERT INTO pessoa
 (nome, logradouro, numero, complemento, bairro, cep, cidade, estado, ativo)
 values
 ('Márcio Tamura', 'Avenida Zucri Zaidan', '2004', 'Condomínio Rochaverá', 'Morumbi', '96.744-666', 'São Paulo', 'SP', true);
