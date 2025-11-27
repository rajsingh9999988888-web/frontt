import React, { useState, useEffect, useMemo } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { API_BASE_URL } from '../config/api';

type PendingPost = {
  id: number;
  name: string;
  description: string;
  city: string;
  district?: string;
  state?: string;
  phone?: string;
  whatsapp?: string;
  category?: string;
  createdAt?: string;
};

export default function AdminDashboard() {
  const { token, isAdmin } = useAuth();
  const [pendingPosts, setPendingPosts] = useState<PendingPost[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [lastRefreshed, setLastRefreshed] = useState<Date | null>(null);
  const [approvalsToday, setApprovalsToday] = useState(0);
  const [rejectionsToday, setRejectionsToday] = useState(0);

  const fetchPendingPosts = async () => {
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`${API_BASE_URL}/admin/posts`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to load pending posts');
      }
      const data = await response.json();
      setPendingPosts(Array.isArray(data) ? data : []);
      setLastRefreshed(new Date());
    } catch (err) {
      console.error(err);
      setError(err instanceof Error ? err.message : 'Network error');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (!isAdmin()) {
      setError('Access denied · Admin only');
      setLoading(false);
      return;
    }
    fetchPendingPosts();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [token]);

  const handleApprove = async (id: number) => {
    try {
      const response = await fetch(`${API_BASE_URL}/admin/posts/${id}/approve`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to approve post');
      }
      setPendingPosts((prev) => prev.filter((post) => post.id !== id));
      setApprovalsToday((prev) => prev + 1);
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Network error');
    }
  };

  const handleReject = async (id: number) => {
    try {
      const response = await fetch(`${API_BASE_URL}/admin/posts/${id}/reject`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to reject post');
      }
      setPendingPosts((prev) => prev.filter((post) => post.id !== id));
      setRejectionsToday((prev) => prev + 1);
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Network error');
    }
  };

  const summaryCards = useMemo(
    () => [
      { label: 'Pending review', value: pendingPosts.length, tone: 'pending' as const },
      { label: 'Approved today', value: approvalsToday, tone: 'success' as const },
      { label: 'Rejected today', value: rejectionsToday, tone: 'danger' as const },
    ],
    [pendingPosts.length, approvalsToday, rejectionsToday],
  );

  if (!isAdmin()) {
    return (
      <div className="min-h-screen bg-slate-100 px-4 py-10 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
        <div className="mx-auto max-w-xl rounded-3xl border border-red-100 bg-white/90 p-8 text-center shadow-sm dark:border-red-400/30 dark:bg-slate-900">
          <p className="text-2xl font-semibold text-red-500">Restricted</p>
          <p className="mt-3 text-sm text-slate-500 dark:text-slate-300">Only administrators can enter the console.</p>
        </div>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-[#f8f8fb] px-4 py-10 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto flex max-w-6xl flex-col gap-8">
        <section className="rounded-3xl border border-slate-200 bg-gradient-to-r from-[#1f1649] via-[#2b0f4a] to-[#ff4f70] p-8 shadow-xl dark:border-slate-800">
          <p className="text-xs font-semibold uppercase tracking-[0.6em] text-white/70">Admin console</p>
          <div className="mt-3 flex flex-col gap-6 lg:flex-row lg:items-center lg:justify-between">
            <div>
              <h1 className="text-3xl font-semibold text-white lg:text-4xl">Moderate live submissions</h1>
              <p className="mt-2 text-sm text-white/80">
                Approve trustworthy listings and keep the gallery fresh. Actions sync instantly with all users.
              </p>
            </div>
            <div className="flex flex-wrap items-center gap-3">
              <button
                onClick={fetchPendingPosts}
                className="inline-flex items-center gap-2 rounded-full border border-white/40 px-5 py-2 text-sm font-semibold text-white transition hover:bg-white/10"
                disabled={loading}
              >
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" className="h-4 w-4">
                  <path d="M19 8.5A7 7 0 1 0 21 12h-2" strokeLinecap="round" strokeLinejoin="round" />
                  <path d="M5 15.5A7 7 0 0 0 3 12h2" strokeLinecap="round" strokeLinejoin="round" />
                </svg>
                Refresh
              </button>
              {lastRefreshed && (
                <span className="text-xs text-white/70">Updated {lastRefreshed.toLocaleTimeString()}</span>
              )}
            </div>
          </div>
          <div className="mt-6 grid gap-4 md:grid-cols-3">
            {summaryCards.map((card) => (
              <article
                key={card.label}
                className="rounded-2xl border border-white/20 bg-white/10 p-4 text-white shadow-inner backdrop-blur"
              >
                <p className="text-xs uppercase tracking-[0.4em] text-white/60">{card.label}</p>
                <p className="mt-2 text-3xl font-semibold">{card.value}</p>
                <span
                  className={`mt-2 inline-flex items-center rounded-full px-3 py-0.5 text-xs font-semibold ${
                    card.tone === 'success'
                      ? 'bg-emerald-500/20 text-emerald-100'
                      : card.tone === 'danger'
                        ? 'bg-red-500/30 text-red-100'
                        : 'bg-white/15 text-white'
                  }`}
                >
                  {card.tone === 'pending' ? 'Awaiting action' : 'Today'}
                </span>
              </article>
            ))}
          </div>
        </section>

        <section className="rounded-3xl border border-slate-200 bg-white shadow-sm dark:border-slate-800 dark:bg-slate-900">
          <header className="flex flex-col gap-3 border-b border-slate-200/70 px-6 py-5 dark:border-slate-800">
            <div className="flex flex-wrap items-center gap-4">
              <div>
                <p className="text-xs font-semibold uppercase tracking-[0.4em] text-slate-400">Queue</p>
                <h2 className="text-2xl font-semibold text-slate-900 dark:text-white">Pending listings</h2>
              </div>
              <span className="rounded-full border border-slate-200 px-3 py-1 text-xs font-semibold text-slate-500 dark:border-slate-700 dark:text-slate-300">
                {pendingPosts.length} waiting
              </span>
            </div>
            <p className="text-sm text-slate-500 dark:text-slate-300">
              Review new posts submitted by employees. Approvals publish instantly, rejections permanently remove the ad.
            </p>
          </header>

          <div className="divide-y divide-slate-100 dark:divide-slate-800">
            {loading ? (
              <div className="flex items-center justify-center gap-2 px-6 py-12 text-sm text-slate-500 dark:text-slate-300">
                <span className="h-2 w-2 animate-pulse rounded-full bg-[#ff4f70]" />
                Loading queue…
              </div>
            ) : pendingPosts.length === 0 ? (
              <div className="px-6 py-16 text-center text-sm text-slate-500 dark:text-slate-300">
                <p className="text-lg font-semibold text-slate-700 dark:text-slate-100">All clear!</p>
                <p className="mt-2">No pending submissions. Come back later for new content.</p>
              </div>
            ) : (
              pendingPosts.map((post) => (
                <article key={post.id} className="grid gap-4 px-6 py-5 lg:grid-cols-[1.8fr_1fr_auto] lg:items-center">
                  <div>
                    <div className="flex items-center gap-2 text-xs font-semibold uppercase tracking-[0.35em] text-slate-400">
                      {post.category || 'Listing'}
                      {post.createdAt && (
                        <span className="rounded-full bg-slate-100 px-2 py-0.5 text-[0.65rem] text-slate-500 dark:bg-slate-800 dark:text-slate-300">
                          {new Date(post.createdAt).toLocaleDateString()}
                        </span>
                      )}
                    </div>
                    <h3 className="mt-2 text-lg font-semibold text-slate-900 dark:text-white">{post.name}</h3>
                    <p className="mt-2 text-sm text-slate-500 dark:text-slate-300 line-clamp-2">{post.description}</p>
                  </div>
                  <div className="rounded-2xl border border-slate-200 bg-slate-50 p-4 text-sm text-slate-600 dark:border-slate-700 dark:bg-slate-800 dark:text-slate-200">
                    <div className="flex items-center gap-2">
                      <span className="text-xs font-semibold uppercase tracking-[0.35em] text-slate-400">Location</span>
                    </div>
                    <p className="mt-2 font-semibold text-slate-800 dark:text-white">{post.city || 'Unknown'}</p>
                    <p className="text-xs text-slate-500 dark:text-slate-300">{[post.district, post.state].filter(Boolean).join(' · ')}</p>
                    <div className="mt-3 flex flex-col gap-1 text-xs">
                      {post.phone && <span className="text-slate-500">Phone · {post.phone}</span>}
                      {post.whatsapp && <span className="text-slate-500">WhatsApp · {post.whatsapp}</span>}
                    </div>
                  </div>
                  <div className="flex flex-col gap-2">
                    <button
                      onClick={() => handleApprove(post.id)}
                      className="inline-flex items-center justify-center rounded-full bg-emerald-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-emerald-500 focus:outline-none focus:ring-2 focus:ring-emerald-600/40"
                    >
                      Approve
                    </button>
                    <button
                      onClick={() => handleReject(post.id)}
                      className="inline-flex items-center justify-center rounded-full border border-red-200 px-4 py-2 text-sm font-semibold text-red-600 transition hover:bg-red-50 focus:outline-none focus:ring-2 focus:ring-red-500/40 dark:border-red-400/40 dark:text-red-300 dark:hover:bg-red-400/10"
                    >
                      Reject
                    </button>
                  </div>
                </article>
              ))
            )}
          </div>
        </section>

        {error && (
          <div className="rounded-3xl border border-red-200 bg-red-50 px-6 py-4 text-sm text-red-700 dark:border-red-400/40 dark:bg-red-500/10 dark:text-red-100">
            {error}
          </div>
        )}
      </div>
    </div>
  );
}
