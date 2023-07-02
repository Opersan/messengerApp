import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";
import getSession from "@/app/actions/getSession";

const getConversationById = async (
    conversationId: string
) => {
    try {
        const currentUser = await getCurrentUser();
        const session = await getSession();
        const access_token = session?.token;

        if (!currentUser!.email) {
            return null;
        }

        const conversation = await axios.get(process.env.SPRING_API_URL + '/api/conversations/conversationById', {
            params: {
                conversationId: conversationId
            },
            headers: {
                'Authorization' : `Bearer ${access_token}`
            }
        });
        return conversation.data;
    } catch (error: any) {
        return null;
    }
}

export default getConversationById;