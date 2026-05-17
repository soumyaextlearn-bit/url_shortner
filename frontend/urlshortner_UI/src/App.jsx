
import { useState } from "react";
import api from "./services/api";

function App() {

  const [url, setUrl] = useState("");

  const [customCode, setCustomCode] =
      useState("");

  const [expiryMinutes, setExpiryMinutes] =
      useState("");

  const [shortUrl, setShortUrl] =
      useState("");

  const [loading, setLoading] =
      useState(false);

  const [error, setError] =
      useState("");

  async function handleShortenUrl() {

    try {

      setLoading(true);

      setError("");

      setShortUrl("");

      const response =
          await api.post("/shorten", {

            url,

            customCode,

            expiryMinutes:
                expiryMinutes || null
          });

      setShortUrl(
          response.data.shortUrl
      );

    } catch (err) {

      const data = err.response?.data;

      if (typeof data === "string") {

        setError(data);

      } else if (data?.message) {

        setError(data.message);

      } else if (data?.url) {

        setError(data.url);

      }else if(data?.error){

        setError(data.error)
        
      }
       else {

        setError("Something went wrong");
      }

    } finally {

      setLoading(false);
    }
  }

  async function handleCopy() {

    await navigator.clipboard.writeText(shortUrl);

    alert("Copied to clipboard 🚀");
  }

  return (

    <div className="
      min-h-screen
      bg-gradient-to-br
      from-gray-100
      to-gray-200
      flex
      items-center
      justify-center
      px-4
      py-8
    ">

      <div className="
        bg-white
        w-full
        max-w-2xl
        rounded-3xl
        shadow-2xl
        p-6
        sm:p-8
      ">

        <h1 className="
          text-3xl
          sm:text-4xl
          font-bold
          text-center
          mb-2
        ">

          URL Shortener

        </h1>

        <div className="space-y-4">

          <input
              type="text"
              placeholder="Enter long URL"
              value={url}
              onChange={(e) =>
                  setUrl(e.target.value)
              }
              className="
                w-full
                border
                border-gray-300
                rounded-xl
                p-3
                sm:p-4
                text-sm
                sm:text-base
                focus:outline-none
                focus:ring-2
                focus:ring-black
              "
          />

          <div className="
            flex
            flex-col
            sm:flex-row
            gap-4
          ">

            <input
                type="text"
                placeholder="Custom shortcode"
                value={customCode}
                onChange={(e) =>
                    setCustomCode(e.target.value)
                }
                className="
                  w-full
                  border
                  border-gray-300
                  rounded-xl
                  p-3
                  sm:p-4
                  text-sm
                  sm:text-base
                  focus:outline-none
                  focus:ring-2
                  focus:ring-black
                "
            />

            <input
                type="number"
                min="1"
                placeholder="Expiry (mins)"
                value={expiryMinutes}
                onChange={(e) =>
                    setExpiryMinutes(e.target.value)
                }
                className="
                  w-full
                  border
                  border-gray-300
                  rounded-xl
                  p-3
                  sm:p-4
                  text-sm
                  sm:text-base
                  focus:outline-none
                  focus:ring-2
                  focus:ring-black
                "
            />

          </div>

          <button
              onClick={handleShortenUrl}
              disabled={loading}
              className="
                w-full
                bg-black
                text-white
                py-3
                sm:py-4
                rounded-xl
                font-semibold
                text-sm
                sm:text-base
                hover:bg-gray-800
                transition-all
                duration-200
                disabled:opacity-50
              "
          >

            {
              loading
                  ? "Creating..."
                  : "Shorten URL"
            }

          </button>

          {
            shortUrl && (

              <div className="
                bg-green-50
                border
                border-green-200
                p-4
                rounded-2xl
                mt-6
              ">

                <p className="
                  text-sm
                  text-gray-600
                  mb-2
                ">

                  Your shortened URL

                </p>

                <div className="
                  flex
                  flex-col
                  sm:flex-row
                  gap-3
                  items-start
                  sm:items-center
                  justify-between
                ">

                  <a
                      href={shortUrl}
                      target="_blank"
                      rel="noreferrer"
                      className="
                        text-blue-600
                        break-all
                        text-sm
                        sm:text-base
                        font-medium
                      "
                  >

                    {shortUrl}

                  </a>

                  <button
                      onClick={handleCopy}
                      className="
                        bg-black
                        text-white
                        px-4
                        py-2
                        rounded-lg
                        text-sm
                        hover:bg-gray-800
                      "
                  >

                    Copy

                  </button>

                </div>

              </div>
            )
          }

          {
            error && (

              <div className="
                bg-red-100
                text-red-700
                p-4
                rounded-xl
                text-sm
                sm:text-base
              ">

                {error}

              </div>
            )
          }

        </div>

      </div>

    </div>
  )
}

export default App;

