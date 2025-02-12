"use client";

import { useState, useEffect } from "react";
import Link from "next/link";
import { useRouter } from "next/navigation";

const Header = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const router = useRouter();

  // تابع بررسی وضعیت لاگین
  const checkLoginStatus = () => {
    const token = localStorage.getItem("token");
    setIsLoggedIn(!!token);
  };

  // اجرا هنگام اولین رندر
  useEffect(() => {
    checkLoginStatus();

    // گوش دادن به تغییرات localStorage
    const handleStorageChange = () => {
      checkLoginStatus();
    };

    window.addEventListener("storage", handleStorageChange);

    return () => {
      window.removeEventListener("storage", handleStorageChange);
    };
  }, []);

  // تابع خروج از حساب
  const handleLogout = () => {
    localStorage.removeItem("token");
    checkLoginStatus(); // وضعیت را دوباره بررسی کن
    router.push("/login");
  };

  return (
    <header className="bg-white shadow-md">
      <nav className="container mx-auto px-6 py-4">
        <div className="flex justify-between items-center">
          <div className="text-xl font-bold text-gray-800">Airplane App</div>
          <div className="space-x-4">
            {!isLoggedIn ? (
              <>
                <Link href="/login" className="text-blue-600 hover:text-blue-800">Login</Link>
                <Link href="/register" className="bg-blue-600 text-white px-4 py-2 rounded hover:bg-blue-700">Register</Link>
              </>
            ) : (
              <button
                onClick={handleLogout}
                className="bg-red-600 text-white px-4 py-2 rounded hover:bg-red-700"
              >
                Logout
              </button>
            )}
          </div>
        </div>
      </nav>
    </header>
  );
};

export default Header;
