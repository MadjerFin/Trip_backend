# Deploy no Render - Trip Backend

Guia passo a passo para fazer deploy da aplicação Spring Boot no Render.

## Pré-requisitos

- [ ] Conta no GitHub
- [ ] Código commitado em um repositório Git
- [ ] Conta no Render (gratuita) - https://render.com
- [ ] Banco de dados Oracle acessível pela internet

## Passo 1: Preparar o Repositório

Certifique-se de que os seguintes arquivos estão no repositório:

- ✅ `Dockerfile` (já existe)
- ✅ `application-prod.properties` (criado)
- ✅ `render.yaml` (criado - opcional)

### Commit e Push

```bash
cd C:\Users\Pichau\Desktop\Trip_backend\trip

git add .
git commit -m "Preparar para deploy no Render"
git push origin main
```

Se ainda não tem repositório Git remoto:

```bash
# Criar repositório no GitHub primeiro, depois:
git remote add origin https://github.com/SEU_USUARIO/trip-backend.git
git branch -M main
git push -u origin main
```

---

## Passo 2: Criar Web Service no Render

### Via Dashboard (Recomendado)

1. **Acessar Render Dashboard**
   - Ir para https://dashboard.render.com
   - Fazer login ou criar conta

2. **Criar Novo Web Service**
   - Clicar em **"New +"** → **"Web Service"**
   - Conectar sua conta do GitHub (autorizar acesso ao repositório)
   - Selecionar o repositório `trip-backend`

3. **Configurar o Serviço**

   **Name:** `trip-backend` (ou nome de sua preferência)

   **Region:** `Oregon (US West)` ou região mais próxima

   **Branch:** `main`

   **Root Directory:** `trip` (ou deixar vazio se o Dockerfile está na raiz)

   **Environment:** `Docker`

   **Docker Build Context Directory:** `.` ou `./trip`

   **Dockerfile Path:** `./Dockerfile` ou `./trip/Dockerfile`

   **Instance Type:** `Free` (para começar)

4. **Configurar Variáveis de Ambiente**

   No painel **"Environment"**, adicionar as seguintes variáveis:

   ```
   SPRING_PROFILES_ACTIVE = prod
   DATABASE_URL = jdbc:oracle:thin:@oracle.fiap.com.br:1521:ORCL
   DATABASE_USERNAME = rm560716
   DATABASE_PASSWORD = 150796
   PORT = 8080
   ```

   **⚠️ IMPORTANTE:** Por segurança, NÃO commitar as credenciais reais. Configure apenas no painel do Render.

5. **Configurar Health Check**

   - **Health Check Path:** `/api/users/health`
   - Render vai verificar automaticamente se a aplicação está rodando

6. **Clicar em "Create Web Service"**

---

## Passo 3: Acompanhar o Deploy

1. Render vai iniciar o build automaticamente
2. Você verá os logs em tempo real:
   - Build da imagem Docker
   - Download de dependências Maven
   - Compilação do código
   - Criação do JAR
   - Inicialização da aplicação Spring Boot

3. Aguardar a mensagem: **"Your service is live 🎉"**

4. O Render fornecerá uma URL pública como:
   ```
   https://trip-backend-xxxx.onrender.com
   ```

---

## Passo 4: Testar a Aplicação

### Healthcheck

```bash
curl https://trip-backend-xxxx.onrender.com/api/users/health
```

**Resposta esperada:**
```
API Java está rodando!
```

### Testar Endpoint de Registro

```bash
curl -X POST https://trip-backend-xxxx.onrender.com/api/users/register \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "Teste User",
    "email": "teste@email.com",
    "senha": "senha123"
  }'
```

### Testar Endpoint de Login

```bash
curl -X POST https://trip-backend-xxxx.onrender.com/api/users/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teste@email.com",
    "senha": "senha123"
  }'
```

---

## Passo 5: Atualizar Frontend

Se você tem frontend no Vercel (`https://trip-red.vercel.app`), atualize a URL da API:

### Exemplo (.env do frontend)

```bash
NEXT_PUBLIC_API_URL=https://trip-backend-xxxx.onrender.com
# ou
VITE_API_URL=https://trip-backend-xxxx.onrender.com
```

Ou diretamente no código:

```javascript
const API_URL = 'https://trip-backend-xxxx.onrender.com';
```

---

## Troubleshooting

### ❌ Deploy falha com erro de memória

**Solução:** Plano Free tem limite de RAM. Adicione no `pom.xml`:

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <executable>true</executable>
        <jvmArguments>-Xmx512m -Xms256m</jvmArguments>
    </configuration>
</plugin>
```

### ❌ Aplicação não conecta ao banco Oracle

**Verificar:**
1. Banco Oracle permite conexões externas?
2. Credenciais estão corretas nas variáveis de ambiente?
3. URL do banco está correta?

**Testar conexão localmente:**
```bash
sqlplus rm560716/150796@oracle.fiap.com.br:1521/ORCL
```

### ❌ Erro CORS no frontend

**Adicionar a URL do Render no application-prod.properties:**

```properties
spring.mvc.cors.allowed-origins=https://trip-red.vercel.app,https://trip-backend-xxxx.onrender.com
```

### ❌ Aplicação "dorme" (spin down) no plano Free

O plano Free do Render coloca a aplicação em standby após 15 minutos de inatividade. Primeira requisição vai demorar ~30 segundos.

**Soluções:**
- Upgrade para plano Starter ($7/mês - sem spin down)
- Usar um serviço de ping (como cron-job.org) para fazer requisições periódicas

---

## Configurações Avançadas

### 1. Deploy Automático (CI/CD)

Render faz deploy automático a cada push na branch `main`. Para desabilitar:

- Dashboard → Service → Settings → Build & Deploy
- Desmarcar "Auto-Deploy"

### 2. Domínio Customizado

1. Dashboard → Service → Settings → Custom Domain
2. Adicionar seu domínio (ex: `api.seusite.com`)
3. Configurar DNS conforme instruções do Render

### 3. Monitoramento e Logs

- **Logs em tempo real:** Dashboard → Logs
- **Métricas:** Dashboard → Metrics (CPU, Memória, Requests)
- **Alertas:** Configurar notificações por email

### 4. Variáveis de Ambiente Secretas

Para senhas e credenciais sensíveis:

1. Dashboard → Environment → Add Environment Variable
2. Marcar como **Secret** (não será exibido nos logs)

---

## Manutenção

### Fazer Redeploy Manual

Dashboard → Manual Deploy → Deploy latest commit

### Ver Logs de Erro

Dashboard → Logs → Filtrar por "ERROR"

### Reiniciar Aplicação

Dashboard → Manual Deploy → Restart

---

## Custo Estimado

| Plano | Preço | Recursos | Spin Down |
|-------|-------|----------|-----------|
| Free | $0 | 512MB RAM, 0.1 CPU | Sim (15min) |
| Starter | $7/mês | 512MB RAM, 0.5 CPU | Não |
| Standard | $25/mês | 2GB RAM, 1 CPU | Não |

**Recomendação:** Começar com Free, upgrade para Starter se precisar de uptime 100%.

---

## Checklist Final

Antes de considerar o deploy completo:

- [ ] Aplicação está acessível via URL do Render
- [ ] Healthcheck retorna sucesso
- [ ] Consegue registrar usuário
- [ ] Consegue fazer login
- [ ] Frontend conecta com sucesso
- [ ] CORS configurado corretamente
- [ ] Logs não mostram erros críticos
- [ ] Banco de dados está recebendo dados

---

## Suporte

- **Documentação Render:** https://render.com/docs
- **Status Render:** https://status.render.com
- **Suporte:** https://render.com/support

## Próximos Passos

1. ✅ Implementar autenticação JWT (substituir senha plain text)
2. ✅ Adicionar rate limiting
3. ✅ Configurar backup do banco
4. ✅ Implementar logs estruturados (ELK, Datadog)
5. ✅ Adicionar testes automatizados
