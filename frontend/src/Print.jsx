"use client"
import { useState, useEffect } from "react";
import { useParams } from "next/navigation";
import axios from "axios";
import { Check, Printer } from "lucide-react";

const BookingConfirmation = () => {
  const params = useParams();
  const [flight, setFlight] = useState(null);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchBookingDetails = async () => {
      try {
        const response = await axios.get(`http://localhost:8080/api/flights/${params.routeId}`);
        const selectedFlight = localStorage.getItem("selectedFlight");
        const flightData = {
          ...response.data,
          ...(selectedFlight ? JSON.parse(selectedFlight) : {})
        };
        setFlight(flightData);
      } catch (err) {
        setError(err.response?.data?.message || "Failed to fetch flight details");
      } finally {
        setLoading(false);
      }
    };

    if (params.routeId) {
      fetchBookingDetails();
    }
  }, [params.routeId]);

  const handlePrint = () => {
    window.print();
  };

  if (loading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
      </div>
    );
  }

  if (error) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="bg-red-50 text-red-700 p-4 rounded-lg">{error}</div>
      </div>
    );
  }

  if (!flight) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <div className="text-gray-600">No flight information found</div>
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto p-6">
      <div className="bg-white rounded-lg shadow-md p-6">
        <div className="flex items-center justify-center mb-8">
          <div className="bg-green-100 rounded-full p-3">
            <Check className="w-8 h-8 text-green-600" />
          </div>
        </div>

        <h1 className="text-2xl font-bold text-center mb-6">پرواز تایید شد!</h1>

        <div className="bg-gray-50 p-6 rounded-lg mb-6">
          <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
            <div>
              <h2 className="text-lg font-semibold mb-4">اطلاعات پرواز</h2>
              <div className="space-y-2">
                <p><span className="font-medium">شماره پرواز:</span> {flight.flightNumber}</p>
                <p><span className="font-medium">زمان پرواز:</span> {new Date(flight.scheduledDepartureTime).toLocaleString("fa-IR")}</p>
                <p><span className="font-medium">زمان رسیدن:</span> {new Date(flight.scheduledArrivalTime).toLocaleString("fa-IR")}</p>
                <p><span className="font-medium">وضعیت:</span> {
                  flight.status === "SCHEDULED" ? "برنامه‌ریزی شده" : flight.status
                }</p>
                <p><span className="font-medium">گیت:</span> {flight.gate || "تعیین نشده"}</p>
              </div>
            </div>

            <div>
              <h2 className="text-lg font-semibold mb-4">اطلاعات قیمت</h2>
              <div className="space-y-2">
                <p><span className="font-medium">قیمت پایه:</span> {flight.basePrice?.toLocaleString()} تومان</p>
                <p><span className="font-medium">توضیحات:</span> {flight.remarks || "ندارد"}</p>
              </div>
            </div>
          </div>
        </div>

        <div className="flex justify-center space-x-4">
          <button
            onClick={handlePrint}
            className="flex items-center gap-2 px-6 py-2 bg-blue-600 text-white rounded-md hover:bg-blue-700"
          >
            <Printer className="w-5 h-5" />
            چاپ بلیط
          </button>
          <button
            onClick={() => window.location.href = "/"}
            className="px-6 py-2 border rounded-md hover:bg-gray-50 mr-4"
          >
            بازگشت به خانه
          </button>
        </div>
      </div>
    </div>
  );
};

export default BookingConfirmation;