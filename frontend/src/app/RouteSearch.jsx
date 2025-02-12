"use client";
import { useState, useEffect } from "react";
import { useRouter } from "next/navigation";
import { Search, Loader2, ArrowRight } from "lucide-react";
import { ChevronDown } from 'lucide-react';
const API_BASE_URL = "http://localhost:8080/api/flights";
import jalaali from "jalaali-js";

const RouteSearch = () => {
  const [origins, setOrigins] = useState([]);
  const [destinations, setDestinations] = useState([]);
  const [selectedOrigin, setSelectedOrigin] = useState("");
  const [selectedDestination, setSelectedDestination] = useState("");
  const [flights, setFlights] = useState([]);
  const [loading, setLoading] = useState(false);
  const router = useRouter();

  const formatDateTime = (dateTimeStr) => {
    const date = new Date(dateTimeStr);
    const jDate = jalaali.toJalaali(date.getFullYear(), date.getMonth() + 1, date.getDate());

    return `${jDate.jy}/${jDate.jm.toString().padStart(2, "0")}/${jDate.jd.toString().padStart(2, "0")} - ${date.getHours()}:${date.getMinutes().toString().padStart(2, "0")}`;
  };

  useEffect(() => {
    fetchOrigins();
  }, []);

  useEffect(() => {
    if (selectedOrigin) {
      fetchDestinations(selectedOrigin);
    } else {
      setDestinations([]);
    }
    setSelectedDestination("");
  }, [selectedOrigin]);

  const fetchOrigins = async () => {
    try {
      const response = await fetch(`${API_BASE_URL}/routes/origins`);
      if (response.ok) {
        const data = await response.json();
        setOrigins(data);
      }
    } catch (error) {
      console.error("Error fetching origins:", error);
    }
  };
  const fetchDestinations = async (origin) => {
    try {
      const response = await fetch(`${API_BASE_URL}/routes/destinations?originCode=${origin}`);
      if (response.ok) {
        const data = await response.json();
        setDestinations(data);
      }
    } catch (error) {
      console.error("Error fetching destinations:", error);
    }
  };

  const handleSearch = async () => {
    if (!selectedOrigin || !selectedDestination) return;
    setLoading(true);
    try {
      const response = await fetch(
        `${API_BASE_URL}/search?departureCode=${selectedOrigin}&arrivalCity=${selectedDestination}`
      );
      if (response.ok) {
        const data = await response.json();
        setFlights(data);
      }
    } catch (error) {
      console.error("Error searching flights:", error);
    } finally {
      setLoading(false);
    }
  };
  const formatDuration = (minutes) => {
    const hours = Math.floor(minutes / 60);
    const remainingMinutes = minutes % 60;
    return `${hours}h ${remainingMinutes}m`;
  };


  return (
    <div className="min-h-screen bg-gray-50 py-12">
      <div className="max-w-4xl mx-auto px-4">
        <div className="bg-white rounded-2xl shadow-xl p-8">
          <h2 className="text-2xl font-bold text-gray-800 mb-8">Find Your Flight</h2>

          <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-8">
            <div className="relative">
              <label className="block text-sm font-medium text-gray-700 mb-2">Origin</label>
              <select
                className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg appearance-none focus:ring-2 focus:ring-blue-600 focus:border-transparent transition-all"
                value={selectedOrigin}
                onChange={(e) => setSelectedOrigin(e.target.value)}
              >
                <option value="">Select Origin</option>
                {origins.map((origin) => (
                  <option key={origin} value={origin}>{origin}</option>
                ))}
              </select>
              <ChevronDown className="absolute right-3 top-11 w-5 h-5 text-gray-400 pointer-events-none" />
            </div>

            <div className="relative">
              <label className="block text-sm font-medium text-gray-700 mb-2">Destination</label>
              <select
                className="w-full p-3 bg-gray-50 border border-gray-200 rounded-lg appearance-none focus:ring-2 focus:ring-blue-600 focus:border-transparent transition-all"
                value={selectedDestination}
                onChange={(e) => setSelectedDestination(e.target.value)}
                disabled={!selectedOrigin}
              >
                <option value="">Select Destination</option>
                {destinations.map((destination) => (
                  <option key={destination} value={destination}>{destination}</option>
                ))}
              </select>
              <ChevronDown className="absolute right-3 top-11 w-5 h-5 text-gray-400 pointer-events-none" />
            </div>
          </div>

          <button
            className="w-full bg-gradient-to-r from-blue-600 to-blue-700 text-white py-3 px-6 rounded-lg hover:from-blue-700 hover:to-blue-800 transition-all shadow-md hover:shadow-lg flex items-center justify-center gap-2"
            onClick={handleSearch}
            disabled={!selectedOrigin || !selectedDestination || loading}
          >
            {loading ? <Loader2 className="animate-spin h-5 w-5" /> : <Search className="w-5 h-5" />}
            {loading ? "Searching..." : "Search Flights"}
          </button>

          <div className="mt-8 space-y-4">
            {flights.map((flight) => (
              <div key={flight.flightId} className="bg-gray-50 rounded-xl p-6 hover:shadow-lg transition-all">
                <div className="grid grid-cols-1 md:grid-cols-3 gap-4 items-center">
                  <div>
                    <p className="text-sm text-gray-600">Flight</p>
                    <p className="font-semibold text-lg">{flight.flightNumber}</p>
                  </div>
                  <div className="text-center">
                    <div className="flex items-center justify-center gap-4">
                      <div className="text-right">
                        <p className="font-semibold">{formatDateTime(flight.departureTime)}</p>
                        <p className="text-sm text-gray-600">{formatDateTime(flight.departureTime)}</p>
                      </div>

                      <div className="text-left">
                        <p className="font-semibold">{formatDateTime(flight.arrivalTime)}</p>
                        <p className="text-sm text-gray-600">{formatDateTime(flight.arrivalTime)}</p>
                      </div>
                    </div>
                    <p className="text-sm text-gray-600 mt-2">{formatDateTime(flight.duration)}</p>
                  </div>
                  <div className="text-right">
                    <p className="text-sm text-gray-600">Price</p>
                    <p className="font-bold text-xl text-blue-600">{flight.basePrice.toLocaleString()} تومان</p>
                  </div>
                </div>
                <div className="mt-4 pt-4 border-t border-gray-200">
                  <button
                    className="w-full bg-green-600 text-white py-2 px-4 rounded-lg hover:bg-green-700 transition-colors flex items-center justify-center gap-2"
                    onClick={() => {
                      localStorage.setItem("selectedFlight", JSON.stringify(flight));
                      router.push(`/route/${flight.flightId}`);
                    }}
                  >
                    <ArrowRight className="w-4 h-4" />
                    Select Flight
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default RouteSearch;