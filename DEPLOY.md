# Guía de Deploy en Render

Esta guía te ayudará a desplegar el backend de HomeCredit en Render.

## 📋 Prerrequisitos

1. Cuenta en [Render](https://render.com)
2. Repositorio Git (GitHub, GitLab o Bitbucket)
3. Código del proyecto subido al repositorio

## 🚀 Pasos para el Deploy

### Opción 1: Usando render.yaml (Recomendado)

1. **Actualiza el archivo `render.yaml`**:
   - Cambia `FRONTEND_URL` con la URL real de tu frontend desplegado
   - Ejemplo: `https://mi-frontend.onrender.com,http://localhost:4200`

2. **Conecta tu repositorio en Render**:
   - Ve a [Render Dashboard](https://dashboard.render.com)
   - Click en "New +" → "Blueprint"
   - Conecta tu repositorio Git
   - Render detectará automáticamente el archivo `render.yaml`

3. **Render creará automáticamente**:
   - Un servicio web (backend)
   - Una base de datos PostgreSQL
   - Las variables de entorno necesarias

### Opción 2: Configuración Manual

Si prefieres configurar manualmente:

#### 1. Crear Base de Datos PostgreSQL

1. En Render Dashboard, click en "New +" → "PostgreSQL"
2. Configura:
   - **Name**: `homecredit-db`
   - **Database**: `homecredit`
   - **User**: `homecredit_user`
   - **Plan**: Free (o el que prefieras)
3. Guarda la **Internal Database URL** que Render te proporciona

#### 2. Crear Servicio Web

1. En Render Dashboard, click en "New +" → "Web Service"
2. Conecta tu repositorio Git
3. Configura:
   - **Name**: `homecredit-backend`
   - **Environment**: `Java`
   - **Build Command**: `./mvnw clean package -DskipTests`
   - **Start Command**: `java -jar target/*.jar`
   - **Plan**: Free (o el que prefieras)

#### 3. Configurar Variables de Entorno

En la sección "Environment" del servicio web, agrega:

| Variable | Valor | Descripción |
|----------|-------|-------------|
| `PORT` | (Render lo asigna automáticamente) | Puerto del servidor |
| `DATABASE_URL` | (Desde la BD creada) | URL completa de la base de datos |
| `DATABASE_USERNAME` | (Desde la BD creada) | Usuario de la BD |
| `DATABASE_PASSWORD` | (Desde la BD creada) | Contraseña de la BD |
| `JWT_SECRET` | (Genera uno nuevo) | Secreto para JWT (usa un generador seguro) |
| `FRONTEND_URL` | `https://tu-frontend.onrender.com,http://localhost:4200` | URLs permitidas para CORS |
| `DDL_AUTO` | `update` | Estrategia de actualización de BD |
| `SHOW_SQL` | `false` | No mostrar SQL en logs de producción |

**Para obtener las variables de la BD:**
- Ve a tu base de datos en Render
- En la sección "Connections", encontrarás:
  - `Internal Database URL` → usar como `DATABASE_URL`
  - `Host`, `Port`, `Database`, `User`, `Password` → usar según corresponda

**Para generar JWT_SECRET:**
```bash
# En Linux/Mac
openssl rand -base64 64

# O usa un generador online seguro
```

#### 4. Desplegar

1. Click en "Create Web Service"
2. Render comenzará a construir y desplegar tu aplicación
3. Espera a que el build termine (puede tomar 5-10 minutos la primera vez)
4. Tu backend estará disponible en: `https://homecredit-backend.onrender.com`

## 🔧 Configuración Adicional

### Actualizar CORS para Producción

Si tu frontend está en otro dominio, actualiza `FRONTEND_URL` en las variables de entorno:

```
https://mi-frontend.onrender.com,https://www.midominio.com,http://localhost:4200
```

### Verificar el Deploy

1. **Health Check**: Render verificará automáticamente que tu aplicación responda
2. **Logs**: Revisa los logs en Render Dashboard para verificar que todo funcione
3. **Test Endpoint**: Prueba el endpoint de autenticación:
   ```
   POST https://tu-backend.onrender.com/HomeCredit/authenticate
   ```

## 📝 Notas Importantes

1. **Plan Free**: 
   - El servicio se "duerme" después de 15 minutos de inactividad
   - El primer request después de dormir puede tardar ~30 segundos
   - Considera usar un plan pago para producción

2. **Base de Datos**:
   - El plan free de PostgreSQL tiene límites de almacenamiento
   - Las conexiones pueden ser limitadas

3. **Variables de Entorno**:
   - **NUNCA** subas `JWT_SECRET` o credenciales al repositorio
   - Usa siempre variables de entorno en producción

4. **Build Time**:
   - El primer build puede tardar 10-15 minutos
   - Builds subsecuentes son más rápidos (~5 minutos)

## 🐛 Troubleshooting

### Error: "Cannot connect to database"
- Verifica que `DATABASE_URL` esté correctamente configurada
- Asegúrate de usar la **Internal Database URL** (no la externa)
- Verifica que la BD esté en la misma región que tu servicio web

### Error: "Port already in use"
- Render asigna automáticamente el puerto, no lo configures manualmente
- Usa `${PORT}` en `application.properties`

### Error: "Build failed"
- Revisa los logs de build en Render
- Verifica que `mvnw` tenga permisos de ejecución
- Asegúrate de que todas las dependencias estén en `pom.xml`

### CORS Error en Frontend
- Verifica que `FRONTEND_URL` incluya la URL exacta de tu frontend
- Asegúrate de incluir el protocolo (`https://` o `http://`)

## 🔗 URLs Importantes

- **Render Dashboard**: https://dashboard.render.com
- **Documentación Render**: https://render.com/docs
- **Tu Backend**: `https://homecredit-backend.onrender.com` (o el nombre que hayas elegido)

## 📞 Soporte

Si tienes problemas:
1. Revisa los logs en Render Dashboard
2. Verifica la configuración de variables de entorno
3. Consulta la [documentación de Render](https://render.com/docs)

