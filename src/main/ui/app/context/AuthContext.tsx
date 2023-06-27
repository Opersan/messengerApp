'use client';

import { SessionProvider } from "next-auth/react";
import {ReactNode} from "react";


interface AuthContextProps {
    children: ReactNode;
}

export default function AuthContext({children}: AuthContextProps) {
    return <SessionProvider refetchInterval={5 * 60} refetchOnWindowFocus={true}>{children}</SessionProvider>
}