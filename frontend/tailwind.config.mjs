/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./src/**/*.{js,ts,jsx,tsx}",       // تمام فایل‌های src
    "./src/app/**/*.{js,ts,jsx,tsx}", // صفحات
    "./src/components/**/*.{js,ts,jsx,tsx}", // کامپوننت‌ها
  ],
  theme: {
    extend: {},
  },
  plugins: [],
};
