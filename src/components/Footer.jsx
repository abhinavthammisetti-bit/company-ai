export default function Footer() {
  return (
    <footer className="py-28 px-8">

      <div
        className="
        max-w-7xl
        mx-auto
        rounded-[40px]
        border
        border-neutral-200
        bg-white/70
        backdrop-blur-xl
        shadow-[0_40px_120px_rgba(0,0,0,.08)]
        p-14
        "
      >

        <div className="grid lg:grid-cols-2 gap-14">

          {/* Left */}

          <div>

            <h2 className="text-5xl font-semibold tracking-[-2px]">
              CompanyAI
            </h2>

            <p className="mt-6 text-neutral-500 text-lg leading-8 max-w-md">
              Enterprise AI Workspace that transforms
              company documents into an intelligent
              assistant for every employee.
            </p>

          </div>

          {/* Right */}

          <div className="grid grid-cols-2 gap-10">

            <div>

              <h4 className="font-semibold mb-6">
                Product
              </h4>

              <div className="space-y-4 text-neutral-500">

                <p className="hover:text-black transition cursor-pointer">
                  Features
                </p>

                <p className="hover:text-black transition cursor-pointer">
                  Workspace
                </p>

                <p className="hover:text-black transition cursor-pointer">
                  AI Chat
                </p>

                <p className="hover:text-black transition cursor-pointer">
                  Security
                </p>

              </div>

            </div>

            <div>

              <h4 className="font-semibold mb-6">
                Company
              </h4>

              <div className="space-y-4 text-neutral-500">

                <p className="hover:text-black transition cursor-pointer">
                  About
                </p>

                <p className="hover:text-black transition cursor-pointer">
                  Privacy
                </p>

                <p className="hover:text-black transition cursor-pointer">
                  Terms
                </p>

                <p className="hover:text-black transition cursor-pointer">
                  Contact
                </p>

              </div>

            </div>

          </div>

        </div>

        {/* Bottom */}

        <div className="border-t border-neutral-200 mt-16 pt-10 flex flex-col md:flex-row justify-between items-center gap-6">

          <div className="text-center md:text-left">

  <p className="text-neutral-400 text-sm">
    © 2026 CompanyAI.
  </p>

  <p className="text-neutral-400 text-sm mt-1">
    made with ❤️ by abhinav.
  </p>

</div>
          <div className="flex gap-4">

            <a
              href="https://linkedin.com/in/abhinav-thammisetti-07544b359/"
              target="_blank"
              rel="noreferrer"
              className="
              w-12
              h-12
              rounded-full
              border
              border-neutral-200
              flex
              items-center
              justify-center
              font-semibold
              text-[15px]
              hover:bg-black
              hover:text-white
              transition-all
              duration-300
              "
            >
              in
            </a>


          </div>

        </div>

      </div>

    </footer>
  );
}