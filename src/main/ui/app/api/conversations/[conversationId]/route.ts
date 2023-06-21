import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";

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

        const existingConversation = await axios.delete(process.env.SPRING_API_URL + '', {
            params: {
                conversationId: conversationId
            }
        }); // todo silme işlemini sadece conversation'ın sahibi olan kullanıcı yapabilir bunu checkle

        if (!existingConversation) {
            return new NextResponse('Invalid ID!', {status: 400});
        }

        return NextResponse.json(existingConversation);

    } catch (error: any) {
        console.log(error, 'ERROR_CONVERSATION_DELETE');
        return new NextResponse('Internal Error', {status: 500});
    }
}