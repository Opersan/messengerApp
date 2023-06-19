export type User = {
    id?: string,
    name?: string,
    email?: string,
    image?: string
}

export type Message = {
    id?: string,
    body?: string,
    image?: string,
    createdAt?: Date,

    seenUsers?: User[],
    conversation?: Conversation,
    sender?: User
}

export type Conversation = {
    id?: string,
    name?: string,
    isGroup?: boolean

    messages?: Message[]

    users: User[]
}

