# How to set `VITE_API_BASE_URL` on Vercel (via dashboard or API)

This document explains two ways to set the `VITE_API_BASE_URL` environment variable for your Vercel project so your frontend points to the Render backend.

Option A — Vercel dashboard (recommended)
1. Open https://vercel.com and sign in.
2. Open your project (the one hosting `nightsathi.com`).
3. Settings → Environment Variables.
4. Add a new variable:
   - Name: `VITE_API_BASE_URL`
   - Value: `https://baby-adoption-backend.onrender.com`
   - Target: `Production`
5. Save and redeploy the project (click Deployments → Redeploy or push a new commit).

Option B — Use the Vercel API from PowerShell (script below)
Prerequisites:
- `VERCEL_TOKEN` environment variable with a Personal Token from Vercel (Account → Tokens).
- `VERCEL_PROJECT_ID` environment variable with your project id (find in project settings or use `vercel projects ls`).

Save the following script as `scripts/set-vercel-env.ps1` and run it in PowerShell.

Notes:
- This script will create a Production-scoped env variable for the project.
- It requires `VERCEL_TOKEN` and `VERCEL_PROJECT_ID` to be exported as environment variables before running.

Example usage:
```powershell
$env:VERCEL_TOKEN = "<your-vercel-token>"
$env:VERCEL_PROJECT_ID = "<your-project-id>"
.\scripts\set-vercel-env.ps1
```
