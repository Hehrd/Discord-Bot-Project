// app/login/layout.tsx (Login-specific Layout)
export default function LoginLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="flex justify-center items-center min-h-screen bg-gray-100">
            <div className="w-full max-w-sm p-8 bg-white rounded-lg shadow-md">
                {children} {/* Only the login form will be rendered here */}
            </div>
        </div>
    );
}
