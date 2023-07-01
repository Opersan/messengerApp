"use client";

import {Conversation, User} from "@/app/types";
import useOtherUser from "@/app/hooks/useOtherUser";
import {useSession} from "next-auth/react";
import {useRouter} from "next/navigation";
import {useCallback, useEffect, useMemo, useState} from "react";
import {format} from "date-fns";
import clsx from "clsx";
import Avatar from "@/app/components/Avatar";
import AvatarGroup from "@/app/components/AvatarGroup";
import useOtherUsers from "@/app/hooks/useOtherUsers";

interface ConversationBoxProps {
    data: Conversation;
    selected?: boolean;
    users: User[]
}

const ConversationBox: React.FC<ConversationBoxProps> = ({
    data,
    selected,
    users
}) => {
    const otherUser = useOtherUser(data);
    const otherUsers = useOtherUsers(data);
    const { data: session, status } = useSession();
    const router = useRouter();
    const [hasSeen, setHasSeen] = useState(false);

    const handleClick = useCallback(() => {
        router.push(`/conversations/${data.id}`)
    }, [data.id, router]);

    const lastMessage = useMemo(() => {
        const messages = data.messages || [];
        return messages[messages.length - 1];
    }, [data.messages]);

    useEffect(()=> {
        if (status == "loading") return
        const currentUser = users.filter((user) => user.email === session?.user?.email)[0];
        const seenArray = lastMessage?.seenUsers || [];
        // @ts-ignore
        setHasSeen(lastMessage && currentUser?.id && seenArray.filter((user) => user.id === currentUser?.id).length != 0);
    }, [session, lastMessage, status]);

    const lastMessageText = useMemo(() => {
        if (lastMessage?.image) {
            return 'Sent an image';
        }

        if (lastMessage?.body) {
            return lastMessage.body;
        }

        return 'Started a conversation';
    }, [lastMessage]);

    return (
        <div onClick = {handleClick} className = {clsx(`w-full relative flex items-center space-x-3
                                                        hover:bg-neutral-100 rounded-lg transition cursor-pointer p-3`,
            selected ? 'bg-neutral-100' : 'bg-white')}>
            {data.isGroup ? (
                <AvatarGroup users = {otherUsers}/>
            ) : (
                <Avatar user = {otherUser}/>
            )}
            <div className = "min-w-0 flex-1">
                <div className = "focus:outline-none">
                    <div className = "flex justify-between items-center mb-1">
                        <p className = "text-md font-medium text-gray-900">
                            {data.name || otherUser.name}
                        </p>
                        {lastMessage?.createdAt && (
                            <p className = "text-xs text-gray-400 font-light">
                                {format(new Date(lastMessage.createdAt), 'p')}
                            </p>
                        )}
                    </div>
                    <p className = {clsx(`truncate text-sm`, hasSeen ? 'text-gray-500' : 'text-black font-medium')}>
                        {lastMessageText}
                    </p>
                </div>
            </div>
        </div>
    );
}

export default ConversationBox;