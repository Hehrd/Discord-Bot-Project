// app/login/layout.tsx (Login-specific Layout)
export default function LoginLayout({ children }: { children: React.ReactNode }) {
    return (
        <div className="flex items-center min-h-screen bg-gray-100">
            {children}
        </div>
    );
}
