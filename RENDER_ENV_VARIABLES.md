# Render Environment Variables Setup

## ⚠️ CRITICAL: Set These Environment Variables in Render

Go to your Render service → **Environment** tab and add these variables:

### Required Variables:

```
SPRING_PROFILES_ACTIVE=production
```

```
SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectTimeout=30000&socketTimeout=60000
```

```
SPRING_DATASOURCE_USERNAME=root
```

```
SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
```

```
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
```

```
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

```
SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
```

**Replace `https://your-frontend.vercel.app` with your actual Vercel frontend URL**

## Step-by-Step Instructions:

1. **Go to Render Dashboard**
   - Visit https://dashboard.render.com
   - Select your backend service

2. **Navigate to Environment Tab**
   - Click on **Environment** in the left sidebar
   - Or go to **Settings** → **Environment Variables**

3. **Add Each Variable**
   - Click **Add Environment Variable**
   - Enter the **Key** (variable name)
   - Enter the **Value** (variable value)
   - Click **Save Changes**

4. **Important Notes:**
   - Copy the values EXACTLY as shown above
   - Don't add extra spaces
   - Make sure `SPRING_PROFILES_ACTIVE=production` is set
   - After adding variables, **restart your service**

5. **Restart Service**
   - Go to **Events** tab
   - Click **Manual Deploy** → **Deploy latest commit**
   - Or wait for automatic redeploy

## Troubleshooting:

### If connection still fails:

1. **Check Variable Names:**
   - Make sure variable names match EXACTLY (case-sensitive)
   - `SPRING_DATASOURCE_URL` not `SPRING_DATASOURCE_URI`

2. **Check Password:**
   - Password: `OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk`
   - No extra spaces before/after

3. **Check Service Restart:**
   - Environment variables only take effect after restart
   - Force a new deployment

4. **Check Logs:**
   - Go to **Logs** tab
   - Look for connection errors
   - Verify the connection URL is being used

5. **Verify Railway Database:**
   - Make sure Railway MySQL is running
   - Check Railway dashboard for database status

## Alternative: Using Individual Variables

If `SPRING_DATASOURCE_URL` doesn't work, try individual variables:

```
MYSQLHOST=shortline.proxy.rlwy.net
MYSQLPORT=56487
MYSQLDATABASE=railway
MYSQLUSER=root
MYSQLPASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
```

But **SPRING_DATASOURCE_URL is recommended** as it's more reliable.
