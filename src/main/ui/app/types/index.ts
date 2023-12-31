import {DateTime} from "next-auth/providers/kakao";

export type User = {
    id?: number,
    name?: string,
    email?: string,
    image?: string
    createdAt: DateTime
}

export type Message = {
    id?: string,
    body?: string,
    image?: string,
    createdAt: Date,
    seenUsers?: User[],
    conversation?: Conversation,
    senderUser?: User
}

export type Conversation = {
    id?: string,
    name?: string,
    isGroup?: boolean

    messages?: Message[]
    users: User[]
}

export type ConversationType2 = {
    id?: string,
    name?: string,
    isGroup?: boolean

    messages?: Message[]
    users: string[]
}


