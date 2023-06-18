import {ReactNode} from "react";
import DesktopSidebar from "@/app/components/sidebar/DesktopSidebar";
import MobileSidebar from "@/app/components/sidebar/MobileSidebar";
import getCurrentUser from "@/app/actions/getCurrentUser";

async function Sidebar({ children }: {
    children: ReactNode
}) {
    const currentUser = await getCurrentUser();
    return (
        <div className="h-full">
            <DesktopSidebar currentUser={currentUser!}/>
            <MobileSidebar/>
            <main className="lg:pl-20 h-full">
                {children}
            </main>
        </div>
    )
}

export default Sidebar;