import FadeUp from "./FadeUp";
const reviews = [

  {
    name: "Sarah Chen",
    role: "HR Director",
    company: "Acme Inc.",
    review:
      "CompanyAI reduced the time employees spend searching documentation from hours to seconds.",
  },

  {
    name: "Michael Ross",
    role: "Engineering Lead",
    company: "Nova Labs",
    review:
      "The AI understands our internal documentation better than traditional search tools.",
  },

  {
    name: "Emily Carter",
    role: "Operations Manager",
    company: "Vertex Systems",
    review:
      "Beautiful interface, incredibly fast responses and effortless document management.",
  },

];

export default function Testimonials() {

  return (
    <FadeUp>

    <section className="py-40 px-8">

      <div className="max-w-7xl mx-auto">

        <p className="uppercase tracking-[0.45em] text-xs text-neutral-400 text-center">

          TESTIMONIALS

        </p>

        <h2 className="text-center text-[64px] font-semibold tracking-[-3px] mt-6">

          Loved by modern teams

        </h2>

        <p className="text-center text-neutral-500 text-xl mt-6 max-w-3xl mx-auto leading-9">

          Thousands of employees use CompanyAI to instantly access internal knowledge.

        </p>

        <div className="grid lg:grid-cols-3 gap-8 mt-20">

          {reviews.map((item,index)=>(

            <div

              key={index}

              className="
              rounded-[34px]
              bg-white
              border
              border-neutral-200
              p-10
              shadow-sm
              transition-all
              duration-500
              hover:-translate-y-3
              hover:shadow-[0_50px_100px_rgba(0,0,0,.08)]
              "

            >

              <div className="flex mb-8">

                {"★★★★★"}

              </div>

              <p className="text-neutral-600 leading-8">

                "{item.review}"

              </p>

              <div className="flex items-center gap-4 mt-10">

                <div
                  className="
                  w-14
                  h-14
                  rounded-full
                  bg-black
                  text-white
                  flex
                  items-center
                  justify-center
                  font-semibold
                  "
                >

                  {item.name.charAt(0)}

                </div>

                <div>

                  <h4 className="font-semibold">

                    {item.name}

                  </h4>

                  <p className="text-neutral-500 text-sm">

                    {item.role}

                  </p>

                  <p className="text-neutral-400 text-sm">

                    {item.company}

                  </p>

                </div>

              </div>

            </div>

          ))}

        </div>

      </div>

    </section>
    </FadeUp>

  );

}