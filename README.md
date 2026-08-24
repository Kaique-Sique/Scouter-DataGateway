<div align="center">

<img src="https://external-preview.redd.it/java-21-launch-event-v0-VKfyXB99AEiHiPvIbK-vLRHcejGsT7-_XARIBlJwh38.jpg?auto=webp&s=c27f66db5bb0a52e5a0949b952ef502d297d59e8" alt="Scouter-DataGateway" width="90" onerror="this.style.display='none'" />

# Scouter-DataGateway

<sub>Internal scouting API for FRC Team 7563 — Megazord</sub>

<br />

<a href="https://spring.io/projects/spring-boot"><img src="https://img.shields.io/badge/Spring_Boot-4.1-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" /></a>
<a href="https://www.java.com"><img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" /></a>
<a href="https://www.postgresql.org"><img src="https://img.shields.io/badge/PostgreSQL-Supabase-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" /></a>
<a href="https://springdoc.org"><img src="https://img.shields.io/badge/OpenAPI-Swagger-85EA2D?style=for-the-badge&logo=swagger&logoColor=black" /></a>

<br />

<img src="https://img.shields.io/github/last-commit/Kaique-Sique/Scouter-DataGateway?style=flat-square&color=8957e5" />
<img src="https://img.shields.io/github/issues/Kaique-Sique/Scouter-DataGateway?style=flat-square&color=orange" />
<img src="https://img.shields.io/github/license/Kaique-Sique/Scouter-DataGateway?style=flat-square&color=yellow" />
<img src="https://img.shields.io/badge/status-active_development-2ea043?style=flat-square" />
<img src="https://img.shields.io/badge/team-FRC_7563-blue?style=flat-square" />
<img src="https://img.shields.io/badge/made%20in-Jundiaí%2C_BR-009c3b?style=flat-square" />

<br /><br />

<a href="#-overview">Overview</a> ·
<a href="#-features">Features</a> ·
<a href="#-getting-started">Getting Started</a> ·
<a href="#-api-reference">API Reference</a> ·
<a href="#-authentication">Authentication</a> ·
<a href="./docs/TECH_STACK.md">Tech Stack</a> ·
<a href="./CONTRIBUTING.md">Contributing</a>

</div>

<br />

## 📡 Overview

<table>
<tr>
<td>

Scouter-DataGateway is the REST API powering Team 7563's internal scouting platform. Built with Spring Boot 4, it handles user authentication, scout data persistence (auto, teleop, pit), and exposes a fully documented OpenAPI interface for the frontend and field tools to consume.

</td>
</tr>
</table>

<br />

## ✨ Features

<table>
<tr>
<td width="33%" valign="top" align="center">
<h3>🔐</h3>
<b>Authentication</b>
<br />
<sub>Credential-based auth via <code>X-Credentials</code> header — BCrypt password hashing, role-based access (SCOUT / ADMIN)</sub>
</td>
<td width="33%" valign="top" align="center">
<h3>👥</h3>
<b>User Management</b>
<br />
<sub>Full CRUD for users, preferences, and favorite events/teams — admin-gated actions</sub>
</td>
<td width="33%" valign="top" align="center">
<h3>📋</h3>
<b>Scout Modules</b>
<br />
<sub>Separate endpoints for auto, teleop and pit scouting — queryable by match, team, event or user</sub>
</td>
</tr>
<tr>
<td width="33%" valign="top" align="center">
<h3>📸</h3>
<b>Pit Photos</b>
<br />
<sub>Photo attachment support for pit scout entries</sub>
</td>
<td width="33%" valign="top" align="center">
<h3>❤️</h3>
<b>Health Check</b>
<br />
<sub>Real DB health check at <code>/health</code> with build info, Java and Spring Boot version</sub>
</td>
<td width="33%" valign="top" align="center">
<h3>📄</h3>
<b>API Docs</b>
<br />
<sub>Interactive Swagger UI at <code>/docs</code> — full OpenAPI 3.0 spec included</sub>
</td>
</tr>
</table>

<br />

## 🚀 Getting Started

**Pré-requisitos:** Java 17+, Gradle, banco PostgreSQL (Supabase ou local)

<table>
<tr><td width="28"><b>1</b></td><td>

```bash
git clone https://github.com/Kaique-Sique/Scouter-DataGateway.git
cd Scouter-DataGateway
```

</td></tr>
<tr><td><b>2</b></td><td>

```bash
cp .env.exemple .env
```

</td></tr>
<tr><td><b>3</b></td><td>

Edite o `.env` com suas credenciais:

```env
DB_HOST=localhost
DB_PORT=5432
DB_NAME=scouter
DB_USER=postgres
DB_PASSWORD=sua_senha
```

</td></tr>
<tr><td><b>4</b></td><td>

```bash
./gradlew bootRun
```

</td></tr>
</table>

<div align="center">→ API rodando em <a href="http://localhost:8080"><code>localhost:8080</code></a> · Swagger em <a href="http://localhost:8080/docs"><code>localhost:8080/docs</code></a></div>

<br />

## 🔐 Authentication

Todas as rotas protegidas exigem o header `X-Credentials` com o formato:

```
X-Credentials: email@exemplo.com/suasenha
```

Rotas públicas (sem autenticação):

| Método | Rota | Descrição |
|---|---|---|
| `POST` | `/auth/register` | Criar conta |
| `POST` | `/auth/login` | Validar credenciais |
| `GET` | `/health` | Status da API e do banco |
| `GET` | `/docs` | Swagger UI |

<br />

## 📚 API Reference

### Auth

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/auth/register` | ❌ | Registrar novo usuário |
| `POST` | `/auth/login` | ❌ | Login |

### Users

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `GET` | `/users/me` | ✅ | Dados do usuário autenticado |
| `PATCH` | `/users/me/username` | ✅ | Atualizar username |
| `PATCH` | `/users/me/email` | ✅ | Atualizar email |
| `GET` | `/users` | 🔒 Admin | Listar todos os usuários |
| `GET` | `/users/{id}` | 🔒 Admin | Buscar usuário por ID |
| `DELETE` | `/users/{id}` | 🔒 Admin | Desativar usuário |

### Scout — Auto

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/scout/auto` | ✅ | Criar entrada de auto |
| `GET` | `/scout/auto/{matchTeamId}` | ✅ | Buscar por ID |
| `GET` | `/scout/auto/user/{userId}` | ✅ | Buscar por usuário |
| `GET` | `/scout/auto/team/{teamKey}` | ✅ | Buscar por time |
| `GET` | `/scout/auto/event/{eventKey}` | ✅ | Buscar por evento |
| `GET` | `/scout/auto/match/{matchKey}` | ✅ | Buscar por partida |
| `GET` | `/scout/auto/team/{teamKey}/match/{matchKey}` | ✅ | Buscar por time + partida |
| `DELETE` | `/scout/auto/{matchTeamId}` | ✅ | Deletar entrada |

### Scout — Teleop

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/scout/teleop` | ✅ | Criar entrada de teleop |
| `GET` | `/scout/teleop/{matchTeamId}` | ✅ | Buscar por ID |
| `GET` | `/scout/teleop/user/{userId}` | ✅ | Buscar por usuário |
| `GET` | `/scout/teleop/team/{teamKey}` | ✅ | Buscar por time |
| `GET` | `/scout/teleop/event/{eventKey}` | ✅ | Buscar por evento |
| `GET` | `/scout/teleop/match/{matchKey}` | ✅ | Buscar por partida |
| `GET` | `/scout/teleop/team/{teamKey}/match/{matchKey}` | ✅ | Buscar por time + partida |
| `DELETE` | `/scout/teleop/{matchTeamId}` | ✅ | Deletar entrada |

### Scout — Pit

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `POST` | `/scout/pit` | ✅ | Criar entrada de pit |
| `GET` | `/scout/pit/user/{userId}` | ✅ | Buscar por usuário |
| `GET` | `/scout/pit/team/{teamKey}` | ✅ | Buscar por time |
| `GET` | `/scout/pit/event/{eventKey}` | ✅ | Buscar por evento |
| `GET` | `/scout/pit/team/{teamKey}/event/{eventKey}` | ✅ | Buscar por time + evento |
| `DELETE` | `/scout/pit/team/{teamKey}/event/{eventKey}` | ✅ | Deletar entrada |
| `POST` | `/scout/pit/{pitScoutId}/photos` | ✅ | Adicionar fotos |
| `GET` | `/scout/pit/{pitScoutId}/photos` | ✅ | Listar fotos |
| `DELETE` | `/scout/pit/{pitScoutId}/photos` | ✅ | Remover fotos |

### System

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| `GET` | `/health` | ❌ | Status da API + banco + build info |

<br />

## 🛠 Tech Stack

| Camada | Tecnologia |
|---|---|
| Runtime | Java 17 |
| Framework | Spring Boot 4.1 |
| Banco de dados | PostgreSQL via Supabase |
| ORM | Spring Data JPA / Hibernate |
| Segurança | Spring Security Crypto (BCrypt) |
| Docs | SpringDoc OpenAPI 3 / Swagger UI |
| Build | Gradle |
| CI | GitHub Actions |

<br />

## 🏗 Estrutura do Projeto

```
src/main/java/com/scouter/gateway/
├── auth/           # Login, registro e autenticação por X-Credentials
├── user/           # Entidade User, CRUD e favoritos
│   ├── favorites/
│   │   ├── events/
│   │   └── teams/
│   └── preferences/
├── scout/          # Módulos de scouting
│   ├── auto/
│   ├── teleop/
│   └── pit_scout/
│       └── pit_scout_photos/
├── health/         # Health check endpoint
└── build/          # Build info via git.properties
```

<br />

<div align="center">

<img src="https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square" />

<sub>Built by <a href="https://megazord7563.com.br">Team 7563 — Megazord</a> · Jundiaí, SP</sub>

</div>