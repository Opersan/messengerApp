import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";

export async function POST(
    request: Request
) {
    try {
        const currentUser = await getCurrentUser();
        const body = await request.json();

        const {
            name,
            image
        } = body;

        if (!currentUser?.id) {
            return new NextResponse( 'Unauthorized', { status: 401});
        }

        const updatedUser = await axios.post('');
        // name ve image güncelle user için

        return NextResponse.json(updatedUser.data);

    } catch (error: any) {
        console.log(error, 'ERROR_SETTINGS');
        return new NextResponse('Internal Error', { status: 500});
    }
}