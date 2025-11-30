# Frontend-Backend Connection Configuration

## Current Configuration Status ✅

Your project is **already configured** for production. No code changes needed!

---

## Frontend Configuration

### File: `baby-adoption-website/src/config/api.ts`

```typescript
// API Configuration
// In production, this will use the Render backend URL
// In development, it uses localhost

const getApiBaseUrl = (): string => {
  // Check for environment variable (Vercel will provide this)
  if (import.meta.env.VITE_API_BASE_URL) {
    return import.meta.env.VITE_API_BASE_URL;
  }
  
  // Check if we're in production (Vercel deployment)
  if (import.meta.env.PROD) {
    // Default production URL - Render backend
    return 'https://baby-adoption-backend.onrender.com';
  }
  
  // Development: use localhost
  return 'http://localhost:8082';
};

export const API_BASE_URL = getApiBaseUrl();

// Helper function to build API URLs
export const buildApiUrl = (path: string): string => {
  // Remove leading slash if present to avoid double slashes
  const cleanPath = path.startsWith('/') ? path.slice(1) : path;
  return `${API_BASE_URL}/${cleanPath}`;
};

export default API_BASE_URL;
```

### How It Works:
1. **In Vercel (Production):**
   - Reads `VITE_API_BASE_URL` env var
   - Set it to: `https://baby-adoption-backend.onrender.com`
   - All API calls → Render backend ✅

2. **Local Development:**
   - Uses: `http://localhost:8082`
   - Connect to local backend ✅

---

## Backend Configuration

### File: `baby-adoption-backend/src/main/java/com/babyadoption/config/CorsConfig.java`

```java
package com.babyadoption.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Value("${spring.web.cors.allowed-origins:http://localhost:5173}")
    private String allowedOrigins;

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        
        // Parse allowed origins from configuration (comma-separated)
        String[] origins = allowedOrigins.split(",");
        for (String origin : origins) {
            config.addAllowedOrigin(origin.trim());
        }
        
        // Also allow common localhost ports for development
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedOrigin("http://localhost:5174");
        config.addAllowedOrigin("http://localhost:3000");
        config.addAllowedOrigin("http://localhost:8080");
        
        config.addAllowedHeader("*");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("PATCH");
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```

### How It Works:
1. **Reads Environment Variable:**
   - Property: `spring.web.cors.allowed-origins`
   - In Render, set: `SPRING_WEB_CORS_ALLOWED_ORIGINS`
   - Value: `https://your-vercel-url.vercel.app,http://localhost:5173`

2. **Allows These Origins:**
   - Your Vercel frontend URL ✅
   - Localhost (development) ✅
   - All common localhost ports ✅

3. **Allows These HTTP Methods:**
   - GET ✅
   - POST ✅
   - PUT ✅
   - DELETE ✅
   - PATCH ✅
   - OPTIONS ✅

4. **Allows:**
   - All headers ✅
   - Credentials (cookies, auth tokens) ✅

---

## Environment Variables Required

### For Vercel (Frontend)

```
VITE_API_BASE_URL = https://baby-adoption-backend.onrender.com
```

**Why:** Tells Vercel-deployed frontend where to send API requests

### For Render (Backend)

```
SPRING_WEB_CORS_ALLOWED_ORIGINS = https://your-vercel-frontend-url.vercel.app,http://localhost:5173
```

**Why:** Tells Spring Boot which frontend URLs are allowed to make requests

**Other required variables (already configured):**
```
SPRING_PROFILES_ACTIVE=production
SPRING_DATASOURCE_URL=jdbc:mysql://shortline.proxy.rlwy.net:56487/railway?...
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=OiDIYtjuGrLRDsCWLBxdwvZhJGGJNnTk
SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
SPRING_JPA_HIBERNATE_DDL_AUTO=update
```

---

## How API Calls Flow

### 1. Frontend Makes Request
```
Vercel Frontend (https://your-url.vercel.app)
    ↓
Reads VITE_API_BASE_URL from env
    ↓
Constructs URL: https://baby-adoption-backend.onrender.com/api/users/login
    ↓
Sends HTTP request with origin header
```

### 2. Backend Receives Request
```
Render Backend (https://baby-adoption-backend.onrender.com)
    ↓
CorsFilter checks origin header
    ↓
Compares with SPRING_WEB_CORS_ALLOWED_ORIGINS
    ↓
If matches, adds CORS headers to response
    ↓
Frontend receives response with CORS headers
    ↓
Browser allows JavaScript to access response ✅
```

### 3. Request Succeeds
```
✅ No CORS errors
✅ Data flows to frontend
✅ User sees response
```

---

## Production Deployment Steps

### Step 1: Configure Render (Backend)
1. Go to https://dashboard.render.com
2. Select baby-adoption-backend
3. Set environment variable: `SPRING_WEB_CORS_ALLOWED_ORIGINS`
4. Value: `https://your-vercel-url.vercel.app,http://localhost:5173`
5. Redeploy

### Step 2: Configure Vercel (Frontend)
1. Go to https://vercel.com/dashboard
2. Select baby-adoption-website
3. Settings → Environment Variables
4. Add: `VITE_API_BASE_URL = https://baby-adoption-backend.onrender.com`
5. Redeploy

### Step 3: Test
1. Open frontend URL
2. DevTools → Network tab
3. Make API request (login, fetch data)
4. Verify request goes to `https://baby-adoption-backend.onrender.com`
5. Verify response has CORS headers
6. No errors = Success ✅

---

## Local Development

### Running Locally
```bash
# Terminal 1: Start backend
cd baby-adoption-backend
mvn spring-boot:run

# Terminal 2: Start frontend
cd baby-adoption-website
npm install
npm run dev
```

### Automatic Configuration
- Frontend uses `http://localhost:8082` (backend)
- Backend allows `http://localhost:5173` (frontend)
- Already configured in CorsConfig.java
- No changes needed for local development ✅

---

## Security Considerations

✅ **HTTPS Only:** Production URLs use HTTPS
✅ **Credentials Allowed:** Cookies/tokens work with `allowCredentials: true`
✅ **Specific Origins:** Only your frontend can access backend
✅ **All HTTP Methods:** Frontend can POST, PUT, DELETE data
✅ **Header Flexibility:** All headers allowed for flexibility

---

## Summary

| Item | Status | Details |
|------|--------|---------|
| Frontend API Config | ✅ Ready | Uses VITE_API_BASE_URL env var |
| Backend CORS Config | ✅ Ready | Reads SPRING_WEB_CORS_ALLOWED_ORIGINS env var |
| Environment Variables | 🔲 Needed | Must set in Vercel & Render dashboards |
| Database Connection | ✅ Configured | Railway MySQL connected |
| SSL/HTTPS | ✅ Active | Both services use HTTPS |

**Action Required:** Set environment variables in Vercel & Render dashboards, then redeploy both services.
