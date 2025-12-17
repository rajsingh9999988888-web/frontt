import React from 'react';
import SafeImage from './SafeImage';

export type ProfileCardData = {
  id: number;
  name: string;
  title: string;
  tagline: string;
  price: number;
  age: number;
  rating: number;
  category: string;
  state: string;
  district: string;
  city: string;
  imageUrl: string;
  services: string[];
  badges?: string[];
  responseTime?: string;
  isFeatured?: boolean;
  ethnicity?: string;
  nationality?: string;
  bustType?: string;
  hairColor?: string;
  bodyType?: string;
  attentionTo?: string[];
  placeOfService?: string[];
};

type ProfileCardProps = {
  post: ProfileCardData;
};

const ProfileCard: React.FC<ProfileCardProps> = ({ post }) => {
  return (
    <article className="group relative mx-auto flex w-full max-w-md flex-col overflow-hidden rounded-3xl border border-slate-200 bg-white shadow-lg shadow-slate-900/5 transition hover:-translate-y-1 hover:shadow-2xl dark:border-slate-700 dark:bg-slate-900 dark:shadow-slate-900/40">
      {post.isFeatured && (
        <span className="absolute left-4 top-4 rounded-full bg-[#ff4f70] px-3 py-1 text-xs font-semibold uppercase tracking-[0.3em] text-white">
          Featured
        </span>
      )}

      <div className="relative aspect-[4/3] w-full overflow-hidden">
        <SafeImage
          src={post.imageUrl}
          alt={post.name}
          className="h-full w-full object-cover transition duration-500 group-hover:scale-105"
          loading="lazy"
        />
        <div className="pointer-events-none absolute inset-0 bg-gradient-to-t from-slate-900/70 via-transparent to-transparent" />
      </div>

      <div className="flex flex-1 flex-col gap-3 px-5 py-5">
        <div className="flex items-start justify-between gap-3">
          <div>
            <h3 className="text-lg font-semibold text-slate-900 dark:text-white">{post.name}</h3>
            <p className="text-sm text-slate-500 dark:text-slate-400">{post.title}</p>
          </div>
          <span className="inline-flex items-center rounded-full border border-amber-200 bg-amber-50 px-2.5 py-0.5 text-xs font-semibold text-amber-600 dark:border-amber-300/40 dark:bg-amber-300/10 dark:text-amber-100">
            {post.rating.toFixed(1)} ★
          </span>
        </div>

        <p className="text-sm text-slate-600 dark:text-slate-400">{post.tagline}</p>

        <div className="flex flex-wrap items-center gap-2 text-[0.8rem] text-slate-500 dark:text-slate-300">
          <span className="text-base font-semibold text-slate-900 dark:text-white">₹ {post.price.toLocaleString('en-IN')}</span>
          <span className="h-1 w-1 rounded-full bg-slate-300 dark:bg-slate-600" />
          <span>
            {post.city}, {post.state}
          </span>
          <span className="h-1 w-1 rounded-full bg-slate-300 dark:bg-slate-600" />
          <span>{post.age} yrs</span>
          {post.responseTime && (
            <>
              <span className="h-1 w-1 rounded-full bg-slate-300 dark:bg-slate-600" />
              <span>{post.responseTime}</span>
            </>
          )}
        </div>

        {post.services.length > 0 && (
          <div className="flex flex-wrap gap-1.5">
            {post.services.map((service) => (
              <span
                key={service}
                className="rounded-full border border-slate-200 bg-slate-50 px-3 py-1 text-xs text-slate-600 transition group-hover:border-[#ff4f70]/40 group-hover:text-[#ff4f70] dark:border-slate-700 dark:bg-slate-800 dark:text-slate-200"
              >
                {service}
              </span>
            ))}
          </div>
        )}

        {post.badges && post.badges.length > 0 && (
          <div className="flex flex-wrap gap-2 text-[0.65rem] uppercase tracking-[0.25em] text-slate-400 dark:text-slate-500">
            {post.badges.map((badge) => (
              <span key={badge}>{badge}</span>
            ))}
          </div>
        )}

        <button className="mt-auto w-full rounded-full bg-[#ff4f70] px-4 py-2 text-sm font-semibold text-white shadow transition hover:bg-[#111827] focus:outline-none focus:ring-2 focus:ring-slate-800/20 dark:bg-slate-800 dark:hover:bg-slate-700">
          View profile
        </button>
      </div>
    </article>
  );
};

export default ProfileCard;

