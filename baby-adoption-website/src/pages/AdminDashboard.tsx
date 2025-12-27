import React, { useState, useEffect, useMemo } from 'react';
import { useNavigate } from 'react-router-dom';
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
  status?: string;
};

export default function AdminDashboard() {
  const { token, isAdmin } = useAuth();
  const navigate = useNavigate();
  const [allPosts, setAllPosts] = useState<PendingPost[]>([]);
  const [filteredPosts, setFilteredPosts] = useState<PendingPost[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [lastRefreshed, setLastRefreshed] = useState<Date | null>(null);
  const [approvalsToday, setApprovalsToday] = useState(0);
  const [rejectionsToday, setRejectionsToday] = useState(0);
  const [deletionsToday, setDeletionsToday] = useState(0);
  const [filterStatus, setFilterStatus] = useState<'all' | 'pending' | 'approved'>('all');

  const fetchPosts = async () => {
    if (!token) {
      setError('Not logged in');
      setLoading(false);
      return;
    }
    setLoading(true);
    setError('');
    try {
      const response = await fetch(`${API_BASE_URL}/babies/admin/posts`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        if (response.status === 403) {
          throw new Error('Access denied. Please login as admin (admin@134)');
        } else if (response.status === 401) {
          throw new Error('Not authenticated. Please login again');
        }
        const errorText = await response.text();
        throw new Error(`Failed to load posts: ${errorText || response.statusText}`);
      }
      const data = await response.json();
      const posts = Array.isArray(data) ? data : [];
      console.log('✅ Admin fetched posts:', posts.length, 'posts');
      console.log('Posts data:', posts);
      if (posts.length > 0) {
        console.log('First post sample:', posts[0]);
      }
      setAllPosts(posts);
      // Apply current filter client-side
      applyFilter(posts, filterStatus);
      setLastRefreshed(new Date());
    } catch (err) {
      console.error('Error fetching posts:', err);
      setError(err instanceof Error ? err.message : 'Network error');
    } finally {
      setLoading(false);
    }
  };

  const getTimeAgo = (dateString?: string): string => {
    if (!dateString) return 'Unknown';
    const now = new Date();
    const created = new Date(dateString);
    const diffMs = now.getTime() - created.getTime();
    const diffMins = Math.floor(diffMs / 60000);
    const diffHours = Math.floor(diffMs / 3600000);
    const diffDays = Math.floor(diffMs / 86400000);
    
    if (diffMins < 1) return 'Just now';
    if (diffMins < 60) return `${diffMins} min ago`;
    if (diffHours < 24) return `${diffHours} hour${diffHours > 1 ? 's' : ''} ago`;
    if (diffDays < 7) return `${diffDays} day${diffDays > 1 ? 's' : ''} ago`;
    return created.toLocaleDateString();
  };

  const applyFilter = (posts: PendingPost[], status: 'all' | 'pending' | 'approved') => {
    let filtered: PendingPost[] = [];
    if (status === 'all') {
      filtered = posts;
    } else if (status === 'pending') {
      filtered = posts.filter((p) => p.status === 'PENDING');
    } else if (status === 'approved') {
      filtered = posts.filter((p) => p.status === 'APPROVED');
    }
    console.log(`✅ Applied filter '${status}': ${filtered.length} posts out of ${posts.length} total`);
    setFilteredPosts(filtered);
  };

  useEffect(() => {
    if (!isAdmin()) {
      setError('Access denied · Admin only. Please login with admin@134');
      setLoading(false);
      return;
    }
    if (!token) {
      setError('Not logged in. Please login first');
      setLoading(false);
      return;
    }
    // Fetch posts when component mounts or token changes
    fetchPosts();
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [token]);

  // Re-apply filter when filterStatus or allPosts change
  useEffect(() => {
    if (allPosts.length > 0) {
      applyFilter(allPosts, filterStatus);
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [filterStatus, allPosts]);

  const handleApprove = async (id: number) => {
    try {
      const response = await fetch(`${API_BASE_URL}/babies/admin/posts/${id}/approve`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Failed to approve post');
      }
      // Update state immediately
      setAllPosts((prev) => prev.map((post) => post.id === id ? { ...post, status: 'APPROVED' } : post));
      setApprovalsToday((prev) => prev + 1);
      // Refresh to ensure data is in sync
      fetchPosts();
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Network error');
    }
  };

  const handleReject = async (id: number) => {
    try {
      const response = await fetch(`${API_BASE_URL}/babies/admin/posts/${id}/reject`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to reject post');
      }
      setAllPosts((prev) => prev.filter((post) => post.id !== id));
      setRejectionsToday((prev) => prev + 1);
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Network error');
    }
  };

  const handleDelete = async (id: number) => {
    if (!confirm('Are you sure you want to delete this post? This action cannot be undone.')) {
      return;
    }
    try {
      const response = await fetch(`${API_BASE_URL}/babies/admin/posts/${id}/delete`, {
        method: 'DELETE',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        const errorText = await response.text();
        let errorMessage = 'Failed to delete post';
        try {
          const errorData = JSON.parse(errorText);
          errorMessage = errorData.error || errorMessage;
        } catch {
          errorMessage = errorText || errorMessage;
        }
        throw new Error(errorMessage);
      }
      
      // Verify deletion was successful
      const result = await response.json();
      if (!result.deleted && result.message !== 'Post deleted successfully') {
        throw new Error('Delete operation may not have completed successfully');
      }
      
      // Update state immediately (optimistic update)
      setAllPosts((prev) => prev.filter((post) => post.id !== id));
      setFilteredPosts((prev) => prev.filter((post) => post.id !== id));
      setDeletionsToday((prev) => prev + 1);
      
      // Refresh from server to ensure data is in sync with database
      await fetchPosts();
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Network error');
    }
  };

  const handleFixImageUrls = async () => {
    if (!confirm('This will fix all image URLs in the database. Continue?')) {
      return;
    }
    try {
      const response = await fetch(`${API_BASE_URL}/babies/admin/fix-image-urls`, {
        method: 'PUT',
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });
      if (!response.ok) {
        throw new Error('Failed to fix image URLs');
      }
      const data = await response.json();
      alert(`Fixed ${data.fixed || 0} image URLs out of ${data.totalPosts || 0} posts`);
      fetchPosts(); // Refresh
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Network error');
    }
  };

  const handleDeleteAllOld = async () => {
    const oldPosts = allPosts.filter((post) => {
      if (!post.createdAt) return false;
      const created = new Date(post.createdAt);
      const daysOld = (Date.now() - created.getTime()) / (1000 * 60 * 60 * 24);
      return daysOld > 7; // Older than 7 days
    });
    
    if (oldPosts.length === 0) {
      alert('No old posts found (older than 7 days)');
      return;
    }
    
    if (!confirm(`Are you sure you want to delete ${oldPosts.length} old posts? This action cannot be undone.`)) {
      return;
    }
    
    try {
      let deleted = 0;
      for (const post of oldPosts) {
        const response = await fetch(`${API_BASE_URL}/babies/admin/posts/${post.id}/delete`, {
          method: 'DELETE',
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        if (response.ok) {
          deleted++;
        }
      }
      alert(`Deleted ${deleted} old posts`);
      fetchPosts(); // Refresh
    } catch (err) {
      alert(err instanceof Error ? err.message : 'Network error');
    }
  };

  // Calculate total posts available on website (approved and not expired)
  const totalAvailablePosts = useMemo(() => {
    const now = new Date();
    const sevenDaysAgo = new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000);
    return allPosts.filter((p) => {
      if (p.status !== 'APPROVED') return false;
      if (!p.createdAt) return false;
      const created = new Date(p.createdAt);
      return created > sevenDaysAgo;
    }).length;
  }, [allPosts]);

  const summaryCards = useMemo(() => {
    const pendingCount = allPosts.filter((p) => p.status === 'PENDING').length;
    const approvedCount = allPosts.filter((p) => p.status === 'APPROVED').length;
    const totalCount = allPosts.length;
    return [
      { label: 'Total posts', value: totalCount, tone: 'pending' as const },
      { label: 'Available on web', value: totalAvailablePosts, tone: 'success' as const },
      { label: 'Pending review', value: pendingCount, tone: 'pending' as const },
      { label: 'Approved', value: approvedCount, tone: 'success' as const },
      { label: 'Deleted today', value: deletionsToday, tone: 'danger' as const },
    ];
  }, [allPosts, deletionsToday, totalAvailablePosts]);

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
                onClick={() => navigate('/add-post')}
                className="inline-flex items-center gap-2 rounded-full bg-white/20 px-5 py-2 text-sm font-semibold text-white transition hover:bg-white/30"
              >
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" className="h-4 w-4">
                  <path d="M12 5v14M5 12h14" strokeLinecap="round" strokeLinejoin="round" />
                </svg>
                Add Post
              </button>
              <button
                onClick={handleFixImageUrls}
                className="inline-flex items-center gap-2 rounded-full bg-blue-500/80 px-5 py-2 text-sm font-semibold text-white transition hover:bg-blue-600/80"
                title="Fix all image URLs (localhost to production)"
              >
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="h-4 w-4">
                  <path strokeLinecap="round" strokeLinejoin="round" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                </svg>
                Fix URLs
              </button>
              <button
                onClick={() => fetchPosts()}
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
          <div className="mt-6 grid gap-4 md:grid-cols-5">
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
                <p className="text-xs font-semibold uppercase tracking-[0.4em] text-slate-400">Posts Management</p>
                <h2 className="text-2xl font-semibold text-slate-900 dark:text-white">
                  {filterStatus === 'all' ? 'All Posts' : filterStatus === 'pending' ? 'Pending Posts' : 'Approved Posts'}
                </h2>
              </div>
              <span className="rounded-full border border-slate-200 px-3 py-1 text-xs font-semibold text-slate-500 dark:border-slate-700 dark:text-slate-300">
                {filteredPosts.length} {filterStatus === 'all' ? 'total' : filterStatus === 'pending' ? 'waiting' : 'approved'}
              </span>
            </div>
            <div className="flex flex-wrap items-center gap-2">
              <button
                onClick={() => setFilterStatus('all')}
                className={`rounded-full px-4 py-2 text-sm font-semibold transition ${
                  filterStatus === 'all'
                    ? 'bg-slate-900 text-white dark:bg-slate-100 dark:text-slate-900'
                    : 'bg-slate-100 text-slate-600 hover:bg-slate-200 dark:bg-slate-800 dark:text-slate-300 dark:hover:bg-slate-700'
                }`}
              >
                All Posts
              </button>
              <button
                onClick={() => setFilterStatus('pending')}
                className={`rounded-full px-4 py-2 text-sm font-semibold transition ${
                  filterStatus === 'pending'
                    ? 'bg-slate-900 text-white dark:bg-slate-100 dark:text-slate-900'
                    : 'bg-slate-100 text-slate-600 hover:bg-slate-200 dark:bg-slate-800 dark:text-slate-300 dark:hover:bg-slate-700'
                }`}
              >
                Pending
              </button>
              <button
                onClick={() => setFilterStatus('approved')}
                className={`rounded-full px-4 py-2 text-sm font-semibold transition ${
                  filterStatus === 'approved'
                    ? 'bg-slate-900 text-white dark:bg-slate-100 dark:text-slate-900'
                    : 'bg-slate-100 text-slate-600 hover:bg-slate-200 dark:bg-slate-800 dark:text-slate-300 dark:hover:bg-slate-700'
                }`}
              >
                Approved
              </button>
            </div>
            <p className="text-sm text-slate-500 dark:text-slate-300">
              {filterStatus === 'all' 
                ? 'Manage all posts. Approve, reject, or delete any post.'
                : filterStatus === 'pending'
                ? 'Review new posts submitted by employees. Approve trustworthy listings and keep the gallery fresh.'
                : 'Manage approved posts. Delete posts that are no longer needed.'}
            </p>
          </header>

          <div className="divide-y divide-slate-100 dark:divide-slate-800">
            {loading ? (
              <div className="flex items-center justify-center gap-2 px-6 py-12 text-sm text-slate-500 dark:text-slate-300">
                <span className="h-2 w-2 animate-pulse rounded-full bg-[#ff4f70]" />
                Loading queue…
              </div>
            ) : filteredPosts.length === 0 ? (
              <div className="px-6 py-16 text-center text-sm text-slate-500 dark:text-slate-300">
                <p className="text-lg font-semibold text-slate-700 dark:text-slate-100">
                  {filterStatus === 'all' ? 'No posts found' : filterStatus === 'pending' ? 'All clear!' : 'No approved posts'}
                </p>
                <p className="mt-2">
                  {filterStatus === 'all' 
                    ? 'No posts in the system yet. Create your first post!'
                    : filterStatus === 'pending'
                    ? 'No pending submissions. Come back later for new content.'
                    : 'No approved posts found. Approve some posts first.'}
                </p>
              </div>
            ) : (
              filteredPosts.map((post) => (
                <article key={post.id} className="grid gap-4 px-6 py-5 lg:grid-cols-[1.8fr_1fr_auto] lg:items-center">
                  <div>
                    <div className="flex items-center gap-2 text-xs font-semibold uppercase tracking-[0.35em] text-slate-400">
                      {post.category || 'Listing'}
                      {post.status && (
                        <span className={`rounded-full px-2 py-0.5 text-[0.65rem] font-semibold ${
                          post.status === 'APPROVED' 
                            ? 'bg-emerald-100 text-emerald-700 dark:bg-emerald-900/30 dark:text-emerald-300'
                            : post.status === 'PENDING'
                            ? 'bg-yellow-100 text-yellow-700 dark:bg-yellow-900/30 dark:text-yellow-300'
                            : 'bg-slate-100 text-slate-500 dark:bg-slate-800 dark:text-slate-300'
                        }`}>
                          {post.status}
                        </span>
                      )}
                      {post.createdAt && (
                        <span className="rounded-full bg-slate-100 px-2 py-0.5 text-[0.65rem] text-slate-500 dark:bg-slate-800 dark:text-slate-300" title={new Date(post.createdAt).toLocaleString()}>
                          {getTimeAgo(post.createdAt)}
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
                    {post.status === 'PENDING' && (
                      <>
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
                      </>
                    )}
                    <button
                      onClick={() => handleDelete(post.id)}
                      className="inline-flex items-center justify-center gap-2 rounded-full bg-red-600 px-4 py-2 text-sm font-semibold text-white transition hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-red-600/40"
                    >
                      <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" className="h-4 w-4">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M14.74 9l-.346 9m-4.788 0L9.26 9m9.968-3.21c.342.052.682.107 1.022.166m-1.022-.165L18.16 19.673a2.25 2.25 0 01-2.244 2.077H8.084a2.25 2.25 0 01-2.244-2.077L4.772 5.79m14.456 0a48.108 48.108 0 00-3.478-.397m-12 .562c.34-.059.68-.114 1.022-.165m0 0a48.11 48.11 0 013.478-.397m7.5 0v-.916c0-1.18-.91-2.164-2.09-2.201a51.964 51.964 0 00-3.32 0c-1.18.037-2.09 1.022-2.09 2.201v.916m7.5 0a48.667 48.667 0 00-7.5 0" />
                      </svg>
                      Delete
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
