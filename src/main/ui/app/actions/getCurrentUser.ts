import getSession from "@/app/actions/getSession";
import axios from "axios";


const getCurrentUser = async () => {
    try {
        const session = await getSession();
        if (!session?.user?.email) {
            return null;
        }
        const currentUser = await axios.get(`/api/userByEmail`, {params: {email: session?.user?.email}});
        if (!currentUser) {
            return null;
        }
        return currentUser.data;

    } catch (error : any) {
        return null;
    }
}

export default getCurrentUser;