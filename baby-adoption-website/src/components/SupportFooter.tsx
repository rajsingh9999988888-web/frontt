import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const footerColumns = [
  {
    heading: 'Legal',
    links: ['Terms and Conditions', 'Privacy Policy', 'Cookie Policy'],
  },
  {
    heading: 'Support',
    links: ['Contact us', 'Help Center', 'Blog'],
  },
  {
    heading: 'Security',
    links: ['How to report a scam'],
  },
  {
    heading: 'Company',
    links: ['Bakeca Incontri', 'Skokka Network'],
  },
];

const countries = ['India', 'Singapore', 'Spain', 'Brazil', 'Italy'];

type SupportFooterProps = {
  showNeedHelp?: boolean;
};

export default function SupportFooter({ showNeedHelp = true }: SupportFooterProps): React.JSX.Element {
  const [selectedCountry, setSelectedCountry] = useState('India');

  return (
    <div className="space-y-6">
      {showNeedHelp && (
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
      )}

      <section className="rounded-3xl border border-slate-200 bg-white p-6 text-sm text-slate-500 shadow-sm dark:border-slate-800 dark:bg-slate-900 dark:text-slate-300">
        <div className="flex flex-col gap-4 lg:flex-row lg:items-center lg:justify-between">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.3em] text-[#ff4f70]">Boost your visibility</p>
            <p className="mt-2 text-sm text-slate-600 dark:text-slate-300">
              Skokka is the largest advertising platform for independent escorts worldwide.
            </p>
          </div>
          <div className="flex flex-wrap items-center gap-3">
            <Link
              to="/add-post"
              className="inline-flex items-center justify-center rounded-full bg-[#ff4f70] px-5 py-2 text-sm font-semibold text-white shadow hover:bg-[#f0537c]"
            >
              Post your ad
            </Link>
            <select
              value={selectedCountry}
              onChange={(e) => setSelectedCountry(e.target.value)}
              className="rounded-full border border-slate-200 px-4 py-2 text-sm text-slate-600 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200"
            >
              {countries.map((country) => (
                <option key={country} value={country}>
                  {country}
                </option>
              ))}
            </select>
          </div>
        </div>

        <div className="mt-6 grid gap-6 sm:grid-cols-2 lg:grid-cols-4">
          {footerColumns.map((column) => (
            <div key={column.heading}>
              <p className="text-xs uppercase tracking-[0.3em] text-slate-400">{column.heading}</p>
              <ul className="mt-3 space-y-1 text-sm">
                {column.links.map((link) => (
                  <li key={link}>
                    <a href="#!" className="text-slate-600 transition hover:text-[#ff4f70] dark:text-slate-300">
                      {link}
                    </a>
                  </li>
                ))}
              </ul>
            </div>
          ))}
        </div>

        <div className="mt-6 border-t border-slate-200 pt-4 text-xs text-slate-400 dark:border-slate-700 dark:text-slate-500">
          Sofyan Limited · Amathus Avenue, 4532 Agios Tychonas, Limassol, Cyprus · Registration Number: HE 445193
        </div>
      </section>
    </div>
  );
}

