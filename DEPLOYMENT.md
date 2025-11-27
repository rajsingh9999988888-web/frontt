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
   
   **Option 1: Using DATABASE_URL (Recommended)**
   ```
   SPRING_PROFILES_ACTIVE=production
   PORT=8082
   DATABASE_URL=mysql://root:OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk@shortline.proxy.rlwy.net:56487/railway
   SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend-url.vercel.app,http://localhost:5173
   ```
   
   **Option 2: Using Individual Variables**
   ```
   SPRING_PROFILES_ACTIVE=production
   PORT=8082
   MYSQLHOST=shortline.proxy.rlwy.net
   MYSQLPORT=56487
   MYSQLDATABASE=railway
   MYSQLUSER=root
   MYSQLPASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
   SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
   SPRING_JPA_HIBERNATE_DDL_AUTO=update
   SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend-url.vercel.app,http://localhost:5173
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

**Railway MySQL Connection Details:**
- **Host:** `shortline.proxy.rlwy.net`
- **Port:** `56487`
- **Database:** `railway`
- **Username:** `root`
- **Password:** `OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk`
- **Connection URL:** `mysql://root:OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk@shortline.proxy.rlwy.net:56487/railway`

**Recommended: Use DATABASE_URL in Render:**
```
DATABASE_URL=mysql://root:OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk@shortline.proxy.rlwy.net:56487/railway
```

**Or use individual variables:**
```
MYSQLHOST=shortline.proxy.rlwy.net
MYSQLPORT=56487
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
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

