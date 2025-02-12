import axios from 'axios';

export default async function handler(req, res) {
  if (req.method === "POST") {
    try {
      const response = await axios.post('http://localhost:8080/api/auth/register', req.body);
      if (response.status === 201) {
        res.status(200).json(response.data);
      } else {
        res.status(400).json({ message: "Registration failed" });
      }
    } catch (error) {
      res.status(400).json({ message: error.response?.data?.message || "Registration failed" });
    }
  } else {
    res.status(405).json({ message: "Method Not Allowed" });
  }
}