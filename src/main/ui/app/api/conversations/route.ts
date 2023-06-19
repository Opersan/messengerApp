import getCurrentUser from "@/app/actions/getCurrentUser";
import {NextResponse} from "next/server";
import axios from "axios";

export async function POST(
    request: Request
) {
    try {
        const currentUser = await getCurrentUser();
        const body = await request.json();
        const {
            userId,
            isGroup,
            members,
            name
        } = body;

        if (!currentUser?.email) {
            return new NextResponse('Unauthorized', {status: 401});
        }

        if (isGroup && (!members || members.length < 2 || !name)) {
            return new NextResponse('Invalid Data', {status: 400});
        }

        /*
        if (isGroup) {
            // todo spring api call ile yeni group conversation(members) oluştur
            const newConversation = "";
            return NextResponse.json(newConversation);
        }

        const existingConversations = await axios.post(process.env.SPRING_API_URL + '/api/conversations/conversationByUserID', {
            userId: currentUser.id
        });
        const singleConversation = existingConversations.data[0];

        if (singleConversation) {
            return NextResponse.json(singleConversation);
        }
         */

        const newConversation = await axios.post('http://localhost:8080' + '/api/conversations/createConversation', {
            senderUser: {
                id: currentUser.id
            },
            receiverUser: {
                id: userId
            }
        });

        return NextResponse.json(newConversation.data);
    } catch (error: any) {
        return new NextResponse('Internal Error', {status: 500});
    }
}