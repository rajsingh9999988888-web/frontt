# Deployment Guide

## Backend Deployment on Render

### Prerequisites
1. GitHub repository connected to Render
2. Railway MySQL database created

### Steps to Deploy on Render

1. **Go to Render Dashboard**
   - Visit https://dashboard.render.com
   - Click "New +" → "Web Service"

2. **Connect Repository**
   - Connect your GitHub repository: `rajsingh9999988888-web/frontt`
   - Select the repository

3. **Configure Service**
   - **Name:** `baby-adoption-backend`
   - **Environment:** `Java`
   - **Build Command:** `cd baby-adoption-backend && mvn clean package -DskipTests`
   - **Start Command:** `cd baby-adoption-backend && java -jar target/baby-adoption-backend-0.0.1-SNAPSHOT.jar`
   - **Root Directory:** Leave empty (or set to repository root)

4. **Set Environment Variables**
   Add these environment variables in Render dashboard:
   ```
   SPRING_PROFILES_ACTIVE=production
   PORT=8082
   SPRING_DATASOURCE_URL=jdbc:mysql://[Railway MySQL Host]:[Port]/[Database]?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true
   SPRING_DATASOURCE_USERNAME=[Railway MySQL Username]
   SPRING_DATASOURCE_PASSWORD=[Railway MySQL Password]
   SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend-url.onrender.com,http://localhost:5173
   ```

## Railway MySQL Database Setup

### Steps to Create MySQL on Railway

1. **Go to Railway Dashboard**
   - Visit https://railway.app
   - Click "New Project"

2. **Add MySQL Database**
   - Click "New" → "Database" → "Add MySQL"
   - Railway will automatically create MySQL instance

3. **Get Connection Details**
   - Go to MySQL service → "Variables" tab
   - Note down these values:
     - `MYSQLHOST` (or `MYSQL_HOST`)
     - `MYSQLPORT` (or `MYSQL_PORT`)
     - `MYSQLDATABASE` (or `MYSQL_DATABASE`)
     - `MYSQLUSER` (or `MYSQL_USER`)
     - `MYSQLPASSWORD` (or `MYSQL_PASSWORD`)

4. **Alternative: Use DATABASE_URL**
   - Railway also provides `DATABASE_URL` in format:
   - `mysql://user:password@host:port/database`
   - You can use this directly in `SPRING_DATASOURCE_URL`

### Environment Variables for Render

Use the Railway MySQL credentials in Render environment variables:

```
SPRING_DATASOURCE_URL=jdbc:mysql://[MYSQLHOST]:[MYSQLPORT]/[MYSQLDATABASE]?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true
SPRING_DATASOURCE_USERNAME=[MYSQLUSER]
SPRING_DATASOURCE_PASSWORD=[MYSQLPASSWORD]
```

## Testing

After deployment:
1. Check Render logs for any errors
2. Test API endpoint: `https://your-backend.onrender.com`
3. Verify database connection in logs
4. Update frontend API URL to point to Render backend

## Notes

- Render provides `PORT` environment variable automatically
- Railway MySQL uses SSL by default, so `useSSL=true` is required
- Update CORS origins to include your frontend URL
- Database tables will be created automatically on first run (ddl-auto=update)

