"use client";

import {Message} from "@/app/types";
import {useEffect, useRef, useState} from "react";
import useConversation from "@/app/hooks/useConversation";
import MessageBox from "@/app/conversations/[conversationId]/components/MessageBox";
import axios from "axios";

interface BodyProps {
    initialMessages: Message[]
}
const Body: React.FC<BodyProps> = ({
    initialMessages
}) => {
    const [messages, setMessages] = useState(initialMessages);
    const bottomRef = useRef<HTMLDivElement>(null);

    const { conversationId } = useConversation();

    useEffect(() => {
        axios.post(`/api/conversations/${conversationId}/seen`)
    }, [conversationId]);

    return (
        <div className="flex-1 overflow-y-auto">
            {messages.map((message, i) => (
                <MessageBox isLast={i === messages.length -1} key={message.id} data={message}/>
            ))}
            <div className="pt-24" ref={bottomRef}></div>
        </div>
    );
}

export default Body;