/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        'byui': {
          'blue': '#003DA5',
          'gold': '#FFD700',
          'dark': '#333333',
          'light-gray': '#F5F5F5',
          'medium-gray': '#666666',
        }
      }
    },
  },
  plugins: [],
} 