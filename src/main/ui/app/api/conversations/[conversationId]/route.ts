import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";

interface IParams{
    conversationId?: string
};

export async function POST(
    request: Request,
    {params} : {params: IParams}
) {
    try {
        const currentUser = await getCurrentUser();

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

        const updatedMessage = "";
        // todo update seen of last message where lastMessage.id user'ı seen set'ine ekle

        return NextResponse.json(updatedMessage);
    } catch (error: any) {
        console.log(error, 'ERROR_MESSAGES_SEEN');
        return new NextResponse("Internal Error", {status: 500});
    }
}