📱 Rede Social - Java Girls
Um projeto de rede social educacional desenvolvido em Java com Spring Boot, demonstrando o uso de estruturas de dados e autenticação JWT.

📋 Descrição
Este projeto é uma API REST para uma rede social básica que implementa funcionalidades como autenticação de usuários, solicitações de amizade, gerenciamento de conexões e feed de notícias. O foco é educacional, mostrando boas práticas de desenvolvimento backend com Spring Boot.

✨ Funcionalidades
✅ Autenticação JWT

✅ CRUD de usuários

✅ Sistema de amizades (solicitar/aceitar/rejeitar)

✅ Feed de publicações

✅ Banco de dados em memória H2

✅ Documentação de API via Postman

✅ Filtros de segurança

🛠️ Stack Tecnológica
Tecnologia	Versão	Finalidade
Java	21	Linguagem principal
Spring Boot	3.4.0	Framework backend
Spring Security	6.3+	Autenticação e autorização
JWT (jjwt)	0.12.5	Tokens de autenticação
H2 Database	2.2+	Banco em memória
Gradle	8+	Gerenciamento de dependências
JUnit 5	5.10+	Testes unitários
Mockito	5+	Mocking em testes
📁 Estrutura do Projeto
text
social-media-ed/
├── src/
│   ├── main/
│   │   ├── java/com/javagirls/social_media_ed/
│   │   │   ├── config/              # Configurações Spring
│   │   │   ├── controller/          # Controladores REST
│   │   │   ├── dto/                 # Data Transfer Objects
│   │   │   ├── exception/           # Tratamento de exceções
│   │   │   ├── filter/              # Filtros HTTP (JWT)
│   │   │   ├── model/               # Entidades JPA
│   │   │   ├── repository/          # Repositórios Spring Data
│   │   │   ├── service/             # Lógica de negócio
│   │   │   └── util/                # Utilitários
│   │   └── resources/
│   │       ├── application.properties
│   │       └── data.sql             # Dados iniciais
│   └── test/                        # Testes
├── build.gradle.kts                 # Configuração Gradle
├── settings.gradle.kts
├── gradlew                          # Wrapper Gradle
├── gradlew.bat
├── .gitignore
├── Rede Social.postman_collection.json
└── README.md
🚀 Como Executar
Pré-requisitos
Java JDK 21 ou superior

Gradle 7.6+ (ou usar o wrapper)

Postman/Insomnia para testar API

Git para controle de versão

Instalação Local
Clone o repositório

bash
git clone <url-do-repositorio>
cd social-media-ed
Configure o ambiente

bash
# Opcional: crie um arquivo application-local.properties
cp src/main/resources/application.properties src/main/resources/application-local.properties
Execute a aplicação

bash
# Usando Gradle Wrapper
./gradlew bootRun

# Ou construa e execute o JAR
./gradlew build
java -jar build/libs/social-media-ed.jar
Acesse os recursos

API: http://localhost:8080

Console H2: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

Usuário: sa

Senha: (deixe em branco)

🔐 Endpoints da API
Autenticação
Método	Endpoint	Descrição	Auth
POST	/auth/registrar-lote	Registrar múltiplos usuários	❌
POST	/auth/login	Autenticar e obter token JWT	❌
Usuários
Método	Endpoint	Descrição	Auth
GET	/home	Feed geral da rede	✅
GET	/usuarios	Listar todos usuários	✅
Amizades
Método	Endpoint	Descrição	Auth
GET	/solicitacoes-amizade	Ver solicitações pendentes	✅
POST	/solicitacoes-amizade	Enviar solicitação	✅
POST	/solicitacoes-amizade/aceitar	Aceitar solicitação	✅
DELETE	/solicitacoes-amizade/{id}	Rejeitar/Cancelar solicitação	✅
GET	/solicitacoes-amizade/amigos	Listar amigos	✅
Publicações
Método	Endpoint	Descrição	Auth
GET	/posts	Ver publicações	✅
POST	/posts	Criar publicação	✅
PUT	/posts/{id}	Editar publicação	✅
DELETE	/posts/{id}	Excluir publicação	✅
📦 Dependências Gradle
kotlin
dependencies {
implementation("org.springframework.boot:spring-boot-starter-web")
implementation("org.springframework.boot:spring-boot-starter-security")
implementation("org.springframework.boot:spring-boot-starter-data-jpa")
implementation("org.springframework.boot:spring-boot-starter-validation")

    implementation("io.jsonwebtoken:jjwt-api:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.5")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.5")
    
    runtimeOnly("com.h2database:h2")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
}
⚙️ Configuração
application.properties
properties
# Servidor
server.port=8080
server.servlet.context-path=/

# Banco de Dados H2
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

# JPA
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# JWT
jwt.secret=sua-chave-secreta-aqui-minimo-32-caracteres
jwt.expiration=86400000 # 24 horas em milissegundos

# Logging
logging.level.com.javagirls=DEBUG
logging.level.org.springframework.security=DEBUG
Variáveis de Ambiente
bash
# Para produção
export JWT_SECRET=seu-segredo-forte-aqui
export DB_URL=jdbc:postgresql://localhost:5432/socialdb
export DB_USERNAME=usuario
export DB_PASSWORD=senha
🧪 Testando a API
1. Usando Postman
   Importe a coleção Rede Social.postman_collection.json no Postman.

Fluxo de testes:

Registrar usuários via /auth/registrar-lote

Fazer login via /auth/login (salvar token)

Usar token em endpoints protegidos