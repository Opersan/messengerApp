import { withAuth} from "next-auth/middleware";
import exp from "constants";

export default withAuth({
    pages: {
        signIn: "/"
    }
});

export const config = {
    matcher: [
        "/users/:path*"
    ]
};