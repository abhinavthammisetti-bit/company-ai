import { useState } from "react";
import { askCompanyAIStream } from "../services/api";
import Confetti from "react-confetti";
import FadeUp from "./FadeUp";
import FloatingCards from "./FloatingCards";
import { motion } from "framer-motion";

export default function Workspace() {

  const [files, setFiles] = useState([]);

  const [companyName, setCompanyName] = useState("");

  const [dragging, setDragging] = useState(false);

  const [uploading, setUploading] = useState(false);

  const [progress, setProgress] = useState(0);

  const [success, setSuccess] = useState(false);

  const [showConfetti, setShowConfetti] = useState(false);
  
  const [question, setQuestion] = useState("");
  
  const [messages, setMessages] = useState([]);
  
  const [thinking, setThinking] = useState(false);

  const handleUpload = async (uploadedFiles) => {

  if (!uploadedFiles.length) return;

  const file = uploadedFiles[0];

  setUploading(true);
  setSuccess(false);
  setProgress(0);

  const formData = new FormData();

  formData.append("file", file);

  try {

    const xhr = new XMLHttpRequest();

    xhr.upload.onprogress = (event) => {

      if (event.lengthComputable) {

        const percent = Math.round(
          (event.loaded * 100) / event.total
        );

        setProgress(percent);

      }

    };

    xhr.onload = () => {

      setUploading(false);

      if (xhr.status === 200) {

  setSuccess(true);

  setShowConfetti(true);

  setFiles(prev => [...prev, file]);

  const message = xhr.responseText;

  const match = message.match(
    /company:\s*(.+)$/i
  );

  if (match) {
    const detectedCompany = match[1].trim();

    console.log(
      ">>> COMPANY DETECTED:",
      detectedCompany
    );

    setCompanyName(detectedCompany);
  }

  setTimeout(() => {

    setShowConfetti(false);

  }, 3000);

} else {

        alert("Upload Failed");

      }

    };

    xhr.onerror = () => {

      setUploading(false);

      alert("Cannot connect to backend");

    };

    xhr.open(
  "POST",
  "http://localhost:8080/api/document/upload"
);

    xhr.send(formData);

  } catch (e) {

    setUploading(false);

    alert("Upload Failed");

  }

};

  const handleInput = (e) => {

    const uploaded = Array.from(e.target.files);

    handleUpload(uploaded);

    e.target.value = "";

  };
  const askAI = async () => {

  if (!question.trim()) return;

if (!companyName) {
  alert("Please upload a company PDF first.");
  return;
}

  const currentQuestion = question;

  setQuestion("");

  let assistantIndex = 0;

  setMessages(prev => {

    assistantIndex = prev.length + 1;

    return [
      ...prev,
      {
        type: "user",
        text: currentQuestion
      },
      {
        type: "assistant",
        text: ""
      }
    ];

  });

  setThinking(true);

  try {

    await askCompanyAIStream(
  companyName,
  currentQuestion,

      async (token) => {

    console.log("TOKEN:", JSON.stringify(token));

    setThinking(false);

    await new Promise(resolve => setTimeout(resolve, 50));

setMessages(prev => {

    const updated = [...prev];

    updated[assistantIndex] = {
        ...updated[assistantIndex],
        text: updated[assistantIndex].text + token
    };

    console.log("CURRENT:", updated[assistantIndex].text);

    return updated;

});

}

    );

  } catch (e) {

    console.error(e);

  }

};

  const handleDrop = (e) => {

    e.preventDefault();

    setDragging(false);

    const uploaded = Array.from(e.dataTransfer.files);

    handleUpload(uploaded);

  };

  const removeFile = (index) => {

    setFiles(files.filter((_, i) => i !== index));

  };

  const formatSize = (size) => {

    return (size / 1024 / 1024).toFixed(2) + " MB";

  };

  return (

    <FadeUp>

      <section className="pb-44 px-8">

        {showConfetti && (

          <Confetti

            recycle={false}

            numberOfPieces={220}

            gravity={0.22}

          />

        )}

        <div
          className="
          max-w-6xl
          mx-auto
          rounded-[42px]
          bg-white/75
          backdrop-blur-3xl
          border
          border-neutral-200
          shadow-[0_70px_180px_rgba(0,0,0,.12)]
          overflow-hidden
          "
        >
            {/* Upload Area */}

<div className="px-16 py-16">

  <div

    onDragOver={(e) => {

      e.preventDefault();

      setDragging(true);

    }}

    onDragLeave={() => setDragging(false)}

    onDrop={handleDrop}

    className={`
      relative
      overflow-hidden
      rounded-[40px]
      border-2
      transition-all
      duration-500
      min-h-[560px]
      flex
      flex-col
      items-center
      justify-center
      px-12
      py-14

      ${
        dragging
          ? "border-black bg-neutral-100 scale-[1.015] shadow-2xl"
          : "border-dashed border-neutral-300 bg-gradient-to-b from-white to-neutral-50"
      }
    `}
  >

    {/* Animated Glow */}

    <div
      className="
      absolute
      w-[650px]
      h-[650px]
      rounded-full
      bg-blue-100/20
      blur-[140px]
      animate-pulse
      "
    />

    <div
      className="
      absolute
      w-[450px]
      h-[450px]
      rounded-full
      bg-purple-100/20
      blur-[120px]
      right-[-120px]
      bottom-[-120px]
      "
    />

    {/* Floating PDFs */}

    <div className="relative z-10">

      <FloatingCards

        latestFile={
          files.length
            ? files[files.length - 1].name
            : null
        }

      />

    </div>

    {/* Heading */}

    <h3
      className="
      relative
      z-10
      mt-10
      text-[42px]
      font-semibold
      tracking-[-1px]
      "
    >

      Drop your PDFs

    </h3>

    <p
      className="
      relative
      z-10
      mt-4
      text-xl
      text-neutral-500
      "
    >

      Drag & Drop or browse from your computer

    </p>

    {/* Upload Progress */}

    {uploading && (

      <div className="relative z-10 mt-10 w-[380px]">

        <div className="flex justify-between mb-3 text-neutral-500">

          <span>Uploading PDF...</span>

          <span>{progress}%</span>

        </div>

        <div className="h-3 rounded-full bg-neutral-200 overflow-hidden">

          <div

            style={{ width: `${progress}%` }}

            className="
            h-full
            bg-gradient-to-r
            from-black
            to-neutral-700
            transition-all
            duration-150
            "

          />

        </div>

      </div>

    )}

    {/* Success */}

    {success && !uploading && (

      <motion.div

        initial={{ opacity:0, scale:.8 }}

        animate={{ opacity:1, scale:1 }}

        className="
        relative
        z-10
        mt-8
        rounded-full
        bg-green-100
        text-green-700
        px-6
        py-3
        font-semibold
        shadow-lg
        "

      >

        ✓ Upload Complete

      </motion.div>

    )}

    {/* Premium PDF Cards */}

    {files.length > 0 && (

      <div

        className="
        relative
        z-10
        mt-12
        grid
        md:grid-cols-2
        gap-5
        w-full
        max-w-3xl
        "

      >

        {files.map((file,index)=>(

          <motion.div

            key={index}

            layout

            initial={{opacity:0,y:20}}

            animate={{opacity:1,y:0}}

            exit={{opacity:0}}

            className="
            rounded-[26px]
            bg-white
            border
            border-neutral-200
            shadow-xl
            p-5
            flex
            items-center
            justify-between
            hover:-translate-y-1
            transition-all
            "

          >

            <div className="flex gap-4">

              <div
                className="
                w-14
                h-16
                rounded-xl
                bg-gradient-to-b
                from-red-500
                to-red-600
                text-white
                flex
                items-center
                justify-center
                text-xs
                font-bold
                shadow-md
                "
              >

                PDF

              </div>

              <div>

                <h4 className="font-semibold truncate max-w-[180px]">

                  {file.name}

                </h4>

                <p className="text-neutral-400 text-sm mt-1">

                  {formatSize(file.size)}

                </p>

              </div>

            </div>

            <button

              onClick={()=>removeFile(index)}

              className="
              w-10
              h-10
              rounded-full
              border
              border-neutral-200
              hover:bg-red-500
              hover:text-white
              transition
              "

            >

              ✕

            </button>

          </motion.div>

        ))}

      </div>

    )}

    {/* Browse */}

    <input

      type="file"

      multiple

      accept=".pdf"

      id="pdf-upload"

      className="hidden"

      onChange={handleInput}

    />

    <label

      htmlFor="pdf-upload"

      className="
      relative
      z-10
      mt-12
      rounded-full
      bg-black
      text-white
      px-10
      py-5
      cursor-pointer
      font-semibold
      hover:scale-105
      transition-all
      duration-300
      shadow-xl
      "

    >

      Browse Files

    </label>

  </div>

</div>
{/* Ask AI */}

        <div
          className="
          border-t
          border-neutral-200
          bg-gradient-to-b
          from-white
          to-neutral-50
          p-12
          "
        >

          <div className="max-w-4xl mx-auto">

            <div className="mb-6">
  <p className="uppercase tracking-[0.4em] text-xs text-neutral-400">
    AI Assistant
  </p>

  {companyName && (
    <p className="mt-2 text-sm text-neutral-500">
      Using knowledge base:{" "}
      <span className="font-semibold text-black">
        {companyName}
      </span>
    </p>
  )}
</div>

            <div
              className="
              relative
              rounded-[30px]
              border
              border-neutral-200
              bg-white
              shadow-[0_20px_70px_rgba(0,0,0,.08)]
              p-3
              flex
              items-center
              gap-4
              transition-all
              duration-300
              hover:shadow-[0_30px_90px_rgba(0,0,0,.12)]
              "
            >

              {/* Glow */}

              <div
                className="
                absolute
                inset-0
                rounded-[30px]
                opacity-0
                hover:opacity-100
                transition-opacity
                duration-500
                bg-gradient-to-r
                from-blue-100/20
                via-purple-100/20
                to-pink-100/20
                blur-xl
                pointer-events-none
                "
              />

              {/* AI Icon */}

              <div
                className="
                relative
                w-14
                h-14
                rounded-2xl
                bg-black
                text-white
                flex
                items-center
                justify-center
                font-bold
                text-lg
                shadow-lg
                "
              >
                AI
              </div>

              {/* Input */}

             <input
  value={question}
  onChange={(e) => setQuestion(e.target.value)}
  placeholder="Ask anything about your company..."
  className="
  relative
  flex-1
  bg-transparent
  outline-none
  text-lg
  px-2
  "
/>

              {/* Button */}

              <button
  onClick={askAI}
  className="
  relative
  rounded-2xl
  bg-black
  text-white
  px-10
  py-5
  font-semibold
  shadow-xl
  hover:scale-105
  active:scale-95
  transition-all
  duration-300
  "
>
  {thinking ? "Thinking..." : "Ask →"}
</button>

            </div>

            {/* AI Answer */}

{messages.length > 0 && (

  <div className="mt-8 space-y-5">

    {messages.map((msg, index) => (

      <div
        key={index}
        className={`rounded-[24px] p-6 shadow-lg border ${
          msg.type === "user"
            ? "bg-black text-white ml-20"
            : "bg-white border-neutral-200 mr-20"
        }`}
      >

        <p className="text-xs uppercase tracking-[0.3em] opacity-70 mb-3">

          {msg.type === "user" ? "You" : "Company AI"}

        </p>

        <p className="leading-8 whitespace-pre-wrap">

          {msg.text}

        </p>

      </div>

    ))}

  </div>

)}

<div className="mt-8 flex items-center justify-between text-sm text-neutral-400">

  <span>
    AI responses are generated from your uploaded company documents.
  </span>

</div>

          </div>

        </div>

      </div>

    </section>

    </FadeUp>

  );

}