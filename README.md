# Projeto: Acesso a Dados com JDBC

## Descrição
Este projeto explora o uso do JDBC (Java Database Connectivity) para conexão com bancos de dados em Java, incluindo operações CRUD e a implementação do padrão DAO (Data Access Object).

## Tecnologias Utilizadas
- Java 8+
- JDBC
- MySQL
- Eclipse (ou outra IDE de sua escolha)
- Maven

## Instalação e Configuração

### Requisitos
Antes de executar o projeto, certifique-se de ter instalado:
- JDK 8+
- MySQL Server
- MySQL Workbench
- MySQL Connector (driver JDBC)

### Configuração do Banco de Dados
1. Instale o MySQL Server e o MySQL Workbench.
2. Crie um banco de dados chamado `coursejdbc`.
3. Crie um arquivo `db.properties` na raiz do projeto com os seguintes dados:
   ```properties
   user=developer
   password=1234567
   dburl=jdbc:mysql://localhost:3306/coursejdbc
   useSSL=false
   ```

### Configuração do Projeto
1. Clone o repositório:
   ```sh
   git clone <URL_DO_REPOSITORIO>
   ```
2. Acesse a pasta do projeto:
   ```sh
   cd acesso-dados-jdbc
   ```
3. Adicione a biblioteca do MySQL Connector ao projeto.
4. Compile e execute o projeto na sua IDE.

## Estrutura do Projeto
```
acesso-dados-jdbc/
│-- src/main/java/
│   ├── db/              # Classe DB para conexão e utilitários
│   ├── model/           # Classes de domínio (Department, Seller)
│   ├── dao/             # Interfaces DAO
│   ├── dao/impl/        # Implementação JDBC do DAO
│   ├── application/     # Classe principal com testes
│-- resources/
│   ├── db.properties    # Configuração do banco de dados
│-- pom.xml              # Dependências do projeto
```

## Funcionalidades
### CRUD no Banco de Dados
- `findById` - Buscar entidade por ID
- `findAll` - Buscar todas as entidades
- `insert` - Inserir novo registro
- `update` - Atualizar registro
- `delete` - Remover registro

### Padrão DAO
- Implementação de `DepartmentDao` e `SellerDao`
- Uso da classe `DaoFactory` para instanciar DAOs

## Exemplo de Dados
### Departamento
```json
{
  "id": 1,
  "name": "Eletrônicos"
}
```

### Vendedor
```json
{
  "id": 1,
  "name": "Maria Brown",
  "email": "maria@gmail.com",
  "baseSalary": 3000.0,
  "birthDate": "1990-05-15",
  "department": {
    "id": 1,
    "name": "Eletrônicos"
  }
}
```

## Autor
Desenvolvido por Adrian Rosselis com os conhecimentos obtidos no curso de Java do professor Nélio Alves.

## Licença
Este projeto está licenciado sob a [MIT License](LICENSE).
