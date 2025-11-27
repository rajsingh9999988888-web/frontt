import React from 'react';

const popularCities = [
  ['Delhi NCR', 'Mumbai', 'Bengaluru', 'Hyderabad'],
  ['Chennai', 'Pune', 'Kolkata', 'Goa'],
  ['Ahmedabad', 'Kochi', 'Indore', 'Jaipur'],
];

const quickLinks = ['About us', 'Privacy policy', 'Terms & conditions', 'Safety tips', 'Blog', 'Contact support'];
const services = ['Escorts', 'Massage', 'Call girls', 'Male escorts', 'Trans escorts', 'International escorts'];
const support = ['Advertise with us', 'City requests', 'Verify profile', 'DMCA', 'Cancellation policy', 'FAQs'];

export default function Footer(): React.JSX.Element {
  return (
    <footer className="mt-16 border-t border-slate-200 bg-slate-950/5 transition-colors duration-300 dark:border-slate-800 dark:bg-slate-950">
      <div className="border-b border-slate-200/70 bg-white/90 transition-colors duration-300 dark:border-slate-800 dark:bg-slate-900/70">
        <div className="mx-auto flex max-w-6xl flex-col gap-8 px-4 py-10 lg:flex-row lg:items-start lg:justify-between lg:px-8">
          <div className="max-w-sm space-y-3">
            <h4 className="text-lg font-semibold text-slate-900 dark:text-white">BabyAdopt Concierge</h4>
            <p className="text-xs leading-relaxed text-slate-600 dark:text-slate-300">
              Verified classifieds with concierge support across India.
            </p>
            <div className="flex flex-wrap items-center gap-2 text-[0.55rem] uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">
              <span>EN</span>
              <span>HI</span>
              <span>ES</span>
            </div>
          </div>

          <div className="grid w-full flex-1 gap-8 sm:grid-cols-2 lg:grid-cols-4">
            <FooterColumn title="Services" items={services} />
            <FooterColumn title="Quick links" items={quickLinks} />
            <FooterColumn title="Support" items={support} />
            <div>
              <h5 className="text-sm font-semibold uppercase tracking-wide text-slate-500 dark:text-slate-400">
                Popular cities
              </h5>
              <div className="mt-3 grid gap-2 text-sm text-slate-600 dark:text-slate-300">
                {popularCities.map((group, index) => (
                  <div key={index} className="flex flex-wrap gap-2">
                    {group.map((city) => (
                      <a key={city} href="#!" className="hover:text-[#ff4f70] dark:hover:text-[#ff4f70]">
                        {city}
                      </a>
                    ))}
                  </div>
                ))}
              </div>
            </div>
          </div>
        </div>
      </div>

      <div className="border-t border-slate-200 transition-colors duration-300 dark:border-slate-800">
        <div className="mx-auto flex max-w-6xl flex-col gap-4 px-4 py-6 text-xs text-slate-500 transition-colors duration-300 dark:text-slate-400 lg:flex-row lg:items-center lg:justify-between lg:px-8">
          <p className="leading-relaxed">
            © {new Date().getFullYear()} BabyAdopt Concierge. All rights reserved. We are an advertising platform and do
            not provide direct escorting services. All content is published by third-party advertisers.
          </p>
          <p>Business hours: 10:00 – 22:00 IST · concierge@babyadopt.com</p>
        </div>
      </div>
    </footer>
  );
}

type FooterColumnProps = {
  title: string;
  items: string[];
};

function FooterColumn({ title, items }: FooterColumnProps): React.JSX.Element {
  return (
    <div>
      <h5 className="text-sm font-semibold uppercase tracking-wide text-slate-500 dark:text-slate-400">{title}</h5>
      <ul className="mt-3 space-y-2 text-sm text-slate-600 dark:text-slate-300">
        {items.map((item) => (
          <li key={item}>
            <a href="#!" className="transition hover:text-[#ff4f70] dark:hover:text-[#ff4f70]">
              {item}
            </a>
          </li>
        ))}
      </ul>
    </div>
  );
}

