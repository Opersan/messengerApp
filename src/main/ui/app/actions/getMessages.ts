const getMessages = async (
    conversationId: string
) => {
    try {
        const messages = [{}]; // conversationId'ye sahip hepsini al createdAt asc

        return messages;
    } catch (error: any) {
        return [];
    }
}

export default getMessages