import { NextRequest, NextResponse } from 'next/server';
import { getToken } from 'next-auth/jwt';

export async function middleware(request: NextRequest) {
    if (request.nextUrl.pathname === '/login') {
        const token = await getToken({ req: request, secret: process.env.NEXTAUTH_SECRET });

        if (token) {
            const homeUrl = new URL('/', request.url);
            return NextResponse.redirect(homeUrl);
        }
    }

    if (request.nextUrl.pathname === '/logout') {
        const response = NextResponse.redirect(new URL('/login', request.url));
        response.cookies.set('next-auth.session-token', '', {
            httpOnly: true,
            maxAge: 0,
            path: '/',
        });
        return response;
    }

    const token = await getToken({ req: request, secret: process.env.NEXTAUTH_SECRET });
    const protectedPaths = ['/test', '/', '/getting-started', '/containers', '/databases', '/tables', '/features', '/about'];

    if (request.nextUrl.pathname === '/') {
        const response = NextResponse.redirect(new URL('/containers', request.url));
        return response;
    }

    if (protectedPaths.includes(request.nextUrl.pathname) && !token) {
        const loginUrl = request.nextUrl.clone();
        loginUrl.pathname = '/login';
        return NextResponse.redirect(loginUrl);
    }

    return NextResponse.next();
}

export const config = {
    matcher: ['/login', '/test', '/', '/getting-started', '/logout', '/containers', '/databases', '/tables', '/features', '/about'],
};
