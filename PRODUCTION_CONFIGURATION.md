# Production Configuration - nightsathi.com

## ✅ Current Production Setup

### Frontend (Vercel)
- **Production URL:** `https://www.nightsathi.com` / `https://nightsathi.com`
- **Status:** ✅ Live and Active
- **Deployment:** Vercel

### Backend (Render)
- **Production URL:** `https://baby-adoption-backend.onrender.com`
- **Status:** ✅ Configured
- **Database:** Railway MySQL

### Database (Railway)
- **Host:** `shortline.proxy.rlwy.net:56487`
- **Database:** `railway`
- **Status:** ✅ Connected

---

## 🔧 Production Configuration Details

### 1. Backend CORS Configuration
**File:** `src/main/java/com/babyadoption/config/CorsConfig.java`

✅ **Production URLs Already Configured:**
- `https://www.nightsathi.com` ✅
- `https://nightsathi.com` ✅
- `https://baby-adoption-website.vercel.app` ✅

### 2. Frontend API Configuration
**File:** `src/config/api.ts`

✅ **Production API URL:**
- Default: `https://baby-adoption-backend.onrender.com`
- Uses environment variable: `VITE_API_BASE_URL` (set in Vercel)

### 3. Application Properties
**File:** `src/main/resources/application-production.properties`

✅ **Production Settings:**
- CORS origins: `https://www.nightsathi.com,https://nightsathi.com`
- Database: Railway MySQL configured
- Port: Uses Render's PORT environment variable

---

## 📋 Environment Variables Required

### Vercel (Frontend)
Set these in Vercel Dashboard → Settings → Environment Variables:

```
VITE_API_BASE_URL=https://baby-adoption-backend.onrender.com
```

**Important:** Set for **Production**, **Preview**, and **Development** environments.

### Render (Backend)
Set these in Render Dashboard → Environment Variables:

```
SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectTimeout=30000&socketTimeout=60000
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
PORT=8082
SPRING_PROFILES_ACTIVE=production
```

**Optional CORS variable:**
```
CORS_ALLOWED_ORIGINS=https://www.nightsathi.com,https://nightsathi.com,http://localhost:5173
```

---

## 🚀 Deployment Checklist

### Backend (Render)
- [x] Code pushed to GitHub
- [ ] Backend redeployed on Render (after code push)
- [ ] Environment variables set in Render
- [ ] Database connection verified
- [ ] API endpoints tested

### Frontend (Vercel)
- [x] Code pushed to GitHub
- [ ] Environment variable `VITE_API_BASE_URL` set in Vercel
- [ ] Frontend redeployed (automatic on push)
- [ ] Custom domain verified (nightsathi.com)
- [ ] 404 routing issue fixed (vercel.json updated)

---

## 🔍 Verification Steps

### 1. Test Frontend
1. Visit: `https://www.nightsathi.com`
2. Open DevTools (F12) → Network tab
3. Try to login or fetch data
4. Verify API calls go to: `https://baby-adoption-backend.onrender.com`

### 2. Test Backend
1. Test endpoint: `https://baby-adoption-backend.onrender.com/api/babies/states`
2. Should return list of states (not 404)
3. Check CORS headers in response

### 3. Test Database
1. POST request to create a post
2. Verify data is saved in database
3. GET request to retrieve posts
4. Verify data persists after server restart

---

## ⚠️ Important Notes

1. **CORS Configuration:** 
   - Global CORS config in `CorsConfig.java` handles all origins
   - No need for individual `@CrossOrigin` annotations
   - Production URLs are already included

2. **API Base URL:**
   - Frontend automatically uses production URL when deployed on Vercel
   - Can override with `VITE_API_BASE_URL` environment variable

3. **Database:**
   - Uses Railway MySQL
   - Connection details in `application-production.properties`
   - Hibernate auto-creates tables on first run

4. **Routing:**
   - `vercel.json` configured for SPA routing
   - `_redirects` file added as backup
   - All routes redirect to `index.html`

---

## 🐛 Troubleshooting

### CORS Errors
- Verify `nightsathi.com` is in CORS allowed origins
- Check Render environment variables
- Restart Render service after CORS changes

### API Connection Failed
- Verify `VITE_API_BASE_URL` is set in Vercel
- Check Render backend is running
- Verify backend URL is accessible

### 404 on Page Refresh
- Verify `vercel.json` is deployed
- Check `_redirects` file in `public/` folder
- Redeploy frontend if needed

### Database Not Saving
- Verify database connection in Render logs
- Check environment variables are set correctly
- Verify `BabyPost` entity is properly configured

---

## 📞 Support

If issues persist:
1. Check Render deployment logs
2. Check Vercel deployment logs
3. Verify all environment variables are set
4. Test API endpoints directly with Postman/curl

