/** @type {import('next').NextConfig} */
const nextConfig = {
  reactStrictMode: true,
  experimental: {
    appDir: true, // اطمینان حاصل کنید که فعال است
  },
  env: {
    NEXT_PUBLIC_API_URL: "https://backend-url.onrender.com", // آدرس بک‌اند
  },
};

export default nextConfig;
