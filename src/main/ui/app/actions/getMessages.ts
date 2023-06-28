import axios from "axios";

const getMessages = async (
    conversationId: string
) => {
    try {
        const messages = await axios.get('/api/messages/messagesByConversationId', {
            params: {
                conversationId: conversationId
            }
        }); // conversationId'ye sahip hepsini al createdAt asc

        return messages.data;
    } catch (error: any) {
        return [];
    }
}

export default getMessages