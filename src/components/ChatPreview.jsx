import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import FadeUp from "./FadeUp";

const responses = {
  leave: {
    source: "Employee-Handbook.pdf",
    answer: `Section 4.2 — Annual Leave

Employees receive 18 paid annual leave days every calendar year.

Unused leave can be carried forward up to 10 days with manager approval.`
  },

  wfh: {
    source: "HR-Policy.pdf",
    answer: `Section 7.1 — Remote Work

Employees may work remotely up to 3 days per week.

Manager approval is required for permanent remote work.`
  },

  dress: {
    source: "HR-Policy.pdf",
    answer: `Section 3.5 — Dress Code

Business casual is expected Monday–Thursday.

Smart casual is permitted on Fridays.`
  },

  expense: {
    source: "Finance-SOP.pdf",
    answer: `Expense claims should be submitted within 15 days.

Manager approval is required above ₹10,000.`
  },

  insurance: {
    source: "Benefits.pdf",
    answer: `All full-time employees receive medical insurance covering spouse and two children.`
  },

  default: {
    source: "Knowledge Base",
    answer: `I couldn't find an exact answer.

Try asking about

• Leave Policy

• Work From Home

• Insurance

• Dress Code

• Expense Policy`
  }
};

export default function ChatPreview() {

  const [question, setQuestion] = useState("");

  const [messages, setMessages] = useState([]);

  const [thinking, setThinking] = useState(false);

  const askAI = () => {

    if (!question.trim()) return;

    const currentQuestion = question;

    setQuestion("");

    setMessages(prev => [

      ...prev,

      {

        id: Date.now(),

        role: "user",

        text: currentQuestion

      }

    ]);

    setThinking(true);

    let reply = responses.default;

    const q = currentQuestion.toLowerCase();

    if (q.includes("leave"))
      reply = responses.leave;

    else if (
      q.includes("remote") ||
      q.includes("wfh") ||
      q.includes("work from home")
    )
      reply = responses.wfh;

    else if (
      q.includes("dress") ||
      q.includes("clothes")
    )
      reply = responses.dress;

    else if (
      q.includes("expense") ||
      q.includes("reimburse")
    )
      reply = responses.expense;

    else if (
      q.includes("insurance") ||
      q.includes("medical")
    )
      reply = responses.insurance;

    setTimeout(() => {

      setThinking(false);

      const aiId = Date.now() + 1;

      setMessages(prev => [

        ...prev,

        {

          id: aiId,

          role: "assistant",

          text: "",

          source: reply.source

        }

      ]);

      let i = 0;

      const timer = setInterval(() => {

        i++;

        setMessages(prev =>

          prev.map(msg =>

            msg.id === aiId

              ? {

                  ...msg,

                  text: reply.answer.slice(0, i)

                }

              : msg

          )

        );

        if (i > reply.answer.length)

          clearInterval(timer);

      }, 18);

    }, 1400);

  };
    return (
    <FadeUp>

      <section className="py-40 px-8">

        <div className="max-w-5xl mx-auto">

          <p className="uppercase tracking-[0.45em] text-xs text-neutral-400 text-center">
            AI DEMO
          </p>

          <h2 className="text-[64px] font-semibold tracking-[-3px] text-center mt-6">
            Ask your company anything
          </h2>

          <p className="text-center text-neutral-500 text-xl mt-6 max-w-3xl mx-auto leading-9">
            Every answer is generated directly from your uploaded company documents.
          </p>

          <div
            className="
            relative
            mt-20
            rounded-[42px]
            border
            border-neutral-200
            bg-white/80
            backdrop-blur-3xl
            shadow-[0_70px_180px_rgba(0,0,0,.12)]
            overflow-hidden
            "
          >

            {/* Background Glow */}

            <div className="absolute w-[450px] h-[450px] rounded-full bg-neutral-200/30 blur-[120px] right-[-100px] top-[-100px]" />

            <div className="absolute w-[320px] h-[320px] rounded-full bg-blue-100/40 blur-[120px] left-[-100px] bottom-[-100px]" />

            {/* Window */}

            <div className="flex items-center gap-3 px-7 py-5 border-b border-neutral-200 bg-white/70">

              <div className="w-3 h-3 rounded-full bg-red-400"></div>

              <div className="w-3 h-3 rounded-full bg-yellow-400"></div>

              <div className="w-3 h-3 rounded-full bg-green-400"></div>

              <span className="ml-5 text-sm text-neutral-400">
                CompanyAI Workspace
              </span>

            </div>

            {/* Messages */}

            <div className="p-10 space-y-8 min-h-[500px]">

              <AnimatePresence>

                {messages.map((msg) => (

                  <motion.div

                    key={msg.id}

                    initial={{
                      opacity: 0,
                      y: 25
                    }}

                    animate={{
                      opacity: 1,
                      y: 0
                    }}

                    exit={{
                      opacity: 0
                    }}

                    transition={{
                      duration: .35
                    }}

                  >

                    {msg.role === "user" ? (

                      <div className="flex justify-end">

                        <div
                          className="
                          max-w-xl
                          rounded-[30px]
                          bg-gradient-to-r
                          from-black
                          to-neutral-700
                          text-white
                          px-7
                          py-5
                          shadow-xl
                          "
                        >

                          {msg.text}

                        </div>

                      </div>

                    ) : (

                      <div className="flex gap-5">

                        {/* AI Avatar */}

                        <motion.div

                          animate={{
                            rotate: thinking ? 360 : 0
                          }}

                          transition={{
                            repeat: thinking ? Infinity : 0,
                            duration: 4,
                            ease: "linear"
                          }}

                          className="
                          w-12
                          h-12
                          rounded-full
                          bg-gradient-to-br
                          from-black
                          to-neutral-700
                          text-white
                          flex
                          items-center
                          justify-center
                          font-semibold
                          shadow-lg
                          "
                        >

                          AI

                        </motion.div>

                        <div className="flex-1">

                          <div
                            className="
                            rounded-[30px]
                            border
                            border-neutral-200
                            bg-white
                            px-8
                            py-7
                            whitespace-pre-line
                            leading-8
                            shadow-lg
                            "
                          >

                            {msg.text}

                            {msg === messages[messages.length - 1] &&

                              msg.text.length > 0 &&

                              <span className="animate-pulse">|</span>

                            }

                            <div
                              className="
                              inline-flex
                              items-center
                              gap-2
                              mt-6
                              rounded-full
                              bg-neutral-100
                              px-4
                              py-2
                              text-sm
                              text-neutral-600
                              "
                            >

                              📄 {msg.source}

                            </div>

                          </div>

                        </div>

                      </div>

                    )}

                  </motion.div>

                ))}

              </AnimatePresence>

              {thinking && (

                <motion.div

                  initial={{
                    opacity: 0
                  }}

                  animate={{
                    opacity: 1
                  }}

                  className="flex gap-5"

                >

                  <div
                    className="
                    w-12
                    h-12
                    rounded-full
                    bg-black
                    text-white
                    flex
                    items-center
                    justify-center
                    "
                  >

                    AI

                  </div>

                  <div
                    className="
                    rounded-[28px]
                    bg-white
                    border
                    border-neutral-200
                    px-8
                    py-6
                    shadow-lg
                    "
                  >

                    <div className="flex gap-2">

                      <div className="w-2 h-2 rounded-full bg-neutral-400 animate-bounce"></div>

                      <div className="w-2 h-2 rounded-full bg-neutral-400 animate-bounce [animation-delay:150ms]"></div>

                      <div className="w-2 h-2 rounded-full bg-neutral-400 animate-bounce [animation-delay:300ms]"></div>

                    </div>

                  </div>

                </motion.div>

              )}

            </div>
                        {/* Ask Box */}

            <div className="border-t border-neutral-200 p-8 bg-white/60 backdrop-blur-xl">

              <div className="relative flex gap-4">

                {/* Cursor Glow */}

                <div
                  className="
                  absolute
                  inset-0
                  rounded-3xl
                  opacity-0
                  hover:opacity-100
                  transition-opacity
                  duration-500
                  pointer-events-none
                  bg-gradient-to-r
                  from-blue-100/20
                  via-purple-100/20
                  to-pink-100/20
                  blur-2xl
                  "
                />

                <input
                  value={question}
                  onChange={(e) => setQuestion(e.target.value)}
                  onKeyDown={(e) => {

                    if (e.key === "Enter") {

                      askAI();

                    }

                  }}
                  placeholder="Ask anything about your company..."
                  className="
                  relative
                  flex-1
                  rounded-[24px]
                  border
                  border-neutral-200
                  bg-white
                  px-7
                  py-5
                  text-[16px]
                  outline-none
                  shadow-lg
                  transition-all
                  duration-300
                  focus:border-black
                  focus:shadow-2xl
                  "
                />

                <motion.button

                  whileHover={{
                    scale: 1.05
                  }}

                  whileTap={{
                    scale: .97
                  }}

                  onClick={askAI}

                  className="
                  rounded-[24px]
                  bg-black
                  text-white
                  px-10
                  font-semibold
                  shadow-xl
                  transition-all
                  duration-300
                  "

                >

                  Ask →

                </motion.button>

              </div>

            </div>

          </div>

        </div>

      </section>

    </FadeUp>

  );

}