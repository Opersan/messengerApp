import NextAuth, {AuthOptions} from "next-auth";
import GitHubProvider from "next-auth/providers/github";
import GoogleProvider from "next-auth/providers/google";
import axios from "axios";
import { toast } from "react-hot-toast";

const MESSENGER_API_URL = "http://localhost:8080";
export const authOptions: AuthOptions = {
    providers: [
        GitHubProvider({
            clientId: process.env.GITHUB_ID as string,
            clientSecret: process.env.GITHUB_SECRET as string
        }),
        GoogleProvider({
            clientId: process.env.GOOGLE_ID as string,
            clientSecret: process.env.GOOGLE_SECRET as string
        })
    ],
    callbacks: {
        async jwt({ token, account, profile }) {
            console.log("Objects: ");
            console.log({objects: [token, account, profile]});
            console.log("Bitti");
            axios.post(`${MESSENGER_API_URL}` + '/api/user/saveSilent', {token: token, account: account, profile: profile})
                .then(function (response){
                }).catch((error) => {
                    if (error == 403) console.log("403 hatası");
            });
            return token;
        },
    },
    debug: process.env.NODE_ENV === 'development',

}

const handler = NextAuth(authOptions);

export {handler as GET, handler as POST};