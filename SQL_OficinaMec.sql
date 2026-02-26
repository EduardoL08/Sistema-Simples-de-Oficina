CREATE database OficinaMecanica;
use OficinaMecanica;

create table Usuarios(
id_Usuario int primary key auto_increment,
login varchar (50) not null,
senha varchar (10) not null,
perfil varchar (20) not null
);
insert into Usuarios (login, senha, perfil) values ("adm","adm","Administrador"),("mec","mec","Mecanico");

create table Clientes(
id_Cliente int primary key auto_increment,
nome varchar (50) not null,
cpf varchar (20) not null UNIQUE, 
telefone varchar (20) not null,
email varchar (50) not null,
endereco varchar (100) not null,
dataCadastro varchar (10) not null

);
INSERT INTO Clientes (nome, cpf, telefone, email, endereco, dataCadastro) VALUES
("Carlos Eduardo", "123.456.789-00", "(11)91234-5678", "carlos@gmail.com", "Rua das Palmeiras, 123", "2025-06-01"),
("Ana Beatriz", "987.654.321-11", "(21)99876-5432", "ana.b@gmail.com", "Av. Brasil, 456", "2025-06-02"),
("Roberto Lima", "111.222.333-44", "(31)98456-7890", "roberto@gmail.com", "Rua Minas, 88", "2025-06-03"),
("Fernanda Souza", "555.666.777-88", "(41)98765-1234", "fernanda@gmail.com", "Rua Paraná, 678", "2025-06-04"),
("João Mendes", "999.888.777-66", "(85)99812-3498", "joaom@gmail.com", "Rua Ceará, 55", "2025-06-04"),
("Patrícia Nunes", "444.333.222-11", "(48)98877-1122", "patricia@gmail.com", "Rua SC 101, 1020", "2025-06-05"),
("Lucas Martins", "222.111.000-99", "(27)99900-8877", "lucas@gmail.com", "Rua Vitória, 34", "2025-06-06");


create table Funcionarios(
id_Funcionario int primary key auto_increment,
nome varchar (50) not null,
cpf varchar (20) not null UNIQUE,
telefone varchar (20) not null,
email varchar (50) not null,
especialidades varchar (50) not null,
dataContratacao varchar (10) not null, 
statu varchar (20) not null,
observacao varchar (100) not null

);
INSERT INTO Funcionarios (nome, cpf, telefone, email, especialidades, dataContratacao, statu, observacao) VALUES 
("Carlos Eduardo", "456.789.123-00", "(11)99876-5432", "carlosedu@gmail.com", "Motor", "10/02/2023", "Ativo", "Experiência em motores diesel"),
("Fernanda Lima", "789.456.123-11", "(21)98765-4321", "ferlima@gmail.com", "Elétrica", "01/08/2022", "Ativo", "Especialista em carros elétricos"),
("João Pedro", "321.654.987-22", "(27)97654-3210", "joaopedro_mec@gmail.com", "Transmissão", "15/05/2021", "Inativo", "Aguardando retorno de licença"),
("Luciana Silva", "147.258.369-33", "(19)96543-2109", "luciana.silva@gmail.com", "Lataria", "23/11/2023", "Ativo", "Alta performance em funilaria"),
("Roberto Nunes", "963.852.741-44", "(85)95432-1098", "roberton@gmail.com", "Pintura", "07/09/2020", "Inativo", "Aposentado recentemente");

CREATE TABLE Fornecedores (
    id_Fornecedor INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(100) NOT NULL,
    cnpj VARCHAR(20) NOT NULL unique,
    email VARCHAR(50) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    pecas_fornecidas VARCHAR(100) NOT NULL,
    quantidade INT NOT NULL
);
INSERT INTO Fornecedores (nome, cnpj, email, telefone, pecas_fornecidas, quantidade) VALUES
("Auto Peças Brasil", "12.345.678/0001-01", "contato@gmail.com", "(11)91234-5678", "Pastilhas de Freio", 300),
("Distribuidora Torque", "98.765.432/0001-22", "vendas@gmail.com.br", "(21)99876-5432", "Filtros de Óleo", 500),
("Fornec Autotech", "45.678.123/0001-33", "suporte@gmail.com", "(31)98456-7890", "Correias Dentadas", 200),
("Peça Certa Ltda", "87.654.321/0001-44", "pecas@gmail.com", "(41)98765-1234", "Velas de Ignição", 450),
("Sistema Freios Ltda", "23.456.789/0001-55", "freios@gmail.com", "(85)99812-3498", "Discos de Freio", 150),
("Top Car Supply", "65.432.198/0001-66", "atendimento@gmail.com", "(48)98877-1122", "Amortecedores", 350),
("Equip Car Pro", "11.222.333/0001-77", "contato@gmail.com", "(27)99900-8877", "Baterias", 275);


create table Veiculos(
id_Veiculo int primary key auto_increment,
fk_id_cliente int not null,
placa varchar (10) not null UNIQUE,
marca varchar (25) not null,
modelo varchar (25) not null,
ano varchar (10) not null,
cor varchar (15) not null,
tipo varchar (20) not null,
kms double not null,
observacao varchar (100) not null,
FOREIGN KEY (fk_id_cliente) REFERENCES Clientes(id_cliente)on delete cascade
);
INSERT INTO Veiculos (fk_id_cliente, placa, marca, modelo, ano, cor, tipo, kms, observacao) VALUES
(1, "ABC1A23", "Toyota", "Corolla", "2020", "Prata", "Sedan", 45000, "Revisado e em ótimo estado"),
(2, "DEF4B56", "Honda", "Fit", "2019", "Preto", "Hatch", 56000, "Troca de óleo recente"),
(3, "GHI7C89", "Fiat", "Toro", "2021", "Branco", "Pickup", 31000, "Uso leve e bem conservado"),
(4, "JKL0D12", "Chevrolet", "Onix", "2022", "Cinza", "Hatch", 15000, "Baixa quilometragem"),
(5, "MNO3E45", "Volkswagen", "Golf", "2018", "Vermelho", "Hatch", 69000, "Rodas esportivas"),
(6, "PQR6F78", "Ford", "Ka", "2020", "Azul", "Compacto", 38000, "IPVA quitado"),
(7, "STU9G01", "Renault", "Duster", "2023", "Verde", "SUV", 12000, "Único dono, sem sinistro");


create table Pecas(
id_Peca int primary key auto_increment,
nome varchar (50) not null,
codigo varchar(25) not null unique,
descricao varchar (100) not null,
qtd_Estoque int not null,
preco_Custo double not null,
preco_Venda double not null,
fk_id_Fornecedor int not null,
FOREIGN KEY (fk_id_Fornecedor) REFERENCES Fornecedores(id_Fornecedor)on delete cascade
);
INSERT INTO Pecas (nome, codigo, descricao, qtd_Estoque, preco_Custo, preco_Venda, fk_id_Fornecedor) VALUES
("Pastilha de Freio", "P123", "Pastilha dianteira", 100, 30.00, 60.00, 1),
("Filtro de Óleo", "P456", "Filtro 1.6", 200, 12.00, 25.00, 2),
("Correia Dentada", "P789", "Correia reforçada", 150, 40.00, 75.00, 3),
("Filtro de Ar", "P321", "Filtro esportivo", 180, 18.00, 32.00, 4),
("Amortecedor", "P654", "Amortecedor traseiro", 120, 70.00, 120.00, 5),
("Bateria", "P001", "Bateria selada", 60, 180.00, 300.00, 6),
("Peça Compacta", "P32", "Compacta para motor 1.0", 140, 50.00, 95.00, 7),
("Disco de Freio", "P998", "Disco ventilado", 90, 90.00, 160.00, 1);


CREATE TABLE VendasPecas (
	codigo_Venda VARCHAR(50) NOT NULL UNIQUE,
    fk_id_Vendedor INT NOT NULL,
    fk_id_Cliente INT NOT NULL,
    data_Venda DATE NOT NULL,
    forma_Pagamento enum('PIX', 'Cartão de Crédito', 'Cartão de Débito', 'Boleto') not null,
    desconto varchar(8) NOT null,
    valor_Total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (fk_id_Cliente) REFERENCES Clientes(id_Cliente)on delete cascade,
    FOREIGN KEY (fk_id_Vendedor) REFERENCES Funcionarios(id_Funcionario)on delete cascade
    
);
INSERT INTO VendasPecas (codigo_Venda, fk_id_Vendedor, fk_id_Cliente, data_Venda, forma_Pagamento, desconto, valor_Total) VALUES
('VP001', 1, 2, '2025-06-10', 'PIX', '10.0%', 250.00),
('VP002', 2, 3, '2025-06-11', 'Cartão de Crédito',  '5.0%', 200.00),
('VP003', 1, 1, '2025-06-12', 'Boleto',  '0.0%', 300.00),
('VP004', 3, 4, '2025-06-13', 'Cartão de Débito', ' 5.0%', 190.00),
('VP005', 2, 5, '2025-06-14', 'PIX', '10.0%', 395.00),
('VP006', 1, 6, '2025-06-15', 'Cartão de Crédito', '5.0%', 480.00);

CREATE TABLE VendasPecas_Tem_Pecas (
	id_Peca_Vendida INT PRIMARY KEY AUTO_INCREMENT,
    fk_codigo_Venda varchar(50) NOT NULL,
    fk_codigo_Peca VARCHAR(25) NOT NULL,
    quantidade INT NOT NULL,
    valor_Unitario DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (fk_codigo_Venda) REFERENCES VendasPecas(codigo_Venda) ON DELETE CASCADE,
    FOREIGN KEY (fk_codigo_Peca) REFERENCES Pecas(codigo) ON DELETE CASCADE
);
INSERT INTO VendasPecas_Tem_Pecas (fk_codigo_Venda, fk_codigo_Peca, quantidade, valor_Unitario)VALUES
('VP001', 'P123', 1, 250.00),
('VP002', 'P456', 1, 100.00),
('VP002', 'P321', 1, 100.00),
('VP003', 'P789', 1, 300.00),
('VP004', 'P123', 2, 95.00),
('VP005', 'P654', 2, 130.00),
('VP005', 'P456', 1, 135.00),
('VP006', 'P001', 4, 125.00);

CREATE TABLE Servicos (
    codigo_Servico VARCHAR(50) NOT NULL UNIQUE,
    descricao VARCHAR(100) NOT NULL,
    preco DECIMAL(10,2) NOT NULL
);
INSERT INTO Servicos (codigo_Servico, descricao, preco) VALUES
("S01","Troca de Amortecedores", 450.00),
("S02", "Troca de Óleo", 120.00),
("S03", "Alinhamento e Balanceamento", 150.00),
("S04", "Revisão Completa", 350.00),
("S05", "Serviço de Freios", 180.00),
("S06", "Diagnóstico Eletrônico", 200.00),
("S07", "Recarga de Ar Condicionado", 250.00),
("S08", "Polimento e Higienização", 300.00);

create table VendaServicos (
	codigo_Venda VARCHAR(50) NOT NULL UNIQUE,
    fk_id_Vendedor INT NOT NULL,
    fk_id_Cliente INT NOT NULL,
    fk_placa varchar (10) not null,
    data_Venda DATE NOT NULL,
    previsao_Entrega DATE NOT NULL,
    forma_Pagamento enum('PIX', 'Cartão de Crédito', 'Cartão de Débito', 'Boleto') not null,
    desconto varchar(8) NOT null,
    valor_Total DECIMAL(10,2) NOT NULL,
    FOREIGN KEY (fk_id_Cliente) REFERENCES Clientes(id_Cliente)on delete cascade,
    FOREIGN KEY (fk_id_Vendedor) REFERENCES Funcionarios(id_Funcionario)on delete cascade,
    FOREIGN key (fk_Placa) references Veiculos(placa) on delete cascade
);
INSERT INTO VendaServicos (codigo_Venda, fk_id_Vendedor, fk_id_Cliente, fk_placa, data_Venda, previsao_Entrega,forma_Pagamento, desconto, valor_Total) VALUES
('VS001', 1, 2, 'ABC1A23', '2025-06-20', '2025-06-22', 'PIX', '10.0%', 570.00),
('VS002', 2, 3, 'DEF4B56', '2025-06-21', '2025-06-23', 'Boleto', '0.0%', 650.00),
('VS003', 1, 1, 'GHI7C89', '2025-06-22', '2025-06-24', 'Cartão de Crédito', '5.0%', 300.00),
('VS004', 3, 4, 'JKL0D12', '2025-06-23', '2025-06-25', 'PIX', '10.0%', 250.00),
('VS005', 4, 5, 'MNO3E45', '2025-06-24', '2025-06-26', 'Cartão de Débito', '5.0%', 450.00);

CREATE TABLE VendaServicos_Tem_Servicos (
	id_Serv_Vendido INT PRIMARY KEY AUTO_INCREMENT,
    fk_codigo_Venda varchar(50) NOT NULL,
    fk_codigo_Servico VARCHAR(50) NOT NULL,
    diagnostico_Tecnico varchar(100) NOT NULL,
    valor_Unitario DECIMAL(10,2) NOT NULL,
    status varchar (30) not null ,
    FOREIGN KEY (fk_codigo_Venda) REFERENCES VendaServicos(codigo_Venda) ON DELETE CASCADE,
    FOREIGN KEY (fk_codigo_Servico) REFERENCES Servicos(codigo_Servico) ON DELETE CASCADE
);
INSERT INTO VendaServicos_Tem_Servicos (fk_codigo_Venda, fk_codigo_Servico, diagnostico_Tecnico, valor_Unitario, status) VALUES
('VS001', 'S01', 'Amortecedor com vazamento traseiro', 450.00, 'Ativo'),
('VS001', 'S02', 'Troca preventiva de óleo', 120.00, 'Ativo'),
('VS002', 'S04', 'Revisão programada completa', 350.00, 'Ativo'),
('VS002', 'S06', 'Problema no sensor ABS', 300.00, 'Ativo'),
('VS003', 'S03', 'Desalinhamento após buraco', 150.00, 'Inativo'),
('VS003', 'S05', 'Ruído ao frear', 150.00, 'Ativo'),
('VS004', 'S07', 'Ar condicionado sem refrigeração', 250.00, 'Suspenço'),
('VS005', 'S08', 'Limpeza e polimento externo', 450.00, 'Ativo');

CREATE VIEW Relatorio_Servicos_Vendidos AS
SELECT codigo_Venda, data_Venda, fk_id_Vendedor as Vendedor ,fk_id_Cliente as Cliente, fk_placa as Praca, codigo_Servico, descricao AS descricao_Servico, valor_Unitario, 
forma_Pagamento, desconto, diagnostico_tecnico, previsao_entrega, status
FROM vendaServicos vs JOIN vendaServicos_Tem_Servicos vst ON vs.codigo_Venda = vst.fk_codigo_Venda
JOIN Servicos s ON vst.fk_codigo_Servico = s.codigo_Servico
ORDER BY vs.codigo_Venda, s.codigo_Servico;

CREATE VIEW Relatorio_Pecas_Vendidas as
SELECT codigo_Venda AS Codigo_Venda, c.nome AS Nome_Cliente, f.nome AS Nome_Vendedor,
COUNT(id_Peca_Vendida) AS Qtd_Pecas, GROUP_CONCAT(CONCAT(p.nome, ' (Qtd: ', quantidade, ', R$ ', valor_Unitario, ')') SEPARATOR ' | ') AS Pecas_Vendidas,
data_Venda AS Data_Venda, valor_Total, forma_Pagamento, desconto
FROM VendasPecas_Tem_Pecas vptp
JOIN VendasPecas vp ON vptp.fk_codigo_Venda = vp.codigo_Venda
JOIN Clientes c ON vp.fk_id_Cliente = c.id_Cliente
JOIN Funcionarios f ON vp.fk_id_Vendedor = f.id_Funcionario
JOIN Pecas p ON vptp.fk_codigo_Peca = p.codigo
GROUP BY vp.codigo_Venda, c.nome, f.nome, vp.data_Venda, vp.valor_Total, vp.forma_Pagamento, vp.desconto
ORDER BY vp.data_Venda DESC;

CREATE VIEW Relatorio_Financeiro AS
SELECT 'Venda de Peças' AS tipo_receita, data_Venda, forma_Pagamento, SUM(valor_Total) AS total_receita
FROM VendasPecas GROUP BY data_Venda, forma_Pagamento UNION ALL

SELECT 'Serviços' AS tipo_receita, data_Venda, forma_Pagamento, SUM(valor_Total) AS total_receita
FROM VendaServicos GROUP BY data_Venda, forma_Pagamento;


create table Despesas(
id_Despesa int primary key auto_increment,
nome varchar(50) not null,
descricao varchar(100) not null,
dataValidade varchar(13) not null,
valor varchar(10) not null,
status enum('Pendente', 'Pago', 'Cancelado') not null
);

INSERT INTO Despesas (nome, descricao, dataValidade, valor, status) VALUES
('Internet', 'Plano de internet mensal da empresa', '2025-07-10', '150.00', 'Pendente'),
('Energia', 'Conta de luz do mês', '2025-07-05', '320.45', 'Pago'),
('Aluguel', 'Aluguel da oficina', '2025-07-01', '2500.00', 'Pago'),
('Material Escritório', 'Compra de papel e canetas', '2025-07-15', '85.90', 'Pendente'),
('Manutenção', 'Troca de lâmpadas e tomadas', '2025-06-30', '110.00', 'Cancelado');

