import { useEffect, useState } from 'react';
import { useRouter } from 'next/router';

export function useAuth(redirectTo = '/login') {
  const router = useRouter();
  const [user, setUser] = useState(null);

  useEffect(() => {
    const fetchUser = async () => {
      try {
        const response = await fetch('/api/auth/me', {
          method: 'GET',
          credentials: 'include', // ارسال کوکی‌ها
        });

        if (response.ok) {
          const data = await response.json();
          setUser(data.user);
        } else {
          setUser(null);
          if (redirectTo) router.push(redirectTo);
        }
      } catch (error) {
        setUser(null);
        if (redirectTo) router.push(redirectTo);
      }
    };

    fetchUser();
  }, []);

  return { user };
}

export function logout() {
  document.cookie = "token=; path=/; expires=Thu, 01 Jan 1970 00:00:00 UTC;";
  localStorage.removeItem('user');
  window.location.href = '/login';
}
