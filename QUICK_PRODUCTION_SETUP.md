# Quick Copy-Paste Setup

## For Render Backend Environment Variables

Go to: https://dashboard.render.com → Select service → Environment tab

Copy-paste these environment variables exactly:

```
SPRING_PROFILES_ACTIVE=production

SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectTimeout=30000&socketTimeout=60000

SPRING_DATASOURCE_USERNAME=root

SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk

SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver

SPRING_JPA_HIBERNATE_DDL_AUTO=update

SPRING_WEB_CORS_ALLOWED_ORIGINS=https://your-vercel-frontend-url.vercel.app,http://localhost:5173
```

**⚠️ IMPORTANT:** Replace `https://your-vercel-frontend-url.vercel.app` with your actual Vercel frontend URL!

Example: `https://baby-adoption-website.vercel.app,http://localhost:5173`

---

## For Vercel Frontend Environment Variables

Go to: https://vercel.com/dashboard → Select project → Settings → Environment Variables

Add this variable:

```
Name: VITE_API_BASE_URL
Value: https://baby-adoption-backend.onrender.com
Environments: Production, Preview, Development (check all)
```

Click Save → Then redeploy your frontend

---

## Deployment Order

1. ✅ **Backend already deployed** on Render at: `https://baby-adoption-backend.onrender.com`

2. **Update Render Backend CORS** (Step 1)
   - Set `SPRING_WEB_CORS_ALLOWED_ORIGINS` with your Vercel URL
   - Redeploy backend
   - Wait 2-3 minutes

3. **Update Vercel Frontend** (Step 2)
   - Set `VITE_API_BASE_URL` environment variable
   - Redeploy frontend
   - Wait 2-3 minutes

4. **Test** (Step 3)
   - Open your Vercel frontend URL in browser
   - Try login/signup
   - Check DevTools Network tab for API calls
   - Should see calls to `https://baby-adoption-backend.onrender.com`

---

## API Configuration (Already Set - No Changes Needed)

### Frontend API Config (`baby-adoption-website/src/config/api.ts`)
```typescript
const getApiBaseUrl = (): string => {
  if (import.meta.env.VITE_API_BASE_URL) {
    return import.meta.env.VITE_API_BASE_URL; // Uses Vercel env var
  }
  if (import.meta.env.PROD) {
    return 'https://baby-adoption-backend.onrender.com';
  }
  return 'http://localhost:8082'; // Development
};
```

### Backend CORS Config (`baby-adoption-backend/src/main/java/com/babyadoption/config/CorsConfig.java`)
```java
@Value("${spring.web.cors.allowed-origins:http://localhost:5173}")
private String allowedOrigins;
// Reads from SPRING_WEB_CORS_ALLOWED_ORIGINS environment variable
```

---

## Common Issues & Solutions

### CORS Error: "No 'Access-Control-Allow-Origin' header"
→ Update `SPRING_WEB_CORS_ALLOWED_ORIGINS` in Render with your exact Vercel URL

### Blank Page or 404
→ Verify backend is running: https://baby-adoption-backend.onrender.com (should show Spring Boot response)

### API Calls to localhost instead of Render
→ Check that `VITE_API_BASE_URL` is set in Vercel environment and frontend redeployed

### 401/403 Authorization Errors
→ Database connection issue, check Railway MySQL credentials in Render environment

### Timeout Errors
→ Free tier Render instances sleep after inactivity. Either upgrade plan or keep service awake

---

## Testing Commands

Test if your backend is running:
```bash
curl https://baby-adoption-backend.onrender.com/
```

Test if CORS is working (adjust URL to your frontend):
```bash
curl -H "Origin: https://your-vercel-frontend-url.vercel.app" \
     -H "Access-Control-Request-Method: GET" \
     https://baby-adoption-backend.onrender.com/
```

---

## URLs Summary

- **Backend:** https://baby-adoption-backend.onrender.com
- **Render Dashboard:** https://dashboard.render.com
- **Vercel Dashboard:** https://vercel.com/dashboard
- **Your Frontend:** https://your-vercel-frontend-url.vercel.app (will be assigned by Vercel)
