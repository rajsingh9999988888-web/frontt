# 🚀 Deployment Scripts & Automation

## 📋 Quick Domain Update

### **Option 1: Use Environment Variable (Recommended)**

**For Vercel/Netlify:**
1. Add environment variable: `VITE_SITE_DOMAIN=https://yourdomain.com`
2. Domain will be automatically used

**For local development:**
1. Create `.env` file in `baby-adoption-website/`
2. Add: `VITE_SITE_DOMAIN=https://yourdomain.com`

### **Option 2: Update Config File**

**File:** `baby-adoption-website/src/config/domain.ts`
- Line 12: Change `'https://yourdomain.com'` to your domain

### **Option 3: Use Update Script**

```bash
cd baby-adoption-website
node public/update-domain.js yourdomain.com
```

This automatically updates:
- `sitemap.xml`
- `index.html`
- `robots.txt`

---

## 🔧 Deployment Commands

### **Backend Deployment (Render.com)**

**1. Connect Repository:**
- Go to Render Dashboard
- New → Web Service
- Connect GitHub repository

**2. Configure:**
```
Name: baby-adoption-backend
Environment: Java
Build Command: mvn clean package
Start Command: java -jar target/baby-adoption-backend-*.jar
```

**3. Environment Variables:**
```
SPRING_PROFILES_ACTIVE=production
DATABASE_URL=your_database_url
BACKEND_BASE_URL=https://your-backend.onrender.com
JWT_SECRET=your-secret-key
```

**4. Deploy:**
- Click "Create Web Service"
- Wait for deployment

---

### **Frontend Deployment (Vercel)**

**1. Connect Repository:**
- Go to Vercel Dashboard
- Import Project
- Select GitHub repository

**2. Configure:**
```
Root Directory: baby-adoption-website
Framework Preset: Vite
Build Command: npm run build
Output Directory: dist
```

**3. Environment Variables:**
```
VITE_API_BASE_URL=https://your-backend.onrender.com
VITE_SITE_DOMAIN=https://yourdomain.com
```

**4. Deploy:**
- Click "Deploy"
- Wait for deployment

---

## 📝 Pre-Deployment Checklist

Before deploying, ensure:

- [ ] Domain updated in `src/config/domain.ts`
- [ ] Domain updated in `index.html` (structured data)
- [ ] Domain updated in `sitemap.xml`
- [ ] Domain updated in `robots.txt`
- [ ] Backend URL set in environment variables
- [ ] Database configured for production
- [ ] JWT secret set (use strong random string)
- [ ] CORS configured correctly
- [ ] All tests pass locally

---

## 🎯 Post-Deployment Checklist

After deployment:

- [ ] Website loads at your domain
- [ ] Backend API responds
- [ ] Frontend connects to backend
- [ ] Images load correctly
- [ ] Search functionality works
- [ ] City-based URLs work
- [ ] Mobile view works
- [ ] Sitemap accessible
- [ ] Robots.txt accessible

---

## 🔍 Verification Commands

**Test Backend:**
```bash
curl https://your-backend.onrender.com/api/search/mumbai/call-girls
```

**Test Frontend:**
```bash
curl https://yourdomain.com
```

**Check Sitemap:**
```bash
curl https://yourdomain.com/sitemap.xml
```

**Check Robots:**
```bash
curl https://yourdomain.com/robots.txt
```

---

## 📊 Monitoring Setup

**1. Google Search Console:**
- Submit sitemap
- Monitor indexing
- Track search performance

**2. Analytics:**
- Set up Google Analytics
- Track user behavior
- Monitor traffic sources

**3. Error Monitoring:**
- Set up error tracking (Sentry, etc.)
- Monitor API errors
- Track frontend errors

---

## ✅ Success Indicators

Your deployment is successful when:

1. ✅ Website loads without errors
2. ✅ All pages accessible
3. ✅ API endpoints respond
4. ✅ Images display correctly
5. ✅ Search functionality works
6. ✅ Mobile responsive
7. ✅ SEO meta tags present
8. ✅ Sitemap indexed by Google

---

## 🆘 Troubleshooting

**Backend not starting:**
- Check database connection
- Verify environment variables
- Check logs for errors

**Frontend not loading:**
- Check build errors
- Verify environment variables
- Check CORS configuration

**API not working:**
- Verify backend URL
- Check CORS settings
- Test API directly

**Images not showing:**
- Check image URLs
- Verify upload directory
- Check file permissions

---

## 📞 Need Help?

If you encounter issues:
1. Check deployment logs
2. Verify environment variables
3. Test API endpoints directly
4. Check browser console for errors
5. Review server logs

