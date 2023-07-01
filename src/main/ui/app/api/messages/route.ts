import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";
import { pusherServer } from "@/app/libs/pusher";

export async function POST(
    request: Request
) {
    try {
        const currentUser = await getCurrentUser();
        const body = await request.json();
        const {
            message,
            image,
            conversationId
        } = body;

        if (!currentUser?.id || !currentUser?.email) {
            return new NextResponse('Unauthorized', {status: 401});
        }

        const newMessage = await axios.post(process.env.SPRING_API_URL + '/api/messages/createMessage', {
            body: message,
            image: image,
            conversationId: conversationId,
            senderUserId: currentUser.id,
            seenUserId: currentUser.id
        });

        const updatedConversation = await axios.put(process.env.SPRING_API_URL + '/api/conversations/updateConversation', {
            conversationId: conversationId
        });

        let payload = newMessage.data;

        delete payload.conversation;

        await pusherServer.trigger(conversationId, 'message:new', payload);

        const lastMessage = updatedConversation.data.messages[updatedConversation.data.messages.length - 1];


        // @ts-ignore
        updatedConversation.data.users.map((user) => {
            pusherServer.trigger(user.email!, 'conversation:update', {
                id: conversationId,
                messages: [lastMessage]
            })
        })

        return NextResponse.json(newMessage.data);

    } catch (error: any) {
        console.log(error, 'ERROR_MESSAGES');
        return new NextResponse('InternalError', {status: 500});
    }
}