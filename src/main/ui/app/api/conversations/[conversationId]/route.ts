import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";
import {pusherServer} from "@/app/libs/pusher";

interface IParams {
    conversationId?: string;
}

export async function DELETE(
    request: Request,
    { params } : {params: IParams}
) {
    try {
        const { conversationId } = params;
        const currentUser = await getCurrentUser();

        if (!currentUser?.id) {
            return new NextResponse('Unauthorized', {status: 401});
        }

        const existingConversation =
            await axios.delete(process.env.SPRING_API_URL + '/api/conversations/deleteConversation', {
            params: {
                conversationId: conversationId,
                userId: currentUser.id
            }
        });

        if (!existingConversation) {
            return new NextResponse('Invalid ID!', {status: 400});
        }

        let payload = existingConversation.data;

        delete payload.users[0].conversations;
        delete payload.users[1].conversations;
        delete payload.users[0].seenMessages;
        delete payload.users[1].seenMessages;

        // @ts-ignore
        existingConversation.data.users.forEach((user) => {
            if (user.email) {
                pusherServer.trigger(user.email, "conversation:remove", payload);
            }
        })

        return NextResponse.json(existingConversation.data);

    } catch (error: any) {
        console.log(error, 'ERROR_CONVERSATION_DELETE');
        return new NextResponse('Internal Error', {status: 500});
    }
}