import FadeUp from "./FadeUp";
export default function Hero() {

  return (
    <FadeUp>

    <section className="pt-52 pb-36">
        <div
className="
absolute
inset-0
overflow-hidden
-z-10
">

<div
className="
absolute
w-[700px]
h-[700px]
rounded-full
bg-blue-200/30
blur-[160px]
left-[-200px]
top-[-150px]
"
/>

<div
className="
absolute
w-[700px]
h-[700px]
rounded-full
bg-purple-200/20
blur-[180px]
right-[-200px]
bottom-[-150px]
"
/>

</div>

      <div className="max-w-6xl mx-auto text-center px-8">

        <p className="uppercase tracking-[0.55em] text-neutral-400 text-xs">

          ENTERPRISE KNOWLEDGE PLATFORM

        </p>

        <h1
          className="
          mt-10
          text-[132px]
          leading-[0.9]
          tracking-[-8px]
          font-semibold
          "
        >

          CompanyAI

        </h1>

        <p
          className="
          mt-12
          max-w-3xl
          mx-auto
          text-[22px]
          leading-10
          text-neutral-500
          "
        >

          Upload company knowledge, employee handbooks,
          HR policies and documentation.

          Ask questions naturally and receive
          instant AI-powered answers.

        </p>

      </div>

    </section>
    </FadeUp>

  );

}