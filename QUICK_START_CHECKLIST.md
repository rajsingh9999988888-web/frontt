# ✅ Quick Start Checklist - Get Your Website Live

## 🎯 Do These 5 Things First (30 minutes)

### **1. Update Domain in Code** (5 min) ⚠️ CRITICAL

**Files to edit:**

**File 1:** `baby-adoption-website/index.html`
- Line 40: Change `https://yourdomain.com` → Your actual domain

**File 2:** `baby-adoption-website/src/pages/CityCategoryPage.tsx`
- Line ~60: Change `https://mydomain.com` → Your actual domain

**File 3:** `baby-adoption-website/public/sitemap.xml`
- Replace all `https://yourdomain.com` → Your actual domain

**File 4:** `baby-adoption-website/public/robots.txt`
- Line with Sitemap: Update domain

**Then commit:**
```bash
git add .
git commit -m "Update domain to production URL"
git push
```

---

### **2. Test Locally** (10 min)

```bash
# Terminal 1 - Start Backend
cd baby-adoption-backend
mvn spring-boot:run

# Terminal 2 - Start Frontend
cd baby-adoption-website
npm run dev
```

**Quick Tests:**
- [ ] Home page loads: http://localhost:5173
- [ ] Images display correctly
- [ ] Logo shows in navbar
- [ ] State cards appear
- [ ] Test search: http://localhost:5173/bhopal/call-girls-stores
- [ ] API works: http://localhost:8082/api/search/mumbai/call-girls

---

### **3. Deploy Backend** (15 min)

**Render.com (Easiest):**
1. Go to: https://render.com
2. New → Web Service
3. Connect GitHub repo
4. Settings:
   - Build Command: `mvn clean package`
   - Start Command: `java -jar target/baby-adoption-backend-*.jar`
   - Environment: `SPRING_PROFILES_ACTIVE=production`
5. Deploy

**Get your backend URL:** `https://your-backend.onrender.com`

---

### **4. Deploy Frontend** (10 min)

**Vercel (Easiest):**
1. Go to: https://vercel.com
2. Import Project → Select your GitHub repo
3. Root Directory: `baby-adoption-website`
4. Environment Variable:
   - `VITE_API_BASE_URL` = Your backend URL
5. Deploy

**Get your frontend URL:** `https://your-site.vercel.app`

---

### **5. Set Up Google Search Console** (10 min)

1. Go to: https://search.google.com/search-console
2. Add Property → Enter your domain
3. Verify ownership (HTML tag method)
4. Submit sitemap: `https://yourdomain.com/sitemap.xml`

---

## ✅ Post-Deployment Checklist

After deployment:

- [ ] Website loads at your domain
- [ ] All pages work
- [ ] Images display
- [ ] Search functionality works
- [ ] Mobile view works
- [ ] API endpoints respond
- [ ] Sitemap accessible: `yourdomain.com/sitemap.xml`
- [ ] Robots.txt accessible: `yourdomain.com/robots.txt`
- [ ] Google Search Console verified
- [ ] Sitemap submitted to Google

---

## 🚀 You're Live!

Once these 5 steps are done, your website is:
- ✅ Deployed and accessible
- ✅ SEO optimized
- ✅ Ready for search engines
- ✅ Ready for users

**Next:** Monitor analytics and optimize based on data!

