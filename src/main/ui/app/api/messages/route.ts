import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";

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

        const newMessage = await axios.post(process.env.SPRING_API_URL + '/api/message/createMessage', {
            body: message,
            image: image,
            conversation: conversationId,
            sender: currentUser.id,
            seen: currentUser.id
            //todo lastMessageAt değerini de güncelle
        });

        const updatedConversation = await axios.post('/api/conversations', {
            userId: currentUser.id
        });

        return NextResponse.json(newMessage.data);

    } catch (error: any) {
        console.log(error, 'ERROR_MESSAGES');
        return new NextResponse('InternalError', {status: 500});
    }
}