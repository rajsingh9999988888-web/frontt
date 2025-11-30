import React, { useCallback, useEffect, useMemo, useState } from 'react';
import { Link, useSearchParams } from 'react-router-dom';
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

const fetchOptions = async (path: string): Promise<string[]> => {
  const response = await fetch(`${API_BASE_URL}/api/babies${path}`);
  if (!response.ok) {
    throw new Error(`Failed to fetch ${path}`);
  }
  const data = await response.json();
  return Array.isArray(data) ? data : [];
};

const dummyBabyPosts: BabyPost[] = [
  {
    id: 101,
    name: 'Kiara Dev',
    description: 'Luxury socialite offering curated dinner dates, red-carpet events, and private jet companionship.',
    imageUrl: 'https://images.pexels.com/photos/2354622/pexels-photo-2354622.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260',
    city: 'Mumbai',
    district: 'Mumbai',
    state: 'Maharashtra',
    category: 'Escorts',
    tags: ['Premium', 'Nightlife', 'Verified'],
    phone: '+911126006000',
    whatsapp: '911126006000',
    rate: '₹25k evening',
  },
  {
    id: 102,
    name: 'Sia Arora',
    description: 'Certified tantra therapist specialising in couples rejuvenation, chakra balance, and candle-lit spa rituals.',
    imageUrl: 'https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260',
    city: 'Bengaluru',
    district: 'Bengaluru Urban',
    state: 'Karnataka',
    category: 'Massage',
    tags: ['Tantra', 'Couples', 'Home visit'],
    phone: '+918880778866',
    whatsapp: '918880778866',
    rate: '₹8k session',
  },
  {
    id: 103,
    name: 'Noah Khan',
    description: 'Corporate host for boardroom dinners, premieres, and private villa parties with discreet concierge add-ons.',
    imageUrl: 'https://images.pexels.com/photos/614810/pexels-photo-614810.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260',
    city: 'Delhi',
    district: 'New Delhi',
    state: 'Delhi',
    category: 'Male Escorts',
    tags: ['Black tie', 'Bilingual', 'Events'],
    phone: '+919948500111',
    whatsapp: '919948500111',
    rate: '₹18k engagement',
  },
  {
    id: 104,
    name: 'Lola Rivera',
    description: 'Latina performer for VIP villa brunches, curated yacht sundowners, and themed after-parties across Goa.',
    imageUrl: 'https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260',
    city: 'Goa',
    district: 'North Goa',
    state: 'Goa',
    category: 'Luxury',
    tags: ['Yacht', 'DJ sets', 'After-party'],
    phone: '+919766443355',
    whatsapp: '919766443355',
    rate: '₹35k night',
  },
  {
    id: 105,
    name: 'Amyra Shah',
    description: 'Influencer companion with verified socials, travel-ready for destination shoots, reels, and branded weekends.',
    imageUrl: 'https://images.pexels.com/photos/1181763/pexels-photo-1181763.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260',
    city: 'Hyderabad',
    district: 'Hyderabad',
    state: 'Telangana',
    category: 'Premium',
    tags: ['Influencer', 'Travel', 'Verified'],
    phone: '+918228440220',
    whatsapp: '918228440220',
    rate: '₹22k day',
  },
  {
    id: 106,
    name: 'Zara Ali',
    description: 'Wellness muse for sunrise yoga, mindful brunches, and conscious cuddling retreats for expat couples.',
    imageUrl: 'https://images.pexels.com/photos/1183266/pexels-photo-1183266.jpeg?auto=compress&cs=tinysrgb&h=750&w=1260',
    city: 'Pune',
    district: 'Pune',
    state: 'Maharashtra',
    category: 'Adult Meetings',
    tags: ['Mindful', 'Retreat', 'Couples'],
    phone: '+917204403322',
    whatsapp: '917204403322',
    rate: '₹12k half-day',
  },
];

export default function BabyList(): React.JSX.Element {
  const [searchParams, setSearchParams] = useSearchParams();
  const initialCategory = searchParams.get('category')?.toLowerCase() ?? 'all';
  const initialState = searchParams.get('state') ?? '';
  const initialDistrict = searchParams.get('district') ?? '';
  const initialCity = searchParams.get('city') ?? '';
  const initialQuery = searchParams.get('q') ?? '';

  const [babyPosts, setBabyPosts] = useState<BabyPost[]>([]);
  const [searchTerm, setSearchTerm] = useState(initialQuery);
  const [activeCategory, setActiveCategory] = useState(initialCategory);
  const [stateOptions, setStateOptions] = useState<string[]>([]);
  const [districtOptions, setDistrictOptions] = useState<string[]>([]);
  const [cityOptions, setCityOptions] = useState<string[]>([]);
  const [selectedState, setSelectedState] = useState(initialState);
  const [selectedDistrict, setSelectedDistrict] = useState(initialDistrict);
  const [selectedCity, setSelectedCity] = useState(initialCity);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const hasLocationFilters = Boolean(selectedState || selectedDistrict || selectedCity);
  const filterSelectClass =
    'rounded-2xl border border-slate-200 bg-white px-3 py-2 text-sm text-slate-600 shadow-sm transition focus:border-[#ff4f70] focus:outline-none focus:ring-2 focus:ring-[#ff4f70]/20 dark:border-slate-700 dark:bg-slate-900 dark:text-slate-100';

  const syncSearchParams = (overrides: Partial<{ category: string; state: string; district: string; city: string; search: string }> = {}) => {
    const params = new URLSearchParams();
    const categoryValue = overrides.category ?? activeCategory;
    const stateValue = overrides.state ?? selectedState;
    const districtValue = overrides.district ?? selectedDistrict;
    const cityValue = overrides.city ?? selectedCity;
    const searchValue = overrides.search ?? searchTerm;

    if (categoryValue && categoryValue !== 'all') params.set('category', categoryValue);
    if (stateValue) params.set('state', stateValue);
    if (districtValue) params.set('district', districtValue);
    if (cityValue) params.set('city', cityValue);
    if (searchValue.trim().length > 0) params.set('q', searchValue.trim());

    if ([...params.keys()].length > 0) {
      setSearchParams(params, { replace: true });
    } else {
      setSearchParams('', { replace: true });
    }
  };

  useEffect(() => {
    const urlCategory = searchParams.get('category')?.toLowerCase() ?? 'all';
    if (urlCategory !== activeCategory) setActiveCategory(urlCategory);
    const urlState = searchParams.get('state') ?? '';
    if (urlState !== selectedState) setSelectedState(urlState);
    const urlDistrict = searchParams.get('district') ?? '';
    if (urlDistrict !== selectedDistrict) setSelectedDistrict(urlDistrict);
    const urlCity = searchParams.get('city') ?? '';
    if (urlCity !== selectedCity) setSelectedCity(urlCity);
    const urlSearch = searchParams.get('q') ?? '';
    if (urlSearch !== searchTerm) setSearchTerm(urlSearch);
  }, [searchParams]);

  useEffect(() => {
    const loadStates = async () => {
      try {
        const data = await fetchOptions('/states');
        setStateOptions(data);
      } catch (err) {
        console.error(err);
        setStateOptions([]);
      }
    };
    loadStates();
  }, []);

  useEffect(() => {
    if (!selectedState) {
      setDistrictOptions([]);
      if (selectedDistrict) setSelectedDistrict('');
      setCityOptions([]);
      if (selectedCity) setSelectedCity('');
      return;
    }
    const loadDistricts = async () => {
      try {
        const data = await fetchOptions(`/districts?state=${encodeURIComponent(selectedState)}`);
        setDistrictOptions(data);
      } catch (err) {
        console.error(err);
        setDistrictOptions([]);
      }
    };
    loadDistricts();
  }, [selectedState]);

  useEffect(() => {
    if (!selectedDistrict) {
      setCityOptions([]);
      if (selectedCity) setSelectedCity('');
      return;
    }
    const loadCities = async () => {
      try {
        const data = await fetchOptions(`/cities?district=${encodeURIComponent(selectedDistrict)}`);
        setCityOptions(data);
      } catch (err) {
        console.error(err);
        setCityOptions([]);
      }
    };
    loadCities();
  }, [selectedDistrict]);

  const loadBabies = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      const url = new URL(`${API_BASE_URL}/api/babies/babies`);
      if (selectedState) url.searchParams.set('state', selectedState);
      if (selectedDistrict) url.searchParams.set('district', selectedDistrict);
      if (selectedCity) url.searchParams.set('city', selectedCity);
      const response = await fetch(url.toString());
      if (!response.ok) {
        throw new Error('Failed to fetch baby posts');
      }
      const data = await response.json();
      setBabyPosts(Array.isArray(data) ? data : []);
    } catch (err) {
      console.error(err);
      setError('Unable to reach live listings. Showing curated samples for now.');
      setBabyPosts(dummyBabyPosts);
    } finally {
      setLoading(false);
    }
  }, [selectedState, selectedDistrict, selectedCity]);

  useEffect(() => {
    loadBabies();
  }, [loadBabies]);

  const categories = useMemo(() => {
    const found = new Set<string>();
    babyPosts.forEach((post) => {
      if (post.category) {
        found.add(post.category.toLowerCase());
      }
    });
    return ['all', ...Array.from(found)];
  }, [babyPosts]);

  const filteredPosts = useMemo(() => {
    const query = searchTerm.trim().toLowerCase();
    return babyPosts.filter((post) => {
      const category = post.category?.toLowerCase() ?? 'other';
      const matchesCategory = activeCategory === 'all' || category === activeCategory;
      const matchesQuery =
        query.length === 0 ||
        `${post.name} ${post.description ?? ''} ${post.city ?? ''}`.toLowerCase().includes(query);
      const matchesLocation =
        (!selectedState || post.state === selectedState) &&
        (!selectedDistrict || post.district === selectedDistrict) &&
        (!selectedCity || post.city === selectedCity);
      return matchesCategory && matchesQuery && matchesLocation;
    });
  }, [activeCategory, babyPosts, searchTerm, selectedState, selectedDistrict, selectedCity]);

  const handleCategoryChange = (category: string) => {
    setActiveCategory(category);
    syncSearchParams({ category });
  };

  const handleStateChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const nextState = event.target.value;
    setSelectedState(nextState);
    setSelectedDistrict('');
    setSelectedCity('');
    syncSearchParams({ state: nextState, district: '', city: '' });
  };

  const handleDistrictChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const nextDistrict = event.target.value;
    setSelectedDistrict(nextDistrict);
    setSelectedCity('');
    syncSearchParams({ district: nextDistrict, city: '' });
  };

  const handleCityChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    const nextCity = event.target.value;
    setSelectedCity(nextCity);
    syncSearchParams({ city: nextCity });
  };

  const handleSearchChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const value = event.target.value;
    setSearchTerm(value);
    syncSearchParams({ search: value });
  };

  const handleClearFilters = () => {
    setActiveCategory('all');
    setSelectedState('');
    setSelectedDistrict('');
    setSelectedCity('');
    setSearchTerm('');
    syncSearchParams({ category: 'all', state: '', district: '', city: '', search: '' });
  };

  return (
    <div className="min-h-screen bg-gradient-to-b from-white via-slate-50 to-slate-100 px-4 py-12 text-slate-900 transition-colors duration-300 dark:from-slate-950 dark:via-slate-900 dark:to-slate-950 dark:text-slate-100">
      <div className="mx-auto w-full max-w-6xl space-y-10">
        <header className="flex flex-col gap-6 sm:flex-row sm:items-end sm:justify-between">
          <div className="space-y-3">
            <p className="text-xs font-semibold uppercase tracking-[0.4em] text-[#ff4f70]">Exclusive directory</p>
            <h1 className="text-3xl font-semibold tracking-tight text-slate-900 dark:text-white sm:text-4xl">
              Browse verified companions ready for unforgettable nights.
            </h1>
            <p className="max-w-2xl text-sm text-slate-600 dark:text-slate-300">
              Search by city, filter by category, and discover premium listings updated in real-time. Every profile comes
              with concierge-assisted contact for discreet arrangements.
            </p>
          </div>
          <label className="flex w-full max-w-xs items-center gap-2 rounded-full border border-slate-200 bg-white px-4 py-2 text-sm text-slate-500 shadow-sm transition focus-within:border-[#ff4f70] focus-within:ring-2 focus-within:ring-[#ff4f70]/20 dark:border-slate-700 dark:bg-slate-900 dark:text-slate-300">
            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.5" className="h-4 w-4">
              <path strokeLinecap="round" strokeLinejoin="round" d="M21 21l-4.35-4.35M17 10A7 7 0 1 1 3 10a7 7 0 0 1 14 0z" />
            </svg>
            <input
              value={searchTerm}
              onChange={handleSearchChange}
              placeholder="Search by name, city, keywords"
              className="w-full bg-transparent text-slate-700 outline-none placeholder:text-slate-400 dark:text-slate-100"
            />
          </label>
        </header>

        <div className="grid gap-3 md:grid-cols-2 lg:grid-cols-4">
          <select value={selectedState} onChange={handleStateChange} className={filterSelectClass}>
            <option value="">All states</option>
            {stateOptions.map((state) => (
              <option key={state} value={state}>
                {state}
              </option>
            ))}
          </select>

          <select
            value={selectedDistrict}
            onChange={handleDistrictChange}
            disabled={!selectedState || districtOptions.length === 0}
            className={`${filterSelectClass} ${!selectedState ? 'opacity-60' : ''}`}
          >
            <option value="">All districts</option>
            {districtOptions.map((district) => (
              <option key={district} value={district}>
                {district}
              </option>
            ))}
          </select>

          <select
            value={selectedCity}
            onChange={handleCityChange}
            disabled={!selectedDistrict || cityOptions.length === 0}
            className={`${filterSelectClass} ${!selectedDistrict ? 'opacity-60' : ''}`}
          >
            <option value="">All cities</option>
            {cityOptions.map((city) => (
              <option key={city} value={city}>
                {city}
              </option>
            ))}
          </select>

          <button
            onClick={handleClearFilters}
            disabled={!hasLocationFilters && activeCategory === 'all' && searchTerm.trim().length === 0}
            className="rounded-2xl border border-slate-200 px-3 py-2 text-sm font-semibold text-slate-600 transition hover:border-[#ff4f70] hover:text-[#ff4f70] disabled:cursor-not-allowed disabled:opacity-60 dark:border-slate-700 dark:text-slate-300"
          >
            Clear filters
          </button>
        </div>

        <div className="flex flex-wrap items-center gap-3 text-xs text-slate-500 dark:text-slate-300">
          {loading && <span>Loading live listings…</span>}
          {hasLocationFilters && !loading && (
            <span className="rounded-full bg-slate-100 px-3 py-1 text-[0.65rem] uppercase tracking-wider text-slate-600 dark:bg-slate-800 dark:text-slate-300">
              Filters applied
            </span>
          )}
        </div>

        {error && (
          <div className="rounded-2xl border border-amber-200 bg-amber-50 px-4 py-3 text-sm text-amber-800 dark:border-amber-400/40 dark:bg-amber-400/10 dark:text-amber-200">
            {error}
          </div>
        )}

        <div className="flex flex-wrap gap-3">
          {categories.map((category) => {
            const isActive = category === activeCategory;
            const label = category === 'all' ? 'All' : category.replace(/(^|\s)\S/g, (s) => s.toUpperCase());
            return (
              <button
                key={category}
                onClick={() => handleCategoryChange(category)}
                className={`inline-flex items-center gap-2 rounded-full border px-4 py-2 text-sm font-semibold transition focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-offset-2 focus-visible:ring-[#ff4f70]/30 ${
                  isActive
                    ? 'border-transparent bg-gradient-to-r from-[#ff4f70] to-[#ff884f] text-white shadow-sm shadow-[#ff4f70]/30'
                    : 'border-slate-200 bg-white text-slate-500 hover:border-[#ff4f70]/60 hover:text-[#ff4f70] dark:border-slate-700 dark:bg-slate-900 dark:text-slate-300'
                }`}
              >
                {label}
              </button>
            );
          })}
        </div>

        <section className="grid grid-cols-1 gap-6 justify-items-center md:grid-cols-2 xl:grid-cols-3">
          {filteredPosts.map((post, index) => {
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

        {filteredPosts.length === 0 && (
          <div className="rounded-3xl border border-dashed border-slate-300 bg-white/80 p-12 text-center shadow-inner transition dark:border-slate-600 dark:bg-slate-900/70 dark:shadow-slate-900/40">
            <h2 className="text-xl font-semibold text-slate-900 dark:text-slate-100">No listings match right now.</h2>
            <p className="mt-2 text-sm text-slate-600 dark:text-slate-300">
              Adjust your filters or clear the search to explore the full gallery.
            </p>
            <button
              onClick={handleClearFilters}
              className="mt-5 inline-flex items-center gap-2 rounded-full border border-slate-300 px-4 py-2 text-sm font-semibold text-slate-600 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-300"
            >
              Reset filters
            </button>
          </div>
        )}
      </div>
    </div>
  );
}

