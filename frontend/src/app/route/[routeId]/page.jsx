"use client";
import { useState, useEffect } from "react";
import { useParams, useRouter } from "next/navigation";
import { Loader2, Check } from "lucide-react";
import axios from "axios";
import * as response from "autoprefixer";
import api from "@/utils/axiosInstance";

const API_BASE_URL = "http://localhost:8080/api";

export default function BookingPage() {
  const params = useParams();
  const router = useRouter();
  const [flightDetails, setFlightDetails] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [step, setStep] = useState(1);
  const [seats, setSeats] = useState([]);
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    nationalId: "",
    phoneNumber: "",
    selectedSeat: null
  });

  useEffect(() => {
    const fetchSeats = async () => {
      try {
        const response = await axios.get(`${API_BASE_URL}/flights/${params.routeId}`);
        const reservedSeatsResponse = await axios.get(`http://localhost:8080/api/seats/reserved/${params.routeId}`);
        const reservedSeats = reservedSeatsResponse.data.map(seat => seat.seatNumber);
        const capacity = response.data.aircraft.capacity;
        const seatsData = Array.from({ length: capacity }, (_, index) => {
          const seatNumber = `${Math.floor(index / 6) + 1}${String.fromCharCode(65 + (index % 6))}`;
          return { id: index + 1, number: seatNumber, isAvailable: !reservedSeats.includes(seatNumber) };
        });
        setSeats(seatsData);
      } catch (error) {
        console.error("Error fetching seats:", error);
        setError("Failed to fetch seat data");
      }
    };
    fetchSeats();
  }, [params.routeId]);

  useEffect(() => {
    const fetchFlightDetails = async () => {
      if (!params.routeId) {
        setError("No route ID provided");
        setLoading(false);
        return;
      }
      try {
        const response = await axios.get(`${API_BASE_URL}/flights/${params.routeId}`);
        console.log("response", response.data);
        const flightData = {
          ...response.data,
          flightNumber: response.data.flightNumber,
          price: response.data.basePrice ? Number(response.data.basePrice) : 0, // مقدار basePrice را جایگزین price کن
          scheduledDepartureTime: response.data.scheduledDepartureTime,
          scheduledArrivalTime: response.data.scheduledArrivalTime
        };
        console.log("flight data ", flightData);
        setFlightDetails(flightData);
      } catch (err) {
        setError(err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchFlightDetails();
  }, [params.routeId]);

  const handleInputChange = (e) => setFormData({ ...formData, [e.target.name]: e.target.value });
  const handleSeatSelect = (seat) => {
    if (seat.isAvailable) {
      setFormData(prev => ({
        ...prev,
        selectedSeat: seat.number
      }));
    }
  };
  const checkLoginStatus = () => {
    const token = localStorage.getItem("token");
    return !!token; // Returns true if token exists, false otherwise
  };

  useEffect(() => {
    console.log("routeId:", params.routeId);
  }, [params.routeId]);

  const handleSubmit = async () => {
    if (step === 3) {
      const isLoggedIn = checkLoginStatus();
      if (!isLoggedIn) {
        // Save current booking data to localStorage before redirecting
        localStorage.setItem('pendingBooking', JSON.stringify({
          formData,
          routeId: params.routeId
        }));
        alert("خطا در تأیید رزرو.لطفا به حساب کاربری  خود وارد شوید");
        router.push('/login');
        return;
      }
    }


    if (step === 1 && (!formData.firstName || !formData.lastName || !formData.nationalId || !formData.phoneNumber)) {
      alert("Please fill in all fields");
      return;
    }
    if (step === 2 && !formData.selectedSeat) {
      alert("Please select a seat");
      return;
    }

    if (step === 3) {
      try {
        const token = localStorage.getItem("token");
        if (!token) {
          throw new Error("No authentication token found");
        }
        // First, book the seat
        const seatResponse = await api.post(`/seats/book`, {
          flightId: params.routeId,
          seatNumber: formData.selectedSeat
        });
        alert("رزرو شما تأیید شد!");
        const bookingData = {
          firstName: formData.firstName,
          lastName: formData.lastName,
          nationalId: formData.nationalId,
          phoneNumber: formData.phoneNumber,
          seatNumber: formData.selectedSeat,
          seatId: seatResponse.data.id,
          flightNumber: flightDetails.flightNumber,
          bookingTime: new Date().toISOString(),
          price: flightDetails.price,
          ticketNumber: null,
          departureTime: flightDetails.scheduledDepartureTime,
          arrivalTime: flightDetails.scheduledArrivalTime
        };
        console.log(bookingData);
        if (!flightDetails.flightNumber) {
          console.error("Invalid flight number");
          setError("Invalid flight number");
          return;
        }
        console.log(bookingData);

        const bookingResponse = await api.post(`/flights/bookings`, bookingData, );

        if (bookingResponse.data.bookingId) {
          router.push(`/booking/${bookingResponse.data.bookingId}`);
        } else {
          throw new Error("No booking ID received");
        }
      } catch (err) {
        console.error("Booking error:", err.response?.data || err);
        setError(err.response?.data || err.message || "Failed to create booking");
        alert("خطا در تأیید رزرو. لطفاً دوباره تلاش کنید.");

        return; // Don't proceed to next step if there's an error
      }
    }
    setStep(step + 1);
  };
  if (loading) return <div className="flex items-center justify-center min-h-screen"><Loader2
    className="w-8 h-8 animate-spin text-blue-600" /></div>;
  if (error) return <div className="flex items-center justify-center min-h-screen">
    <div className="bg-red-50 text-red-700 p-4 rounded-lg">{error}</div>
  </div>;

  return (
    <div className="max-w-4xl mx-auto p-6 bg-white rounded-lg shadow-md">
      <h1 className="text-2xl font-bold">Book Your Flight</h1>
      {step === 1 && <PassengerForm formData={formData} onChange={handleInputChange} />}
      {step === 2 && <SeatSelection seats={seats} selectedSeat={formData.selectedSeat} onSelect={handleSeatSelect} />}
      {step === 3 && <BookingSummary formData={formData} flightDetails={flightDetails} />}
      <div className="flex justify-between mt-8">
        {step > 1 && <button onClick={() => setStep(step - 1)}
                             className="px-4 py-2 border rounded-md hover:bg-gray-50">Back</button>}
        <button onClick={handleSubmit}
                className="ml-auto px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700">
          {step === 3 ? "Confirm Booking" : "Next"}
        </button>
      </div>
    </div>
  );
}

const PassengerForm = ({ formData, onChange }) => (
  <div className="space-y-4">
    <h2 className="text-xl font-semibold">Passenger Information</h2>
    {["firstName", "lastName", "nationalId", "phoneNumber"].map((field) => (
      <div key={field}>
        <label
          className="block text-sm font-medium text-gray-700 mb-1">{field.replace(/([A-Z])/g, " $1").trim()}</label>
        <input type="text" name={field} value={formData[field]} onChange={onChange}
               className="w-full p-2 border rounded-md" required />
      </div>
    ))}
  </div>
);

const SeatSelection = ({ seats, selectedSeat, onSelect }) => (
  <div className="space-y-4">
    <h2 className="text-xl font-semibold">Select Your Seat</h2>
    <div className="grid grid-cols-6 gap-2 max-w-md mx-auto">
      {seats.map((seat) => (
        <button key={seat.id} onClick={() => onSelect(seat)} disabled={!seat.isAvailable || selectedSeat}
                className={`p-2 rounded-md text-center ${selectedSeat === seat.number ? "bg-blue-600 text-white" : seat.isAvailable ? "bg-white border hover:bg-gray-50" : "bg-gray-200 cursor-not-allowed"}`}>{seat.number}</button>
      ))}
    </div>
  </div>
);

const BookingSummary = ({ formData, flightDetails }) => (
  <div className="space-y-4">
    <h2 className="text-xl font-semibold">Confirm Your Booking</h2>
    <div className="bg-gray-50 p-4 rounded-lg">
      <h3 className="font-medium mb-2">Passenger Details</h3>
      {Object.entries(formData).map(([key, value]) => <p key={key}>{key}: {value}</p>)}
    </div>
  </div>
);
