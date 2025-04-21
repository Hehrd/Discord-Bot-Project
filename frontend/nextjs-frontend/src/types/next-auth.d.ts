import NextAuth from "next-auth";
import { JWT } from "next-auth/jwt";
import { Session } from "next-auth";

// Extend the JWT type
declare module "next-auth" {
    interface Session {
        accessToken?: string | null;
        user: {
            id?: string | null;
            email?: string | null;
            username?: string | null;
            image?: string | null;
        };
    }

    interface JWT {
        accessToken?: string | null;
        id?: string | null;
        email?: string | null;
        username?: string | null;
    }
}
