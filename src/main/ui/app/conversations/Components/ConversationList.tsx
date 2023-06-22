"use client";
import {Conversation, User} from "@/app/types";
import {useEffect, useMemo, useState} from "react";
import {useRouter} from "next/navigation";
import useConversation from "@/app/hooks/useConversation";
import clsx from "clsx";
import {MdOutlineGroupAdd} from "react-icons/md";
import ConversationBox from "@/app/conversations/Components/ConversationBox";
import GroupChatModal from "@/app/conversations/Components/GroupChatModal";
import {useSession} from "next-auth/react";
import {pusherClient} from "@/app/libs/pusher";
import {find} from "lodash";

interface ConversationsListProps {
    initialItems: Conversation[];
    users: User[]
}

const ConversationList: React.FC<ConversationsListProps> = ({
    initialItems,
    users
}) => {
    const session = useSession();
    const [items, setItems] = useState(initialItems);
    const [isModalOpen, setIsModelOpen] = useState(false);

    const router = useRouter();

    const pusherKey = useMemo(() => {
        return session.data?.user?.email;
    }, [session.data?.user?.email]);

    useEffect(() => {
        if (!pusherKey) {
            return;
        }

        pusherClient.subscribe(pusherKey);

        const newHandler = (conversation: Conversation) => {
            // @ts-ignore
            setItems((current) => {
                if (find(current, { id: conversation.id })) {
                    return current;
                }

                return [conversation, ...current];
            })
        };

        pusherClient.bind('conversation:new', newHandler);

        return () => {
            pusherClient.unsubscribe(pusherKey);
            pusherClient.unbind('conversation:new', newHandler);
        }
    })

    const { conversationId, isOpen } = useConversation();
    return (
        <>
            <GroupChatModal users={users} isOpen={isModalOpen} onClose={() => setIsModelOpen(false)}/>
            <aside className={clsx(`fixed inset-y-0 pb-20 lg:pb-0 lg:left-20 lg:w-80
                                          lg:block overflow-y-auto border-r border-gray-200`,
                                          isOpen ? 'hidden' : 'block w-full left-0')}>
                <div className="px-5">
                    <div className="flex justify-between mb-4 pt-4">
                        <div className="text-2xl font-bold text-neutral-800">
                            Messages
                        </div>
                        <div className="rounded-full p-2 bg-gray-100 text-gray-600 cursor-pointer
                                        hover:opacity-75 transition" onClick={() => setIsModelOpen(true)}>
                            <MdOutlineGroupAdd size={20}/>
                        </div>
                    </div>
                    {items.map((item) => (
                        <ConversationBox key={item.id} data={item} selected={conversationId === item.id}/>
                    ))}
                </div>
            </aside>
        </>
    );
}

export default ConversationList;