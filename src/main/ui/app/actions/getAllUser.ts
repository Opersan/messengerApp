import getSession from "@/app/actions/getSession";
import axios from "axios";

const getAllUsers = async () => {
    const session = await getSession();

    if(!session?.user?.email) {
        return [];
    }

    try {
        const users = await axios.get(`/api/users`, {params: {email: session?.user?.email}});
        return users.data;
    } catch (error: any) {
        return [];
    }
}

export default getAllUsers;