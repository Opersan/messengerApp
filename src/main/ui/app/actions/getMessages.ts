import axios from "axios";
import getSession from "@/app/actions/getSession";

const getMessages = async (
    conversationId: string
) => {
    try {
        const session = await getSession();
        const access_token = session?.token;

        const messages = await axios.get(process.env.SPRING_API_URL + '/api/messages/messagesByConversationId', {
            params: {
                conversationId: conversationId
            }, headers: {'Authorization' : `Bearer ${access_token}`}
        }); // todo conversationId'ye sahip hepsini al createdAt asc

        return messages.data;
    } catch (error: any) {
        return [];
    }
}

export default getMessages