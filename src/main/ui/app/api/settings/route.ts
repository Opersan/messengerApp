import {NextResponse} from "next/server";
import getCurrentUser from "@/app/actions/getCurrentUser";
import axios from "axios";
import getSession from "@/app/actions/getSession";

export async function POST(
    request: Request
) {
    try {
        const currentUser = await getCurrentUser();
        const session = await getSession();
        const access_token = session?.token;
        const body = await request.json();

        axios.interceptors.request.use(
            config => {
                config.headers['Authorization'] = `Bearer ${access_token}`;
                return config;
            },
            error => {
                return Promise.reject(error);
            }
        );

        const {
            name,
            email,
            image
        } = body;

        if (!currentUser?.id) {
            return new NextResponse( 'Unauthorized', { status: 401});
        }

        const updatedUser = await axios.put( process.env.SPRING_API_URL + '/api/updateUser', {
                name: name,
                email: email,
                image: image

        });

        return NextResponse.json(updatedUser.data);

    } catch (error: any) {
        console.log(error, 'ERROR_SETTINGS');
        return new NextResponse('Internal Error', { status: 500});
    }
}