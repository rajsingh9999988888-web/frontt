# Deployment URLs - Production Status

## 🌐 Your Custom Domain
**URL:** `https://www.nightsathi.com`  
**Domain:** nightsathi.com  
**Status:** ✅ Active & Live on Vercel  
**Server:** Vercel  
**HTTP Status:** 200 OK

## Backend (Render)
**URL:** `https://baby-adoption-backend.onrender.com`  
**Status:** ✅ Server Online | ⏳ Code Not Deployed Yet

**API Base Endpoints:**
- User Login: `https://baby-adoption-backend.onrender.com/api/users/login`
- User Signup: `https://baby-adoption-backend.onrender.com/api/users/signup`
- Baby Posts: `https://baby-adoption-backend.onrender.com/api/babies/babies`
- States: `https://baby-adoption-backend.onrender.com/api/babies/states`
- Districts: `https://baby-adoption-backend.onrender.com/api/babies/districts`
- Cities: `https://baby-adoption-backend.onrender.com/api/babies/cities`
- Admin: `https://baby-adoption-backend.onrender.com/api/babies/admin/posts`

**Current Status:** Endpoints returning 404 (needs backend redeploy)

## Frontend (Vercel)
**URL:** `https://www.nightsathi.com` (nightsathi.com)  
**Status:** ✅ Deployed (Old version) | ⏳ Needs redeploy with API fixes

**Previous Test URLs:**
- Vercel Default: `https://baby-adoption-website.vercel.app` (Auto-generated)
- Custom Domain: `https://www.nightsathi.com` (Your domain - NOW LIVE!)

## Database (Railway MySQL)
**Host:** `shortline.proxy.rlwy.net`  
**Port:** `56487`  
**Database:** `railway`  
**Username:** `root`  
**Status:** ✅ Configured & Connected

## Current Deployment Status

```
BACKEND (Render)
├─ Server:           ✅ Online
├─ Code Fixes:       ✅ Committed (commit 83f81ab)
├─ Code Deployed:    ❌ NOT YET (needs redeploy)
├─ API Routes:       ❌ 404 errors
└─ Action Needed:    Redeploy backend

FRONTEND (Vercel)
├─ Domain:           ✅ nightsathi.com active
├─ Website:          ✅ Running at www.nightsathi.com
├─ Code Fixes:       ✅ Committed (commit 6a791cf)
├─ Code Deployed:    ❌ NOT YET (old version live)
├─ API Calls:        ❌ Still using old paths
└─ Action Needed:    Redeploy frontend

DATABASE (Railway)
├─ Status:           ✅ Connected
├─ Configuration:    ✅ Set in Render env
└─ Data:             ✅ Ready
```

## Configuration

### Vercel Environment Variables (Needed):
```
VITE_API_BASE_URL=https://baby-adoption-backend.onrender.com
```

### Render Environment Variables (Set):
```
SPRING_PROFILES_ACTIVE=production
SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectTimeout=30000&socketTimeout=60000
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
SPRING_WEB_CORS_ALLOWED_ORIGINS=https://www.nightsathi.com,http://localhost:5173
```

## What Needs to Happen

### Step 1: Redeploy Render Backend (3 min)
1. Go to https://dashboard.render.com
2. Select baby-adoption-backend
3. Click **Manual Deploy**
4. Wait until "✓ Live"
→ After this, API endpoints will work

### Step 2: Redeploy Vercel Frontend (3 min)
1. Go to https://vercel.com/dashboard
2. Select project
3. Click **Redeploy**
4. Wait until "Ready"
→ After this, frontend will call correct API endpoints

### Step 3: Verify Connection (5 min)
1. Open https://www.nightsathi.com
2. Press F12 → Network tab
3. Refresh page
4. Check if API calls return 200 OK
5. Verify baby posts display

## Testing URLs

**Live Domain:**
```
https://www.nightsathi.com
```

**Backend API:**
```
GET https://baby-adoption-backend.onrender.com/api/babies/babies
GET https://baby-adoption-backend.onrender.com/api/babies/states
```

**Test Commands:**
```bash
# Check frontend
curl -I https://www.nightsathi.com

# Check backend
curl https://baby-adoption-backend.onrender.com/api/babies/babies

# Check CORS
curl -H "Origin: https://www.nightsathi.com" \
     -X OPTIONS \
     https://baby-adoption-backend.onrender.com/api/users
```

## Summary

| Component | URL | Status | Action |
|-----------|-----|--------|--------|
| Frontend | nightsathi.com | ✅ Live | Redeploy |
| Backend | baby-adoption-backend.onrender.com | ✅ Online | Redeploy |
| Database | Railway MySQL | ✅ Ready | None |
| API Connection | /api/babies/* | ❌ 404 | Wait for backend redeploy |
| Data Display | Frontend UI | ❌ Not showing | Wait for both redeploys |

**Time to Full Production:** ~15 minutes (2 redeployments)

