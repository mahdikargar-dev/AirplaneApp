import Header from "./Header";
import '../globals.css';
import RouteSearch from "@/app/RouteSearch";

export  function Home() {
  return (
    <div className="min-h-screen bg-gray-100">
      <Header />
      <div className="container mx-auto px-6 py-8">
        <h1 className="text-3xl font-bold text-center text-gray-800">Welcome to Airplane App</h1>
        <p className="text-center text-gray-600 mt-4">Please login or register to continue</p>

      <RouteSearch/>
      </div>
    </div>
  );
}
