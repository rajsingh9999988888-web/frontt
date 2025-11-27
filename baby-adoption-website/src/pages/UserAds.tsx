import React, { useState } from 'react';
import SupportFooter from '../components/SupportFooter';

type AdStatus = 'active' | 'not-published';

export default function UserAds(): React.JSX.Element {
  const [status, setStatus] = useState<AdStatus>('active');

  return (
    <div className="min-h-screen bg-[#f7f8fc] px-4 py-10 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto flex max-w-4xl flex-col gap-8">
        <header className="text-center">
          <h1 className="text-3xl font-semibold">Your Ads</h1>
        </header>

        <section className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-800 dark:bg-slate-900">
          <div className="flex flex-col gap-4">
            <div className="flex items-center gap-4 text-sm font-semibold">
              <button
                className={`pb-2 ${status === 'active' ? 'border-b-2 border-[#ff4f70] text-[#ff4f70]' : 'text-slate-400'}`}
                onClick={() => setStatus('active')}
              >
                Active
              </button>
              <button
                className={`pb-2 ${status === 'not-published' ? 'border-b-2 border-[#ff4f70] text-[#ff4f70]' : 'text-slate-400'}`}
                onClick={() => setStatus('not-published')}
              >
                Not published
              </button>
            </div>

            <div className="rounded-2xl border border-slate-200 bg-white p-4 dark:border-slate-700 dark:bg-slate-900">
              <div className="flex flex-col gap-3 sm:flex-row sm:items-center">
                <div className="flex-1">
                  <label className="text-xs font-semibold uppercase tracking-[0.3em] text-slate-400">Ads type</label>
                  <select className="mt-1 w-full rounded-2xl border border-slate-200 px-4 py-2 text-sm text-slate-700 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200">
                    <option>All</option>
                  </select>
                </div>
                <button className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-600 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300">
                  Open filters
                </button>
              </div>
            </div>

            <p className="text-sm text-slate-500 dark:text-slate-300">You have 1 active ad</p>

            <article className="rounded-3xl border border-slate-200 bg-white p-5 shadow-sm dark:border-slate-800 dark:bg-slate-900">
              <div className="flex flex-col gap-4 md:flex-row">
                <div className="w-full md:w-48">
                  <div className="aspect-[3/4] overflow-hidden rounded-2xl bg-slate-100 dark:bg-slate-800">
                    <img
                      src="https://images-na.ssl-images-amazon.com/images/I/A1jHr3j9arL.jpg"
                      alt="Ad preview"
                      className="h-full w-full object-cover"
                      loading="lazy"
                    />
                  </div>
                  <p className="mt-2 text-xs text-slate-400">Preview with hidden photos</p>
                </div>

                <div className="flex-1 space-y-3 text-sm">
                  <div className="flex flex-wrap items-center gap-2 text-xs uppercase tracking-[0.3em] text-slate-400">
                    <span>Expires on: November 26, 2025</span>
                    <span>Published</span>
                  </div>
                  <p className="text-sm text-slate-600 dark:text-slate-300">
                    🟢 safe and secure only cash 💳 payment service 24 hr available call me
                  </p>
                  <ul className="space-y-1 text-sm text-slate-600 dark:text-slate-300">
                    <li>✔ VIP top girl 24 hr available</li>
                    <li>✔ Call me</li>
                    <li>✔ Cash payment</li>
                  </ul>
                  <div className="rounded-2xl bg-slate-50 p-3 text-xs text-slate-500 dark:bg-slate-800 dark:text-slate-300">
                    <p className="font-semibold text-slate-700 dark:text-slate-100">Free ad</p>
                    <p>1 online visible photo, activates a promo and unlocks images.</p>
                  </div>
                </div>
              </div>

              <div className="mt-4 flex flex-wrap gap-3 text-sm">
                <button className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-600 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300">
                  Pause
                </button>
                <button className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-600 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300">
                  Resume
                </button>
                <button className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-600 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300">
                  Edit
                </button>
                <button className="rounded-full border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-600 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300">
                  Statistics
                </button>
                <button className="rounded-full bg-[#ff4f70] px-4 py-2 text-sm font-semibold text-white hover:bg-[#f0537c]">
                  Activate a promo and unlock images
                </button>
              </div>
            </article>
          </div>
        </section>

        <SupportFooter />
      </div>
    </div>
  );
}

