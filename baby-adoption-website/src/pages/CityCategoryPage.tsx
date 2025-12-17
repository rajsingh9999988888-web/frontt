import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { Helmet } from 'react-helmet-async';
import { API_BASE_URL } from '../config/api';

type BabyPost = {
  id: number;
  name: string;
  description: string;
  imageUrl: string;
  city: string;
  state?: string;
  district?: string;
  category?: string;
  tags?: string[];
  phone?: string;
  whatsapp?: string;
  rate?: string;
};

const categoryAccent: Record<string, string> = {
  escorts: 'from-[#f97316] via-[#fb7185] to-[#f43f5e]',
  massage: 'from-[#38bdf8] via-[#818cf8] to-[#a855f7]',
  premium: 'from-[#facc15] via-[#f97316] to-[#fb7185]',
  luxury: 'from-[#a855f7] via-[#ec4899] to-[#facc15]',
  'call girls': 'from-[#fb7185] via-[#f43f5e] to-[#d946ef]',
};

const vibeIcons = ['💋', '🕯️', '🫦', '🍸', '🖤', '🔥', '🌙', '🪩'];

/**
 * CityCategoryPage - Dynamic city-based search page
 * Route: /:city/:category-stores
 * Example: /bhopal/call-girls-stores
 * 
 * This component:
 * - Extracts city and category from URL
 * - Formats them for display and API calls
 * - Fetches data from /api/search/{city}/{category}
 * - Displays results using existing UI components
 * - Handles SEO meta tags dynamically
 */
export default function CityCategoryPage(): React.JSX.Element {
  const { city: cityParam, category: categoryParam } = useParams<{
    city: string;
    category: string;
  }>();

  const [posts, setPosts] = useState<BabyPost[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  // Normalize city name: "bhopal" -> "Bhopal"
  const formatCityName = (city: string): string => {
    if (!city) return '';
    return city
      .split('-')
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  };

  // Normalize category: "call-girls-stores" -> "call-girls"
  const normalizeCategorySlug = (slug: string): string => {
    if (!slug) return '';
    // Remove "-stores" suffix if present
    return slug.replace(/-stores$/, '').replace(/-store$/, '');
  };

  // Format category for display: "call-girls" -> "Call Girls"
  const formatCategoryName = (category: string): string => {
    if (!category) return '';
    return category
      .split('-')
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  };

  const city = cityParam ? formatCityName(cityParam) : '';
  const categorySlug = categoryParam ? normalizeCategorySlug(categoryParam) : '';
  const categoryDisplay = categorySlug ? formatCategoryName(categorySlug) : '';

  useEffect(() => {
    const fetchData = async () => {
      if (!cityParam || !categorySlug) {
        setError('Invalid city or category');
        setLoading(false);
        return;
      }

      setLoading(true);
      setError(null);

      try {
        // Call the search API
        const response = await fetch(
          `${API_BASE_URL}/api/search/${encodeURIComponent(cityParam)}/${encodeURIComponent(categorySlug)}`
        );

        if (!response.ok) {
          throw new Error(`Failed to fetch data: ${response.statusText}`);
        }

        const data = await response.json();
        setPosts(Array.isArray(data) ? data : []);
      } catch (err) {
        console.error('Error fetching city category data:', err);
        setError(err instanceof Error ? err.message : 'Failed to load data');
        setPosts([]);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [cityParam, categorySlug]);

  // Generate canonical URL
  const canonicalUrl = `https://mydomain.com/${cityParam}/${categoryParam}`;

  return (
    <React.Fragment>
      <Helmet>
        <title>{`Top ${categoryDisplay} Stores in ${city} | babyadoption`}</title>
        <meta
          name="description"
          content={`Find the best ${categoryDisplay.toLowerCase()} call girls in ${city}. Ratings, reviews, contact details.`}
        />
        <meta property="og:title" content={`Top ${categoryDisplay} Stores in ${city} | babyadoption`} />
        <meta
          property="og:description"
          content={`Find the best ${categoryDisplay.toLowerCase()} call girls in ${city}. Ratings, reviews, contact details.`}
        />
        <link rel="canonical" href={canonicalUrl} />
      </Helmet>

      <div className="min-h-screen bg-[#f6f7fb] text-slate-800 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
        <div className="mx-auto max-w-7xl px-4 py-8 lg:px-8">
          {/* Page Header */}
          <header className="mb-8 space-y-4">
            <h1 className="text-3xl font-bold text-slate-900 dark:text-slate-100">
              Top {categoryDisplay} girls in {city}
            </h1>
            <p className="text-sm text-slate-500 dark:text-slate-300">
              Showing {posts.length} {posts.length === 1 ? 'listing' : 'listings'} in {city}
            </p>
          </header>

          {/* Loading State */}
          {loading && (
            <div className="flex items-center justify-center py-20">
              <div className="text-center">
                <div className="mb-4 inline-block h-8 w-8 animate-spin rounded-full border-4 border-solid border-[#ff4f70] border-r-transparent"></div>
                <p className="text-sm text-slate-500 dark:text-slate-400">Loading listings...</p>
              </div>
            </div>
          )}

          {/* Error State */}
          {error && !loading && (
            <div className="rounded-3xl border border-red-200 bg-red-50 p-8 text-center dark:border-red-800 dark:bg-red-900/20">
              <h2 className="text-xl font-semibold text-red-900 dark:text-red-100">Error loading data</h2>
              <p className="mt-2 text-sm text-red-700 dark:text-red-300">{error}</p>
            </div>
          )}

          {/* Results Grid */}
          {!loading && !error && (
            <section className="grid grid-cols-1 gap-6 justify-items-center md:grid-cols-2 xl:grid-cols-3">
              {posts.map((post, index) => {
                const accent = categoryAccent[post.category?.toLowerCase() ?? ''] ?? 'from-[#38bdf8] via-[#818cf8] to-[#a855f7]';
                const vibe = vibeIcons[index % vibeIcons.length];

                return (
                  <article
                    key={post.id}
                    className="group mx-auto w-full max-w-xs overflow-hidden rounded-xl border border-slate-200 bg-white shadow-sm transition hover:-translate-y-1 hover:shadow-lg dark:border-slate-700 dark:bg-slate-900 dark:shadow-slate-900/40"
                  >
                    <div className="relative aspect-video w-full overflow-hidden bg-slate-100 dark:bg-slate-800">
                      <div className={`absolute inset-x-0 top-0 h-1 bg-gradient-to-r ${accent}`} />
                      <Link to={`/baby-detail/${post.id}`}>
                        <img
                          src={post.imageUrl}
                          alt={post.name}
                          className="h-full w-full object-cover transition duration-500 group-hover:scale-105"
                        />
                      </Link>
                      <span className="absolute left-3 top-3 rounded-full bg-white/85 px-2 py-1 text-[0.6rem] font-semibold uppercase tracking-[0.3em] text-slate-600 shadow-sm backdrop-blur dark:bg-slate-900/75 dark:text-slate-200">
                        {post.category ? post.category.toUpperCase() : 'FEATURED'} {vibe}
                      </span>
                    </div>

                    <div className="space-y-2.5 px-4 py-4">
                      <div className="flex items-start justify-between gap-3">
                        <div className="space-y-1">
                          <h3 className="text-base font-semibold text-slate-900 dark:text-slate-100">{post.name}</h3>
                          <p className="text-sm text-slate-500 dark:text-slate-400 line-clamp-2">{post.description}</p>
                        </div>
                      </div>

                      <div className="flex flex-wrap items-center gap-1.5 text-xs text-slate-500 dark:text-slate-400">
                        <span className="text-sm font-semibold text-slate-800 dark:text-slate-100">{post.city}</span>
                        {post.district && (
                          <>
                            <span className="h-1 w-1 rounded-full bg-slate-300 dark:bg-slate-600" />
                            <span>{post.district}</span>
                          </>
                        )}
                        {post.state && (
                          <>
                            <span className="h-1 w-1 rounded-full bg-slate-300 dark:bg-slate-600" />
                            <span>{post.state}</span>
                          </>
                        )}
                        {post.rate && (
                          <>
                            <span className="h-1 w-1 rounded-full bg-slate-300 dark:bg-slate-600" />
                            <span>{post.rate}</span>
                          </>
                        )}
                      </div>

                      <div className="flex flex-wrap gap-1.5 text-[0.65rem] text-slate-500 dark:text-slate-400">
                        {[post.category, ...(post.tags ?? [])]
                          .filter(Boolean)
                          .slice(0, 3)
                          .map((tag) => (
                            <span
                              key={tag}
                              className="rounded-full border border-slate-200 px-2 py-0.5 uppercase tracking-wide dark:border-slate-700"
                            >
                              {String(tag)}
                            </span>
                          ))}
                      </div>

                      <div className="flex gap-2 pt-1 text-xs">
                        <a
                          href={`tel:${post.phone ?? ''}`}
                          className="inline-flex w-full items-center justify-center gap-1.5 rounded-full border border-[#ff4f70]/60 bg-white px-3 py-1.5 font-semibold text-[#ff4f70] transition hover:border-[#ff4f70] hover:bg-[#ff4f70]/10 dark:bg-slate-900"
                        >
                          Call
                        </a>
                        <a
                          href={`https://wa.me/${post.whatsapp ?? ''}`}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="inline-flex w-full items-center justify-center gap-1.5 rounded-full border border-emerald-500/70 bg-emerald-500/10 px-3 py-1.5 font-semibold text-emerald-600 transition hover:border-emerald-500 hover:bg-emerald-500/20 dark:border-emerald-400/60 dark:text-emerald-300"
                        >
                          WhatsApp
                        </a>
                      </div>
                    </div>
                  </article>
                );
              })}
            </section>
          )}

          {/* Empty State */}
          {!loading && !error && posts.length === 0 && (
            <div className="rounded-3xl border border-dashed border-slate-300 bg-white/80 p-12 text-center shadow-inner transition dark:border-slate-600 dark:bg-slate-900/70 dark:shadow-slate-900/40">
              <h2 className="text-xl font-semibold text-slate-900 dark:text-slate-100">
                No {categoryDisplay.toLowerCase()} listings found in {city}
              </h2>
              <p className="mt-2 text-sm text-slate-600 dark:text-slate-300">
                Try searching for a different city or category.
              </p>
              <Link
                to="/"
                className="mt-5 inline-flex items-center gap-2 rounded-full border border-slate-300 px-4 py-2 text-sm font-semibold text-slate-600 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-300"
              >
                Go to Home
              </Link>
            </div>
          )}
        </div>
      </div>
    </React.Fragment>
  );
}

