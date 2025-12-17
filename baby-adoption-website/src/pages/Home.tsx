import React, { useState } from 'react';
import { Helmet } from 'react-helmet-async';
import { useNavigate } from 'react-router-dom';

type FeaturedCategory = {
  id: string;
  label: string;
  description: string;
};

const stateOptions = ['All States', 'Delhi', 'Maharashtra', 'Karnataka', 'Telangana', 'Tamil Nadu', 'West Bengal'];
const cityOptions = ['All cities', 'Delhi', 'Mumbai', 'Bengaluru', 'Hyderabad', 'Kolkata', 'Pune'];

type StateData = {
  state: string;
  capital: string;
  districts: string[];
};

const allStatesData: StateData[] = [
  { state: 'Andhra Pradesh', capital: 'Amaravati', districts: ['Visakhapatnam', 'Vijayawada', 'Guntur', 'Nellore', 'Kurnool'] },
  { state: 'Arunachal Pradesh', capital: 'Itanagar', districts: ['Tawang', 'West Kameng', 'East Kameng', 'Papum Pare', 'Lower Subansiri'] },
  { state: 'Assam', capital: 'Dispur', districts: ['Guwahati', 'Jorhat', 'Dibrugarh', 'Silchar', 'Tezpur'] },
  { state: 'Bihar', capital: 'Patna', districts: ['Patna', 'Gaya', 'Bhagalpur', 'Muzaffarpur', 'Darbhanga'] },
  { state: 'Chhattisgarh', capital: 'Raipur', districts: ['Raipur', 'Bilaspur', 'Durg', 'Korba', 'Jagdalpur'] },
  { state: 'Goa', capital: 'Panaji', districts: ['North Goa', 'South Goa', 'Ponda', 'Margao', 'Mapusa'] },
  { state: 'Gujarat', capital: 'Gandhinagar', districts: ['Ahmedabad', 'Surat', 'Vadodara', 'Rajkot', 'Jamnagar'] },
  { state: 'Haryana', capital: 'Chandigarh', districts: ['Gurgaon', 'Faridabad', 'Panipat', 'Karnal', 'Ambala'] },
  { state: 'Himachal Pradesh', capital: 'Shimla', districts: ['Shimla', 'Manali', 'Dharamshala', 'Kullu', 'Solan'] },
  { state: 'Jharkhand', capital: 'Ranchi', districts: ['Ranchi', 'Jamshedpur', 'Dhanbad', 'Bokaro', 'Hazaribagh'] },
  { state: 'Karnataka', capital: 'Bengaluru', districts: ['Bengaluru', 'Mysuru', 'Mangaluru', 'Hubli', 'Belagavi'] },
  { state: 'Kerala', capital: 'Thiruvananthapuram', districts: ['Kochi', 'Kozhikode', 'Thrissur', 'Kannur', 'Alappuzha'] },
  { state: 'Madhya Pradesh', capital: 'Bhopal', districts: ['Indore', 'Bhopal', 'Gwalior', 'Jabalpur', 'Ujjain'] },
  { state: 'Maharashtra', capital: 'Mumbai', districts: ['Mumbai', 'Pune', 'Nagpur', 'Nashik', 'Aurangabad'] },
  { state: 'Manipur', capital: 'Imphal', districts: ['Imphal', 'Thoubal', 'Bishnupur', 'Churachandpur', 'Ukhrul'] },
  { state: 'Meghalaya', capital: 'Shillong', districts: ['Shillong', 'Tura', 'Jowai', 'Nongpoh', 'Williamnagar'] },
  { state: 'Mizoram', capital: 'Aizawl', districts: ['Aizawl', 'Lunglei', 'Saiha', 'Champhai', 'Kolasib'] },
  { state: 'Nagaland', capital: 'Kohima', districts: ['Kohima', 'Dimapur', 'Mokokchung', 'Tuensang', 'Wokha'] },
  { state: 'Odisha', capital: 'Bhubaneswar', districts: ['Bhubaneswar', 'Cuttack', 'Puri', 'Rourkela', 'Sambalpur'] },
  { state: 'Punjab', capital: 'Chandigarh', districts: ['Amritsar', 'Ludhiana', 'Jalandhar', 'Patiala', 'Bathinda'] },
  { state: 'Rajasthan', capital: 'Jaipur', districts: ['Jaipur', 'Jodhpur', 'Udaipur', 'Kota', 'Ajmer'] },
  { state: 'Sikkim', capital: 'Gangtok', districts: ['Gangtok', 'Namchi', 'Mangan', 'Gyalshing', 'Soreng'] },
  { state: 'Tamil Nadu', capital: 'Chennai', districts: ['Chennai', 'Coimbatore', 'Madurai', 'Tiruchirappalli', 'Salem'] },
  { state: 'Telangana', capital: 'Hyderabad', districts: ['Hyderabad', 'Warangal', 'Nizamabad', 'Karimnagar', 'Khammam'] },
  { state: 'Tripura', capital: 'Agartala', districts: ['Agartala', 'Udaipur', 'Dharmanagar', 'Kailashahar', 'Belonia'] },
  { state: 'Uttar Pradesh', capital: 'Lucknow', districts: ['Lucknow', 'Kanpur', 'Agra', 'Varanasi', 'Allahabad'] },
  { state: 'Uttarakhand', capital: 'Dehradun', districts: ['Dehradun', 'Haridwar', 'Nainital', 'Mussoorie', 'Rishikesh'] },
  { state: 'West Bengal', capital: 'Kolkata', districts: ['Kolkata', 'Howrah', 'Durgapur', 'Asansol', 'Siliguri'] },
  { state: 'Delhi', capital: 'New Delhi', districts: ['New Delhi', 'Central Delhi', 'North Delhi', 'South Delhi', 'East Delhi'] },
  { state: 'Jammu and Kashmir', capital: 'Srinagar', districts: ['Srinagar', 'Jammu', 'Anantnag', 'Baramulla', 'Udhampur'] },
  { state: 'Ladakh', capital: 'Leh', districts: ['Leh', 'Kargil', 'Nubra', 'Zanskar', 'Drass'] },
  { state: 'Puducherry', capital: 'Puducherry', districts: ['Puducherry', 'Karaikal', 'Mahe', 'Yanam', 'Ozhukarai'] },
];

const featuredCategories: FeaturedCategory[] = [
  {
    id: 'call-girls',
    label: 'Call Girls',
    description: 'Hot call girls and independent girls ready to meet in your city.',
  },
  {
    id: 'massage',
    label: 'Massages',
    description: 'Enjoy the best massages on all fours with happy ending with India’s most sensual women.',
  },
  {
    id: 'male-escorts',
    label: 'Male Escorts',
    description: 'Male escorts, gigolos, gay escorts ready for your events and parties.',
  },
  {
    id: 'transsexual',
    label: 'Transsexual',
    description: 'Transsexual dating. Discover the most exciting trans escorts, always truly verified.',
  },
  {
    id: 'adult-meetings',
    label: 'Adult Meetings',
    description: 'Casual dating for singles. If you are looking for a partner who has a strong sex drive, make friends with an adult dating site.',
  },
];

const promoBanners = [
  {
    id: 'verified-safer',
    heading: 'Every Booking, Verified & Safe',
    subheading: 'Our concierge team screens profiles daily for authenticity and safety compliance.',
    ctaLabel: 'Read safety guide',
    ctaHref: '#safety',
  },
  {
    id: 'premium-pass',
    heading: 'Upgrade to Premium Pass',
    subheading: 'Unlock concierge booking, zero wait times, and private previews of elite profiles.',
    ctaLabel: 'View membership',
    ctaHref: '#premium',
  },
];

const categoryChips: Record<string, string[]> = {
  'call-girls': ['Bengalore', 'Hyderabad', 'Mumbai', 'Delhi', 'Pune', 'All cities'],
  massage: ['Bengalore', 'Hyderabad', 'Mumbai', 'Delhi', 'Pune', 'All cities'],
  'male-escorts': ['Bengalore', 'Hyderabad', 'Mumbai', 'Delhi', 'Pune', 'All cities'],
  transsexual: ['Bengalore', 'Hyderabad', 'Mumbai', 'Delhi', 'Pune', 'All cities'],
  'adult-meetings': ['Bengalore', 'Hyderabad', 'Mumbai', 'Delhi', 'Pune', 'All cities'],
};

const categoryImages: Record<string, string> = {
  'call-girls': 'https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg?auto=compress&cs=tinysrgb&w=800&h=600&fit=crop',
  massage: 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.mm.bing.net%2Fth%2Fid%2FOIP.x6RqQ25w0bmeq1a8IkzfoAHaFj%3Fcb%3Ducfimg2%26pid%3DApi%26ucfimg%3D1&f=1&ipt=7e7c761bcfd0cc339af8640df8ac01185c618c1fbb09eda3ca47b75580096ca0&ipo=images',
  'male-escorts': 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.explicit.bing.net%2Fth%2Fid%2FOIP.Gpc25SW3bpm70SBCld_f1AHaJo%3Fcb%3Ducfimg2%26pid%3DApi%26ucfimg%3D1&f=1&ipt=5ace293409e59978a78cb85bfbd555eaf79f2fe044fddfbed044c50a8b381506&ipo=images',
  transsexual: 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse1.explicit.bing.net%2Fth%2Fid%2FOIP.OAuAnG7N-119tQz4JGNXagHaEn%3Fcb%3Ducfimg2%26pid%3DApi%26ucfimg%3D1&f=1&ipt=abc35bdbb35fe9812de1c99b65400e608293f2cbb7da96481b453ace90e714b6&ipo=images',
  'adult-meetings': 'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse3.mm.bing.net%2Fth%2Fid%2FOIP.NjRHGgYPgxvwVF0nQpaSpQHaFJ%3Fcb%3Ducfimg2%26pid%3DApi%26ucfimg%3D1&f=1&ipt=03ec1dcd2b35a1d87780a3278f8febacf6b8aae769bbdabced60249c20c92fe0&ipo=images',
};

const filterKeys = ['ethnicity', 'nationality', 'bustType', 'hairColor', 'bodyType', 'services', 'attentionTo', 'placeOfService'] as const;
type FilterKey = (typeof filterKeys)[number];
type FilterState = Record<FilterKey, string[]>;

const filterOptionMap: Record<FilterKey, string[]> = {
  ethnicity: ['African', 'Indian', 'Asian', 'Arab', 'Latin', 'Caucasian'],
  nationality: ['Indian', 'South African', 'Kenyan', 'English'],
  bustType: ['Natural Boobs', 'Busty'],
  hairColor: ['Blond Hair', 'Brown Hair', 'Black Hair', 'Red Hair'],
  bodyType: ['Slim', 'Curvy'],
  services: ['Oral', 'Anal', 'BDSM', 'Girlfriend experience', 'Porn actresses', 'Body ejaculation', 'Erotic massage', 'Tantric massage', 'Fetish', 'French kiss', 'Role play', 'Threesome', 'Sexting', 'Video call'],
  attentionTo: ['Men', 'Women', 'Couples', 'Disabled'],
  placeOfService: ['At home', 'Events and parties', 'Hotel / Motel', 'Clubs', 'Outcall'],
};

const filterGroups: { key: FilterKey; label: string }[] = [
  { key: 'ethnicity', label: 'Ethnicity' },
  { key: 'nationality', label: 'Nationality' },
  { key: 'bustType', label: 'Breast' },
  { key: 'hairColor', label: 'Hair' },
  { key: 'bodyType', label: 'Body type' },
  { key: 'services', label: 'Services' },
  { key: 'attentionTo', label: 'Attention to' },
  { key: 'placeOfService', label: 'Place of service' },
];

const createEmptyFilters = (): FilterState =>
  filterKeys.reduce((acc, key) => {
    acc[key] = [];
    return acc;
  }, {} as FilterState);

const cloneFilters = (source: FilterState): FilterState =>
  filterKeys.reduce((acc, key) => {
    acc[key] = [...source[key]];
    return acc;
  }, {} as FilterState);

const heroImage =
  'https://images-na.ssl-images-amazon.com/images/I/A1jHr3j9arL.jpg';

export default function Home(): React.JSX.Element {
  const navigate = useNavigate();
  const [selectedState, setSelectedState] = useState('All States');
  const [selectedCategory, setSelectedCategory] = useState<string | null>(null);
  const [selectedCity, setSelectedCity] = useState('All cities');
  const [searchTerm, setSearchTerm] = useState('');
  const [isSearchModalOpen, setIsSearchModalOpen] = useState(false);
  const [modalCategory, setModalCategory] = useState('call-girls');
  const [modalSearch, setModalSearch] = useState('');
  const [modalState, setModalState] = useState('All States');
  const [modalCity, setModalCity] = useState('All cities');
  const [appliedFilters, setAppliedFilters] = useState<FilterState>(() => createEmptyFilters());
  const [modalFilters, setModalFilters] = useState<FilterState>(() => createEmptyFilters());

  const resolveCategoryLabel = (slug: string | null): string | null => {
    if (!slug) return null;
    const match = featuredCategories.find((category) => category.id === slug);
    return match?.label ?? slug;
  };

  const goToListings = (options: { category?: string | null; state?: string | null; district?: string | null; city?: string | null; query?: string | null } = {}) => {
    const params = new URLSearchParams();
    const categoryValue = options.category && options.category !== 'all' ? options.category : null;
    const stateValue = options.state && options.state !== 'All States' ? options.state : null;
    const districtValue = options.district && options.district !== 'All Districts' ? options.district : null;
    const cityValue = options.city && options.city !== 'All cities' ? options.city : null;
    const queryValue = options.query && options.query.trim().length > 0 ? options.query.trim() : null;

    if (categoryValue) params.set('category', categoryValue);
    if (stateValue) params.set('state', stateValue);
    if (districtValue) params.set('district', districtValue);
    if (cityValue) params.set('city', cityValue);
    if (queryValue) params.set('q', queryValue);

    const search = params.toString();
    navigate(`/baby-list${search ? `?${search}` : ''}`);
  };

  const handleFilterToggle = (key: FilterKey, option: string) => {
    setModalFilters((prev) => {
      const exists = prev[key].includes(option);
      const nextValues = exists ? prev[key].filter((value) => value !== option) : [...prev[key], option];
      return { ...prev, [key]: nextValues };
    });
  };

  return (
    <React.Fragment>
      <Helmet>
        <title>BabyAdopt — Browse adoption listings</title>
        <meta name="description" content="Browse verified adoption listings and connect with adoptive parents. Search by city, category and more on BabyAdopt." />
        <meta property="og:title" content="BabyAdopt — Browse adoption listings" />
        <meta property="og:description" content="Browse verified adoption listings and connect with adoptive parents on BabyAdopt." />
      </Helmet>
      <div className="min-h-screen bg-[#f6f7fb] text-slate-800 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <section className="relative min-h-[90vh] overflow-hidden border-b border-slate-200 bg-slate-900 text-white transition-colors duration-300 dark:border-slate-800">
        <div className="absolute inset-0">
          <picture className="block h-full w-full">
            <source srcSet={`${heroImage}?w=1200`} media="(min-width: 1024px)" />
            <source srcSet={`${heroImage}?w=768`} media="(min-width: 640px)" />
            <img
              src={`${heroImage}?w=480`}
              alt="Concierge hero"
              className="h-full w-full object-cover object-[center_20%]"
              loading="lazy"
              decoding="async"
            />
          </picture>
          <div className="absolute inset-0 bg-gradient-to-r from-slate-900 via-slate-900/70 to-[#ff4f70]/60" />
        </div>
        <div className="relative mx-auto flex max-w-5xl flex-col items-center gap-8 px-4 pb-28 pt-32 text-center lg:px-8">
          <div className="max-w-3xl space-y-6">
            
            <h1 className="text-3xl font-semibold leading-tight sm:text-4xl">
              Single? Taken? Doesn’t matter, BabyAdopt is for everyone.
            </h1>
            <p className="text-base text-white/80">
              Search discreetly by city or category. Verified imagery, real availability, and fast responses.
            </p>
            <button
              onClick={() => {
                setModalCategory(selectedCategory ?? 'call-girls');
                setModalSearch(searchTerm);
                setModalState(selectedState);
                setModalCity(selectedCity);
                setModalFilters(cloneFilters(appliedFilters));
                setIsSearchModalOpen(true);
              }}
              className="mx-auto inline-flex w-full max-w-lg items-center justify-between rounded-full bg-white/95 px-4 py-3 text-sm text-slate-500 shadow-lg shadow-slate-900/30 backdrop-blur transition hover:shadow-xl"
            >
              <span>Search by city, category…</span>
              <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8" className="h-4 w-4 text-slate-400">
                <circle cx="11" cy="11" r="7" />
                <path d="M20 20l-3.5-3.5" strokeLinecap="round" />
              </svg>
            </button>
          </div>
        </div>
      </section>

      <div className="mx-auto max-w-6xl px-4 py-10 lg:px-8 space-y-12">
        <section className="space-y-6">
          <div className="text-center">
            <p className="text-sm font-semibold uppercase tracking-[0.35em] text-slate-500 dark:text-slate-300">Hot meetings in your city</p>
            <p className="text-sm text-slate-500 dark:text-slate-300">Find your favorite escort in BabyAdopt</p>
          </div>
          <div className="grid gap-6 sm:grid-cols-2 xl:grid-cols-3">
            {featuredCategories.map((category) => (
              <article key={category.id} className="space-y-4 rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-700 dark:bg-slate-900">
                <div className="overflow-hidden rounded-2xl">
                <img
  src={categoryImages[category.id] || heroImage}
  alt={category.label}
  className="h-40 w-full object-cover cursor-pointer hover:opacity-90 transition"
  loading="lazy"
  onClick={() =>
    goToListings({
      category: resolveCategoryLabel(category.id),
      city: selectedCity,
      state: selectedState,
      query: searchTerm,
    })
  }
/>

                </div>
                <div className="flex items-center justify-between">
                  <h3 className="text-lg font-semibold">{category.label}</h3>
                  <span className="text-xs text-slate-400">✦</span>
                </div>
                <p className="text-sm text-slate-500 dark:text-slate-300">{category.description}</p>
                <div className="flex flex-wrap gap-2">
                  {categoryChips[category.id]?.map((city) => (
                    <button
                      key={city}
                      onClick={() =>
                        goToListings({
                          category: resolveCategoryLabel(category.id),
                          city,
                          state: selectedState,
                          query: searchTerm,
                        })
                      }
                      className="rounded-full border border-slate-200 px-3 py-1 text-xs text-[#ff4f70] hover:border-[#ff4f70] dark:border-slate-700"
                    >
                      {city}
                    </button>
                  ))}
                </div>
              </article>
            ))}
          </div>
        </section>

        <section className="space-y-10">
          {featuredCategories.map((category) => (
            <article key={`${category.id}-chips`} className="space-y-4 rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-700 dark:bg-slate-900">
              <div className="flex flex-col gap-2 text-center">
                <p className="text-sm font-semibold uppercase tracking-[0.3em] text-slate-500">{category.label}</p>
                <p className="text-sm text-slate-500 dark:text-slate-300">{category.description}</p>
              </div>
              <div className="flex flex-wrap justify-center gap-2">
                {categoryChips[category.id]?.map((city) => (
                  <button
                    key={`${category.id}-${city}`}
                    onClick={() =>
                      goToListings({
                        category: resolveCategoryLabel(category.id),
                        city,
                        state: selectedState,
                        query: searchTerm,
                      })
                    }
                    className="rounded-full border border-[#ff4f70] px-4 py-1 text-xs font-semibold text-[#ff4f70]"
                  >
                    {city}
                  </button>
                ))}
              </div>
            </article>
          ))}
        </section>

        <section className="space-y-6">
          <div className="text-center">
            <h2 className="text-2xl font-bold text-slate-900 dark:text-slate-100 uppercase tracking-wide">Call Girls</h2>
            <p className="text-sm text-slate-500 dark:text-slate-300 mt-2">Hot call girls and independent girls ready to meet in your city.</p>
          </div>
          <div className="grid gap-6 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4">
            {allStatesData.map((stateData) => (
              <article key={stateData.state} className="space-y-4 rounded-3xl border border-slate-200 bg-white p-6 shadow-sm dark:border-slate-700 dark:bg-slate-900">
                <div className="flex items-center justify-between">
                  <h3 className="text-lg font-semibold text-slate-900 dark:text-slate-100">{stateData.state}</h3>
                  <span className="text-xs text-slate-400">✦</span>
                </div>
                <p className="text-xs text-slate-500 dark:text-slate-400">Capital: {stateData.capital}</p>
                <div className="flex flex-wrap gap-2">
                  <button
                    onClick={() =>
                      goToListings({
                        state: stateData.state,
                        city: stateData.capital,
                        category: selectedCategory ? resolveCategoryLabel(selectedCategory) : null,
                        query: searchTerm,
                      })
                    }
                    className="rounded-full border border-slate-200 px-3 py-1 text-xs text-[#ff4f70] hover:border-[#ff4f70] dark:border-slate-700"
                  >
                    {stateData.capital}
                  </button>
                  {stateData.districts.map((district) => (
                    <button
                      key={district}
                      onClick={() =>
                        goToListings({
                          state: stateData.state,
                          district: district,
                          category: selectedCategory ? resolveCategoryLabel(selectedCategory) : null,
                          query: searchTerm,
                        })
                      }
                      className="rounded-full border border-slate-200 px-3 py-1 text-xs text-[#ff4f70] hover:border-[#ff4f70] dark:border-slate-700"
                    >
                      {district}
                    </button>
                  ))}
                </div>
              </article>
            ))}
          </div>
        </section>

        <section className="space-y-6">
          <div className="grid gap-6 lg:grid-cols-2">
            {promoBanners.map((banner) => (
              <article
                key={banner.id}
                className="rounded-2xl border border-slate-200 bg-white p-6 shadow-sm transition hover:shadow-md dark:border-slate-700 dark:bg-slate-900 dark:hover:shadow-slate-900/40"
              >
                <h3 className="text-lg font-semibold text-slate-900 dark:text-slate-100">{banner.heading}</h3>
                <p className="mt-2 text-sm text-slate-600 dark:text-slate-300">{banner.subheading}</p>
                <a
                  href={banner.ctaHref}
                  className="mt-4 inline-flex items-center gap-2 text-sm font-semibold text-[#ff4f70] hover:text-[#e24462]"
                >
                  {banner.ctaLabel}
                  <span aria-hidden="true">→</span>
                </a>
              </article>
            ))}
          </div>
        </section>

         
      </div>

      {isSearchModalOpen && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/70 px-4 py-10">
          <div className="relative w-full max-w-3xl max-h-[92vh] overflow-y-auto rounded-2xl bg-white shadow-xl dark:bg-slate-900">
            <button
              className="absolute right-4 top-4 text-slate-400 hover:text-slate-600 dark:text-slate-500 dark:hover:text-slate-300"
              onClick={() => setIsSearchModalOpen(false)}
              aria-label="Close search"
            >
              X
            </button>
            <div className="px-6 py-6">
              <h3 className="text-lg font-semibold text-slate-900 dark:text-slate-100">Search</h3>
              <div className="mt-4 grid gap-3 sm:grid-cols-2">
                <select
                value={modalCategory}
                onChange={(e) => setModalCategory(e.target.value)}
                className="rounded-xl border border-slate-200 px-3 py-2 text-sm text-slate-700 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200"
              >
                <option value="all">All categories</option>
                {featuredCategories.map((category) => (
                  <option key={category.id} value={category.id}>
                    {category.label}
                  </option>
                ))}
              </select>
                <input
                value={modalSearch}
                onChange={(e) => setModalSearch(e.target.value)}
                type="text"
                placeholder="Search here..."
                className="rounded-xl border border-slate-200 px-3 py-2 text-sm text-slate-700 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200"
              />
                <select
                value={modalState}
                onChange={(e) => setModalState(e.target.value)}
                className="rounded-xl border border-slate-200 px-3 py-2 text-sm text-slate-700 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200"
              >
                {stateOptions.map((state) => (
                  <option key={state}>{state}</option>
                ))}
              </select>
                <select
                value={modalCity}
                onChange={(e) => setModalCity(e.target.value)}
                className="rounded-xl border border-slate-200 px-3 py-2 text-sm text-slate-700 focus:border-[#ff4f70] focus:outline-none dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200"
              >
                {cityOptions.map((city) => (
                  <option key={city}>{city}</option>
                ))}
              </select>
              </div>
            </div>

            <div className="mt-6 space-y-4 rounded-2xl border border-slate-200 bg-white p-4 dark:border-slate-700 dark:bg-slate-900">
              {filterGroups.map(({ key, label }) => (
                <div key={key} className="space-y-3 border-b border-slate-100 pb-4 last:border-none last:pb-0 dark:border-slate-800">
                  <div className="flex items-center justify-between text-sm font-semibold text-slate-600 dark:text-slate-300">
                    <span>{label}</span>
                    <span className="text-xs text-slate-400">v</span>
                  </div>
                  <div className="flex flex-wrap gap-2">
                    {filterOptionMap[key].map((option) => {
                      const isActive = modalFilters[key].includes(option);
                      return (
                        <button
                          type="button"
                          key={option}
                          onClick={() => handleFilterToggle(key, option)}
                          className={`rounded-full border px-3 py-1 text-xs font-semibold transition ${
                            isActive
                              ? 'border-[#ff4f70] bg-[#ff4f70]/10 text-[#ff4f70]'
                              : 'border-slate-200 text-slate-600 hover:border-[#ff4f70]/50 hover:text-[#ff4f70] dark:border-slate-700 dark:text-slate-300'
                          }`}
                        >
                          {option}
                        </button>
                      );
                    })}
                  </div>
                </div>
              ))}
            </div>

            <div className="mt-6 flex flex-col gap-4 border-t border-slate-200 pt-4 text-sm font-semibold dark:border-slate-700 sm:flex-row sm:items-center sm:justify-between">
              <button
                className="text-slate-500 transition hover:text-[#ff4f70]"
                onClick={() => {
                  setModalCategory('call-girls');
                  setModalSearch('');
                  setModalState('All States');
                  setModalCity('All cities');
                  setModalFilters(createEmptyFilters());
                }}
              >
                Delete all
              </button>
              <button
                className="inline-flex w-full items-center justify-center gap-2 rounded-full bg-[#ff4f70] px-6 py-2.5 text-base text-white shadow-lg shadow-[#ff4f70]/40 transition hover:bg-[#e24462] sm:w-auto"
                onClick={() => {
                  const resolvedCategory = modalCategory === 'all' ? null : resolveCategoryLabel(modalCategory);
                  setSelectedCategory(modalCategory === 'all' ? null : modalCategory);
                  setSearchTerm(modalSearch);
                  setSelectedState(modalState);
                  setSelectedCity(modalCity);
                  setAppliedFilters(cloneFilters(modalFilters));
                  setIsSearchModalOpen(false);
                  goToListings({
                    category: resolvedCategory,
                    state: modalState,
                    city: modalCity,
                    query: modalSearch,
                  });
                }}
              >
                <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.6" className="h-4 w-4">
                  <circle cx="11" cy="11" r="7" />
                  <path d="M20 20l-3.5-3.5" strokeLinecap="round" />
                </svg>
                Search
              </button>
            </div>
          </div>
        </div>
      )}
    </div>
    </React.Fragment>
  );
}
