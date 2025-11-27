import { useEffect, useMemo, useState, type ReactNode } from 'react';
import { useParams } from 'react-router-dom';

type Baby = {
  id: number;
  name: string;
  description?: string;
  phone?: string;
  whatsapp?: string;
  imageUrl?: string;
  city?: string;
  category?: string;
  address?: string;
  age?: number;
  nickname?: string;
  title?: string;
  text?: string;
  ethnicity?: string;
  nationality?: string;
  bodytype?: string;
  services?: string;
  place?: string;
};

const FALLBACK_IMAGE =
  'https://images.unsplash.com/photo-1500530855697-b586d89ba3ee?auto=format&fit=crop&w=1200&q=80';

const PageShell = ({ children }: { children: ReactNode }) => (
  <div className="min-h-screen bg-gradient-to-b from-slate-50 via-white to-slate-100 text-slate-900 transition-colors duration-300 dark:from-slate-950 dark:via-slate-900 dark:to-slate-950 dark:text-slate-100">
    <div className="mx-auto max-w-5xl px-4 py-12 sm:px-6 lg:px-8">{children}</div>
  </div>
);

const LoadingCard = () => (
  <div className="mx-auto w-full max-w-3xl animate-pulse rounded-[2rem] border border-slate-200/70 bg-white/80 p-8 shadow-xl shadow-slate-900/5 backdrop-blur dark:border-slate-800/70 dark:bg-slate-900/60 dark:shadow-slate-900/30">
    <div className="h-8 w-48 rounded-full bg-slate-200 dark:bg-slate-800" />
    <div className="mt-6 h-64 rounded-[1.5rem] bg-slate-200 dark:bg-slate-800" />
    <div className="mt-6 grid gap-4 sm:grid-cols-2">
      {Array.from({ length: 4 }).map((_, index) => (
        <div key={index} className="h-20 rounded-2xl bg-slate-100 dark:bg-slate-800/80" />
      ))}
    </div>
  </div>
);

const ErrorCard = ({ message }: { message: string }) => (
  <div className="mx-auto w-full max-w-lg rounded-[2rem] border border-red-200/70 bg-white/95 p-8 text-center shadow-lg shadow-red-500/10 dark:border-red-500/40 dark:bg-slate-900/70">
    <p className="text-lg font-semibold text-red-600 dark:text-red-300">{message}</p>
    <p className="mt-2 text-sm text-slate-500 dark:text-slate-400">Please refresh or return to the listings page.</p>
  </div>
);

export default function BabyDetail() {
  const { id } = useParams();
  const [baby, setBaby] = useState<Baby | null>(null);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    let isActive = true;

    const fetchBaby = async () => {
      setIsLoading(true);
      setError(null);

      try {
        const response = await fetch(`http://localhost:8082/babies/${id}`);
        if (!response.ok) {
          throw new Error('Failed to fetch baby details');
        }

        const data = (await response.json()) as Baby;
        if (isActive) {
          setBaby(data);
        }
      } catch (err) {
        console.error(err);
        if (isActive) {
          setError('Unable to load this profile. Please try again shortly.');
        }
      } finally {
        if (isActive) {
          setIsLoading(false);
        }
      }
    };

    if (id) {
      fetchBaby();
    }

    return () => {
      isActive = false;
    };
  }, [id]);

  const detailSections = useMemo(() => {
    if (!baby) return [];

    const sections = [
      {
        title: 'Profile snapshot',
        items: [
          { label: 'Category', value: baby.category },
          { label: 'Age', value: baby.age ? `${baby.age} yrs` : null },
          { label: 'Nickname', value: baby.nickname },
          { label: 'Title', value: baby.title },
        ],
      },
      {
        title: 'Background',
        items: [
          { label: 'Ethnicity', value: baby.ethnicity },
          { label: 'Nationality', value: baby.nationality },
          { label: 'Body type', value: baby.bodytype },
          { label: 'Services', value: baby.services },
        ],
      },
    ];

    return sections
      .map((section) => ({
        ...section,
        items: section.items.filter((item) => item.value),
      }))
      .filter((section) => section.items.length > 0);
  }, [baby]);

  const locationInfo = useMemo(
    () =>
      [
        { label: 'City', value: baby?.city },
        { label: 'Address', value: baby?.address },
        { label: 'Place', value: baby?.place },
      ].filter((item) => item.value),
    [baby?.city, baby?.address, baby?.place]
  );

  if (isLoading) {
    return (
      <PageShell>
        <LoadingCard />
      </PageShell>
    );
  }

  if (error || !baby) {
    return (
      <PageShell>
        <ErrorCard message={error || 'Profile unavailable'} />
      </PageShell>
    );
  }

  const imageSrc = baby.imageUrl || FALLBACK_IMAGE;
  const story = baby.description || baby.text || 'Details will be updated soon.';
  const highlightStats = [
    { label: 'Category', value: baby.category || '—' },
    { label: 'Location', value: baby.city || baby.place || '—' },
    { label: 'Age', value: baby.age ? `${baby.age} yrs` : '—' },
  ];

  return (
    <PageShell>
      <article className="rounded-[2rem] border border-slate-200/70 bg-white/95 p-6 shadow-2xl shadow-slate-900/5 ring-1 ring-white/60 transition dark:border-slate-800/70 dark:bg-slate-900/80 dark:shadow-slate-900/40 dark:ring-slate-900/40 sm:p-10">
        <div className="flex flex-wrap items-center gap-3 text-xs font-semibold uppercase tracking-[0.35em] text-slate-400 dark:text-slate-500">
          <span>Baby profile</span>
          <span className="h-px w-12 bg-slate-200 dark:bg-slate-700" />
          <span>Ref #{baby.id.toString().padStart(4, '0')}</span>
        </div>

        <div className="mt-8 grid gap-8 lg:grid-cols-[1.1fr_0.9fr]">
          <section className="space-y-6">
            <div className="relative overflow-hidden rounded-[1.75rem] border border-slate-200/60 bg-slate-100 shadow-lg shadow-slate-900/10 dark:border-slate-800/60 dark:bg-slate-900/50 dark:shadow-slate-900/50">
              <img src={imageSrc} alt={baby.name} className="h-80 w-full object-cover" loading="lazy" />
              <div className="absolute inset-x-0 bottom-0 bg-gradient-to-t from-black/70 via-black/30 to-transparent px-6 py-6 text-white">
                <p className="text-[0.65rem] uppercase tracking-[0.35em] text-white/70">Profile</p>
                <h1 className="text-3xl font-bold tracking-tight">{baby.name}</h1>
                {(baby.title || baby.nickname) && (
                  <p className="text-sm text-white/80">{baby.title || `Also known as ${baby.nickname}`}</p>
                )}
              </div>
            </div>

            {(baby.category || baby.city || baby.place) && (
              <div className="flex flex-wrap gap-2">
                {baby.category && (
                  <span className="inline-flex items-center rounded-full border border-[#ff4f70]/40 bg-[#ff4f70]/10 px-4 py-1 text-xs font-semibold uppercase tracking-[0.3em] text-[#ff4f70] dark:border-[#ff8ca2]/40 dark:bg-[#ff4f70]/15 dark:text-[#ffb6c7]">
                    {baby.category}
                  </span>
                )}
                {(baby.city || baby.place) && (
                  <span className="inline-flex items-center rounded-full border border-slate-200 px-4 py-1 text-xs font-semibold uppercase tracking-[0.25em] text-slate-500 dark:border-slate-700 dark:text-slate-400">
                    {baby.city || baby.place}
                  </span>
                )}
              </div>
            )}

            <div className="rounded-[1.5rem] border border-slate-200/60 bg-white/90 p-5 text-slate-600 shadow-inner shadow-white dark:border-slate-800/60 dark:bg-slate-900/60 dark:text-slate-300">
              <div className="flex items-center justify-between text-xs uppercase tracking-[0.35em] text-slate-400 dark:text-slate-500">
                <span>Story</span>
                <span>{baby.city || baby.category || ''}</span>
              </div>
              <p className="mt-3 text-base leading-relaxed text-slate-700 dark:text-slate-200">{story}</p>
            </div>

            {locationInfo.length > 0 && (
              <div className="rounded-[1.5rem] border border-slate-200/60 bg-white/90 p-5 shadow-sm dark:border-slate-800/60 dark:bg-slate-900/60">
                <div className="flex items-center gap-2 text-sm font-semibold uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">
                  <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
                    <path strokeLinecap="round" strokeLinejoin="round" d="M12 21c4-4 6-7 6-10a6 6 0 10-12 0c0 3 2 6 6 10z" />
                    <circle cx="12" cy="11" r="2.5" />
                  </svg>
                  Location
                </div>
                <p className="mt-2 text-sm text-slate-500 dark:text-slate-400">Exact address shared once contact is established.</p>
                <dl className="mt-4 grid gap-3 text-sm sm:grid-cols-2">
                  {locationInfo.map((item) => (
                    <div key={item.label} className="rounded-xl border border-slate-100/80 px-4 py-3 dark:border-slate-800">
                      <dt className="text-[0.6rem] uppercase tracking-[0.35em] text-slate-400 dark:text-slate-500">{item.label}</dt>
                      <dd className="mt-1 text-base font-semibold text-slate-900 dark:text-white">{item.value}</dd>
                    </div>
                  ))}
                </dl>
                {(baby.phone || baby.whatsapp) && (
                  <div className="mt-6 rounded-2xl border border-slate-200/70 bg-slate-50 p-4 dark:border-slate-800/70 dark:bg-slate-900">
                    <div className="flex items-center gap-2 text-xs font-semibold uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">
                      <svg xmlns="http://www.w3.org/2000/svg" className="h-4 w-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="1.8">
                        <path strokeLinecap="round" strokeLinejoin="round" d="M22 16.92v3a2 2 0 01-2.18 2 19.79 19.79 0 01-8.63-3.07 19.5 19.5 0 01-6-6A19.79 19.79 0 012.09 4.18 2 2 0 014.11 2h3a2 2 0 012 1.72c.12.94.37 1.85.75 2.71a2 2 0 01-.45 2.11l-1.27 1.27a16 16 0 006 6l1.27-1.27a2 2 0 012.11-.45 11.36 11.36 0 002.71.75A2 2 0 0122 16.92z" />
                      </svg>
                      Contact
                    </div>
                    <p className="mt-2 text-xs text-slate-500 dark:text-slate-400">Reach out to learn more or schedule a meeting.</p>
                    <div className="mt-3 space-y-2 text-sm">
                      {baby.phone && (
                        <a
                          href={`tel:${baby.phone}`}
                          className="flex items-center justify-between rounded-xl border border-slate-200 bg-white px-4 py-2 font-semibold text-slate-700 transition hover:border-blue-400 hover:text-blue-600 dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200 dark:hover:border-blue-500 dark:hover:text-blue-300"
                        >
                          <span>Call</span>
                          <span>{baby.phone}</span>
                        </a>
                      )}
                      {baby.whatsapp && (
                        <a
                          href={`https://wa.me/${baby.whatsapp}`}
                          target="_blank"
                          rel="noopener noreferrer"
                          className="flex items-center justify-between rounded-xl border border-slate-200 bg-white px-4 py-2 font-semibold text-slate-700 transition hover:border-emerald-400 hover:text-emerald-600 dark:border-slate-700 dark:bg-slate-900 dark:text-slate-200 dark:hover:border-emerald-500 dark:hover:text-emerald-300"
                        >
                          <span>WhatsApp</span>
                          <span>{baby.whatsapp}</span>
                        </a>
                      )}
                    </div>
                  </div>
                )}
              </div>
            )}
          </section>

          <aside className="space-y-5">
            <div className="rounded-2xl border border-slate-200/70 bg-white p-5 shadow-sm dark:border-slate-800/70 dark:bg-slate-900">
              <p className="text-xs uppercase tracking-[0.35em] text-slate-400 dark:text-slate-500">Profile reference</p>
              <h2 className="mt-1 text-2xl font-bold text-slate-900 dark:text-white">#{baby.id.toString().padStart(4, '0')}</h2>
              <div className="mt-4 grid gap-3 sm:grid-cols-3">
                {highlightStats.map(({ label, value }) => (
                  <div key={label} className="rounded-xl border border-slate-100/80 px-4 py-3 text-center dark:border-slate-800">
                    <p className="text-[0.6rem] uppercase tracking-[0.35em] text-slate-400 dark:text-slate-500">{label}</p>
                    <p className="mt-1 text-base font-semibold text-slate-900 dark:text-white">{value}</p>
                  </div>
                ))}
              </div>
            </div>

            {detailSections.map((section) => (
              <div key={section.title} className="rounded-2xl border border-slate-200/70 bg-white p-5 shadow-sm dark:border-slate-800/70 dark:bg-slate-900">
                <h3 className="text-sm font-semibold uppercase tracking-[0.3em] text-slate-400 dark:text-slate-500">{section.title}</h3>
                <dl className="mt-4 grid gap-3 text-sm">
                  {section.items.map((item) => (
                    <div key={item.label} className="rounded-xl border border-slate-100/80 px-4 py-3 dark:border-slate-800">
                      <dt className="text-[0.6rem] uppercase tracking-[0.35em] text-slate-400 dark:text-slate-500">{item.label}</dt>
                      <dd className="mt-1 text-base font-semibold text-slate-900 dark:text-white">{item.value}</dd>
                    </div>
                  ))}
                </dl>
              </div>
            ))}

          </aside>
        </div>
      </article>
    </PageShell>
  );
}
