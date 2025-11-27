import React, { useMemo, useState } from 'react';
import SupportFooter from '../components/SupportFooter';

type CreditPack = {
  credits: number;
  price: number;
  discount?: string;
  badge?: string;
};

const packs: CreditPack[] = [
  { credits: 15, price: 705 },
  { credits: 30, price: 1363, discount: '2% discount' },
  { credits: 62, price: 2728, discount: '5% discount', badge: 'Most popular' },
  { credits: 104, price: 4410, discount: '10% discount' },
  { credits: 172, price: 7050, discount: '13% discount' },
  { credits: 297, price: 11750, discount: '15% discount' },
  { credits: 493, price: 18800, discount: '18% discount' },
  { credits: 895, price: 32800, discount: '23% discount' },
  { credits: 1330, price: 47000, discount: '25% discount', badge: 'Best deal' },
];

export default function BuyCredits(): React.JSX.Element {
  const [selectedPack, setSelectedPack] = useState<CreditPack | null>(packs[2]);
  const [coupon, setCoupon] = useState('');

  const total = useMemo(() => (selectedPack ? selectedPack.price : 0), [selectedPack]);

  return (
    <div className="min-h-screen bg-[#f7f8fc] px-4 py-10 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto flex max-w-4xl flex-col gap-8">
        <header className="text-center">
          <h1 className="text-3xl font-semibold">Buy Credits</h1>
        </header>

        <section className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-800 dark:bg-slate-900">
          <div className="flex flex-wrap items-center justify-between gap-4">
            <div>
              <p className="text-sm font-semibold text-slate-600 dark:text-slate-300">Available · 0</p>
              <p className="text-xs text-slate-400">104 used</p>
            </div>
            <button className="rounded-full border border-slate-200 px-4 py-1.5 text-xs font-semibold text-slate-500 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300">
              Transaction history
            </button>
          </div>
          <div className="mt-6 flex flex-col gap-3 sm:flex-row">
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

        <section className="space-y-4">
          {packs.map((pack) => {
            const isSelected = selectedPack?.credits === pack.credits;
            return (
              <button
                key={pack.credits}
                onClick={() => setSelectedPack(pack)}
                className={`flex w-full items-center justify-between rounded-3xl border px-5 py-4 text-left shadow-sm transition ${
                  isSelected ? 'border-[#ff4f70] bg-white dark:border-[#ff4f70]' : 'border-slate-200 bg-white dark:border-slate-800 dark:bg-slate-900'
                }`}
              >
                <div>
                  <p className="text-lg font-semibold">{pack.credits} credits</p>
                  {pack.discount && <p className="text-sm text-emerald-500">{pack.discount}</p>}
                  {pack.badge && (
                    <span className="mt-2 inline-block rounded-full border border-slate-200 px-3 py-0.5 text-xs uppercase tracking-[0.3em] text-slate-400 dark:border-slate-700">
                      {pack.badge}
                    </span>
                  )}
                </div>
                <p className="text-lg font-semibold">₹ {pack.price.toLocaleString('en-IN')}</p>
              </button>
            );
          })}
        </section>

        <section className="rounded-3xl border border-slate-200 bg-white p-6 text-sm shadow-sm dark:border-slate-800 dark:bg-slate-900">
          <div className="flex items-center justify-between text-base font-semibold">
            <span>Total</span>
            <span>₹ {total.toLocaleString('en-IN')}</span>
          </div>
          <button className="mt-4 w-full rounded-full bg-[#ff4f70] px-4 py-2 text-sm font-semibold text-white shadow hover:bg-[#f0537c]">
            Pay
          </button>
        </section>

        <SupportFooter />
      </div>
    </div>
  );
}

