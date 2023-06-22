import {useSession} from "next-auth/react";
import {useMemo} from "react";
import {Conversation, User} from "@/app/types";

const useOtherUser = (conversation: Conversation | {
    users: User[]
}) => {
    const session = useSession();

    return useMemo(() => {
        const currentUserEmail = session?.data?.user?.email;

        return conversation.users.filter((user) => user.email != currentUserEmail);
    }, [session?.data?.user?.email, conversation.users]);
}

export default useOtherUser;