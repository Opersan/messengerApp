import getCurrentUser from "@/app/actions/getCurrentUser";
import {NextResponse} from "next/server";
import axios from "axios";
import {pusherServer} from "@/app/libs/pusher";

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

        if (members) {
            members.push({
                value: currentUser.id,
                label: currentUser.name
            })
        }

        if (isGroup) {
            const newConversation = await axios.post(process.env.SPRING_API_URL + '/api/conversations/createGroupConversation', {
                name: name,
                members: members
            });

            // @ts-ignore
            newConversation.data.users.forEach((user) => {
                if (user.email) {
                    pusherServer.trigger(user.email, 'conversation:new', newConversation);
                }
            })

            return NextResponse.json(newConversation.data);
        }

        const existingConversations = await axios.get(process.env.SPRING_API_URL + '/api/conversations/conversationByUserId', {
            params: {
                senderId: currentUser.id,
                receiverId: userId
            }
        });
        const singleConversation = existingConversations.data;

        if (singleConversation) {
            return NextResponse.json(singleConversation);
        }

        const newConversation = await axios.post(process.env.SPRING_API_URL + '/api/conversations/createConversation', {
            senderUser: {
                id: currentUser.id
            },
            receiverUser: {
                id: userId
            }
        });

        // @ts-ignore
        newConversation.data.users.forEach((user) => {
            if (user.email) {
                pusherServer.trigger(user.email, 'conversation:new', newConversation.data);
            }
        });

        return NextResponse.json(newConversation.data);
    } catch (error: any) {
        return new NextResponse('Internal Error', {status: 500});
    }
}