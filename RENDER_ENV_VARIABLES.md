# Render Environment Variables Configuration

## Required Environment Variables for Render Backend

Copy and paste these into your Render dashboard → Environment Variables:

### Option 1: Using DATABASE_URL (Recommended - Single Variable)

```
SPRING_PROFILES_ACTIVE=production
DATABASE_URL=mysql://root:OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk@shortline.proxy.rlwy.net:56487/railway
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
```

**Note:** If using DATABASE_URL, you need to convert it to JDBC format. Use Option 2 instead.

### Option 2: Using Individual Variables (Recommended)

```
SPRING_PROFILES_ACTIVE=production
MYSQLHOST=shortline.proxy.rlwy.net
MYSQLPORT=56487
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
```

### Option 3: Using Direct JDBC URL

```
SPRING_PROFILES_ACTIVE=production
SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&requireSSL=false
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
```

## Railway MySQL Connection Details

- **Host:** `shortline.proxy.rlwy.net`
- **Port:** `56487`
- **Database:** `railway`
- **Username:** `root`
- **Password:** `OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk`
- **Connection String:** `mysql://root:OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk@shortline.proxy.rlwy.net:56487/railway`

## Important Notes

1. Replace `https://your-frontend.vercel.app` with your actual Vercel frontend URL
2. `PORT` variable is automatically provided by Render - no need to set it
3. After setting environment variables, restart your Render service
4. Check Render logs to verify database connection

## Testing Connection

After deployment, check Render logs for:
- "Started BabyAdoptionBackendApplication" - Application started successfully
- No database connection errors
- Tables created automatically (ddl-auto=update)

