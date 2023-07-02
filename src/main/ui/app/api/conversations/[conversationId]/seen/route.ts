import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";
import {pusherServer} from "@/app/libs/pusher";
import getSession from "@/app/actions/getSession";

interface IParams{
    conversationId?: string
};

export async function POST(
    request: Request,
    {params} : {params: IParams}
) {
    try {
        const currentUser = await getCurrentUser();
        const session = await getSession();
        const access_token = session?.token;

        axios.interceptors.request.use(
            config => {
                config.headers['Authorization'] = `Bearer ${access_token}`;
                return config;
            },
            error => {
                return Promise.reject(error);
            }
        );

        const { conversationId } = params;

        if (!currentUser?.id || !currentUser?.email) {
            return new NextResponse("Unauthorized", {status: 401})
        }

        const conversation = await axios.get(process.env.SPRING_API_URL + '/api/conversations/conversationById', {
            params: {conversationId: conversationId}
        });

        if (!conversation.data) {
            return new NextResponse('Invalid ID', {status: 400});
        }

        const lastMessage = conversation.data.messages[conversation.data.messages.length - 1];

        if (!lastMessage) {
            return NextResponse.json(conversation.data);
        }

        const updatedMessage = await axios.put(process.env.SPRING_API_URL + '/api/messages/updateMessage', {
            messageId: lastMessage.id,
            userId: currentUser.id
        });

        let payload = updatedMessage.data;

        delete payload.conversation;

        await pusherServer.trigger(currentUser.email, 'conversation:update', {
            id: conversationId,
            messages: [payload]
        });

        if(lastMessage.senderUser == currentUser.id) {
            return NextResponse.json(conversation.data);
        }

        await pusherServer.trigger(conversationId!, 'message:update', updatedMessage.data);

        return NextResponse.json('Success');
    } catch (error: any) {
        console.log(error, 'ERROR_MESSAGES_SEEN');
        return new NextResponse("Internal Error", {status: 500});
    }
}