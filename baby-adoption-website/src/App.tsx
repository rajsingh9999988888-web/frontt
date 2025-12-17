import React, { useEffect } from 'react';
import { Routes, Route, Navigate, useLocation } from 'react-router-dom';
import { useAuth } from './contexts/AuthContext';
import Navbar from './components/Navbar';
import Footer from './components/Footer';
import Home from './pages/Home';
import AddPost from './pages/AddPost';
import BabyList from './pages/BabyList';
import BabyDetail from './pages/BabyDetail';
import LoginSignup from './pages/LoginSignup';
import AdminDashboard from './pages/AdminDashboard';
import FloatingAddButton from './components/FloatingAddButton';
import UserDashboard from './pages/UserDashboard';
import AccountSettings from './pages/AccountSettings';
import Coupons from './pages/Coupons';
import BuyCredits from './pages/BuyCredits';
import UserAds from './pages/UserAds';
import CityCategoryPage from './pages/CityCategoryPage';

interface ProtectedRouteProps {
  children: React.ReactNode;
}

function ProtectedRoute({ children }: ProtectedRouteProps) {
  const { isLoggedIn, isAuthReady, setRedirectPath } = useAuth();
  const location = useLocation();

  useEffect(() => {
    if (isAuthReady && !isLoggedIn) {
      setRedirectPath(location.pathname);
    }
  }, [isLoggedIn, isAuthReady, location.pathname, setRedirectPath]);

  if (!isAuthReady) {
    return null;
  }

  if (!isLoggedIn) {
    return <Navigate to="/login-signup" />;
  }

  return <React.Fragment>{children}</React.Fragment>;
}

function AdminRoute({ children }: ProtectedRouteProps) {
  const { isLoggedIn, isAuthReady, setRedirectPath } = useAuth();
  const location = useLocation();

  useEffect(() => {
    if (isAuthReady && !isLoggedIn) {
      setRedirectPath(location.pathname);
    }
  }, [isLoggedIn, isAuthReady, location.pathname, setRedirectPath]);

  if (!isAuthReady) {
    return null;
  }

  if (!isLoggedIn) {
    return <Navigate to="/login-signup" />;
  }

  return <React.Fragment>{children}</React.Fragment>;
}

export default function App(): React.JSX.Element {
  return (
    <div className="min-h-screen bg-gray-100 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <ScrollToTop />
      <Navbar />
      <main className="pb-16">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/add-post" element={<AddPost />} />
          <Route path="/baby-list" element={<BabyList />} />
          <Route path="/baby-detail/:id" element={<BabyDetail />} />
          <Route path="/login-signup" element={<LoginSignup />} />
          <Route path="/employee-register" element={<Navigate to="/login-signup" />} />
          
          {/* Dynamic city-based search route */}
          {/* Example: /bhopal/call-girls-stores, /mumbai/massage-stores */}
          <Route path="/:city/:category-stores" element={<CityCategoryPage />} />

          <Route path="/dashboard" element={<ProtectedRoute><UserDashboard /></ProtectedRoute>} />
          <Route path="/admin" element={<AdminRoute><AdminDashboard /></AdminRoute>} />
          <Route path="/account-settings" element={<ProtectedRoute><AccountSettings /></ProtectedRoute>} />
          <Route path="/coupons" element={<ProtectedRoute><Coupons /></ProtectedRoute>} />
          <Route path="/buy-credits" element={<ProtectedRoute><BuyCredits /></ProtectedRoute>} />
          <Route path="/my-ads" element={<ProtectedRoute><UserAds /></ProtectedRoute>} />
        </Routes>
      </main>
      <FloatingAddButton />
      <Footer />
    </div>
  );
}

function ScrollToTop(): React.JSX.Element | null {
  const location = useLocation();

  useEffect(() => {
    window.scrollTo({ top: 0, behavior: 'auto' });
  }, [location.pathname, location.search]);

  return null;
}
