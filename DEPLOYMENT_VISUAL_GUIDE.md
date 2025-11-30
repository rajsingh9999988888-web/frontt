# Visual Deployment Guide - Step by Step

## Current Status Dashboard

```
┌─────────────────────────────────────────────────────────────────┐
│                    DEPLOYMENT STATUS                             │
├─────────────────────────────────────────────────────────────────┤
│                                                                   │
│  BACKEND (Render)                                                 │
│  URL: https://baby-adoption-backend.onrender.com                │
│  Status: ✅ ONLINE & RUNNING                                     │
│  Code Fixes: ✅ COMMITTED & PUSHED                               │
│  Config Needed: ⏳ PENDING                                        │
│  Deploy Needed: ⏳ PENDING                                        │
│                                                                   │
│  FRONTEND (Vercel)                                                │
│  URL: https://baby-adoption-website.vercel.app                  │
│  Status: ⏳ NEEDS DEPLOYMENT                                     │
│  Code: ✅ READY                                                   │
│  Config Needed: ⏳ PENDING                                        │
│  Deploy Needed: ⏳ PENDING                                        │
│                                                                   │
│  DATABASE (Railway MySQL)                                         │
│  Status: ✅ CONFIGURED                                            │
│  Host: shortline.proxy.rlwy.net:56487                           │
│  Database: railway                                               │
│                                                                   │
└─────────────────────────────────────────────────────────────────┘
```

---

## Step 1: Configure Render Backend ⏳

### Visual Walkthrough

```
STEP 1.1: Go to Render Dashboard
─────────────────────────────────
1. Open: https://dashboard.render.com
2. You should see your services listed
3. Click on: "baby-adoption-backend"

        ┌─────────────────────────────────┐
        │  Services                        │
        │  ├─ baby-adoption-backend  ←── CLICK HERE
        │  └─ database               
        └─────────────────────────────────┘


STEP 1.2: Open Environment Variables
────────────────────────────────────
1. You're now in the backend service page
2. Look at left sidebar
3. Click: "Environment"

        ┌──────────────┐
        │ Environment  │ ←── CLICK
        │ Settings     │
        │ ...          │
        └──────────────┘


STEP 1.3: Add CORS Environment Variable
─────────────────────────────────────────
1. You should see a list of environment variables
2. Look for: "SPRING_WEB_CORS_ALLOWED_ORIGINS"
3. If it exists: CLICK to edit
4. If not: Click "Add Environment Variable"

        ┌─────────────────────────────────────────────┐
        │ Environment Variables                       │
        │                                              │
        │ SPRING_PROFILES_ACTIVE     | production     │
        │ SPRING_DATASOURCE_URL      | jdbc:mysql:... │
        │ SPRING_DATASOURCE_USERNAME | root           │
        │ ...                                          │
        │                                              │
        │ + Add Environment Variable                  │
        └─────────────────────────────────────────────┘


STEP 1.4: Set the Value
──────────────────────
Replace YOUR_VERCEL_URL with your actual URL!

        ┌────────────────────────────────────────────────┐
        │ Key: SPRING_WEB_CORS_ALLOWED_ORIGINS           │
        │                                                │
        │ Value: (paste below, replace URL)             │
        │ https://baby-adoption-website.vercel.app,    │
        │ http://localhost:5173                          │
        │                                                │
        │                   [SAVE]                       │
        └────────────────────────────────────────────────┘

NOTES:
  • Include the comma between URLs
  • Keep http://localhost:5173 for development
  • Use your actual Vercel URL (you'll get it later)


STEP 1.5: Redeploy Backend
───────────────────────────
1. Look for "Manual Deploy" button
2. Click it
3. Wait for deployment (2-3 minutes)
4. You'll see: "✓ Live" when done

        ┌─────────────────────┐
        │ [Manual Deploy] ←── CLICK
        │                      │
        │ Deploying...        │
        │ ████████░░ 50%      │
        │                      │
        │ ✓ Live (after 3min)  │
        └─────────────────────┘
```

---

## Step 2: Configure Vercel Frontend ⏳

### Visual Walkthrough

```
STEP 2.1: Go to Vercel Dashboard
────────────────────────────────
1. Open: https://vercel.com/dashboard
2. You should see your projects
3. Click: "baby-adoption-website"

        ┌──────────────────────────────────┐
        │ Projects                         │
        │ ├─ baby-adoption-website  ←── CLICK HERE
        │ └─ other-projects         
        └──────────────────────────────────┘


STEP 2.2: Open Settings
───────────────────────
1. You're in the project page
2. Click "Settings" tab at top

        ┌──────────────────────────────┐
        │ [Overview] [Settings] ←── HERE
        │            [Deployments]
        └──────────────────────────────┘


STEP 2.3: Go to Environment Variables
──────────────────────────────────────
1. In Settings page, click left sidebar
2. Find: "Environment Variables"
3. Click it

        ┌──────────────────────────┐
        │ Settings Sidebar         │
        │ ├─ General               │
        │ ├─ Environment ←── HERE   │
        │ ├─ Domains               │
        │ └─ ...                   │
        └──────────────────────────┘


STEP 2.4: Add New Variable
──────────────────────────
1. Click "Add New" or "+ New"

        ┌─────────────────────────────┐
        │ Environment Variables       │
        │                              │
        │ [+ Add New] ←── CLICK HERE   │
        │                              │
        │ (no variables yet)           │
        └─────────────────────────────┘


STEP 2.5: Fill in the Values
────────────────────────────
        ┌──────────────────────────────────────┐
        │ Name: VITE_API_BASE_URL              │
        │                                       │
        │ Value: https://baby-adoption-       │
        │        backend.onrender.com          │
        │                                       │
        │ Environments: (check these)          │
        │ ☑ Production                         │
        │ ☑ Preview                           │
        │ ☑ Development                       │
        │                                       │
        │              [SAVE]                  │
        └──────────────────────────────────────┘


STEP 2.6: Redeploy Frontend
────────────────────────────
Option A - Automatic (Recommended):
  1. Open terminal
  2. cd baby-adoption-website
  3. git add .
  4. git commit -m "deploy: configure production"
  5. git push origin main
  → Vercel auto-redeploys in 1-2 minutes

Option B - Manual:
  1. Go to "Deployments" tab
  2. Find latest deployment
  3. Click "⋯" (three dots)
  4. Select "Redeploy"

        ┌─────────────────────┐
        │ Deployments         │
        │                      │
        │ Latest | ⋯ | Redeploy│
        │ 2 min ago           │
        │ Status: Ready ✓     │
        │                      │
        │ 5 min ago           │
        │ Status: Cancelled   │
        └─────────────────────┘

  5. Wait 2-3 minutes for deployment
```

---

## Step 3: Verify Everything Works ✅

### Test Checklist

```
TEST 1: Backend is Running
────────────────────────
Open in terminal or browser:
  https://baby-adoption-backend.onrender.com/

Expected: 404 or Spring Boot error (NOT connection refused)
Status: ✅ Pass / ❌ Fail


TEST 2: API Endpoints
─────────────────────
Open in terminal:
  curl https://baby-adoption-backend.onrender.com/api/babies/states

Expected: JSON array like ["Andhra Pradesh", "Bihar", ...]
Status: ✅ Pass / ❌ Fail


TEST 3: Frontend Loads
──────────────────────
Open in browser:
  https://baby-adoption-website.vercel.app

Expected: Website loads, no console errors
Status: ✅ Pass / ❌ Fail


TEST 4: CORS Works
──────────────────
1. Open DevTools (F12)
2. Go to Console tab
3. Try logging in or loading data
4. Check for errors

Expected: No CORS errors (red errors)
Status: ✅ Pass / ❌ Fail


TEST 5: API Calls
─────────────────
1. Open DevTools (F12)
2. Go to Network tab
3. Try logging in
4. Look for API requests

Expected: Requests go to https://baby-adoption-backend.onrender.com
         Status codes are 200 (green)
Status: ✅ Pass / ❌ Fail


TEST 6: Login Works
───────────────────
1. Try to login with test account
2. Check if you get logged in

Expected: Successfully logged in, no errors
Status: ✅ Pass / ❌ Fail
```

---

## Complete Flow Diagram

```
┌────────────────────────────────────────────────────────────────────┐
│                      YOUR APPLICATION FLOW                         │
└────────────────────────────────────────────────────────────────────┘

USER BROWSER
    ↓
    ├─→ VERCEL FRONTEND
    │   URL: https://baby-adoption-website.vercel.app
    │   Environment: VITE_API_BASE_URL
    │   
    ├─→ API REQUEST (HTTP/CORS)
    │   https://baby-adoption-backend.onrender.com/api/users/login
    │
    └─→ RENDER BACKEND
        URL: https://baby-adoption-backend.onrender.com
        Environment: SPRING_WEB_CORS_ALLOWED_ORIGINS
        
        ├─→ VALIDATE CORS
        │   ✓ Check if origin is allowed
        │   
        ├─→ PROCESS REQUEST
        │   ✓ Parse JSON
        │   ✓ Authenticate user
        │   ✓ Query database
        │   
        └─→ RESPONSE
            ✓ User data
            ✓ Auth token
            ✓ CORS headers
            
            ↓
        BROWSER RECEIVES RESPONSE
            ✓ CORS headers present
            ✓ JavaScript can access data
            ✓ User sees result
```

---

## Expected Timeline

```
ACTION              TIME    STATUS
─────────────────────────────────────
Configure Render    5 min   ⏳ DO NOW
Render Deploy       3 min   ⏳ AUTO
Configure Vercel    5 min   ⏳ DO NOW
Vercel Deploy       3 min   ⏳ AUTO
Wait + Test         5 min   ⏳ DO NOW
                    ─────
TOTAL              21 min   ⏳ START
```

---

## Quick Reference URLs

```
RENDER DASHBOARD:
→ https://dashboard.render.com

VERCEL DASHBOARD:
→ https://vercel.com/dashboard

RAILWAY DASHBOARD:
→ https://railway.app/dashboard

GITHUB REPOSITORY:
→ https://github.com/rajsingh9999988888-web/frontt

BACKEND API:
→ https://baby-adoption-backend.onrender.com

FRONTEND (after deployment):
→ https://baby-adoption-website.vercel.app
```

---

## Emergency Commands

### If something goes wrong:

```bash
# Check git status
cd c:\updated\frontt
git status

# View recent commits
git log --oneline -10

# Check if changes were pushed
git push origin main --force-with-lease

# Manual test backend
curl -v https://baby-adoption-backend.onrender.com/api/babies/states

# Test CORS
curl -H "Origin: http://localhost:5173" -X OPTIONS \
     https://baby-adoption-backend.onrender.com/api/users
```

---

## You're All Set! 🎉

Just follow the steps above and your production deployment will be complete!
