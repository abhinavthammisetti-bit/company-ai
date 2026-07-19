import Navbar from "./components/Navbar";
import Hero from "./components/Hero";
import Workspace from "./components/Workspace";
import Features from "./components/Features";
import Stats from "./components/Stats";
import Testimonials from "./components/Testimonials";
import ChatPreview from "./components/ChatPreview";
import Footer from "./components/Footer";

function App() {
  return (
    <div className="relative overflow-hidden bg-[#FAFAF8] min-h-screen">

      {/* Background Glow */}

      <div className="absolute -top-72 -left-72 w-[700px] h-[700px] rounded-full bg-neutral-200 blur-[140px] opacity-40"></div>

      <div className="absolute top-[700px] right-[-250px] w-[500px] h-[500px] rounded-full bg-neutral-300 blur-[140px] opacity-30"></div>

      <div className="absolute top-[1600px] left-[20%] w-[400px] h-[400px] rounded-full bg-neutral-200 blur-[120px] opacity-20"></div>

      <div className="relative z-10">

        <Navbar />

        <Hero />

        <Workspace />

        <Features />

        <Stats />

        <Testimonials />

        <ChatPreview />

        <Footer />
      </div>

    </div>
  );
}

export default App;