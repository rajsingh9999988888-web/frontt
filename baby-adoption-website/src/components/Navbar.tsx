import React, { useState } from 'react';
import { NavLink, useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { useTheme } from '../contexts/ThemeContext';

const navLinks = [
  { to: '/', label: 'Home', key: 'home', audience: 'all' as const },
  { to: '/baby-list', label: 'Baby List', key: 'baby-list', audience: 'all' as const },
  { to: '/admin', label: 'Admin Console', key: 'admin', audience: 'admin' as const },
];

const animationStyles = `
@keyframes brandDrift {
  0% { opacity: 0; transform: translateY(18px); letter-spacing: 0.28em; }
  45% { opacity: 0.6; transform: translateY(-4px); letter-spacing: 0.18em; }
  100% { opacity: 1; transform: translateY(0); letter-spacing: 0.12em; }
}

@keyframes navEaseIn {
  0% { opacity: 0; transform: translateY(16px); }
  65% { opacity: 0.7; transform: translateY(-3px); }
  100% { opacity: 1; transform: translateY(0); }
}
`;

type ThemeToggleProps = {
  isDark: boolean;
  onToggle: () => void;
  className?: string;
};

function ThemeToggle({ isDark, onToggle, className = '' }: ThemeToggleProps): React.JSX.Element {
  return (
    <button
      type="button"
      onClick={onToggle}
      className={`flex h-9 w-9 items-center justify-center rounded-full border border-slate-200 bg-white text-slate-600 transition hover:border-blue-500 hover:text-blue-500 dark:border-slate-700 dark:bg-slate-800 dark:text-slate-200 dark:hover:border-blue-400 dark:hover:text-blue-400 ${className}`}
      aria-label={isDark ? 'Activate light mode' : 'Activate dark mode'}
      title={isDark ? 'Switch to light mode' : 'Switch to dark mode'}
    >
      {isDark ? (
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" className="h-5 w-5">
          <circle cx="12" cy="12" r="4" />
          <path strokeLinecap="round" d="M12 3v2M12 19v2M4.93 4.93l1.41 1.41M17.66 17.66l1.41 1.41M3 12h2M19 12h2M4.93 19.07l1.41-1.41M17.66 6.34l1.41-1.41" />
        </svg>
      ) : (
        <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" className="h-5 w-5">
          <path strokeLinecap="round" strokeLinejoin="round" d="M21 12.79A9 9 0 0 1 11.21 3a7 7 0 1 0 9.79 9.79z" />
        </svg>
      )}
    </button>
  );
}

export default function Navbar(): React.JSX.Element {
  const { isLoggedIn, logout, isAdmin, setRedirectPath } = useAuth();
  const { theme, toggleTheme } = useTheme();
  const isDark = theme === 'dark';
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isProfileMenuOpen, setIsProfileMenuOpen] = useState(false);
  const navigate = useNavigate();

  const filterLinks = () =>
    navLinks.filter(({ audience }) => {
      if (audience === 'all') return true;
      if (audience === 'publisher') return !isAdmin();
      if (audience === 'admin') return isLoggedIn && isAdmin();
      return false;
    });

  const linkClass = ({ isActive }: { isActive: boolean }) =>
    `inline-flex items-center gap-1.5 rounded-full px-4 py-1.5 text-sm font-semibold transition focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:ring-[#ff4f70]/40 ${
      isActive
        ? 'border border-[#ff547a]/60 bg-white text-[#ff547a] dark:border-[#ff547a]/40 dark:bg-slate-800 dark:text-[#ff8cab]'
        : 'border border-slate-200 text-slate-600 hover:border-[#ff4f70]/60 hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-300 dark:hover:border-[#ff4f70]/60 dark:hover:text-[#ff4f70]'
    }`;

  const handleNavSelection = (cb?: () => void) => () => {
    setIsMenuOpen(false);
    setIsProfileMenuOpen(false);
    if (cb) cb();
  };

  const handleAddPostClick = () => {
    if (!isLoggedIn) {
      setRedirectPath('/add-post');
    }
    navigate('/add-post');
  };

  return (
    <>
      <style>{animationStyles}</style>
      <header className="sticky top-0 z-40 border-b border-slate-200/70 bg-white/85 backdrop-blur-xl shadow-sm transition-colors duration-300 dark:border-slate-800/70 dark:bg-slate-900/75">
        <div className="mx-auto flex max-w-6xl items-center justify-between px-4 py-3 lg:px-8">
          <NavLink to="/" onClick={handleNavSelection()} className="flex items-center gap-2">
            <div className="leading-tight">
              <span className="block text-2xl font-black text-[#ff4f70] dark:text-[#ff4f70]">
                BabyAdopt
              </span>
            </div>
          </NavLink>

          <div className="flex items-center gap-3 lg:hidden">
            <ThemeToggle isDark={isDark} onToggle={toggleTheme} />
            <button
              type="button"
              className="flex h-10 w-10 items-center justify-center rounded-full border border-slate-200 bg-white text-slate-700 transition hover:border-blue-500 hover:text-blue-500 dark:border-slate-700 dark:bg-slate-800 dark:text-slate-200 dark:hover:border-blue-400 dark:hover:text-blue-400"
              onClick={() => setIsMenuOpen((prev) => !prev)}
              aria-label={isMenuOpen ? 'Close navigation menu' : 'Open navigation menu'}
            >
              {isMenuOpen ? (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" className="h-6 w-6">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M6 18L18 6M6 6l12 12" />
                </svg>
              ) : (
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" className="h-6 w-6">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M4 6h16M4 12h16M4 18h16" />
                </svg>
              )}
            </button>
          </div>

          <nav className="hidden items-center gap-4 lg:flex">
            {filterLinks().map(({ to, label, key }, index) => (
              <NavLink
                key={key}
                to={to}
                className={linkClass}
                onClick={handleNavSelection()}
                style={{ animation: 'navEaseIn 1.45s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${index * 220}ms`, opacity: 0 }}
              >
                {label}
              </NavLink>
            ))}

            <div className="flex items-center gap-3">
              {/* Always show Add Post button to improve discoverability */}
              <button
                type="button"
                onClick={handleNavSelection(handleAddPostClick)}
                className="inline-flex items-center gap-2 rounded-full border border-slate-300 bg-[#ff4f70] px-4 py-1.5 text-sm font-semibold text-[#ffffff] transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:bg-slate-900 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]"
                style={{ animation: 'navEaseIn 1.45s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${filterLinks().length * 220}ms`, opacity: 0 }}
              >
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" className="h-4 w-4">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M12 5v14M5 12h14" />
                </svg>
                POST YOUR ADD
              </button>
              {isLoggedIn ? (
                <div className="relative" style={{ animation: 'navEaseIn 1.45s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${(filterLinks().length + 1) * 220}ms`, opacity: 0 }}>
                  <button
                    type="button"
                    onClick={() => setIsProfileMenuOpen((prev) => !prev)}
                    className="inline-flex h-10 w-10 items-center justify-center rounded-full border border-slate-300 bg-white text-slate-700 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:bg-slate-900 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]"
                    aria-label="Open profile menu"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" className="h-5 w-5">
                      <circle cx="12" cy="8" r="3.2" />
                      <path strokeLinecap="round" strokeLinejoin="round" d="M6.5 19c1.2-2.1 3.5-3.5 5.5-3.5s4.3 1.4 5.5 3.5" />
                    </svg>
                  </button>
                  {isProfileMenuOpen && (
                    <div className="absolute right-0 mt-3 w-56 rounded-2xl border border-slate-200 bg-white p-3 text-sm text-slate-700 shadow-lg shadow-slate-900/10 dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200">
                      <p className="px-3 pb-2 text-xs uppercase tracking-[0.3em] text-slate-400">{isAdmin() ? 'Admin' : 'Member'}</p>
                      <div className="space-y-1">
                        {isAdmin() ? (
                          <>
                            <NavLink to="/admin" onClick={handleNavSelection()} className="flex items-center gap-2 rounded-xl px-3 py-2 hover:bg-slate-100 dark:hover:bg-slate-800">
                              Admin console
                            </NavLink>
                            <NavLink to="/baby-list" onClick={handleNavSelection()} className="flex items-center gap-2 rounded-xl px-3 py-2 hover:bg-slate-100 dark:hover:bg-slate-800">
                              View listings
                            </NavLink>
                          </>
                        ) : (
                          <>
                            <NavLink to="/dashboard" onClick={handleNavSelection()} className="flex items-center gap-2 rounded-xl px-3 py-2 hover:bg-slate-100 dark:hover:bg-slate-800">
                              Dashboard
                            </NavLink>
                            <NavLink to="/my-ads" onClick={handleNavSelection()} className="flex items-center gap-2 rounded-xl px-3 py-2 hover:bg-slate-100 dark:hover:bg-slate-800">
                              Ads
                            </NavLink>
                            <NavLink to="/buy-credits" onClick={handleNavSelection()} className="flex items-center gap-2 rounded-xl px-3 py-2 hover:bg-slate-100 dark:hover:bg-slate-800">
                              Wallet
                            </NavLink>
                            <NavLink to="/coupons" onClick={handleNavSelection()} className="flex items-center gap-2 rounded-xl px-3 py-2 hover:bg-slate-100 dark:hover:bg-slate-800">
                              Coupons
                            </NavLink>
                            <NavLink to="/account-settings" onClick={handleNavSelection()} className="flex items-center gap-2 rounded-xl px-3 py-2 hover:bg-slate-100 dark:hover:bg-slate-800">
                              Settings
                            </NavLink>
                          </>
                        )}
                      </div>
                      <button
                        onClick={handleNavSelection(logout)}
                        className="mt-3 flex w-full items-center justify-center gap-2 rounded-xl border border-slate-200 px-3 py-2 text-sm font-semibold text-[#ff4f70] transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700"
                      >
                        Log out
                      </button>
                    </div>
                  )}
                </div>
              ) : (
                <NavLink
                  to="/login-signup"
                  onClick={handleNavSelection()}
                  className="inline-flex h-9 w-9 items-center justify-center rounded-full border border-slate-300 bg-white text-slate-700 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:bg-slate-900 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]"
                  style={{ animation: 'navEaseIn 1.45s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${(filterLinks().length + 2) * 220}ms`, opacity: 0 }}
                  aria-label="Open profile signup"
                >
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" className="h-5 w-5">
                    <circle cx="12" cy="8" r="3.2" />
                    <path strokeLinecap="round" strokeLinejoin="round" d="M6.5 19c1.2-2.1 3.5-3.5 5.5-3.5s4.3 1.4 5.5 3.5" />
                  </svg>
                </NavLink>
              )}
            </div>

            <ThemeToggle isDark={isDark} onToggle={toggleTheme} className="ml-2" />
          </nav>
        </div>

        {isMenuOpen && (
          <div className="border-t border-slate-200/70 bg-white/90 px-4 py-4 transition-colors duration-300 dark:border-slate-800/70 dark:bg-slate-900/85 lg:hidden">
            <nav className="flex flex-col gap-3 text-sm font-medium text-slate-700 dark:text-slate-200">
              {filterLinks().map(({ to, label, key }, index) => (
                <NavLink
                  key={key}
                  to={to}
                  className={linkClass}
                  onClick={handleNavSelection()}
                  style={{ animation: 'navEaseIn 1.1s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${index * 180}ms`, opacity: 0 }}
                >
                  {label}
                </NavLink>
              ))}

              {/* Mobile: Add Post CTA */}
              <div className="mt-2">
                <button
                  type="button"
                  onClick={handleNavSelection(handleAddPostClick)}
                  className="w-full inline-flex items-center justify-center gap-2 rounded-full border border-slate-200 px-4 py-2 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200"
                  style={{ animation: 'navEaseIn 1.1s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${(filterLinks().length + 1) * 180}ms`, opacity: 0 }}
                >
                  <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" className="h-4 w-4">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M12 5v14M5 12h14" />
                  </svg>
                  Post your add
                </button>
              </div>

              {isLoggedIn ? (
                <>
                  <div className="flex flex-col gap-2 text-sm">
                    {isAdmin() ? (
                      <>
                        <NavLink to="/admin" onClick={handleNavSelection()} className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]">
                          Admin console
                        </NavLink>
                        <NavLink to="/baby-list" onClick={handleNavSelection()} className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]">
                          View listings
                        </NavLink>
                      </>
                    ) : (
                      <>
                        <NavLink to="/dashboard" onClick={handleNavSelection()} className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]">
                          Dashboard
                        </NavLink>
                        <NavLink to="/my-ads" onClick={handleNavSelection()} className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]">
                          Ads
                        </NavLink>
                        <NavLink to="/buy-credits" onClick={handleNavSelection()} className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]">
                          Wallet
                        </NavLink>
                      </>
                    )}
                    {!isAdmin() && (
                      <>
                        <NavLink to="/coupons" onClick={handleNavSelection()} className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]">
                          Coupons
                        </NavLink>
                        <NavLink to="/account-settings" onClick={handleNavSelection()} className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 font-semibold text-slate-700 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]">
                          Settings
                        </NavLink>
                      </>
                    )}
                  </div>
                  <button
                    onClick={handleNavSelection(logout)}
                    className="mt-3 inline-flex items-center rounded-full border border-slate-200 px-4 py-1.5 text-sm font-semibold text-slate-700 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]"
                    style={{ animation: 'navEaseIn 1.1s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${(filterLinks().length + 1) * 180}ms`, opacity: 0 }}
                  >
                    Logout
                  </button>
                </>
              ) : (
                <div className="flex flex-col gap-2">
                  <NavLink
                    to="/login-signup"
                    onClick={handleNavSelection()}
                    className="inline-flex h-10 w-10 items-center justify-center rounded-full border border-slate-300 bg-white text-slate-700 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:bg-slate-900 dark:text-slate-200 dark:hover:border-[#ff4f70]/70 dark:hover:text-[#ff4f70]"
                    style={{ animation: 'navEaseIn 1.1s cubic-bezier(0.16, 1, 0.3, 1) forwards', animationDelay: `${(filterLinks().length + 1) * 180}ms`, opacity: 0 }}
                    aria-label="Open profile signup"
                  >
                    <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" className="h-5 w-5">
                      <circle cx="12" cy="8" r="3.2" />
                      <path strokeLinecap="round" strokeLinejoin="round" d="M6.5 19c1.2-2.1 3.5-3.5 5.5-3.5s4.3 1.4 5.5 3.5" />
                    </svg>
                  </NavLink>
                </div>
              )}
            </nav>
          </div>
        )}
      </header>
    </>
  );
}
