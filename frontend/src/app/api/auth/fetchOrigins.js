export const fetchOrigins = async () => {
  try {
    const response = await fetch(`${API_BASE_URL}/origins`);
    if (!response.ok) {
      throw new Error("Failed to fetch origins");
    }
    const data = await response.json();
    setOrigins(data);
  } catch (error) {
    console.error("Error fetching origins:", error);
  }
};

const fetchDestinations = async (origin) => {
  try {
    const response = await fetch(`${API_BASE_URL}/destinations?origin=${origin}`);
    if (!response.ok) {
      throw new Error("Failed to fetch destinations");
    }
    const data = await response.json();
    setDestinations(data);
  } catch (error) {
    console.error("Error fetching destinations:", error);
  }
};

const handleSearch = async () => {
  if (!selectedOrigin || !selectedDestination) return;

  setLoading(true);
  try {
    const response = await fetch(
      `${API_BASE_URL}/search?originCity=${selectedOrigin}&destinationCity=${selectedDestination}`
    );
    if (!response.ok) {
      throw new Error("Failed to search routes");
    }
    const data = await response.json();
    setRoutes(data);
  } catch (error) {
    console.error("Error searching routes:", error);
  } finally {
    setLoading(false);
  }
};
