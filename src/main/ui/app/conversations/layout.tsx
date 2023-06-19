import Sidebar from "@/app/components/sidebar/Sidebar";
import ConversationList from "@/app/conversations/Components/ConversationList";
import getConversations from "@/app/actions/getConversations";

export default async function ConversationsLayout({
    children
}: {
    children: React.ReactNode
}){
    const conversations = await getConversations();
    console.log(conversations);

    return (
      <Sidebar>
          <div className="h-full">
              <ConversationList initialItems={conversations}/>
              {children}
          </div>
      </Sidebar>
    );
}