# ============================================================
# Scouter-DataGateway - Testes PowerShell
# Roda o backend antes (./gradlew bootRun) - porta 8080
# ============================================================

$base = "http://localhost:8080"

# ------------------------------------------------------------
# 1. REGISTER (cria usuário) — role padrão vem SCOUT
# ------------------------------------------------------------
$registerBody = @{
    username = "kaique"
    email    = "kaique@teste.com"
    password = "senha123"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$base/auth/register" `
    -Method Post `
    -ContentType "application/json" `
    -Body $registerBody

# ------------------------------------------------------------
# 2. LOGIN (só confirma email/senha, não retorna token)
# ------------------------------------------------------------
$loginBody = @{
    email    = "kaique@teste.com"
    password = "senha123"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$base/auth/login" `
    -Method Post `
    -ContentType "application/json" `
    -Body $loginBody

# ------------------------------------------------------------
# 3. Credenciais pro header X-Credentials (email/senha)
# ------------------------------------------------------------
$cred = "kaique@teste.com/senha123"
$headers = @{ "X-Credentials" = $cred }

# ------------------------------------------------------------
# 4. USER — /me
# ------------------------------------------------------------
Invoke-RestMethod -Uri "$base/users/me" -Method Get -Headers $headers

# Atualizar username
Invoke-RestMethod -Uri "$base/users/me/username?username=kaique_novo" `
    -Method Patch -Headers $headers

# Atualizar email
Invoke-RestMethod -Uri "$base/users/me/email?email=kaique.novo@teste.com" `
    -Method Patch -Headers $headers

# Volta pro email antigo (senão os próximos testes quebram o header)
$headers = @{ "X-Credentials" = "kaique.novo@teste.com/senha123" }

# ------------------------------------------------------------
# 5. USER — Admin (vai dar 401 se seu usuário não for ADMIN no banco)
# ------------------------------------------------------------
Invoke-RestMethod -Uri "$base/users" -Method Get -Headers $headers

# ------------------------------------------------------------
# 6. AUTO SCOUT — criar
# ------------------------------------------------------------
$autoBody = @{
    matchTeamId    = "2026caav_qm1_7563"
    eventKey       = "2026caav"
    matchKey       = "2026caav_qm1"
    teamKey        = "frc7563"
    year           = 2026
    l1             = 2
    l2             = 3
    l3             = 1
    l4             = 0
    coralMisseds   = 1
    coralPrecision = 0.85
    algaeRemoved   = 2
    algaeNet       = 1
    algaeProcessor = 1
    regionScored   = "reef"
    score          = 45
    startline      = $true
    notes          = "auto rapido, sem colisao"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$base/scout/auto" `
    -Method Post -Headers $headers `
    -ContentType "application/json" -Body $autoBody

# Buscar por matchTeamId
Invoke-RestMethod -Uri "$base/scout/auto/2026caav_qm1_7563" -Method Get -Headers $headers

# Buscar por team
Invoke-RestMethod -Uri "$base/scout/auto/team/frc7563" -Method Get -Headers $headers

# Buscar por evento
Invoke-RestMethod -Uri "$base/scout/auto/event/2026caav" -Method Get -Headers $headers

# Buscar por match
Invoke-RestMethod -Uri "$base/scout/auto/match/2026caav_qm1" -Method Get -Headers $headers

# Buscar por team + match
Invoke-RestMethod -Uri "$base/scout/auto/team/frc7563/match/2026caav_qm1" -Method Get -Headers $headers

# Deletar (precisa ser admin, senão 401)
Invoke-RestMethod -Uri "$base/scout/auto/2026caav_qm1_7563" -Method Delete -Headers $headers

# ------------------------------------------------------------
# 7. TELEOP SCOUT — criar
# ------------------------------------------------------------
$teleopBody = @{
    matchTeamId            = "2026caav_qm1_7563"
    eventKey               = "2026caav"
    matchKey               = "2026caav_qm1"
    teamKey                = "frc7563"
    year                   = 2026
    l1                     = 5
    l2                     = 4
    l3                     = 2
    l4                     = 1
    coralMisseds           = 3
    coralPrecision         = 0.78
    algaeRemoved           = 4
    algaeNet               = 2
    algaeProcessor         = 2
    climb                  = "deep"
    collectedCoralFloor    = $true
    collectedCoralStation  = $true
    collectedAlgaeReef     = $false
    defended               = $false
    defendedEffectiveness  = $null
    wasDefended            = $true
    defenseEffectiveness   = 3
    disabled               = $false
    tipped                 = $false
    immobilized            = $false
    issues                 = $false
    issuesNotes            = ""
    driverRating           = 4
    score                  = 68
    notes                  = "boa consistencia no teleop"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$base/scout/teleop" `
    -Method Post -Headers $headers `
    -ContentType "application/json" -Body $teleopBody

# Buscar por matchTeamId
Invoke-RestMethod -Uri "$base/scout/teleop/2026caav_qm1_7563" -Method Get -Headers $headers

# Buscar por team
Invoke-RestMethod -Uri "$base/scout/teleop/team/frc7563" -Method Get -Headers $headers

# Deletar (admin)
Invoke-RestMethod -Uri "$base/scout/teleop/2026caav_qm1_7563" -Method Delete -Headers $headers

# ------------------------------------------------------------
# 8. PIT SCOUT — criar
# ------------------------------------------------------------
$pitBody = @{
    teamKey     = "frc7563"
    eventKey    = "2026caav"
    description = "Robo com garra de coral, chassi swerve"
} | ConvertTo-Json

$pitResult = Invoke-RestMethod -Uri "$base/scout/pit" `
    -Method Post -Headers $headers `
    -ContentType "application/json" -Body $pitBody

$pitResult   # mostra o id do pit scout criado
$pitScoutId = $pitResult.id

# Buscar por team
Invoke-RestMethod -Uri "$base/scout/pit/team/frc7563" -Method Get -Headers $headers

# Buscar por evento
Invoke-RestMethod -Uri "$base/scout/pit/event/2026caav" -Method Get -Headers $headers

# Buscar por team + evento
Invoke-RestMethod -Uri "$base/scout/pit/team/frc7563/event/2026caav" -Method Get -Headers $headers

# ------------------------------------------------------------
# 9. PIT SCOUT PHOTOS — criar (usa o id retornado acima)
# ------------------------------------------------------------
$photoBody = @{
    imgUrl      = "https://exemplo.com/foto1.jpg"
    description = "Foto do robo de frente"
} | ConvertTo-Json

Invoke-RestMethod -Uri "$base/scout/pit/$pitScoutId/photos" `
    -Method Post -Headers $headers `
    -ContentType "application/json" -Body $photoBody

# Listar fotos
Invoke-RestMethod -Uri "$base/scout/pit/$pitScoutId/photos" -Method Get -Headers $headers

# Deletar fotos (admin)
Invoke-RestMethod -Uri "$base/scout/pit/$pitScoutId/photos" -Method Delete -Headers $headers

# Deletar pit scout (admin)
Invoke-RestMethod -Uri "$base/scout/pit/team/frc7563/event/2026caav" -Method Delete -Headers $headers

# ------------------------------------------------------------
# 10. Testes de erro esperado
# ------------------------------------------------------------

# Sem header -> 400 (header obrigatório ausente)
try {
    Invoke-RestMethod -Uri "$base/users/me" -Method Get
} catch {
    Write-Host "Esperado 400:" $_.Exception.Response.StatusCode
}

# Credenciais erradas -> 401
try {
    $badHeaders = @{ "X-Credentials" = "kaique@teste.com/senhaerrada" }
    Invoke-RestMethod -Uri "$base/users/me" -Method Get -Headers $badHeaders
} catch {
    Write-Host "Esperado 401:" $_.Exception.Response.StatusCode
}

# Id inexistente -> 404
try {
    Invoke-RestMethod -Uri "$base/scout/auto/nao-existe" -Method Get -Headers $headers
} catch {
    Write-Host "Esperado 404:" $_.Exception.Response.StatusCode
}