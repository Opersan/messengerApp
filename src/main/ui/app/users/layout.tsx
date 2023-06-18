import {ReactNode} from "react";
import Sidebar from "@/app/components/sidebar/Sidebar";
import getUser from "@/app/actions/getUser";
import UserList from "@/app/users/components/UserList";
export default async function UsersLayout({
    children
}: {
    children: ReactNode
}) {

    const users = await getUser();
    return (
        <Sidebar>
            <div className="h-full">
                <UserList items={users}/>
                {children}
            </div>
        </Sidebar>
    )
}