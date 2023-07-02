import NextAuth, {AuthOptions} from "next-auth";
import GitHubProvider from "next-auth/providers/github";
import GoogleProvider from "next-auth/providers/google";
import CredentialsProvider from "next-auth/providers/credentials";
import axios from "axios";
export enum Role {
    user = "user",
    admin = "admin",
}
export const authOptions: AuthOptions = {
    providers: [
        GitHubProvider({
            clientId: process.env.GITHUB_ID as string,
            clientSecret: process.env.GITHUB_SECRET as string
        }),
        GoogleProvider({
            clientId: process.env.GOOGLE_ID as string,
            clientSecret: process.env.GOOGLE_SECRET as string
        }),
        CredentialsProvider({
            name: 'credentials',
            credentials: {
                email: {label: 'email', type: 'text'},
                password: {label: 'password', type: 'password'},
                access_token: {}
            },
            // todo Şifreyi API'ye yollamadan önce şifrelemek mantıklı olabilir?
            async authorize(credentials, req) {
                const response = await axios.post( process.env.SPRING_API_URL + '/api/auth/loginWithPwd', {email: credentials?.email, password: credentials?.password})
                .catch(function (error) {
                        if (error.response.status === 403) {
                            return null;
                        }
                    });

                const user = {
                    email: response?.data.email,
                    access_token: response?.headers.authorization,
                }
                console.log(user.access_token);
                if(user) return user;
                return null;
            }
        })
    ],
    secret: process.env.NEXTAUTH_SECRET,
    session: {
        strategy: "jwt",
        maxAge: 15 * 60
    },
    callbacks: {
        async signIn({ user, account, profile, email, credentials }) {
            const isAllowedToSignIn = true
            if (isAllowedToSignIn) {
                if (account?.type == 'oauth') {
                    axios.post(process.env.SPRING_API_URL + '/api/auth/loginWithOAuth2', {
                        account: account,
                        profile: profile
                    }).then().catch(function (error) {
                    })
                }
                return true
            } else {
                return false
            }
        },
        // todo refresh at interval given token
        async jwt({ token, user, account, profile, isNewUser }) {
            if(user) {
                // @ts-ignore
                token.token = user.access_token;
            }
            return token
        },
        async session({ session, user, token }) {
            if(token) {
                session.token = token.token;
            }
            return session
        }
    },
    debug: process.env.NODE_ENV === 'development',
}

const handler = NextAuth(authOptions);

export {handler as GET, handler as POST};