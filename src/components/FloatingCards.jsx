export default function FloatingCards({
  latestFile,
  uploadComplete,
}) {
  return (
    <div className="relative w-[200px] h-[170px]">

      {/* Left Card */}

      <div
        className="
        absolute
        left-0
        top-5
        w-32
        h-44
        rounded-[26px]
        bg-white/90
        backdrop-blur-xl
        border
        border-neutral-200
        shadow-xl
        rotate-[-15deg]
        animate-[float1_7s_ease-in-out_infinite]
        "
      />

      {/* Right Card */}

      <div
        className="
        absolute
        right-0
        top-5
        w-32
        h-44
        rounded-[26px]
        bg-white/90
        backdrop-blur-xl
        border
        border-neutral-200
        shadow-xl
        rotate-[15deg]
        animate-[float2_8s_ease-in-out_infinite]
        "
      />

      {/* Main Card */}

      <div
        className="
        absolute
        left-1/2
        -translate-x-1/2
        w-36
        h-48
        rounded-[28px]
        bg-white
        border
        border-neutral-200
        shadow-2xl
        px-4
        py-4
        flex
        flex-col
        justify-between
        animate-[float3_6s_ease-in-out_infinite]
        "
      >

        {/* Fake Document */}

        <div>

          <div className="flex justify-between">

            <div className="w-16 h-2 rounded-full bg-neutral-200"></div>

            <div className="w-5 h-5 rounded-full bg-neutral-100"></div>

          </div>

          <div className="space-y-2 mt-5">

            <div className="h-2 rounded-full bg-neutral-200"></div>

            <div className="h-2 rounded-full bg-neutral-200"></div>

            <div className="h-2 rounded-full bg-neutral-200 w-5/6"></div>

            <div className="h-2 rounded-full bg-neutral-200"></div>

            <div className="h-2 rounded-full bg-neutral-200 w-4/5"></div>

          </div>

        </div>

        {/* Bottom */}

        <div>

          <div className="text-sm font-semibold truncate">

            {latestFile || "Company.pdf"}

          </div>

          <div className="text-xs text-neutral-400 mt-1">

            {uploadComplete
              ? "Ready for AI"
              : "Waiting for upload"}

          </div>

        </div>

      </div>

    </div>
  );
}