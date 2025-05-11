import React, {useEffect, useState} from "react";

const App = () => {
  const [mode, setMode] = useState("LD");
  const [search, setSearch] = useState("");
  const [data, setData] = useState([]);
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  useEffect(() => {
      if (mode === "LD") {
          const fetchData = async () => {
              try {
                  const response = await fetch(`http://localhost:8080/api/words/ld/spellcheck?word=${search}&maxDistance=10&limit=10`);
                  const result = await response.json();
                  if(result["isCorrect"]) {
                      const res = await   fetch(`http://localhost:8080/api/words/ld/autocomplete?perfix=${search}&maxDistance=2&limit=10`);
                      const r = await res.json();
                      setData(r["suggestions"]);
                  } else {
                      setData([]);
                  }

              } catch (error) {
                  console.error("Error fetching data:", error);
              }
          };
          fetchData();
      } else {
            const fetchData = async () => {
                try {
                    const response = await fetch(`http://localhost:8080/api/words/tst/spellcheck?word=${search}&limit=15`);
                    const result = await response.json();
                    if(result["isCorrect"]) {
                        const res = await   fetch(`http://localhost:8080/api/words/tst/autocomplete?prefix=${search}&limit=10`);
                        const r = await res.json();
                        setData(r["suggestions"]);
                    } else {
                        setData([]);
                    }

                } catch (error) {
                    console.error("Error fetching data:", error);
                }
            };
            fetchData();
      }
  }, [search]);

  useEffect(() => {
      setSearch("");
        setData([]);
  }, [mode]);
    return (
        <div
            style={{
                backgroundColor: "#d1e8e9",
                height: "100vh",
                display: "flex",
                flexDirection: "column",
                alignItems: "center",
                justifyContent: "center",
                position: "relative",
            }}
        >
            {/* Menu */}
            <div
                style={{
                    position: "absolute",
                    top: "20px",
                    left: "20px",
                    zIndex: 10,
                    border: "2px solid #00bcd4", // Added border
                    borderRadius: "10px", // Rounded corners
                    padding: "10px", // Added padding
                    backgroundColor: "white", // Background for better visibility
                }}
            >
                <div
                    style={{
                        color: "#00bcd4",
                        fontWeight: "bold",
                        marginBottom: "12px",
                        cursor: "pointer",
                        fontSize: "32px",
                    }}
                    onClick={() => setIsMenuOpen(!isMenuOpen)}
                >
                    Menu
                </div>
                {isMenuOpen && (
                    <div>
                        <div
                            style={{
                                color: mode === "LD" ? "#00bcd4" : "gray",
                                fontWeight: mode === "LD" ? "bold" : "normal",
                                cursor: "pointer",
                                marginBottom: "12px",
                                fontSize: "28px",
                            }}
                            onClick={() => {
                                setMode("LD");
                                setIsMenuOpen(false);
                            }}
                        >
                            LD
                        </div>
                        <div
                            style={{
                                color: mode === "TST" ? "#00bcd4" : "gray",
                                fontWeight: mode === "TST" ? "bold" : "normal",
                                cursor: "pointer",
                                fontSize: "28px",
                            }}
                            onClick={() => {
                                setMode("TST");
                                setIsMenuOpen(false);
                            }}
                        >
                            TST
                        </div>
                    </div>
                )}
            </div>

            {/* Search Box */}
            <div
                style={{
                    background: "white",
                    borderRadius: "10px",
                    padding: "30px",
                    width: "800px",
                    height: "auto",
                    boxShadow: "0 4px 10px rgba(0,0,0,0.1)",
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    border: "2px solid #00bcd4", // Added border
                }}
            >
                <input
                    type="text"
                    placeholder="Search..."
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                    style={{
                        width: "100%",
                        padding: "15px",
                        fontSize: "24px",
                        fontFamily: "Arial",
                        border: "none",
                        borderBottom: "2px solid #d1e8e9",
                        outline: "none",
                        marginBottom: "10px",
                    }}
                />
                <ul
                    style={{
                        listStyle: "none",
                        padding: 0,
                        width: "100%",
                        border: "1px solid #00bcd4", // Added border for results
                        borderRadius: "5px", // Rounded corners for results
                    }}
                >
                    {data.map((item, index) => (
                        <li
                            key={index}
                            style={{
                                padding: "8px",
                                backgroundColor: index === 0 ? "#e0f7fa" : "white",
                                color: "#00bcd4",
                                fontSize: "20px",
                                fontFamily: "Arial",
                                cursor: "pointer",
                                borderBottom: index !== data.length - 1 ? "1px solid #d1e8e9" : "none", // Divider between items
                            }}
                        >
                            {item}
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default App;
