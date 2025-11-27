import { ChangeEvent, useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const API_BASE_URL = 'http://localhost:8082';

const inputClass =
  "w-full rounded-2xl border border-slate-200 bg-white/95 px-4 py-2.5 text-sm text-slate-700 shadow-sm transition focus:border-[#ff4f70] focus:outline-none focus:ring-2 focus:ring-[#ff4f70]/20 dark:border-slate-700 dark:bg-slate-900/80 dark:text-slate-100";
const selectClass = inputClass + " pr-10 appearance-none cursor-pointer";
const textareaClass = inputClass + " min-h-[110px] resize-y";
const subtleLabel = "text-[11px] font-semibold tracking-[0.2em] text-slate-500 uppercase";

type ContactMethod = 'phone' | 'both' | 'email';
type MultiSelectKey = 'services' | 'attention' | 'placeOfService';

const fetchOptions = async (path: string): Promise<string[]> => {
  const response = await fetch(`${API_BASE_URL}${path}`);
  if (!response.ok) {
    throw new Error(`Failed to fetch ${path}`);
  }
  const data = await response.json();
  return Array.isArray(data) ? data : [];
};

interface FormState {
  name: string;
  state: string;
  district: string;
  category: string;
  city: string;
  address: string;
  postalcode: string;
  age: string;
  nickname: string;
  title: string;
  text: string;
  ethnicity: string;
  nationality: string;
  breast: string;
  hair: string;
  bodytype: string;
  services: string[];
  attention: string[];
  placeOfService: string[];
  email: string;
  phone: string;
  whatsapp: boolean;
  contactMethod: ContactMethod;
  agreeTerms: boolean;
  agreeSensitive: boolean;
  imageFiles: File[]; // ✅ local files instead of URLs
}

const escapeHTML = (s: string) =>
  String(s || '')
    .replace(/&/g, '&amp;')
    .replace(/</g, '&lt;')
    .replace(/>/g, '&gt;');

const createInitialFormState = (): FormState => ({
  name: "",
  state: "",
  district: "",
  category: "Call Girls",
  city: "",
  address: "",
  postalcode: "",
  age: "",
  nickname: "",
  title: "",
  text: "",

  ethnicity: "Indian",
  nationality: "Indian",
  breast: "Natural",
  hair: "Brown",
  bodytype: "Slim",

  services: [],
  attention: [],
  placeOfService: [],

  email: "",
  phone: "",
  whatsapp: false,
  contactMethod: "both",

  agreeTerms: false,
  agreeSensitive: false,

  imageFiles: [], // ✅ initially empty
});

export default function AddPost() {
  const { token, isLoggedIn, setRedirectPath } = useAuth();
  const navigate = useNavigate();

  const [formData, setFormData] = useState<FormState>(createInitialFormState);
  const [previewHTML, setPreviewHTML] = useState<string | null>(null);
  const [showLoginPrompt, setShowLoginPrompt] = useState<boolean>(!isLoggedIn);
  const [statesList, setStatesList] = useState<string[]>([]);
  const [districtList, setDistrictList] = useState<string[]>([]);
  const [cityList, setCityList] = useState<string[]>([]);
  const [locationError, setLocationError] = useState<string | null>(null);
  const [submitting, setSubmitting] = useState(false);

  const resetForm = () => {
    setFormData(() => {
      const baseline = createInitialFormState();
      if (statesList.length > 0) {
        baseline.state = statesList[0];
      }
      return baseline;
    });
  };

  useEffect(() => setShowLoginPrompt(!isLoggedIn), [isLoggedIn]);

  useEffect(() => {
    const loadStates = async () => {
      try {
        const stateNames = await fetchOptions('/states');
        setStatesList(stateNames);
        setLocationError(null);
        setFormData((prev) => {
          if (prev.state || stateNames.length === 0) {
            return prev;
          }
          return { ...prev, state: stateNames[0] };
        });
      } catch (error) {
        console.error(error);
        setLocationError('Unable to load states right now.');
      }
    };
    loadStates();
  }, []);

  useEffect(() => {
    if (!formData.state) {
      setDistrictList([]);
      setCityList([]);
      setFormData((prev) => (prev.district || prev.city ? { ...prev, district: "", city: "" } : prev));
      return;
    }
    const loadDistricts = async () => {
      try {
        const data = await fetchOptions(`/districts?state=${encodeURIComponent(formData.state)}`);
        setDistrictList(data);
        setLocationError(null);
        setFormData((prev) => {
          if (data.length === 0) {
            return prev.district || prev.city ? { ...prev, district: "", city: "" } : prev;
          }
          if (prev.district && data.includes(prev.district)) {
            return prev;
          }
          return { ...prev, district: data[0], city: "" };
        });
      } catch (error) {
        console.error(error);
        setLocationError('Unable to load districts.');
        setDistrictList([]);
      }
    };
    loadDistricts();
  }, [formData.state]);

  useEffect(() => {
    if (!formData.district) {
      setCityList([]);
      setFormData((prev) => (prev.city ? { ...prev, city: "" } : prev));
      return;
    }
    const loadCities = async () => {
      try {
        const data = await fetchOptions(`/cities?district=${encodeURIComponent(formData.district)}`);
        setCityList(data);
        setLocationError(null);
        setFormData((prev) => {
          if (data.length === 0) {
            return prev.city ? { ...prev, city: "" } : prev;
          }
          if (prev.city && data.includes(prev.city)) {
            return prev;
          }
          return { ...prev, city: data[0] };
        });
      } catch (error) {
        console.error(error);
        setLocationError('Unable to load cities.');
        setCityList([]);
      }
    };
    loadCities();
  }, [formData.district]);

  const servicesList: string[] = [
    "Oral",
    "Anal",
    "BDSM",
    "Girlfriend experience",
    "Body ejaculation",
    "Erotic massage",
    "Tantric massage",
    "Fetish",
    "French kiss",
    "Role play",
    "Threesome",
    "Sexting",
    "Videocall",
  ];

  const attentionList: string[] = ["Men", "Women", "Couples", "Disabled"];
  const placeList: string[] = ["At home", "Events and parties", "Hotel / Motel", "Outcall", "Clubs"];
  const ethnicityTags: string[] = ["African", "Indian", "Asian", "Arab", "Latin", "Caucasian"];

  function ensureLoggedIn(): boolean {
    if (!isLoggedIn) {
      setShowLoginPrompt(true);
      return false;
    }
    return true;
  }

  const handleChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    if (!ensureLoggedIn()) return;
    const target = e.target as HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement;
    const field = target.name as keyof FormState;
    let nextValue: string | boolean = target.value;
    if (target instanceof HTMLInputElement && target.type === "checkbox") {
      nextValue = target.checked;
    }
    if (field === "state") {
      setFormData((prev) => ({
        ...prev,
        state: nextValue as string,
        district: "",
        city: "",
      }));
      return;
    }
    if (field === "district") {
      setFormData((prev) => ({
        ...prev,
        district: nextValue as string,
        city: "",
      }));
      return;
    }
    setFormData((prev) => ({
      ...prev,
      [field]: nextValue as FormState[typeof field],
    }));
  };

  const toggleArray = (key: MultiSelectKey, val: string) => {
    if (!ensureLoggedIn()) return;
    setFormData((prev) => {
      const list = prev[key];
      const updated = list.includes(val) ? list.filter((item) => item !== val) : [...list, val];
      return { ...prev, [key]: updated };
    });
  };

  const buildPreview = () => {
    if (!ensureLoggedIn()) return;
    const html = `
      <div style="font-family:system-ui, -apple-system, 'Segoe UI', Roboto, 'Helvetica Neue', Arial; font-size:14px; color:#0f172a;">
        <h3 style="font-weight:600; margin-bottom:8px">Preview</h3>
        <div style="margin-bottom:6px"><strong>Title:</strong> ${escapeHTML(formData.title || '-')}</div>
        <div style="margin-bottom:6px"><strong>State:</strong> ${escapeHTML(formData.state || '-')}</div>
        <div style="margin-bottom:6px"><strong>District:</strong> ${escapeHTML(formData.district || '-')}</div>
        <div style="margin-bottom:6px"><strong>City:</strong> ${escapeHTML(formData.city || '-')}</div>
        <div style="margin-bottom:6px"><strong>Age:</strong> ${escapeHTML(formData.age || '-')}</div>
        <div style="margin-bottom:6px"><strong>Services:</strong> ${escapeHTML((formData.services || []).join(', ') || '-')}</div>
        <div style="margin-bottom:6px"><strong>Images selected:</strong> ${formData.imageFiles.length}</div>
        <div style="margin-top:8px">${escapeHTML(formData.text || '-')}</div>
      </div>
    `;
    setPreviewHTML(html);
  };

  const submitPost = async () => {
    if (!ensureLoggedIn()) return;
    if (!formData.agreeTerms) return alert('You must accept Terms & Conditions');
    if (!formData.name.trim()) return alert('Enter your profile name');
    if (!formData.state || !formData.district || !formData.city) return alert('Select your full location');
    if (formData.imageFiles.length === 0) return alert('Add at least one profile image');
    if (!formData.title.trim() || !formData.text.trim()) return alert('Title and description are required');
    if (!formData.age) return alert('Age is required');
    if (formData.contactMethod !== 'email' && !formData.phone.trim()) return alert('Phone number is required for phone/WhatsApp contact');
    if (formData.contactMethod !== 'phone' && !formData.email.trim()) return alert('Email is required for email contact');

    const servicesSummary = formData.services.join(', ');
    const placeSummary = formData.placeOfService.join(', ');

    // ✅ Use FormData for multipart/form-data
    const form = new FormData();
    form.append('name', formData.name.trim());
    form.append('description', formData.text.trim());
    form.append('phone', formData.phone.trim());
    form.append('whatsapp', formData.whatsapp ? formData.phone.trim() : '');
    form.append('state', formData.state);
    form.append('district', formData.district);
    form.append('city', formData.city);
    form.append('category', formData.category);
    form.append('address', formData.address);
    form.append('postalcode', formData.postalcode);
    form.append('age', formData.age);
    form.append('nickname', formData.nickname);
    form.append('title', formData.title);
    form.append('text', formData.text);
    form.append('ethnicity', formData.ethnicity);
    form.append('nationality', formData.nationality);
    form.append('bodytype', formData.bodytype);
    form.append('services', servicesSummary);
    form.append('place', placeSummary);

    // ✅ Attach images (multiple)
    formData.imageFiles.forEach((file) => {
      form.append('images', file); // backend should accept "images"
    });

    try {
      setSubmitting(true);
      const res = await fetch(`${API_BASE_URL}/babies`, {
        method: 'POST',
        headers: {
          Authorization: token ? `Bearer ${token}` : '',
          // ❌ DO NOT set Content-Type, browser will set multipart boundary
        },
        body: form,
      });
      if (!res.ok) throw new Error('Network error');
      await res.json();
      alert('Post submitted successfully');
      resetForm();
      setPreviewHTML(null);
      setDistrictList([]);
      setCityList([]);
    } catch (err) {
      console.error(err);
      alert('Error submitting post');
    } finally {
      setSubmitting(false);
    }
  };

  return (
    <div className="min-h-screen bg-slate-100 px-4 py-12 text-slate-900 transition-colors duration-300 dark:bg-slate-950 dark:text-slate-100">
      <div className="mx-auto max-w-4xl space-y-8">
        <div className="rounded-3xl border border-slate-200 bg-white/95 p-8 shadow-xl shadow-slate-900/5 dark:border-slate-800 dark:bg-slate-900/80">
          <p className="text-xs font-semibold uppercase tracking-[0.35em] text-slate-400">Required fields marked with *</p>

          {/* PAGE 1 */}
          <div className="mt-8 rounded-2xl border border-slate-200/80 bg-white/90 p-6 shadow-sm dark:border-slate-800/70 dark:bg-slate-900/70">
            <p className="text-sm font-semibold uppercase tracking-[0.3em] text-slate-500">Your data — About you</p>

            <div className="grid gap-4 md:grid-cols-2 mt-3">
              <div>
                <label className={subtleLabel}>Profile name *</label>
                <input
                  name="name"
                  className={inputClass}
                  value={formData.name}
                  onChange={handleChange}
                  placeholder="Full display name"
                />
              </div>

              <div>
                <label className={subtleLabel}>Select category *</label>
                <select name="category" value={formData.category} onChange={handleChange} className={selectClass}>
                  <option>Call Girls</option>
                  <option>Massage</option>
                  <option>Other</option>
                </select>
              </div>

              <div>
                <label className={subtleLabel}>State *</label>
                <select
                  name="state"
                  value={formData.state}
                  onChange={handleChange}
                  className={selectClass}
                >
                  <option value="">Choose a state</option>
                  {statesList.map((state) => (
                    <option key={state}>{state}</option>
                  ))}
                </select>
              </div>

              <div>
                <label className={subtleLabel}>District *</label>
                <select
                  name="district"
                  value={formData.district}
                  onChange={handleChange}
                  className={selectClass}
                  disabled={!districtList.length}
                >
                  {districtList.length === 0 && <option value="">Select state first</option>}
                  {districtList.map((district) => (
                    <option key={district}>{district}</option>
                  ))}
                </select>
              </div>

              <div>
                <label className={subtleLabel}>City *</label>
                <select
                  name="city"
                  value={formData.city}
                  onChange={handleChange}
                  className={selectClass}
                  disabled={!cityList.length}
                >
                  {cityList.length === 0 && <option value="">Select district first</option>}
                  {cityList.map((city) => (
                    <option key={city}>{city}</option>
                  ))}
                </select>
              </div>

              <div>
                <label className={subtleLabel}>Address</label>
                <input
                  name="address"
                  className={inputClass}
                  value={formData.address}
                  onChange={handleChange}
                  placeholder="Area / Neighbourhood"
                />
              </div>

              <div>
                <label className={subtleLabel}>Postal code</label>
                <input name="postalcode" className={inputClass} value={formData.postalcode} onChange={handleChange} placeholder="400001" />
              </div>

              <div>
                <label className={subtleLabel}>Age *</label>
                <input name="age" type="number" className={inputClass} value={formData.age} onChange={handleChange} min={18} />
              </div>

              <div>
                <label className={subtleLabel}>Nickname *</label>
                <input name="nickname" className={inputClass} value={formData.nickname} onChange={handleChange} placeholder="Ex: Ava" />
              </div>

              <div className="md:col-span-2">
                <label className={subtleLabel}>Title *</label>
                <input name="title" className={inputClass} value={formData.title} onChange={handleChange} placeholder="Give your ad a good title" />
              </div>

              <div className="md:col-span-2">
                <label className={subtleLabel}>Text *</label>
                <textarea name="text" className={textareaClass} value={formData.text} onChange={handleChange} placeholder="Use this space to describe yourself, your body, skills, availability..."></textarea>
              </div>

              <div className="md:col-span-2">
                <label className={subtleLabel}>Ethnicity</label>
                <div className="flex flex-wrap gap-2 mt-2">
                  {ethnicityTags.map((t) => (
                    <button
                      key={t}
                      type="button"
                      onClick={() => setFormData((p) => ({ ...p, ethnicity: t }))}
                      className={`px-3 py-1 text-sm border rounded-full transition ${
                        formData.ethnicity === t
                          ? 'border-transparent bg-[#ff4f70] text-white shadow'
                          : 'border-slate-200 bg-white text-slate-700 hover:border-[#ff4f70]/60'
                      }`}>
                      {t}
                    </button>
                  ))}
                </div>
              </div>
            </div>

            {locationError && (
              <p className="mt-3 text-xs font-semibold text-red-500">
                {locationError}
              </p>
            )}
          </div>

          {/* PAGE 2 */}
          <div className="mt-6 rounded-2xl border border-slate-200/80 bg-white/90 p-6 shadow-sm dark:border-slate-800/70 dark:bg-slate-900/70">
            <p className="text-sm font-semibold uppercase tracking-[0.3em] text-slate-500">Nationality / Body / Services</p>

            <div className="grid gap-4 md:grid-cols-3 mt-3">
              <div>
                <label className={subtleLabel}>Nationality</label>
                <select name="nationality" value={formData.nationality} onChange={handleChange} className={selectClass}>
                  <option>Indian</option>
                  <option>Other</option>
                </select>
              </div>

              <div>
                <label className={subtleLabel}>Breast</label>
                <select name="breast" value={formData.breast} onChange={handleChange} className={selectClass}>
                  <option>Natural</option>
                  <option>Busty</option>
                </select>
              </div>

              <div>
                <label className={subtleLabel}>Hair</label>
                <select name="hair" value={formData.hair} onChange={handleChange} className={selectClass}>
                  <option>Brown</option>
                  <option>Black</option>
                  <option>Blond</option>
                  <option>Red</option>
                </select>
              </div>

              <div className="md:col-span-3">
                <label className={subtleLabel}>Body type</label>
                <select name="bodytype" value={formData.bodytype} onChange={handleChange} className={selectClass}>
                  <option>Slim</option>
                  <option>Curvy</option>
                  <option>Athletic</option>
                </select>
              </div>

              <div className="md:col-span-3">
                <label className={subtleLabel}>Services</label>
                <div className="flex flex-wrap gap-2 mt-2">
                  {servicesList.map((s) => (
                    <button
                      key={s}
                      type="button"
                      onClick={() => toggleArray('services', s)}
                      className={`px-3 py-1 text-sm border rounded-full transition ${
                        formData.services.includes(s)
                          ? 'border-transparent bg-[#ff4f70] text-white shadow'
                          : 'border-slate-200 bg-white text-slate-700 hover:border-[#ff4f70]/60'
                      }`}>
                      {s}
                    </button>
                  ))}
                </div>
              </div>

              <div className="md:col-span-3">
                <label className={subtleLabel}>Attention to</label>
                <div className="flex flex-wrap gap-2 mt-2">
                  {attentionList.map((a) => (
                    <button
                      key={a}
                      type="button"
                      onClick={() => toggleArray('attention', a)}
                      className={`px-3 py-1 text-sm border rounded-full transition ${
                        formData.attention.includes(a)
                          ? 'border-transparent bg-[#ff4f70] text-white shadow'
                          : 'border-slate-200 bg-white text-slate-700 hover:border-[#ff4f70]/60'
                      }`}>
                      {a}
                    </button>
                  ))}
                </div>
              </div>

              <div className="md:col-span-3">
                <label className={subtleLabel}>Place of service</label>
                <div className="flex flex-wrap gap-2 mt-2">
                  {placeList.map((p) => (
                    <button
                      key={p}
                      type="button"
                      onClick={() => toggleArray('placeOfService', p)}
                      className={`px-3 py-1 text-sm border rounded-full transition ${
                        formData.placeOfService.includes(p)
                          ? 'border-transparent bg-[#ff4f70] text-white shadow'
                          : 'border-slate-200 bg-white text-slate-700 hover:border-[#ff4f70]/60'
                      }`}>
                      {p}
                    </button>
                  ))}
                </div>
              </div>
            </div>
          </div>

          {/* PAGE 3 */}
          <div className="mt-6 rounded-2xl border border-slate-200/80 bg-white/90 p-6 shadow-sm dark:border-slate-800/70 dark:bg-slate-900/70">
            <p className="text-sm font-semibold uppercase tracking-[0.3em] text-slate-500">Contact details</p>

            <div className="mt-3">
              <label className={subtleLabel}>How would you like to be contacted?</label>
              <div className="flex gap-4 mt-2 text-sm">
                <label className="flex items-center gap-2">
                  <input type="radio" name="contactMethod" value="phone" checked={formData.contactMethod === 'phone'} onChange={handleChange} /> Only Phone
                </label>
                <label className="flex items-center gap-2">
                  <input type="radio" name="contactMethod" value="both" checked={formData.contactMethod === 'both'} onChange={handleChange} /> Email and Phone
                </label>
                <label className="flex items-center gap-2">
                  <input type="radio" name="contactMethod" value="email" checked={formData.contactMethod === 'email'} onChange={handleChange} /> Only Email
                </label>
              </div>

              <div className="grid gap-4 md:grid-cols-2 mt-4">
                <div>
                  <label className={subtleLabel}>Email address</label>
                  <input name="email" className={inputClass} value={formData.email} onChange={handleChange} placeholder="you@domain.com" />
                  <p className="text-xs text-slate-400 mt-1">Not visible online</p>
                </div>

                <div>
                  <label className={subtleLabel}>Phone number</label>
                  <input name="phone" className={inputClass} value={formData.phone} onChange={handleChange} placeholder="+91 8XXXXXXXXX" />
                  <label className="flex items-center gap-2 mt-2 text-sm">
                    <input type="checkbox" name="whatsapp" checked={formData.whatsapp} onChange={handleChange} /> WhatsApp Available
                  </label>
                </div>
              </div>
            </div>
          </div>

          {/* PAGE 4 - LEGAL & MEDIA */}
          <div className="mt-6 rounded-2xl border border-slate-200/80 bg-white/90 p-6 shadow-sm dark:border-slate-800/70 dark:bg-slate-900/70">
            <p className="text-sm font-semibold uppercase tracking-[0.3em] text-slate-500">Legal & consent</p>

            <div className="mt-3 text-sm">
              <label className="flex items-start gap-3">
                <input type="checkbox" name="agreeTerms" checked={formData.agreeTerms} onChange={handleChange} />
                <div>
                  I have read the Terms and Conditions of use and Privacy Policy and I hereby authorize the processing of my personal data for the purpose of providing this web service.
                </div>
              </label>

              <label className="flex items-start gap-3 mt-3">
                <input type="checkbox" name="agreeSensitive" checked={formData.agreeSensitive} onChange={handleChange} />
                <div>
                  I authorise the processing of my special categories of personal data (e.g. lifestyle and sexual behavior) for the purpose of publishing an ad on the Website.
                </div>
              </label>

              <p className="text-xs text-slate-500 mt-3">Need help? Contact support Monday–Friday 2pm–8pm</p>
            </div>

            {/* MULTIPLE IMAGES UPLOAD SECTION (FILES) */}
            <div className="mt-6">
              <p className="text-sm font-medium mb-2">
                Profile Images * ({formData.imageFiles.length} added)
              </p>

              {/* File input for local upload */}
              <input
                type="file"
                accept="image/*"
                multiple
                onChange={(e) => {
                  if (!ensureLoggedIn()) return;
                  const files = e.target.files ? Array.from(e.target.files) : [];
                  if (files.length === 0) return;
                  setFormData((prev) => ({
                    ...prev,
                    imageFiles: [...prev.imageFiles, ...files],
                  }));
                }}
                className={`${inputClass} cursor-pointer`}
              />
              <p className="text-xs text-slate-400 mt-1">
                Upload JPEG / PNG images from your phone or computer. You can select multiple.
              </p>

              {/* Display selected images */}
              {formData.imageFiles.length > 0 && (
                <div className="mt-4 grid grid-cols-2 gap-3 md:grid-cols-3">
                  {formData.imageFiles.map((file, index) => (
                    <div
                      key={index}
                      className="group relative overflow-hidden rounded-xl border-2 border-slate-200 bg-slate-50 dark:border-slate-700 dark:bg-slate-800"
                    >
                      <img
                        src={URL.createObjectURL(file)}
                        alt={`Upload ${index + 1}`}
                        className="h-32 w-full object-cover transition group-hover:opacity-75"
                      />
                      <button
                        type="button"
                        onClick={() =>
                          setFormData((prev) => ({
                            ...prev,
                            imageFiles: prev.imageFiles.filter((_, i) => i !== index),
                          }))
                        }
                        className="absolute right-2 top-2 rounded-full bg-red-500 p-1.5 text-white opacity-0 shadow-lg transition hover:bg-red-600 group-hover:opacity-100"
                        title="Remove image"
                      >
                        <svg className="h-4 w-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                          <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M6 18L18 6M6 6l12 12" />
                        </svg>
                      </button>
                      <div className="absolute bottom-0 left-0 right-0 bg-gradient-to-t from-black/60 to-transparent p-2">
                        <p className="truncate text-xs text-white">{file.name}</p>
                      </div>
                    </div>
                  ))}
                </div>
              )}
            </div>
          </div>

          {/* ACTIONS */}
          <div className="mt-6 flex flex-col gap-3 text-sm md:flex-row md:items-center md:justify-between">
            <button
              onClick={buildPreview}
              className="inline-flex items-center justify-center rounded-full border border-slate-300 px-4 py-2 font-semibold text-slate-700 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200"
            >
              Quick preview
            </button>

            <div className="flex flex-wrap gap-3">
              <button
                onClick={() => {
                  setPreviewHTML(null);
                  window.scrollTo({ top: 0, behavior: 'smooth' });
                }}
                className="inline-flex items-center justify-center rounded-full border border-slate-200 px-4 py-2 font-semibold text-slate-500 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-300"
              >
                Reset preview
              </button>
              <button
                onClick={submitPost}
                disabled={submitting}
                className="inline-flex items-center justify-center rounded-full bg-[#ff4f70] px-5 py-2 font-semibold text-white shadow hover:bg-[#f53c6a] focus:outline-none focus:ring-2 focus:ring-[#ff4f70]/40 disabled:cursor-not-allowed disabled:opacity-60"
              >
                {submitting ? 'Publishing…' : 'Publish listing'}
              </button>
            </div>
          </div>

          {previewHTML && (
            <div
              className="mt-4 rounded-2xl border border-[#ff4f70]/30 bg-[#fff5f7] p-4 text-sm text-slate-700 dark:bg-[#2a1b2b] dark:text-slate-200"
              dangerouslySetInnerHTML={{ __html: previewHTML }}
            />
          )}

          {/* LOGIN PROMPT */}
          {showLoginPrompt && (
            <div className="fixed inset-0 z-50 flex items-center justify-center bg-slate-900/80 px-4 backdrop-blur-sm">
              <div className="w-full max-w-sm rounded-2xl border border-slate-200 bg-white/95 p-6 text-center shadow-2xl dark:border-slate-700 dark:bg-slate-900">
                <p className="text-xl font-semibold text-slate-900 dark:text-white">Please log in first</p>
                <p className="mt-2 text-sm text-slate-500 dark:text-slate-300">
                  Sign in to continue filling the Add Post form. We'll bring you right back.
                </p>
                <button
                  onClick={() => {
                    setRedirectPath('/add-post');
                    navigate('/login-signup');
                  }}
                  className="mt-5 inline-flex w-full items-center justify-center rounded-full bg-[#ff4f70] px-5 py-2 text-sm font-semibold text-white transition hover:bg-[#f53c6a]"
                >
                  Go to login
                </button>
                <button
                  onClick={() => navigate(-1)}
                  className="mt-3 inline-flex w-full items-center justify-center rounded-full border border-slate-300 px-5 py-2 text-sm font-semibold text-slate-600 transition hover:border-[#ff4f70] hover:text-[#ff4f70] dark:border-slate-600 dark:text-slate-200"
                >
                  Go back
                </button>
              </div>
            </div>
          )}

        </div>
      </div>
    </div>
  );
}
