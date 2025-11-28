# Deployment URLs

## Backend (Render)
**URL:** `https://baby-adoption-backend.onrender.com`

**Status:** ✅ Deployed

**API Endpoints:**
- Base URL: `https://baby-adoption-backend.onrender.com`
- Login: `https://baby-adoption-backend.onrender.com/api/users/login`
- Signup: `https://baby-adoption-backend.onrender.com/api/users/signup`
- Babies: `https://baby-adoption-backend.onrender.com/babies`
- Admin: `https://baby-adoption-backend.onrender.com/admin/posts`

## Frontend (Vercel)
**URL:** `https://your-frontend.vercel.app` (Update with your actual Vercel URL)

**Status:** ⏳ Update Vercel environment variable

## Database (Railway MySQL)
**Host:** `shortline.proxy.rlwy.net`  
**Port:** `56487`  
**Database:** `railway`  
**Username:** `root`

## Configuration

### Vercel Environment Variable:
```
VITE_API_BASE_URL=https://baby-adoption-backend.onrender.com
```

### Render Environment Variables:
```
SPRING_PROFILES_ACTIVE=production
SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=UTF-8&connectTimeout=30000&socketTimeout=60000
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
```

## Next Steps

1. ✅ Backend deployed on Render
2. ⏳ Set `VITE_API_BASE_URL` in Vercel
3. ⏳ Update `CORS_ALLOWED_ORIGINS` in Render with your Vercel frontend URL
4. ⏳ Test the connection

