// src/app/page.tsx
import MagneticButton from '../components/magneticButton';

export default function Home() {
  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24 bg-neutral-950">
      <div className="max-w-2xl mx-auto text-center">
        <h1 className="text-4xl font-bold text-white mb-8">
          Magnetic Button Demo
        </h1>

        <div className="flex flex-col gap-8 items-center">
          <MagneticButton className="px-8 py-4 bg-white text-black rounded-full font-medium text-lg hover:bg-opacity-90 transition-all">
            White Cursor Friendly
          </MagneticButton>

          <MagneticButton
            className="px-8 py-4 bg-blue-600 text-white rounded-full font-medium text-lg
                      hover:bg-blue-500 transition-colors"
          >
            Colored Magnetic Button
          </MagneticButton>

          {/* Outline Button */}
          <MagneticButton
            className="px-8 py-4 border-2 border-white text-white rounded-full font-medium text-lg
                      hover:bg-white hover:text-black transition-all"
          >
            Outline + Magnetic
          </MagneticButton>
        </div>

        <p className="mt-12 text-neutral-400">
          Now you should see a <span className="text-white font-medium">white cursor</span> clearly against dark background
        </p>
      </div>
    </main>
  );
}