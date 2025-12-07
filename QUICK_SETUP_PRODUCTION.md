# 🚀 Quick Production Setup - nightsathi.com

## ✅ Code Already Configured!

All production URLs are already set in the code. You just need to set environment variables in Vercel and Render dashboards.

---

## 📋 STEP 1: Vercel Environment Variable (Frontend)

### Go to Vercel Dashboard:
1. Visit: https://vercel.com/dashboard
2. Select your project: `baby-adoption-website` (or your project name)
3. Go to: **Settings** → **Environment Variables**

### Add This Variable:
```
Name: VITE_API_BASE_URL
Value: https://baby-adoption-backend.onrender.com
```

### Important:
- ✅ Set for **Production**
- ✅ Set for **Preview** 
- ✅ Set for **Development**

### After Adding:
- Go to **Deployments** tab
- Click **⋯** (three dots) on latest deployment
- Click **Redeploy**

---

## 📋 STEP 2: Render Environment Variables (Backend)

### Go to Render Dashboard:
1. Visit: https://dashboard.render.com
2. Select your backend service: `baby-adoption-backend`
3. Go to: **Environment** tab

### Add These Variables (Copy EXACTLY):

#### 1. Production Profile:
```
Key: SPRING_PROFILES_ACTIVE
Value: production
```

#### 2. Database URL:
```
Key: SPRING_DATASOURCE_URL
Value: jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectTimeout=30000&socketTimeout=60000
```

#### 3. Database Username:
```
Key: SPRING_DATASOURCE_USERNAME
Value: root
```

#### 4. Database Password:
```
Key: SPRING_DATASOURCE_PASSWORD
Value: OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
```

#### 5. CORS Origins (IMPORTANT - Use nightsathi.com):
```
Key: CORS_ALLOWED_ORIGINS
Value: https://www.nightsathi.com,https://nightsathi.com,https://baby-adoption-website.vercel.app,http://localhost:5173
```

### After Adding All Variables:
1. Click **Save Changes**
2. Go to **Events** tab
3. Click **Manual Deploy** → **Deploy latest commit**
4. Wait for deployment (2-5 minutes)

---

## ✅ STEP 3: Verify Everything Works

### Test Frontend:
1. Visit: https://www.nightsathi.com
2. Open DevTools (F12) → **Console** tab
3. Try to login or create a post
4. Check Network tab - API calls should go to `baby-adoption-backend.onrender.com`

### Test Backend:
1. Visit: https://baby-adoption-backend.onrender.com/api/babies/states
2. Should return JSON with list of states (not 404)

### Test Database:
1. Create a post from frontend
2. Refresh page - post should still be there
3. This confirms database is saving data

---

## 🎯 What's Already Done in Code:

✅ **CORS Configuration:**
- `nightsathi.com` already in allowed origins
- `baby-adoption-backend.onrender.com` configured

✅ **Frontend API Config:**
- Production URL: `https://baby-adoption-backend.onrender.com`
- Environment variable support ready

✅ **Database Configuration:**
- Railway MySQL connection ready
- Hibernate auto-creates tables

✅ **Routing:**
- `vercel.json` configured for SPA
- `_redirects` file added

---

## ⚠️ Common Issues:

### Issue: CORS Error
**Solution:** Make sure `CORS_ALLOWED_ORIGINS` in Render includes:
- `https://www.nightsathi.com`
- `https://nightsathi.com`

### Issue: API Not Connecting
**Solution:** 
- Verify `VITE_API_BASE_URL` is set in Vercel
- Check Render backend is running
- Verify backend URL is accessible

### Issue: 404 on Refresh
**Solution:**
- `vercel.json` is already configured
- Just need to redeploy frontend

### Issue: Database Not Saving
**Solution:**
- Verify all database environment variables in Render
- Check Render logs for connection errors
- Verify `SPRING_PROFILES_ACTIVE=production` is set

---

## 📞 Quick Checklist:

### Vercel:
- [ ] `VITE_API_BASE_URL` environment variable added
- [ ] Frontend redeployed

### Render:
- [ ] `SPRING_PROFILES_ACTIVE=production` added
- [ ] `SPRING_DATASOURCE_URL` added
- [ ] `SPRING_DATASOURCE_USERNAME=root` added
- [ ] `SPRING_DATASOURCE_PASSWORD` added
- [ ] `CORS_ALLOWED_ORIGINS` added with nightsathi.com
- [ ] Backend redeployed

### Testing:
- [ ] Frontend loads at nightsathi.com
- [ ] API calls work
- [ ] Database saves data
- [ ] Page refresh works (no 404)

---

## 🎉 That's It!

Once you set these environment variables and redeploy, everything should work perfectly!

**Estimated Time:** 5-10 minutes

**Need Help?** Check the logs in:
- Vercel: Deployments → View Build Logs
- Render: Events → View Logs

