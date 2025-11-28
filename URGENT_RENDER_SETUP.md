# ⚠️ URGENT: Render Environment Variables Setup

## 🔴 CRITICAL ERROR FIX

Your application is showing: **"No active profile set, falling back to 1 default profile: 'default'"**

This means `SPRING_PROFILES_ACTIVE=production` is **NOT SET** in Render!

## ✅ IMMEDIATE ACTION REQUIRED

### Step 1: Go to Render Dashboard
1. Visit: https://dashboard.render.com
2. Click on your backend service

### Step 2: Add Environment Variables
Go to **Environment** tab (or **Settings** → **Environment Variables**)

### Step 3: Add These Variables (Copy EXACTLY):

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

**Replace `https://your-frontend.vercel.app` with your actual Vercel URL**

### Step 4: Save and Restart
1. Click **Save Changes** after adding each variable
2. Go to **Events** tab
3. Click **Manual Deploy** → **Deploy latest commit**
4. Wait for deployment to complete

## 🔍 How to Verify

After deployment, check logs. You should see:
- ✅ `The following profiles are active: production` (NOT "default")
- ✅ `HikariPool-1 - Starting...`
- ✅ `HikariPool-1 - Start completed.`
- ✅ `Started BabyAdoptionBackendApplication`

## ❌ If You See This, Variables Are NOT Set:
- ❌ `No active profile set, falling back to 1 default profile: "default"`
- ❌ `Communications link failure`

## 📋 Quick Checklist

- [ ] `SPRING_PROFILES_ACTIVE=production` is set
- [ ] `SPRING_DATASOURCE_URL` is set with full JDBC URL
- [ ] `SPRING_DATASOURCE_USERNAME=root` is set
- [ ] `SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk` is set
- [ ] All variables saved
- [ ] Service restarted after adding variables

## 🚨 Common Mistakes

1. **Variable name typo**: `SPRING_PROFILES_ACTIVE` not `SPRING_PROFILE_ACTIVE`
2. **Missing value**: Variable name set but value is empty
3. **Extra spaces**: Don't add spaces before/after `=`
4. **Not restarting**: Variables only take effect after restart

## 📞 Still Not Working?

1. Double-check all variable names match EXACTLY
2. Verify password is correct: `OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk`
3. Check Railway database is running
4. Look at Render logs for specific error messages

