import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";
import getSession from "@/app/actions/getSession";

const getConversations =  async () => {
    const currentUser = await getCurrentUser();
    const session = await getSession();
    const access_token = session?.token;

    if (!currentUser?.id) {
        return [];
    }

    try {
        const conversations =  await axios.get(process.env.SPRING_API_URL + '/api/conversations/allConversationsByUserId', {
            params: {id: currentUser.id}, headers: {'Authorization' : `Bearer ${access_token}`}
        });

        return conversations.data;
    } catch (error: any) {
        return [];
    }
}

export default getConversations;