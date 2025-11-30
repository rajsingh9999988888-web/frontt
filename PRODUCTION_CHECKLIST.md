# Production Connection Checklist - Step by Step

## Your Setup Summary
- **Backend URL:** https://baby-adoption-backend.onrender.com ✅
- **Frontend:** Deploying to Vercel
- **Database:** Railway MySQL
- **Status:** Ready for production connection

---

## STEP 1️⃣: Get Your Vercel Frontend URL

1. Go to https://vercel.com/dashboard
2. Click on your **baby-adoption-website** project
3. Look at the top - you'll see your frontend URL
4. It will look like: `https://baby-adoption-website-xyz.vercel.app`
5. **Copy this URL** - you'll need it in next steps

**Your Vercel Frontend URL:** `_________________________________`  
(Paste your URL here for reference)

---

## STEP 2️⃣: Configure Render Backend

### Part A: Update CORS Configuration

1. Go to https://dashboard.render.com
2. Select your service: **baby-adoption-backend**
3. Click **Environment** in the left sidebar
4. Look for environment variables section
5. Find or create variable: `SPRING_WEB_CORS_ALLOWED_ORIGINS`

**If it exists, UPDATE it to:**
```
https://YOUR-VERCEL-FRONTEND-URL.vercel.app,http://localhost:5173
```

**If it doesn't exist, CREATE NEW:**
- Key: `SPRING_WEB_CORS_ALLOWED_ORIGINS`
- Value: `https://YOUR-VERCEL-FRONTEND-URL.vercel.app,http://localhost:5173`
- Click **Save**

### Part B: Verify All Required Variables

Check that these variables are present (ask admin if missing):
- ✓ `SPRING_PROFILES_ACTIVE` = `production`
- ✓ `SPRING_DATASOURCE_URL` = `jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?...`
- ✓ `SPRING_DATASOURCE_USERNAME` = `root`
- ✓ `SPRING_DATASOURCE_PASSWORD` = `OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk`
- ✓ `SPRING_DATASOURCE_DRIVER_CLASS_NAME` = `com.mysql.cj.jdbc.Driver`
- ✓ `SPRING_JPA_HIBERNATE_DDL_AUTO` = `update`

### Part C: Redeploy Backend

1. Look for **Deploy** button or **Manual Deploy**
2. Click it
3. Wait for deployment to complete (takes 2-3 minutes)
4. You'll see "Live" status when done
5. ✅ Backend CORS configuration complete!

---

## STEP 3️⃣: Configure Vercel Frontend

### Part A: Add Environment Variable

1. Go to https://vercel.com/dashboard
2. Click on **baby-adoption-website** project
3. Click **Settings** tab at the top
4. Click **Environment Variables** in left sidebar
5. Click **Add New** button (or "Add Environment Variable")

**Fill in the form:**
- Name/Key: `VITE_API_BASE_URL`
- Value: `https://baby-adoption-backend.onrender.com`
- Environments: **Check all three boxes** (Production, Preview, Development)
- Click **Save**

### Part B: Redeploy Frontend

Option 1 (Automatic - Recommended):
```bash
cd baby-adoption-website
git add .
git commit -m "chore: configure production backend"
git push origin main
```

Option 2 (Manual):
1. In Vercel dashboard, go to **Deployments**
2. Click the **⋯** (three dots) on latest deployment
3. Select **Redeploy**
4. Confirm

3. Wait for redeployment to complete (takes 2-3 minutes)
5. ✅ Frontend environment variable configured!

---

## STEP 4️⃣: Test Everything Works

### Test 1: Backend is Running
```bash
# Open in browser or run:
curl https://baby-adoption-backend.onrender.com/
```
Expected: Response from Spring Boot (not error)

### Test 2: Frontend Loads
1. Open your Vercel frontend URL in browser
2. Page should load normally
3. ✅ Frontend connects!

### Test 3: API Connection
1. Open your Vercel frontend URL
2. Open DevTools: Press `F12`
3. Go to **Network** tab
4. Perform an action (login, view babies, etc.)
5. Look for network requests
6. Check that URLs show: `https://baby-adoption-backend.onrender.com/api/...`
7. All requests should be **green (200 status)**
8. ✅ API calls successful!

### Test 4: No CORS Errors
1. Stay in DevTools
2. Go to **Console** tab
3. Look for red errors mentioning "CORS" or "Access-Control"
4. If NO CORS errors appear: ✅ CORS configured correctly!
5. If CORS errors appear: Go back to STEP 2 and verify `SPRING_WEB_CORS_ALLOWED_ORIGINS`

### Test 5: Database Working
1. Try to create a new user/post
2. Refresh page
3. Data should persist
4. ✅ Database connected!

---

## STEP 5️⃣: Verify Configuration Files (No Changes Needed)

Your files are already configured correctly:

### Frontend API Config ✅
File: `baby-adoption-website/src/config/api.ts`
- Uses `VITE_API_BASE_URL` from Vercel environment ✓
- Falls back to Render backend URL ✓
- Falls back to localhost for development ✓

### Backend CORS Config ✅
File: `baby-adoption-backend/src/main/java/com/babyadoption/config/CorsConfig.java`
- Reads `SPRING_WEB_CORS_ALLOWED_ORIGINS` from environment ✓
- Allows all necessary HTTP methods ✓
- Allows credentials ✓

**No code changes required!**

---

## Final Checklist

- [ ] Vercel frontend URL obtained
- [ ] Render backend `SPRING_WEB_CORS_ALLOWED_ORIGINS` updated with Vercel URL
- [ ] Render backend redeployed
- [ ] Vercel `VITE_API_BASE_URL` environment variable set
- [ ] Vercel frontend redeployed
- [ ] Backend responds at: https://baby-adoption-backend.onrender.com
- [ ] Frontend loads without errors: https://your-vercel-url.vercel.app
- [ ] API calls go to Render backend (check Network tab)
- [ ] No CORS errors in console
- [ ] Login/signup functionality works
- [ ] Data persists in database
- [ ] Production deployment complete! 🎉

---

## Troubleshooting

### Problem: CORS Error in Console
**Message:** "Access to XMLHttpRequest at 'https://baby-adoption-backend.onrender.com' from origin 'https://your-url.vercel.app' has been blocked by CORS policy"

**Solutions:**
1. Double-check `SPRING_WEB_CORS_ALLOWED_ORIGINS` includes your exact Vercel URL
2. Did you add the `.vercel.app` part?
3. Redeploy backend after changing env vars
4. Wait 2-3 minutes for changes to take effect
5. Clear browser cache (Ctrl+Shift+Delete)

### Problem: API Returns 404
**Message:** Network tab shows 404 on API requests

**Solutions:**
1. Verify endpoint path is correct
2. Check backend logs in Render dashboard
3. Test endpoint with Postman/curl
4. Ensure Spring Boot app is running

### Problem: Cannot Connect to Database
**Message:** Login fails, or data doesn't save

**Solutions:**
1. Verify Railway MySQL credentials in Render env vars
2. Check Railway dashboard for database status
3. Verify database tables exist
4. Check Render logs for connection errors

### Problem: Vercel Frontend Won't Deploy
**Solutions:**
1. Check deployment logs in Vercel
2. Run `npm install` locally to verify deps
3. Run `npm run build` to check for build errors
4. Push to GitHub to trigger redeploy

### Problem: Changes Not Appearing
**Solutions:**
1. Clear browser cache: Ctrl+Shift+Delete
2. Hard refresh: Ctrl+F5
3. Verify environment variables are set
4. Verify services are redeployed
5. Wait 2-3 minutes for CDN to update

---

## Emergency Contacts & Dashboards

- **Render Dashboard:** https://dashboard.render.com
- **Vercel Dashboard:** https://vercel.com/dashboard
- **Railway Dashboard:** https://railway.app/dashboard
- **GitHub:** https://github.com/rajsingh9999988888-web/frontt

---

## Success! 🎉

Your production setup is now complete!

- Frontend: Deployed on Vercel ✅
- Backend: Deployed on Render ✅
- Database: Connected via Railway ✅
- CORS: Configured ✅
- Environment Variables: Set ✅

Your Baby Adoption Website is live in production!
