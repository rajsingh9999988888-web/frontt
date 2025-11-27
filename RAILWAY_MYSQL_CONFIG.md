# Railway MySQL Configuration

## Connection Details

**Railway MySQL Database:**
- **Host:** `shortline.proxy.rlwy.net`
- **Port:** `56487`
- **Database:** `railway`
- **Username:** `root`
- **Password:** `OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk`

**Connection URL:**
```
mysql://root:OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk@shortline.proxy.rlwy.net:56487/railway
```

**JDBC URL:**
```
jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&requireSSL=false
```

## Render Environment Variables

Add these environment variables in your Render backend service:

### Option 1: Direct JDBC URL (Recommended)
```
SPRING_PROFILES_ACTIVE=production
SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=true&serverTimezone=UTC&allowPublicKeyRetrieval=true&requireSSL=false
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
```

### Option 2: Individual Variables
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

## Testing Connection

You can test the connection using MySQL CLI:
```bash
mysql -h shortline.proxy.rlwy.net -u root -p --port 56487 --protocol=TCP railway
# Enter password: OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
```

## Notes

- Railway MySQL uses SSL, so `useSSL=true` is required
- The connection uses TCP protocol on port 56487
- Database name is `railway`
- Tables will be created automatically on first run (ddl-auto=update)
- Make sure to update CORS_ALLOWED_ORIGINS with your Vercel frontend URL

