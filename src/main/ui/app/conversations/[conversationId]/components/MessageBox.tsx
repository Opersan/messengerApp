"use client";

import {Message} from "@/app/types";
import {useSession} from "next-auth/react";
import clsx from "clsx";
import Avatar from "@/app/components/Avatar";
import {format} from "date-fns";
import Image from "next/image";
import {compileNonPath} from "next/dist/shared/lib/router/utils/prepare-destination";
import {useState} from "react";
import ImageModal from "@/app/conversations/[conversationId]/components/ImageModal";

interface MessageBoxProps {
    data: Message,
    isLast?: boolean
}

const MessageBox: React.FC<MessageBoxProps> = ({
    data,
    isLast
}) => {
    const session = useSession();
    const [imageModalOpen, setImageModalOpen] = useState(false);

    const isOwn = session?.data?.user?.email === data?.senderUser?.email;
    const seenList = (data.seenUsers || [])
    .filter((user) => user.email != data?.senderUser?.email)
    .map((user) => user.name)
    .join(', ');

    const container = clsx('flex gap-3 p-4', isOwn && 'justify-end');
    const avatar = clsx(isOwn && 'order-2');
    const body = clsx('flex flex-col gap-2', isOwn && 'items-end');

    const message = clsx('text-sm w-fit overflow-hidden',
        isOwn ? 'bg-sky-500 text-white' : 'bg-gray-100',
        data.image ? 'rounded-md p-0': 'rounded-full py-2 px-3');

    return (
        <div className={container}>
            <div className={avatar}>
                <Avatar user={data.senderUser}/>
            </div>
            <div className={body}>
                <div className="flex items-center gap-1">
                    <div className="text-sm text-gray-500">
                        {data.senderUser?.name}
                    </div>
                    <div className="text-xs text-gray-400">
                        {format(new Date(data.createdAt), 'p')}
                    </div>
                </div>
                <div className={message}>
                    <ImageModal src={data.image} isOpen={imageModalOpen} onClose={() => setImageModalOpen(false)}/>
                    {data.image ? (
                        <Image alt="Image" height="288" width="288" src={data.image} onClick={() => setImageModalOpen(true)}
                               className="object-cover cursor-pointer transition translate hover:scale-110"/>
                    ) : (
                        <div>{data.body}</div>
                    )}
                </div>
                {isLast && isOwn && seenList.length > 0 && (
                    <div className="text-xs font-light text-gray-500">
                        {`Seen by ${seenList}`}
                    </div>
                )}
            </div>
        </div>
    );
}

export default MessageBox;