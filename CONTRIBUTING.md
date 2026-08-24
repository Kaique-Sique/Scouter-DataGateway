# Contributing to Scouter-DataGateway

Obrigado por querer contribuir! Este guia explica como o projeto é organizado e como colaborar de forma eficiente.

<br />

## 📋 Índice

- [Pré-requisitos](#-pré-requisitos)
- [Setup do ambiente](#-setup-do-ambiente)
- [Fluxo de branches](#-fluxo-de-branches)
- [Convenções de commit](#-convenções-de-commit)
- [Estrutura do projeto](#-estrutura-do-projeto)
- [Padrões de código](#-padrões-de-código)
- [Abrindo um Pull Request](#-abrindo-um-pull-request)
- [Reportando bugs](#-reportando-bugs)

<br />

## 🛠 Pré-requisitos

- Java 17+
- Gradle (wrapper incluso — use `./gradlew`)
- PostgreSQL local ou acesso ao Supabase
- Git

<br />

## ⚙️ Setup do ambiente

```bash
# 1. Fork e clone
git clone https://github.com/SEU_USER/Scouter-DataGateway.git
cd Scouter-DataGateway

# 2. Configure o upstream
git remote add upstream https://github.com/Kaique-Sique/Scouter-DataGateway.git

# 3. Crie o .env
cp .env.exemple .env
# edite o .env com suas credenciais de banco

# 4. Suba a aplicação
./gradlew bootRun
```

Para rodar os testes:

```bash
./gradlew test
```

> Os testes usam H2 em memória — não é necessário ter PostgreSQL rodando para testar.

<br />

## 🌿 Fluxo de branches

| Branch | Finalidade |
|---|---|
| `main` | Código estável, sempre funcional |
| `feat/<nome>` | Nova feature |
| `fix/<nome>` | Correção de bug |
| `refactor/<nome>` | Refatoração sem mudança de comportamento |
| `docs/<nome>` | Apenas documentação |

**Nunca commite direto na `main`.** Sempre abra um PR.

```bash
# Exemplo
git checkout -b feat/teleop-improvements
```

<br />

## 📝 Convenções de commit

Seguimos [Conventional Commits](https://www.conventionalcommits.org/):

```
<tipo>(<escopo>): <descrição curta>
```

| Tipo | Quando usar |
|---|---|
| `feat` | Nova funcionalidade |
| `fix` | Correção de bug |
| `refactor` | Refatoração |
| `docs` | Documentação |
| `test` | Testes |
| `chore` | Build, CI, dependências |

**Exemplos:**

```
feat(scout): add teleop scoring endpoint
fix(auth): handle null password in login
refactor(user): simplify UserService.getById
docs(readme): update API reference table
```

<br />

## 🏗 Estrutura do projeto

```
src/main/java/com/scouter/gateway/
├── auth/           # Autenticação — X-Credentials, BCrypt
├── user/           # Entidade User + favoritos + preferências
├── scout/          # Módulos de scouting (auto, teleop, pit)
├── health/         # /health endpoint
└── build/          # Build info via git.properties
```

Cada módulo segue o mesmo padrão:

```
<modulo>/
├── <Entidade>.java          # JPA Entity
├── <Entidade>Id.java        # Chave composta (se houver)
├── <Entidade>Repository.java
├── <Entidade>Request.java   # DTO de entrada
├── <Entidade>Response.java  # DTO de saída
├── <Entidade>Service.java
└── <Entidade>Controller.java (centralizado em ScoutController)
```

<br />

## ✅ Padrões de código

- **Autenticação:** todas as rotas protegidas devem usar `Authenticator` para validar o header `X-Credentials` (`email/senha`)
- **Roles:** use `authenticator.authenticate()` para scouts e `authenticator.authenticateAdmin()` para ações admin
- **DTOs:** use `record` para Request e Response
- **Retornos:** sempre `ResponseEntity<T>` nos controllers
- **Sem Spring Security filter:** a autenticação é feita manualmente por header — não adicione filtros HTTP sem discutir antes
- **Formato:** siga o estilo existente (sem tabs, 4 espaços)

<br />

## 🔃 Abrindo um Pull Request

1. Atualize sua branch com o upstream antes de abrir o PR:
   ```bash
   git fetch upstream
   git rebase upstream/main
   ```
2. Certifique-se que `./gradlew build` passa sem erros
3. Abra o PR com título no formato Conventional Commits
4. Descreva o que foi feito e por quê
5. Referencie a issue relacionada se houver (`Closes #42`)

<br />

## 🐛 Reportando bugs

Abra uma [issue](https://github.com/Kaique-Sique/Scouter-DataGateway/issues) com:

- Descrição do comportamento esperado vs. o que aconteceu
- Endpoint afetado
- Payload/headers usados (sem senhas reais)
- Stack trace se houver

<br />

---

<div align="center">
<sub>Built by <a href="https://megazord7563.com.br">Team 7563 — Megazord</a> · Jundiaí, SP</sub>
</div>