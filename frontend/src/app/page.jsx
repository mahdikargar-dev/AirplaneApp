"use client";

import RouteSearch from "@/app/RouteSearch";
import Header from "@/app/Header";

export default function Home() {
  return (
    <main className="min-h-screen p-4">
      <Header />
      <RouteSearch />
    </main>
  );
}
