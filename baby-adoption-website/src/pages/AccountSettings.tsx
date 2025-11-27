import React, { useState } from 'react';
import { useAuth } from '../contexts/AuthContext';
import SupportFooter from '../components/SupportFooter';

export default function AccountSettings(): React.JSX.Element {
  const { user } = useAuth();
  const [email, setEmail] = useState('');
  const [marketing, setMarketing] = useState(true);

  return (
    <div className="min-h-screen bg-[#f7f8fc] px-4 py-10 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto flex max-w-4xl flex-col gap-8">
        <header className="text-center">
          <h1 className="text-3xl font-semibold">Account Settings</h1>
          <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">My Account</p>
          <p className="mt-1 text-sm font-semibold">{user?.email ?? 'you@example.com'}</p>
        </header>

        <section className="space-y-6">
          <Card>
            <SectionHeader title="Verify your age" actions={['How it works?', 'Have any questions?']} />
            <div className="mt-4 rounded-2xl border border-dashed border-slate-200 p-6 text-center dark:border-slate-700">
              <div className="mx-auto flex h-16 w-16 items-center justify-center rounded-full border border-slate-200 text-2xl dark:border-slate-700">
                📷
              </div>
              <p className="mt-4 text-sm text-slate-500 dark:text-slate-300">
                As BabyAdopt we need to be sure that everyone who publishes is of age. Verify your age now! If you need help, contact
                support@babyadopt.com.
              </p>
              <div className="mt-3 text-xs text-[#ff4f70]">Info</div>
              <button className="mt-4 w-full rounded-full bg-[#ff4f70] px-4 py-2 text-sm font-semibold text-white hover:bg-[#f0537c]">
                Start now
              </button>
            </div>
          </Card>

          <Card>
            <SectionHeader title="Change password" />
            <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">We will send you an email to change your password.</p>
            <button className="mt-4 inline-flex items-center rounded-full border border-[#ff4f70] px-5 py-2 text-sm font-semibold text-[#ff4f70] hover:bg-[#ff4f70]/10">
              Change password
            </button>
          </Card>

          <Card>
            <SectionHeader title="Add new email" />
            <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">To post and manage your ads</p>
            <div className="mt-4 flex flex-col gap-3 sm:flex-row">
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="Email"
                className="flex-1 rounded-2xl border border-slate-200 px-4 py-2 text-sm text-slate-700 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200"
              />
              <button className="rounded-2xl border border-slate-200 px-4 py-2 text-sm font-semibold text-slate-600 hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-200">
                Add email
              </button>
            </div>
            <div className="mt-3 flex items-center gap-2 text-sm text-emerald-500">
              <span>✔</span> Success!
            </div>
            <p className="mt-6 text-sm font-semibold text-slate-600 dark:text-slate-300">Verified emails</p>
            <p className="text-sm text-slate-500 dark:text-slate-400">You don’t have any verified email.</p>
          </Card>

          <Card>
            <p className="text-xs uppercase tracking-[0.3em] text-slate-400">Marketing communications</p>
            <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">
              I authorize the processing of my personal data for advertising purposes (Privacy Policy).
            </p>
            <label className="mt-3 inline-flex items-center gap-2 text-sm text-slate-600 dark:text-slate-300">
              <input type="checkbox" checked={marketing} onChange={() => setMarketing((prev) => !prev)} />
              Enabled
            </label>
          </Card>

          <Card>
            <p className="text-sm text-slate-500 dark:text-slate-300">Want to leave?</p>
            <button className="mt-3 inline-flex items-center rounded-full border border-red-200 px-5 py-2 text-sm font-semibold text-red-500 hover:bg-red-50">
              Delete my account
            </button>
          </Card>
        </section>

        <SupportFooter />
      </div>
    </div>
  );
}

type CardProps = {
  children: React.ReactNode;
};

function Card({ children }: CardProps): React.JSX.Element {
  return (
    <article className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-800 dark:bg-slate-900">{children}</article>
  );
}

type SectionHeaderProps = {
  title: string;
  actions?: string[];
};

function SectionHeader({ title, actions }: SectionHeaderProps): React.JSX.Element {
  return (
    <div className="flex flex-wrap items-center justify-between gap-3">
      <p className="text-base font-semibold">{title}</p>
      {actions && (
        <div className="space-x-3 text-xs text-[#ff4f70]">
          {actions.map((action) => (
            <button key={action} className="underline-offset-2 hover:underline">
              {action}
            </button>
          ))}
        </div>
      )}
    </div>
  );
}

