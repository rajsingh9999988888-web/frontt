# Production Deployment Setup Guide

## Overview
This guide connects your **Baby Adoption Website** (Vercel Frontend) with **Baby Adoption Backend** (Render) for production.

- **Backend URL:** `https://baby-adoption-backend.onrender.com`
- **Frontend:** Deploy to Vercel
- **Database:** Railway MySQL

---

## Prerequisites
1. Backend deployed on Render: ✅ `https://baby-adoption-backend.onrender.com`
2. Frontend deployed on Vercel (get your URL from Vercel dashboard)
3. Railway MySQL configured
4. Admin access to both Render and Vercel dashboards

---

## STEP 1: Configure Render Backend CORS

### ⚠️ IMPORTANT: Replace with Your Actual Vercel Frontend URL

1. **Go to Render Dashboard**
   - Visit: https://dashboard.render.com
   - Select service: **baby-adoption-backend**

2. **Navigate to Environment Variables**
   - Click **Environment** tab in left sidebar
   - Or go to **Settings** → **Environment**

3. **Update CORS Configuration**
   - Find or create: `SPRING_WEB_CORS_ALLOWED_ORIGINS`
   - Update value to: `https://your-vercel-frontend-url.vercel.app,http://localhost:5173`
   - Example: `https://baby-adoption-website.vercel.app,http://localhost:5173`
   - **Save changes**

4. **Verify Other Required Variables**
   Ensure these are set in Render environment:
   ```
   SPRING_PROFILES_ACTIVE = production
   SPRING_DATASOURCE_URL = jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectTimeout=30000&socketTimeout=60000
   SPRING_DATASOURCE_USERNAME = root
   SPRING_DATASOURCE_PASSWORD = OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
   SPRING_DATASOURCE_DRIVER_CLASS_NAME = com.mysql.cj.jdbc.Driver
   SPRING_JPA_HIBERNATE_DDL_AUTO = update
   ```

5. **Redeploy Backend** (if environment variables changed)
   - Click **Manual Deploy** button
   - Wait for deployment to complete

---

## STEP 2: Configure Vercel Frontend Environment

1. **Go to Vercel Dashboard**
   - Visit: https://vercel.com/dashboard
   - Select project: **baby-adoption-website**

2. **Navigate to Environment Variables**
   - Click **Settings** tab
   - Go to **Environment Variables**

3. **Add Backend API URL**
   - Click **Add New** button
   - Name: `VITE_API_BASE_URL`
   - Value: `https://baby-adoption-backend.onrender.com`
   - Environments: Select **Production, Preview, and Development**
   - Click **Save**

4. **Other Environment Variables** (if needed)
   ```
   VITE_API_BASE_URL = https://baby-adoption-backend.onrender.com
   ```

---

## STEP 3: Redeploy Vercel Frontend

### Option A: Automatic (Recommended)
1. Push a new commit to your main branch:
   ```bash
   git add .
   git commit -m "chore: update production backend URL"
   git push origin main
   ```
2. Vercel will automatically redeploy

### Option B: Manual Redeploy
1. In Vercel dashboard, go to **Deployments**
2. Find the latest deployment
3. Click the **⋯** (three dots)
4. Select **Redeploy**
5. Confirm

---

## STEP 4: Verify Connection

### Check Frontend Build Variable
```bash
cd baby-adoption-website
npm run build
```
Verify that build shows the correct backend URL.

### Test in Browser
1. Open your Vercel frontend URL: `https://your-vercel-frontend-url.vercel.app`
2. Open **DevTools** (Press `F12`)
3. Go to **Network** tab
4. Try to login or perform any API action
5. Look for API calls in Network tab
6. Verify they go to: `https://baby-adoption-backend.onrender.com/...`
7. Check **Console** tab for CORS errors - there should be none

### Test API Endpoints
```bash
# Test backend health
curl https://baby-adoption-backend.onrender.com/

# Test CORS (from your frontend URL)
curl -H "Origin: https://your-vercel-frontend-url.vercel.app" \
     -H "Access-Control-Request-Method: GET" \
     https://baby-adoption-backend.onrender.com/
```

---

## STEP 5: Frontend Code Configuration

Your `baby-adoption-website/src/config/api.ts` is already configured correctly:

```typescript
const getApiBaseUrl = (): string => {
  // Check for environment variable (Vercel will provide this)
  if (import.meta.env.VITE_API_BASE_URL) {
    return import.meta.env.VITE_API_BASE_URL;
  }
  
  // Check if we're in production (Vercel deployment)
  if (import.meta.env.PROD) {
    return 'https://baby-adoption-backend.onrender.com';
  }
  
  // Development: use localhost
  return 'http://localhost:8082';
};

export const API_BASE_URL = getApiBaseUrl();
```

**No code changes needed** - just set the environment variable in Vercel.

---

## STEP 6: Backend Code Configuration

Your CORS configuration is already set up in `CorsConfig.java`. It reads from:
- Environment variable: `SPRING_WEB_CORS_ALLOWED_ORIGINS`
- Default fallback: `http://localhost:5173`

**No code changes needed** - just set the environment variable in Render.

---

## Troubleshooting

### Issue: CORS Error in Browser Console
**Solution:**
- Verify `SPRING_WEB_CORS_ALLOWED_ORIGINS` is set in Render environment
- Include your exact Vercel frontend URL (https://...vercel.app)
- Redeploy Render backend after changing environment variables
- Wait 2-3 minutes for Render to apply changes

### Issue: 404 Error on API Calls
**Solution:**
- Verify backend is running on Render
- Check that API endpoints are correctly formatted
- Open Render logs to see errors
- Test in Postman: `https://baby-adoption-backend.onrender.com/api/endpoint`

### Issue: Connection Timeout
**Solution:**
- Check Render backend is not sleeping (upgrade plan or configure timeout)
- Verify Railway MySQL connection is active
- Check internet connection and firewall

### Issue: Database Connection Error
**Solution:**
- Verify all `SPRING_DATASOURCE_*` variables in Render environment
- Check Railway MySQL credentials haven't changed
- Test database connection locally first

---

## Production Checklist

- [ ] Backend deployed on Render: `https://baby-adoption-backend.onrender.com`
- [ ] Frontend deployed on Vercel: `https://your-url.vercel.app`
- [ ] `VITE_API_BASE_URL` set in Vercel environment: `https://baby-adoption-backend.onrender.com`
- [ ] `SPRING_WEB_CORS_ALLOWED_ORIGINS` set in Render environment with frontend URL
- [ ] Both services redeployed after environment changes
- [ ] API calls test successfully from frontend
- [ ] No CORS errors in browser console
- [ ] Database connectivity verified
- [ ] Login/signup functionality working
- [ ] User data persists in Railway MySQL

---

## Support URLs

- **Render Backend:** https://baby-adoption-backend.onrender.com
- **Render Dashboard:** https://dashboard.render.com
- **Vercel Dashboard:** https://vercel.com/dashboard
- **Railway Dashboard:** https://railway.app/dashboard

---

## Quick Reference

| Component | URL | Status |
|-----------|-----|--------|
| Backend API | https://baby-adoption-backend.onrender.com | ✅ Deployed |
| Frontend | https://your-vercel-frontend-url.vercel.app | Deploy to Vercel |
| Database | Railway MySQL | ✅ Configured |

