import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const summaryCards = [
  {
    title: 'Ads',
    metrics: [
      { label: 'Active', value: '1' },
      { label: 'Not published', value: '0' },
    ],
    action: { label: 'Manage ads', to: '/baby-list', tone: 'ghost' },
  },
  {
    title: 'Wallet',
    metrics: [
      { label: 'Current credits', value: '1' },
      { label: 'Used this month', value: '104' },
    ],
    action: { label: 'Buy credits', to: '/add-post', tone: 'solid' },
  },
  {
    title: 'Coupons',
    metrics: [{ label: 'Available coupons', value: '3' }],
    action: { label: 'View coupons', to: '/baby-list', tone: 'ghost' },
  },
  {
    title: 'Settings',
    metrics: [{ label: 'Manage your personal info.', value: '' }],
    action: { label: 'Open settings', to: '/settings', tone: 'ghost' },
  },
];

const quickLinks = [
  { label: 'Create ad', to: '/add-post' },
  { label: 'Payment history', to: '/baby-list' },
  { label: 'Support tickets', to: '/baby-list' },
];

const activityLog = [
  { time: '08:40', message: 'Ad “Mumbai elite” approved', status: 'success' },
  { time: 'Yesterday', message: 'Credits applied to wallet', status: 'neutral' },
  { time: '2 days ago', message: 'Verification reminder sent', status: 'warning' },
];

const securityTips = [
  'Block suspicious requests immediately.',
  'Only reply through official channels.',
  'Never share OTPs or payment screenshots.',
];

export default function UserDashboard(): React.JSX.Element {
  const { user } = useAuth();
  const [newsletter, setNewsletter] = useState(true);

  return (
    <div className="min-h-screen bg-[#f7f8fc] px-4 py-10 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto max-w-6xl space-y-8">
        <header className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-800 dark:bg-slate-900">
          <div className="grid gap-6 md:grid-cols-[1.6fr_1fr]">
            <div>
              <p className="text-xs font-semibold uppercase tracking-[0.4em] text-[#ff4f70]">Welcome back</p>
              <h1 className="mt-2 text-3xl font-semibold">
                Hello{user?.email ? `, ${user.email}` : ''}!
              </h1>
              <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">Customer code · <strong>IN356GFM</strong></p>
              <div className="mt-5 flex flex-wrap gap-3">
                {quickLinks.map((link) => (
                  <Link
                    key={link.label}
                    to={link.to}
                    className="inline-flex items-center gap-2 rounded-full border border-slate-200 px-4 py-1.5 text-xs font-semibold text-slate-500 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300"
                  >
                    {link.label}
                    <span aria-hidden="true">→</span>
                  </Link>
                ))}
              </div>
            </div>
            <div className="space-y-3 rounded-2xl border border-slate-200 bg-slate-50 p-4 text-sm text-slate-600 shadow-inner dark:border-slate-800 dark:bg-slate-900">
              <div className="flex items-center justify-between">
                <span className="text-xs uppercase tracking-[0.35em] text-slate-400">Checklist</span>
                <span className="text-xs font-semibold text-slate-500">3 steps</span>
              </div>
              <ul className="space-y-2 text-sm">
                {activityLog.map((item) => (
                  <li key={item.message} className="flex items-start gap-3">
                    <span className="text-xs font-semibold text-slate-400">{item.time}</span>
                    <span className="flex-1">{item.message}</span>
                  </li>
                ))}
              </ul>
            </div>
          </div>
        </header>

        <div className="grid gap-6 lg:grid-cols-[2fr_1fr]">
          <section className="grid gap-4 sm:grid-cols-2">
            {summaryCards.map((card) => (
              <article
                key={card.title}
                className="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm dark:border-slate-800 dark:bg-slate-900"
              >
                <div className="flex items-center justify-between">
                  <h2 className="text-base font-semibold">{card.title}</h2>
                  <span className="text-sm text-slate-400">☆</span>
                </div>
                <div className="mt-4 space-y-2 text-sm text-slate-500 dark:text-slate-300">
                  {card.metrics.map((metric) => (
                    <div key={metric.label} className="flex items-center justify-between">
                      <span>{metric.label}</span>
                      {metric.value && <span className="text-base font-semibold text-slate-900 dark:text-white">{metric.value}</span>}
                    </div>
                  ))}
                </div>
                <Link
                  to={card.action.to}
                  className={`mt-4 inline-flex w-full items-center justify-center rounded-full border px-4 py-2 text-sm font-semibold transition ${
                    card.action.tone === 'solid'
                      ? 'border-transparent bg-[#ff4f70] text-white hover:bg-[#f0537c]'
                      : 'border-slate-200 text-slate-600 hover:border-[#ff4f70] hover:text-[#ff4f70]'
                  }`}
                >
                  {card.action.label}
                </Link>
              </article>
            ))}
          </section>

          <section className="space-y-4">
            <article className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-800 dark:bg-slate-900">
              <header className="flex items-center justify-between text-sm font-semibold">
                <span>Verify your age</span>
                <div className="space-x-3 text-xs text-slate-400">
                  <button className="underline-offset-2 hover:underline">How it works?</button>
                  <button className="underline-offset-2 hover:underline">Questions?</button>
                </div>
              </header>
              <div className="mt-4 rounded-2xl border border-dashed border-slate-200 p-6 text-center dark:border-slate-700">
                <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full border border-slate-200 text-2xl text-slate-500">
                  📷
                </div>
                <p className="mt-4 text-sm text-slate-500 dark:text-slate-300">
                  As BabyAdopt we must ensure every publisher is of age. If you need help, contact support@babyadopt.com.
                </p>
                <div className="mt-4 text-xs text-[#ff4f70]">Info</div>
                <button className="mt-4 w-full rounded-full bg-[#ff4f70] px-4 py-2 text-sm font-semibold text-white hover:bg-[#f0537c]">
                  Start now
                </button>
              </div>
            </article>

          </section>
        </div>

        <div className="grid gap-6 lg:grid-cols-[2fr_1fr]">
          <section className="grid gap-6 lg:grid-cols-2">
            <article className="rounded-3xl border border-slate-200 bg-white px-6 py-5 text-sm shadow-sm dark:border-slate-800 dark:bg-slate-900">
              <div className="flex items-center justify-between">
                <div>
                  <p className="text-xs uppercase tracking-[0.3em] text-slate-400">Newsletter</p>
                  <p className="text-sm text-slate-500 dark:text-slate-300">
                    Join to get exclusive news, promotions, and more.
                  </p>
                </div>
                <button
                  onClick={() => setNewsletter((prev) => !prev)}
                  className={`relative h-6 w-11 rounded-full transition ${newsletter ? 'bg-[#ff4f70]' : 'bg-slate-300 dark:bg-slate-600'}`}
                >
                  <span
                    className={`absolute top-0.5 h-5 w-5 rounded-full bg-white transition-transform ${
                      newsletter ? 'translate-x-5' : 'translate-x-0.1'
                    }`}
                  />
                </button>
              </div>
              <p className="mt-3 text-xs text-slate-400 dark:text-slate-500">
                Marketing communications · I authorize the processing of my personal data for advertising purposes.
              </p>
            </article>

            <article className="rounded-3xl border border-slate-200 bg-white p-6 text-sm shadow-sm dark:border-slate-800 dark:bg-slate-900">
              <p className="text-xs uppercase tracking-[0.3em] text-slate-400">Security tips</p>
              <div className="mt-4 rounded-2xl border border-slate-200 bg-slate-50 p-5 dark:border-slate-700 dark:bg-slate-800">
                <p className="text-sm font-semibold text-slate-900 dark:text-white">Buy safely. Always use the official website.</p>
                <ul className="mt-3 space-y-2 text-sm text-slate-600 dark:text-slate-300">
                  {securityTips.map((tip) => (
                    <li key={tip} className="flex items-start gap-2">
                      <span className="text-[#ff4f70]">•</span>
                      <span>{tip}</span>
                    </li>
                  ))}
                </ul>
                <Link to="/baby-list" className="mt-4 inline-flex text-xs font-semibold text-[#ff4f70] hover:text-[#f0537c]">
                  Visit our Help Center →
                </Link>
              </div>
            </article>
          </section>

          <article className="rounded-3xl border border-slate-200 bg-white p-6 text-sm shadow-sm dark:border-slate-800 dark:bg-slate-900">
            <p className="text-xs uppercase tracking-[0.35em] text-slate-400">Need help?</p>
            <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">
              Contact us Monday to Friday, 2pm – 8pm through official channels.
            </p>
            <div className="mt-4 flex flex-col gap-2 text-sm text-[#ff4f70]">
              <a href="https://wa.me/0000000000" target="_blank" rel="noreferrer" className="inline-flex items-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" className="h-4 w-4">
                  <path d="M12 22c5.523 0 10-4.477 10-10S17.523 2 12 2 2 6.477 2 12c0 2.01.595 3.878 1.613 5.437L3 22l4.779-1.586A9.964 9.964 0 0 0 12 22Z" />
                  <path d="M8.5 10.5c.5 2 2.5 4 4.5 4.5M13.5 11.5l1 1.5" strokeLinecap="round" strokeLinejoin="round" />
                </svg>
                WhatsApp
              </a>
              <a href="https://t.me" target="_blank" rel="noreferrer" className="inline-flex items-center gap-2">
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" className="h-4 w-4">
                  <path d="M21.5 3.5 2.5 11.5l6 2 2 6 3-4 4 4 4-16Z" strokeLinecap="round" strokeLinejoin="round" />
                </svg>
                Telegram
              </a>
              <Link to="/baby-list" className="inline-flex items-center gap-2">
                <span className="text-lg">✉️</span>
                Support inbox
              </Link>
            </div>
          </article>
        </div>

       
      </div>
    </div>
  );
}
