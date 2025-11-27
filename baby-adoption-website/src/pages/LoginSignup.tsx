import React, { useEffect, useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { useLocation, useNavigate } from 'react-router-dom';
import { API_BASE_URL } from '../config/api';

type Mode = 'login' | 'signup';
type UserType = 'user' | 'recruiter';

const createInitialFormState = () => ({
  email: '',
  password: '',
  confirmPassword: '',
  fullName: '',
  mobile: '',
  primarySkill: '',
  experience: '',
  companyName: '',
  recruiterName: '',
  address: '',
});

const featureList = [
  'Publish and manage your premium profiles',
  'Discover concierge-only news & updates',
  'Activate curated promotions instantly',
];

const legalLinks = [
  { label: 'Terms & Conditions', href: '#' },
  { label: 'Privacy Policy', href: '#' },
  { label: 'Cookie Policy', href: '#' },
];

const supportLinks = [
  { label: 'Contact Us', href: '#' },
  { label: 'Help Center', href: '#' },
  { label: 'Blog', href: '#' },
];

export default function LoginSignup(): React.JSX.Element {
  const { login, redirectPath, clearRedirectPath } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const [mode, setMode] = useState<Mode>(() => {
    const params = new URLSearchParams(location.search);
    return params.get('mode') === 'signup' ? 'signup' : 'login';
  });
  const [userType, setUserType] = useState<UserType>('user');
  const [formData, setFormData] = useState(createInitialFormState);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] = useState(false);

  const getDefaultDashboard = (role?: string) => {
    if (role === 'ADMIN') return '/admin';
    if (role === 'NORMAL') return '/dashboard';
    return '/add-post';
  };

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const nextMode = params.get('mode') === 'signup' ? 'signup' : 'login';
    setMode(nextMode);
  }, [location.search]);

  const isSignup = mode === 'signup';

  const handleModeChange = (nextMode: Mode) => {
    if (mode === nextMode) return;
    navigate(`${location.pathname}${nextMode === 'signup' ? '?mode=signup' : ''}`, { replace: true });
    setMode(nextMode);
    setError('');
    setFormData(createInitialFormState());
  };

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setLoading(true);
    setError('');

    try {
      if (!isSignup) {
        const response = await fetch(`${API_BASE_URL}/api/users/login`, {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({ email: formData.email, password: formData.password }),
        });

        const data = await response.json().catch(() => ({}));
        if (!response.ok) {
          throw new Error(data.message || 'Unable to login right now.');
        }

        const role = data.role || 'NORMAL';
        login({ email: formData.email, role }, data.token);
        const destination = redirectPath || getDefaultDashboard(role);
        navigate(destination);
        if (redirectPath) {
          clearRedirectPath();
        }
        setFormData(createInitialFormState());
        return;
      }

      if (formData.password !== formData.confirmPassword) {
        throw new Error('Passwords do not match');
      }

      const payload =
        userType === 'user'
          ? {
              email: formData.email,
              password: formData.password,
              role: 'NORMAL',
              fullName: formData.fullName,
              mobile: formData.mobile,
              primarySkill: formData.primarySkill,
              experience: formData.experience,
            }
          : {
              email: formData.email,
              password: formData.password,
              role: 'EMPLOYEE',
              mobile: formData.mobile,
              companyName: formData.companyName,
              recruiterName: formData.recruiterName,
              address: formData.address,
            };

      const response = await fetch(`${API_BASE_URL}/api/users/signup`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      const data = await response.json().catch(() => ({}));
      if (!response.ok) {
        throw new Error(data.message || 'Unable to sign up right now.');
      }

      const derivedRole = data.role || (userType === 'user' ? 'NORMAL' : 'EMPLOYEE');
      login({ email: formData.email, role: derivedRole }, data.token);
      alert('Signed up successfully!');
      const destination = redirectPath || getDefaultDashboard(derivedRole);
      navigate(destination);
      if (redirectPath) {
        clearRedirectPath();
      }
      setFormData(createInitialFormState());
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Something went wrong.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-100 px-4 py-16 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto w-full max-w-3xl space-y-10">
        <div className="text-center">
          <p className="text-xs font-semibold uppercase tracking-[0.5em] text-[#ff4f70]">BabyAdopt Access</p>
          <h1 className="mt-2 text-4xl font-semibold text-slate-900 dark:text-white">Get into BabyAdopt!</h1>
          <p className="mt-3 text-sm text-slate-500 dark:text-slate-300">
            Publish and manage private listings, discover concierge news, and stay ahead with exclusive promotions.
          </p>
        </div>

        <div className="rounded-3xl border border-slate-200 bg-white/95 p-10 shadow-xl shadow-slate-900/5 dark:border-slate-800 dark:bg-slate-900/80">
          <div className="grid gap-8 lg:grid-cols-[1.2fr_1fr]">
            <div className="space-y-6">
              <div className="space-y-4">
                {featureList.map((feature) => (
                  <div key={feature} className="flex items-start gap-3 text-sm text-slate-600 dark:text-slate-300">
                    <span className="mt-1 inline-flex h-5 w-5 items-center justify-center rounded-full bg-[#ff4f70]/10 text-[#ff4f70]">
                      ✓
                    </span>
                    <p>{feature}</p>
                  </div>
                ))}
              </div>

              <div className="rounded-2xl border border-slate-200/70 bg-white/80 p-4 text-xs font-semibold uppercase tracking-[0.3em] text-slate-500 dark:border-slate-800/70 dark:bg-slate-900/60 dark:text-slate-400">
                Restricted to adults · Private concierge network
              </div>
            </div>

            <div>
            <form className="space-y-4" onSubmit={handleSubmit}>
  {/* Email */}
  <div>
    <label className="mb-1 block text-sm font-semibold text-slate-600 dark:text-slate-300">
      Email
    </label>
    <input
      type="email"
      name="email"
      value={formData.email}
      onChange={handleChange}
      required
      className="w-full rounded-lg border border-slate-200 bg-white/90 px-3 py-2 text-sm text-slate-700 
      transition focus:border-blue-500 focus:outline-none focus:ring-2 focus:ring-blue-500/20 
      dark:border-slate-700 dark:bg-slate-900/80 dark:text-slate-100"
    />
  </div>

  {/* Password */}
  <div>
    <label className="mb-1 block text-sm font-semibold text-slate-600 dark:text-slate-300">
      Password
    </label>
    <div className="relative">
      <input
        type={showPassword ? "text" : "password"}
        name="password"
        value={formData.password}
        onChange={handleChange}
        required
        className="w-full rounded-lg border border-slate-200 bg-white/90 px-3 py-2 pr-10 text-sm 
        text-slate-700 transition focus:border-blue-500 focus:outline-none focus:ring-2 
        focus:ring-blue-500/20 dark:border-slate-700 dark:bg-slate-900/80 dark:text-slate-100"
      />
      <button
        type="button"
        onClick={() => setShowPassword((prev) => !prev)}
        className="absolute inset-y-0 right-2 text-slate-500 hover:text-slate-700 
        dark:text-slate-400 dark:hover:text-slate-200"
      >
        {showPassword ? "🙈" : "👁️"}
      </button>
    </div>
  </div>

  {/* Signup extra fields */}
  {isSignup && (
    <>
      {/* Confirm Password */}
      <div>
        <label className="mb-1 block text-sm font-semibold text-slate-600 dark:text-slate-300">
          Confirm Password
        </label>
        <input
          type={showConfirmPassword ? "text" : "password"}
          name="confirmPassword"
          value={formData.confirmPassword}
          onChange={handleChange}
          required
          className="w-full rounded-lg border border-slate-200 bg-white/90 px-3 py-2 text-sm 
          text-slate-700 transition focus:border-blue-500 focus:outline-none focus:ring-2 
          focus:ring-blue-500/20 dark:border-slate-700 dark:bg-slate-900/80 dark:text-slate-100"
        />
      </div>

      {/* Full Name */}
      <div>
        <label className="mb-1 block text-sm font-semibold text-slate-600 dark:text-slate-300">
          Full Name
        </label>
        <input
          type="text"
          name="fullName"
          value={formData.fullName}
          onChange={handleChange}
          required
          className="w-full rounded-lg border border-slate-200 bg-white/90 px-3 py-2 text-sm 
          text-slate-700 dark:border-slate-700 dark:bg-slate-900/80 dark:text-slate-100"
        />
      </div>

      {/* Mobile */}
      <div>
        <label className="mb-1 block text-sm font-semibold text-slate-600 dark:text-slate-300">
          Mobile
        </label>
        <input
          type="text"
          name="mobile"
          value={formData.mobile}
          onChange={handleChange}
          required
          className="w-full rounded-lg border border-slate-200 bg-white/90 px-3 py-2 text-sm 
          text-slate-700 dark:border-slate-700 dark:bg-slate-900/80 dark:text-slate-100"
        />
      </div>
    </>
  )}

  {/* Error message */}
  {error && <p className="text-sm text-red-500">{error}</p>}

  {/* Forgot Password */}
  <div className="text-right">
    <button
      type="button"
      className="text-xs font-semibold text-slate-500 hover:text-slate-800 
      dark:text-slate-400 dark:hover:text-slate-200"
    >
      Forgot your password?
    </button>
  </div>

  {/* Submit */}
  <button
    type="submit"
    disabled={loading}
    className="w-full rounded-full bg-[#ff547a] px-4 py-2 text-sm font-semibold text-white 
    transition hover:bg-[#f53c6a] disabled:opacity-60"
  >
    {loading ? "Processing..." : isSignup ? "Create account" : "Login"}
  </button>
</form>

            </div>
          </div>
        </div>

        <div className="space-y-3 text-center text-xs text-slate-500 dark:text-slate-400">
          <p>
            {isSignup ? 'Already have an account? ' : 'Don’t have an account? '}
            <button
              type="button"
              onClick={() => handleModeChange(isSignup ? 'login' : 'signup')}
              className="font-semibold text-[#ff4f70] underline-offset-4 hover:underline"
            >
              {isSignup ? 'Login' : 'Create account'}
            </button>
          </p>
          <p className="uppercase tracking-[0.3em] text-[0.6rem] text-slate-400 dark:text-slate-500">Restricted to adults</p>
          <div className="flex flex-wrap justify-center gap-3 text-[0.75rem]">
            {legalLinks.map((link) => (
              <a key={link.label} href={link.href} className="hover:text-[#ff4f70]">
                {link.label}
              </a>
            ))}
          </div>
          <div className="flex flex-wrap justify-center gap-3 text-[0.75rem]">
            {supportLinks.map((link) => (
              <a key={link.label} href={link.href} className="hover:text-[#ff4f70]">
                {link.label}
              </a>
            ))}
          </div>
          <p className="text-[0.7rem] text-slate-400">
            Sofyan Limited · Amathus Avenue, 4532 Agios Tychonas, Limassol, Cyprus · Registration Number: HE 445193
          </p>
        </div>
      </div>
    </div>
  );
}
