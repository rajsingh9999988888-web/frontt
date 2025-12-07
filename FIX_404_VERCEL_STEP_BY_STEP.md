# Step-by-Step Guide: Fix 404 Error on Page Refresh (Vercel)

## Problem
When you refresh any page on `nightsathi.com` (like `/baby-list`, `/baby-detail/2`, etc.), you get a 404 error.

## Solution Overview
We need to configure Vercel to serve `index.html` for all routes so that React Router can handle the routing on the client side.

---

## Step 1: Navigate to Your Project Settings

1. Go to **Vercel Dashboard**: https://vercel.com/dashboard
2. **IMPORTANT**: You're currently in **Team Settings**. We need **Project Settings** instead.
3. Click on **"Deployments"** in the top navigation bar
4. Find your project (should be `nightsathi.com` or `baby-adoption-website`)
5. Click on the **project name** to open it
6. Once inside the project, click on **"Settings"** tab (top navigation)
7. Click on **"General"** in the left sidebar (under Project Settings, not Team Settings)

---

## Step 2: Verify Project Settings

In the **General** settings page, check these settings:

### Root Directory
- **Option A**: If your repository root is `Fun/` and the frontend is in `baby-adoption-website/`:
  - Set **Root Directory** to: `baby-adoption-website`
  
- **Option B**: If Vercel is already connected to the `baby-adoption-website` folder:
  - Leave **Root Directory** empty (auto-detected)

### Build & Development Settings
- **Framework Preset**: Should be **"Vite"** (or auto-detected)
- **Build Command**: Should be `npm run build` (or auto-detected)
- **Output Directory**: Should be `dist` (or auto-detected)
- **Install Command**: Should be `npm install` (or auto-detected)

### Node.js Version
- **Node.js Version**: Should be `18.x` or `20.x` (latest LTS)

---

## Step 3: Verify vercel.json Configuration

1. In your project settings, scroll down to **"Build & Development Settings"**
2. The `vercel.json` file should be automatically detected
3. If you want to verify it manually:
   - Go to your GitHub repository
   - Navigate to `baby-adoption-website/vercel.json`
   - It should contain:
   ```json
   {
     "rewrites": [
       {
         "source": "/(.*)",
         "destination": "/index.html"
       }
     ],
     "headers": [
       {
         "source": "/(.*)",
         "headers": [
           {
             "key": "X-Content-Type-Options",
             "value": "nosniff"
           },
           {
             "key": "X-Frame-Options",
             "value": "DENY"
           },
           {
             "key": "X-XSS-Protection",
             "value": "1; mode=block"
           }
         ]
       }
     ]
   }
   ```

---

## Step 4: Force a Fresh Deployment

1. Go to **"Deployments"** tab (top navigation)
2. Find the **latest deployment**
3. Click the **three dots (⋯)** on the right side of the deployment
4. Click **"Redeploy"**
5. In the popup, check **"Use existing Build Cache"** - **UNCHECK THIS** (we want a fresh build)
6. Click **"Redeploy"**
7. Wait for the deployment to complete (usually 1-2 minutes)

---

## Step 5: Verify the Deployment

1. Once deployment is complete, check the **"Functions"** or **"Routes"** tab
2. You should see a rewrite rule that routes all paths to `/index.html`
3. The deployment should show **"Ready"** status

---

## Step 6: Test the Fix

1. Go to your website: `https://nightsathi.com`
2. Navigate to any page, for example: `https://nightsathi.com/baby-list`
3. **Refresh the page** (Press F5 or Ctrl+R)
4. The page should load correctly **without** showing a 404 error

### Test Multiple Routes:
- ✅ `https://nightsathi.com/baby-list` (refresh)
- ✅ `https://nightsathi.com/baby-detail/2` (refresh)
- ✅ `https://nightsathi.com/add-post` (refresh)
- ✅ `https://nightsathi.com/login-signup` (refresh)

---

## Step 7: If Still Not Working - Check Build Logs

1. Go to **"Deployments"** tab
2. Click on the **latest deployment**
3. Click on **"Build Logs"** tab
4. Look for any errors or warnings
5. Verify that:
   - The build completed successfully
   - The `vercel.json` file was detected
   - The `dist` folder was created with `index.html`

---

## Step 8: Alternative - Manual Configuration in Vercel

If `vercel.json` is not being detected:

1. Go to **Project Settings** → **"Build & Development Settings"**
2. Scroll down to **"Rewrites"** section
3. Click **"Add Rewrite"**
4. Set:
   - **Source**: `/(.*)`
   - **Destination**: `/index.html`
5. Click **"Save"**
6. Redeploy the project

---

## Step 9: Verify _redirects File (Backup Method)

The `_redirects` file in `public/` folder should also work as a backup:

1. Verify the file exists: `baby-adoption-website/public/_redirects`
2. Content should be:
   ```
   /*    /index.html   200
   ```
3. Vite automatically copies this to `dist/_redirects` during build
4. Vercel should pick it up automatically

---

## Troubleshooting

### Issue: Still getting 404 after redeploy
**Solution**: 
- Clear browser cache (Ctrl+Shift+Delete)
- Try in incognito/private mode
- Check if the deployment actually completed successfully

### Issue: vercel.json not detected
**Solution**:
- Ensure the file is in the root of `baby-adoption-website/` folder
- Check that it's committed to GitHub
- Verify the Root Directory setting in Vercel matches your project structure

### Issue: Build fails
**Solution**:
- Check Node.js version (should be 18.x or 20.x)
- Verify all dependencies are installed correctly
- Check build logs for specific errors

### Issue: Static files (JS, CSS) not loading
**Solution**:
- The rewrite pattern `/(.*)` should automatically exclude files with extensions
- Vercel handles static files automatically
- If issues persist, check the "Functions" tab to see all routes

---

## Quick Checklist

- [ ] Navigated to **Project Settings** (not Team Settings)
- [ ] Verified **Root Directory** is set correctly
- [ ] Verified **Build Command** is `npm run build`
- [ ] Verified **Output Directory** is `dist`
- [ ] Verified `vercel.json` exists in `baby-adoption-website/` folder
- [ ] Redeployed with **fresh build** (cache disabled)
- [ ] Tested page refresh on multiple routes
- [ ] Verified deployment shows "Ready" status

---

## Expected Result

After completing these steps:
- ✅ All page refreshes should work
- ✅ Direct URL access should work (e.g., typing `nightsathi.com/baby-list` directly)
- ✅ No more 404 errors on refresh
- ✅ React Router handles all client-side routing

---

## Need More Help?

If you're still experiencing issues:
1. Check Vercel deployment logs
2. Verify the `vercel.json` file is in the correct location
3. Ensure the latest code is pushed to GitHub
4. Try redeploying again with cache disabled

---

**Last Updated**: Based on current codebase configuration
**Configuration File**: `baby-adoption-website/vercel.json`
**Backup File**: `baby-adoption-website/public/_redirects`

