# Tech Stack & Architecture

Referência técnica do Scouter-DataGateway para contribuidores.

<br />

## Stack

| Camada | Tecnologia | Versão |
|---|---|---|
| Linguagem | Java | 17 |
| Framework | Spring Boot | 4.1.0 |
| ORM | Spring Data JPA / Hibernate | — |
| Banco de dados | PostgreSQL (Supabase) | — |
| Segurança | Spring Security Crypto | — |
| Docs | SpringDoc OpenAPI | 2.8.9 |
| Build | Gradle | wrapper incluso |
| CI | GitHub Actions | — |
| Env vars | springboot4-dotenv | 5.1.0 |

<br />

## Autenticação

A API usa autenticação por **header customizado** — sem sessões, sem JWT, sem cookies.

```
X-Credentials: email@exemplo.com/suasenha
```

Cada requisição protegida passa pelo `Authenticator`, que separa email e senha pelo delimitador `/` e valida contra o banco via `AuthService` (BCrypt).

Existem três níveis de autenticação:

| Método | Nível |
|---|---|
| `authenticate(credentials)` | Qualquer usuário ativo |
| `authenticateAdmin(credentials)` | Apenas ADMIN |
| `authenticateId(credentials, userId)` | Usuário ativo + ID correspondente |

Roles disponíveis: `SCOUT` (padrão) e `ADMIN`.

<br />

## Estrutura de pacotes

```
com.scouter.gateway
├── auth
│   ├── AuthController        POST /auth/register, POST /auth/login
│   ├── AuthService           lógica de registro, login e autenticação
│   ├── Authenticator         helper para validar X-Credentials nos controllers
│   ├── LoginRequest/Response
│   └── RegisterRequest/Response
│
├── user
│   ├── User                  entidade JPA (UUID, username, email, role, active)
│   ├── UserController        /users/** endpoints
│   ├── UserService
│   ├── UserRepository
│   ├── UserResponse
│   ├── favorites/
│   │   ├── events/           favoritar eventos
│   │   └── teams/            favoritar times
│   └── preferences/          preferências do usuário
│
├── scout
│   ├── ScoutController       centraliza todos os endpoints de scouting
│   ├── auto/                 auto scout (por partida/time/evento/match)
│   ├── teleop/               teleop scout
│   └── pit_scout/
│       ├── PitScout*         pit scout
│       └── pit_scout_photos/ fotos do pit scout
│
├── health
│   ├── HealthController      GET /health
│   ├── HealthDatabaseProvider verificação real do banco
│   ├── HealthDependency
│   └── HealthResponse
│
└── build
    ├── BuildInfo             dados do git.properties
    └── BuildInfoProvider     lê git commit, branch, etc.
```

<br />

## Banco de dados

PostgreSQL hospedado no Supabase. A conexão é configurada via variáveis de ambiente no `.env`:

```env
DB_HOST=
DB_PORT=
DB_NAME=
DB_USER=
DB_PASSWORD=
```

O schema é gerenciado pelos scripts SQL na pasta `/SQL`. O Hibernate está configurado para **não** criar/alterar tabelas automaticamente — toda migração deve ser feita manualmente via SQL.

<br />

## Health Check

O endpoint `GET /health` retorna:

```json
{
  "status": "UP",
  "service": "Scouter Gateway",
  "version": "0.0.1",
  "java": "17.0.x",
  "springBoot": "4.1.0",
  "environment": "development",
  "timestamp": "...",
  "build": {
    "branch": "main",
    "commit": "abc1234",
    ...
  },
  "dependencies": [
    { "name": "database", "status": "UP" }
  ]
}
```

<br />

## CI/CD

O GitHub Actions roda em todo push e pull request:

1. Checkout do código
2. Setup Java 17 (Temurin)
3. Setup Gradle
4. Cria `.env` a partir dos secrets do repositório
5. `./gradlew build` (compila + testes com H2 em memória)

Os testes usam H2 em memória — o banco PostgreSQL real não é necessário no CI.

Para funcionar, configure os secrets no repositório:
`DB_NAME`, `DB_USER`, `DB_PASSWORD`, `DB_HOST`, `DB_PORT`

<br />

## Convenções

- **Records** para DTOs de entrada/saída (`Request`, `Response`)
- **ResponseEntity\<T\>** em todos os controllers
- **Autenticação manual** nos controllers via `Authenticator` — não há Spring Security filter chain configurado
- **UUID** como chave primária para `User`
- **Chaves compostas** com `@EmbeddedId` nos módulos de scout