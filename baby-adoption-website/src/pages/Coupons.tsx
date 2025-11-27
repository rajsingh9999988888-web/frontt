import React, { useState } from 'react';
import SupportFooter from '../components/SupportFooter';

const faqItems = [
  { question: 'How to use a coupon?', answer: 'Enter the coupon code above and click apply. Valid coupons will adjust your total automatically.' },
  { question: 'Where do I see my coupons?', answer: 'All available coupons appear in the wallet section and on the checkout screen.' },
];

export default function Coupons(): React.JSX.Element {
  const [coupon, setCoupon] = useState('');

  return (
    <div className="min-h-screen bg-[#f7f8fc] px-4 py-10 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto flex max-w-4xl flex-col gap-8">
        <header className="text-center">
          <h1 className="text-3xl font-semibold">Coupons</h1>
          <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">Do you have a coupon?</p>
        </header>

        <section className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-800 dark:bg-slate-900">
          <div className="flex flex-col gap-3 sm:flex-row">
            <input
              value={coupon}
              onChange={(e) => setCoupon(e.target.value)}
              placeholder="Insert coupon code"
              className="flex-1 rounded-2xl border border-slate-200 px-4 py-2 text-sm text-slate-700 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200"
            />
            <button className="rounded-2xl border border-[#ff4f70] px-5 py-2 text-sm font-semibold text-[#ff4f70] hover:bg-[#ff4f70]/10">
              Apply
            </button>
          </div>
        </section>

        <section className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-800 dark:bg-slate-900">
          <p className="text-center text-sm font-semibold text-slate-500 dark:text-slate-300">Frequently asked questions</p>
          <div className="mt-4 divide-y divide-slate-200 dark:divide-slate-800">
            {faqItems.map((faq) => (
              <details key={faq.question} className="py-3">
                <summary className="flex cursor-pointer items-center justify-between text-sm font-semibold">
                  {faq.question}
                  <span className="text-slate-400">▾</span>
                </summary>
                <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">{faq.answer}</p>
              </details>
            ))}
          </div>
        </section>

        <SupportFooter />
      </div>
    </div>
  );
}

