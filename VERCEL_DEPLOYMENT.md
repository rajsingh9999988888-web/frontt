# Vercel Frontend Deployment Guide

## Prerequisites
- Frontend deployed on Vercel
- Backend deployed on Render
- Railway MySQL database configured

## Step 1: Set Environment Variable in Vercel

1. Go to your Vercel project dashboard
2. Navigate to **Settings** → **Environment Variables**
3. Add the following environment variable:

   ```
   Name: VITE_API_BASE_URL
   Value: https://baby-adoption-backend.onrender.com
   ```

   **Backend URL:** `https://baby-adoption-backend.onrender.com`

4. Make sure to set this for **Production**, **Preview**, and **Development** environments
5. Click **Save**

## Step 2: Redeploy Frontend

After adding the environment variable:
1. Go to **Deployments** tab
2. Click the **⋯** (three dots) on the latest deployment
3. Select **Redeploy**
4. Or push a new commit to trigger automatic deployment

## Step 3: Update Backend CORS

In your Render backend environment variables, add/update:

```
CORS_ALLOWED_ORIGINS=https://your-frontend.vercel.app,http://localhost:5173
```

Replace `your-frontend.vercel.app` with your actual Vercel frontend URL.

## Step 4: Verify Connection

1. Open your Vercel frontend URL
2. Open browser DevTools (F12) → **Network** tab
3. Try to login or fetch data
4. Check if API calls are going to your Render backend URL
5. Verify there are no CORS errors in the console

## Troubleshooting

### CORS Errors
- Make sure `CORS_ALLOWED_ORIGINS` in Render includes your Vercel URL
- Check that the URL matches exactly (including https://)
- Restart Render service after updating environment variables

### API Connection Failed
- Verify `VITE_API_BASE_URL` is set correctly in Vercel
- Check that Render backend is running and accessible
- Verify the backend URL is correct (should start with https://)

### Environment Variable Not Working
- Make sure variable name starts with `VITE_` (required for Vite)
- Redeploy after adding environment variables
- Check Vercel build logs for any errors

## Example Configuration

### Vercel Environment Variables:
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

**Note:** Replace `https://your-frontend.vercel.app` with your actual Vercel frontend URL.

## Notes

- Vite requires environment variables to start with `VITE_` to be exposed to the client
- Always use HTTPS URLs in production
- Update CORS origins whenever you deploy to a new domain
- Test locally first with `VITE_API_BASE_URL=http://localhost:8082`

