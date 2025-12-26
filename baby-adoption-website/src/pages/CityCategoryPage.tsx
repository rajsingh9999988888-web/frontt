import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { Helmet } from 'react-helmet-async';
import { API_BASE_URL } from '../config/api';
import { buildCanonicalUrl } from '../config/domain';
import SafeImage from '../components/SafeImage';
import { handleWhatsAppClick } from '../utils/tracking';

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
 * CityCategoryPage - Dynamic city-based search page (Justdial style)
 * Routes: 
 *   - /:city/:category (e.g., /bhopal/call girls)
 *   - /:category (e.g., /call girls - will auto-detect city)
 * 
 * This component:
 * - Extracts city and category from URL
 * - Auto-detects city if missing (using geolocation/IP)
 * - Formats them for display and API calls
 * - Fetches data from /api/search/{city}/{category}
 * - Displays results using existing UI components
 * - Handles SEO meta tags dynamically
 */
export default function CityCategoryPage(): React.JSX.Element {
  const { city: cityParam, category: categoryParam } = useParams<{
    city?: string;
    category: string;
  }>();

  const [posts, setPosts] = useState<BabyPost[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const [detectedCity, setDetectedCity] = useState<string | null>(null);
  const [isDetectingLocation, setIsDetectingLocation] = useState(false);

  // Normalize city name: "bhopal" -> "Bhopal"
  const formatCityName = (city: string): string => {
    if (!city) return '';
    return city
      .split(/[-_]/)
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  };

  // Normalize category slug: "call-girls" or "call girls" -> "call-girls" (for API)
  const normalizeCategorySlug = (slug: string): string => {
    if (!slug) return '';
    // Convert spaces to hyphens and lowercase for API call
    return slug.toLowerCase().replace(/\s+/g, '-');
  };

  // Format category for display: "call-girls" or "call girls" -> "Call Girls"
  const formatCategoryName = (category: string): string => {
    if (!category) return '';
    return category
      .split(/[-_\s]/)
      .map((word) => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase())
      .join(' ');
  };

  // Auto-detect user city using browser geolocation or IP
  const detectUserCity = async (): Promise<string | null> => {
    setIsDetectingLocation(true);
    
    try {
      // Method 1: Try browser geolocation first
      if (navigator.geolocation) {
        return new Promise((resolve) => {
          navigator.geolocation.getCurrentPosition(
            async (position) => {
              try {
                // Reverse geocoding using a free API
                const response = await fetch(
                  `https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=${position.coords.latitude}&longitude=${position.coords.longitude}&localityLanguage=en`
                );
                const data = await response.json();
                const city = data.city || data.locality || null;
                setIsDetectingLocation(false);
                resolve(city);
              } catch (err) {
                // Fallback to IP-based detection
                detectCityByIP().then(resolve);
              }
            },
            () => {
              // Geolocation failed, try IP-based
              detectCityByIP().then(resolve);
            },
            { timeout: 5000 }
          );
        });
      } else {
        // No geolocation support, use IP-based
        return await detectCityByIP();
      }
    } catch (err) {
      setIsDetectingLocation(false);
      return null;
    }
  };

  // Detect city using IP address
  const detectCityByIP = async (): Promise<string | null> => {
    try {
      const response = await fetch('https://ipapi.co/json/');
      const data = await response.json();
      setIsDetectingLocation(false);
      return data.city || null;
    } catch (err) {
      setIsDetectingLocation(false);
      return null;
    }
  };

  // Determine actual city to use
  const actualCityParam = cityParam || detectedCity;
  const city = actualCityParam ? formatCityName(actualCityParam) : '';
  const categorySlug = categoryParam ? normalizeCategorySlug(categoryParam) : '';
  const categoryDisplay = categoryParam ? formatCategoryName(categoryParam) : '';

  // Auto-detect city if missing
  useEffect(() => {
    const handleAutoLocation = async () => {
      // If city is missing, detect it
      if (!cityParam && categoryParam) {
        const detected = await detectUserCity();
        if (detected) {
          setDetectedCity(detected);
          // Redirect to /{detectedCity}/{category}
          const normalizedCity = detected.toLowerCase().replace(/\s+/g, '-');
          const normalizedCategory = categoryParam.toLowerCase().replace(/\s+/g, '-');
          window.location.href = `/${normalizedCity}/${normalizedCategory}`;
        }
      }
    };

    handleAutoLocation();
  }, [cityParam, categoryParam]);

  // Fetch data when city and category are available
  useEffect(() => {
    const fetchData = async () => {
      // Wait for city detection if needed
      if (!cityParam && !detectedCity) {
        return;
      }

      const cityToUse = cityParam || detectedCity;
      if (!cityToUse || !categorySlug) {
        setError('City or category missing');
        setLoading(false);
        return;
      }

      setLoading(true);
      setError(null);

      try {
        // Call the search API: /api/search/{city}/{category}
        // Use original city param (lowercase) for API, not formatted
        const apiCity = cityToUse.toLowerCase().replace(/\s+/g, '-');
        const response = await fetch(
          `${API_BASE_URL}/search/${encodeURIComponent(apiCity)}/${encodeURIComponent(categorySlug)}`
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
  }, [cityParam, detectedCity, categorySlug]);

  // Generate canonical URL
  const actualCitySlug = (cityParam || detectedCity || '').toLowerCase().replace(/\s+/g, '-');
  const categorySlugForUrl = categoryParam?.toLowerCase().replace(/\s+/g, '-') || '';
  const canonicalUrl = `https://nightsaathi.com/${actualCitySlug}/${categorySlugForUrl}`;

  return (
    <React.Fragment>
      <Helmet>
        {/* Dynamic SEO - All generated from code */}
        <title>{`Top ${categoryDisplay} Stores in ${city} | MyBabayadopt`}</title>
        <meta
          name="description"
          content={`Find the best ${categoryDisplay.toLowerCase()} call girls in ${city}. Ratings, reviews, contact details.`}
        />
        <meta property="og:title" content={`Top ${categoryDisplay} Stores in ${city} | MyBabayadopt`} />
        <meta
          property="og:description"
          content={`Find the best ${categoryDisplay.toLowerCase()} call girls in ${city}. Ratings, reviews, contact details.`}
        />
        <link rel="canonical" href={canonicalUrl} />
      </Helmet>

      <div className="min-h-screen bg-[#f6f7fb] text-slate-800 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
        <div className="mx-auto max-w-7xl px-4 py-8 lg:px-8">
          {/* Auto-location detection message */}
          {isDetectingLocation && !cityParam && (
            <div className="mb-4 rounded-lg border border-blue-200 bg-blue-50 p-4 text-center dark:border-blue-800 dark:bg-blue-900/20">
              <p className="text-sm text-blue-800 dark:text-blue-200">
                Detecting your location...
              </p>
            </div>
          )}

          {/* Page Header */}
          <header className="mb-8 space-y-4">
            <h1 className="text-3xl font-bold text-slate-900 dark:text-slate-100">
              Top {categoryDisplay} call girls in {city}
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
                        <SafeImage
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
                        <button
                          onClick={(e) => {
                            e.preventDefault();
                            if (post.whatsapp) {
                              handleWhatsAppClick(post.whatsapp, {
                                id: post.id,
                                city: post.city || city,
                                district: post.district,
                                state: post.state,
                                name: post.name,
                              });
                            }
                          }}
                          className="inline-flex w-full items-center justify-center gap-1.5 rounded-full border border-emerald-500/70 bg-emerald-500/10 px-3 py-1.5 font-semibold text-emerald-600 transition hover:border-emerald-500 hover:bg-emerald-500/20 dark:border-emerald-400/60 dark:text-emerald-300"
                        >
                          WhatsApp
                        </button>
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

