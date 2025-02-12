import axios from "axios";
import Cookies from "cookies";

export default async function handler(req, res) {
  if (req.method === "GET") {
    const cookies = new Cookies(req, res);
    const token = cookies.get("token");

    if (!token) {
      return res.status(401).json({ message: "Unauthorized" });
    }

    try {
      const response = await axios.get("http://localhost:8080/api/auth/me", {
        headers: { Authorization: `Bearer ${token}` },
      });

      res.status(200).json(response.data);
    } catch (error) {
      res.status(401).json({ message: "Invalid token" });
    }
  } else {
    res.status(405).json({ message: "Method Not Allowed" });
  }
}
