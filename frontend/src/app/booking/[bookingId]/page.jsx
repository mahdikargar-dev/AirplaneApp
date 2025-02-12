"use client";
import { useState, useEffect } from "react";
import { useParams } from "next/navigation";
import axios from "axios";
import { Check, Printer, Loader2, User, Plane } from "lucide-react";

const BookingConfirmation = () => {
  const params = useParams();
  const [booking, setBooking] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const bookingId = params.bookingId;

  useEffect(() => {
    const fetchBookingDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/flights/bookings/${bookingId}`);
        setBooking({
          ...response.data,
          flightNumber: response.data.flightNumber,
          departureTime: response.data.departureTime || response.data.scheduledDepartureTime,
          arrivalTime: response.data.arrivalTime || response.data.scheduledArrivalTime,
          price: response.data.price
        });
      } catch (err) {
        setError(err.response?.data || "Error retrieving booking information");
      } finally {
        setLoading(false);
      }
    };

    if (bookingId) {
      fetchBookingDetails();
    }
  }, [bookingId]);

  const handlePrint = () => window.print();

  const formatPrice = (price) => {
    if (price === null || price === undefined) return "Price not available";
    if (price === 0) return "$0";
    return `$${price.toLocaleString()}`;
  };

  if (loading) return (
    <div className="flex items-center justify-center min-h-screen">
      <Loader2 className="w-8 h-8 animate-spin text-blue-600" />
    </div>
  );

  if (error) return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="bg-red-50 text-red-700 p-4 rounded-lg">{error}</div>
    </div>
  );

  if (!booking) return (
    <div className="flex items-center justify-center min-h-screen">
      <div className="text-gray-600">Booking information not found</div>
    </div>
  );

  return (
    <div className="min-h-screen bg-gray-50 py-12">
      <div className="max-w-4xl mx-auto px-4">
        <div className="bg-white rounded-2xl shadow-xl overflow-hidden">
          <div className="bg-gradient-to-r from-blue-600 to-blue-700 p-6">
            <div className="flex items-center justify-center">
              <div className="bg-white rounded-full p-3 shadow-lg">
                <Check className="w-8 h-8 text-blue-600" />
              </div>
            </div>
            <h1 className="text-3xl font-bold text-center text-white mt-4">Booking Confirmed!</h1>
            <p className="text-center text-blue-100 mt-2">Your flight has been successfully booked</p>
          </div>

          <div className="p-8">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
              <div className="bg-gray-50 rounded-xl p-6 hover:shadow-md transition-shadow">
                <h2 className="text-lg font-semibold text-gray-800 flex items-center gap-2">
                  <User className="w-5 h-5 text-blue-600" />
                  Passenger Details
                </h2>
                <div className="mt-4 space-y-3">
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Ticket Number</span>
                    <span className="font-medium">{booking.ticketNumber}</span>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Name</span>
                    <span className="font-medium">{`${booking.firstName} ${booking.lastName}`}</span>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">National ID</span>
                    <span className="font-medium">{booking.nationalId}</span>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Phone</span>
                    <span className="font-medium">{booking.phoneNumber}</span>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Seat</span>
                    <span className="font-medium">{booking.seatNumber}</span>
                  </div>
                </div>
              </div>

              <div className="bg-gray-50 rounded-xl p-6 hover:shadow-md transition-shadow">
                <h2 className="text-lg font-semibold text-gray-800 flex items-center gap-2">
                  <Plane className="w-5 h-5 text-blue-600" />
                  Flight Details
                </h2>
                <div className="mt-4 space-y-3">
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Flight Number</span>
                    <span className="font-medium">{booking.flightNumber || "N/A"}</span>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Departure</span>
                    <span className="font-medium">
                      {booking.departureTime ? new Date(booking.departureTime).toLocaleString() : "N/A"}
                    </span>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Arrival</span>
                    <span className="font-medium">
                      {booking.arrivalTime ? new Date(booking.arrivalTime).toLocaleString() : "N/A"}
                    </span>
                  </div>
                  <div className="flex items-center justify-between text-sm">
                    <span className="text-gray-600">Price</span>
                    <span className="font-medium text-green-600">{formatPrice(booking.price)}</span>
                  </div>
                </div>
              </div>
            </div>

            <div className="flex justify-center space-x-4 mt-8">
              <button
                onClick={handlePrint}
                className="flex items-center gap-2 px-6 py-3 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors shadow-md hover:shadow-lg"
              >
                <Printer className="w-5 h-5" />
                Print Ticket
              </button>
              <button
                onClick={() => window.location.href = "/"}
                className="px-6 py-3 border border-gray-200 rounded-lg hover:bg-gray-50 transition-colors"
              >
                Return Home
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default BookingConfirmation;