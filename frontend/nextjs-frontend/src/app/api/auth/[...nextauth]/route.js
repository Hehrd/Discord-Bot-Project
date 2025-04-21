import NextAuth from 'next-auth';
import DiscordProvider from 'next-auth/providers/discord';

export const handler = NextAuth({
    providers: [
        DiscordProvider({
            clientId: process.env.DISCORD_CLIENT_ID,
            clientSecret: process.env.DISCORD_CLIENT_SECRET,
            authorization: {
                params: {
                    scope: 'identify email',
                },
            },
        }),
    ],
    debug: true, callbacks: {
        async jwt({ token, account, user }) {
            if (account && user) {
		console.log('ACCESS TOKEN GOSHETO E GEJ');
		console.log(account.access_token);
                token.accessToken = account.access_token;
                token.id = user.id;
                token.email = user.email;
                token.username = user.name;
            }
            return token;
        },
        async session({ session, token }) {
            if (token) {
		console.log(token.accessToken);
                session.accessToken = token.accessToken;
                session.user.id = token.id;
                session.user.email = token.email;
                session.user.username = token.username;
            }
            return session;
        },
    },
    secret: process.env.NEXTAUTH_SECRET,
});

export { handler as GET, handler as POST };
