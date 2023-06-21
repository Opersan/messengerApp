import axios from "axios";

const getMessages = async (
    conversationId: string
) => {
    try {
        const messages = await axios.get(process.env.SPRING_API_URL + '/api/messages/messagesByConversationId', {
            params: {
                conversationId: conversationId
            }
        }); // conversationId'ye sahip hepsini al createdAt asc
        console.log(messages.data)
        return messages.data;
    } catch (error: any) {
        return [];
    }
}

export default getMessages