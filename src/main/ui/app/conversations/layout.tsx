import Sidebar from "@/app/components/sidebar/Sidebar";
import ConversationList from "@/app/conversations/Components/ConversationList";
import getConversations from "@/app/actions/getConversations";
import getUser from "@/app/actions/getUser";

export default async function ConversationsLayout({
    children
}: {
    children: React.ReactNode
}){

    const conversations = await getConversations();
    const users = await getUser();

    return (
      <Sidebar>
          <div className="h-full">
              <ConversationList users={users} initialItems={conversations}/>
              {children}
          </div>
      </Sidebar>
    );
}