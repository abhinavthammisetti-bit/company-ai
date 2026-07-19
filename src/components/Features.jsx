import {
  Brain,
  ShieldCheck,
  Search,
  Database,
  Sparkles,
  Lock
} from "lucide-react";
import FadeUp from "./FadeUp";

const features = [
  {
    icon: Brain,
    title: "AI Assistant",
    desc: "Ask natural language questions across your enterprise knowledge."
  },
  {
    icon: Search,
    title: "Semantic Search",
    desc: "Instantly locate information hidden across thousands of documents."
  },
  {
    icon: Database,
    title: "Knowledge Base",
    desc: "Centralize HR policies, reports, SOPs and documentation."
  },
  {
    icon: ShieldCheck,
    title: "Enterprise Security",
    desc: "Private, secure and encrypted document storage."
  },
  {
    icon: Sparkles,
    title: "AI Summaries",
    desc: "Generate intelligent summaries from lengthy reports."
  },
  {
    icon: Lock,
    title: "Private Workspace",
    desc: "Only your organization can access your uploaded knowledge."
  }
];

export default function Features() {

  return (
    <FadeUp>

    <section className="py-40 px-8">

      <div className="max-w-7xl mx-auto">

        <p className="uppercase tracking-[0.45em] text-neutral-400 text-xs text-center">

          FEATURES

        </p>

        <h2 className="text-center text-[64px] font-semibold tracking-[-3px] mt-6">

          Built for modern companies

        </h2>

        <p className="text-center text-neutral-500 mt-6 max-w-3xl mx-auto text-xl leading-9">

          Everything you need to transform documents into an intelligent AI workspace.

        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 mt-20">

          {features.map((feature, index) => {

            const Icon = feature.icon;

            return (

              <div

                key={index}

                className="
                group
                rounded-[32px]
                border
                border-neutral-200
                bg-white
                p-10
                transition-all
                duration-500
                hover:-translate-y-3
                hover:shadow-[0_40px_80px_rgba(0,0,0,0.08)]
                "

              >

                <div
                  className="
                  w-16
                  h-16
                  rounded-2xl
                  bg-black
                  text-white
                  flex
                  items-center
                  justify-center
                  mb-8
                  group-hover:rotate-6
                  transition
                  "
                >

                  <Icon size={30} />

                </div>

                <h3 className="text-2xl font-semibold">

                  {feature.title}

                </h3>

                <p className="mt-5 text-neutral-500 leading-8">

                  {feature.desc}

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