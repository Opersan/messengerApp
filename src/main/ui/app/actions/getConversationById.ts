import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";

const getConversationById = async (
    conversationId: string
) => {
    try {
        const currentUser = await getCurrentUser();

        if (!currentUser!.email) {
            return null;
        }

        const conversation = await axios.get(process.env.SPRING_API_URL + '/api/conversations/conversationById', {
            params: {
                conversationId: conversationId
            }
        });
        return conversation.data;
    } catch (error: any) {
        return null;
    }
}

export default getConversationById;