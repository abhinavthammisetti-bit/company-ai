export default function Navbar() {
  return (
    <header className="fixed top-6 left-1/2 -translate-x-1/2 z-50 w-full px-6">

      <div
        className="
        max-w-7xl
        mx-auto
        rounded-full
        border
        border-white/40
        bg-white/70
        backdrop-blur-2xl
        shadow-[0_20px_60px_rgba(0,0,0,.08)]
        px-10
        py-5
        flex
        items-center
        justify-between
        transition-all
        duration-500
        "
      >

        {/* Logo */}

        <h1 className="text-2xl font-bold tracking-tight">
          CompanyAI
        </h1>

        {/* Navigation */}

        <nav className="hidden md:flex items-center gap-10 text-[15px] text-neutral-500">

          <a
            href="#"
            className="hover:text-black transition duration-300"
          >
            Product
          </a>

          <a
            href="#"
            className="hover:text-black transition duration-300"
          >
            Features
          </a>

          <a
            href="#"
            className="hover:text-black transition duration-300"
          >
            AI Demo
          </a>

          <a
            href="#"
            className="hover:text-black transition duration-300"
          >
            Contact
          </a>

        </nav>

        {/* CTA */}

        <button
          className="
          rounded-full
          bg-black
          text-white
          px-6
          py-3
          font-medium
          hover:scale-105
          transition-all
          duration-300
          "
        >
          Get Started
        </button>

      </div>

    </header>
  );
}