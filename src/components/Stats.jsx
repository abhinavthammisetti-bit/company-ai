import { Building2, FileText, Users, Sparkles } from "lucide-react";
import CountUp from "react-countup";
import FadeUp from "./FadeUp";

const stats = [
  {
    number: "35+",
    title: "Departments",
    icon: Building2,
  },
  {
    number: "12K+",
    title: "Documents",
    icon: FileText,
  },
  {
    number: "95%",
    title: "AI Accuracy",
    icon: Sparkles,
  },
  {
    number: "5K+",
    title: "Employees",
    icon: Users,
  },
];

export default function Stats() {
  return (
    <FadeUp>
    <section className="py-40 px-8 bg-neutral-50">

      <div className="max-w-7xl mx-auto">

        <p className="uppercase tracking-[0.45em] text-xs text-neutral-400 text-center">
          TRUSTED BY ENTERPRISES
        </p>

        <h2 className="text-center text-[64px] font-semibold tracking-[-3px] mt-6">
          Built for scale
        </h2>

        <p className="text-center text-neutral-500 text-xl mt-6 max-w-3xl mx-auto leading-9">
          Powerful enough for enterprise organizations while remaining simple
          for every employee.
        </p>

        <div className="grid grid-cols-2 lg:grid-cols-4 gap-8 mt-20">

          {stats.map((item, index) => {

            const Icon = item.icon;

            return (

              <div
                key={index}
                className="
                rounded-[34px]
                bg-white
                border
                border-neutral-200
                p-10
                text-center
                shadow-sm
                transition-all
                duration-500
                hover:-translate-y-3
                hover:shadow-[0_40px_80px_rgba(0,0,0,.08)]
                "
              >

                <div
                  className="
                  w-16
                  h-16
                  mx-auto
                  rounded-2xl
                  bg-black
                  text-white
                  flex
                  items-center
                  justify-center
                  "
                >

                  <Icon size={30} />

                </div>

                <h3 className="text-[54px] font-bold mt-8 tracking-[-2px]">

                  {item.number}

                </h3>

                <p className="text-neutral-500 mt-3 text-lg">

                  {item.title}

                </p>

              </div>

            );

          })}

        </div>

      </div>

    </section>
    </FadeUp>
  );
}