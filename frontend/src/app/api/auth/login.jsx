export const login = async (username, password) => {
  try {
    const response = await fetch("http://localhost:8080/api/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    });

    if (!response.ok) {
      throw new Error("Page failed");
    }

    const data = await response.json();
    localStorage.setItem("token", data.token); // ذخیره توکن در localStorage
    return data; // اطلاعات کاربر برمی‌گردونه
  } catch (error) {
    console.error("Page error:", error);
    throw error;
  }
};

export default login;
