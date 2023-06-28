import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";

const getConversations =  async () => {
    const currentUser = await getCurrentUser();

    if (!currentUser?.id) {
        return [];
    }

    try {
        const conversations =  await axios.get('/api/conversations/allConversationsByUserId', {
            params: {id: currentUser.id}
        });

        return conversations.data;
    } catch (error: any) {
        return [];
    }
}

export default getConversations;