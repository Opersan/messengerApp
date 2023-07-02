import getSession from "@/app/actions/getSession";
import axios from "axios";


const getCurrentUser = async () => {
    try {
        const session = await getSession();
        const access_token = session?.token;

        if (!session?.user?.email) {
            return null;
        }
        const currentUser = await axios.get(process.env.SPRING_API_URL + `/api/userByEmail`,
            {params: {email: session?.user?.email}, headers: {'Authorization' : `Bearer ${access_token}`}});
        if (!currentUser) {
            return null;
        }

        return currentUser.data;

    } catch (error : any) {
        return null;
    }
}

export default getCurrentUser;