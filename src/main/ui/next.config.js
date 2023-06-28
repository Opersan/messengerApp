/** @type {import('next').NextConfig} */

const nextConfig = {
    experimental: {
        swcPlugins: [
            ["next-superjson-plugin", {}]
        ]
    },
    images: {
        domains: [
            "res.cloudinary.com",
            "avatars.githubusercontent.com",
            "lh3.googleusercontent.com"
        ]
    },
    async rewrites() {
        return [
            {
                source: '/api/auth/save',
                destination: 'http://localhost:8080/api/auth/save',
            }
        ]
    },
}

module.exports = nextConfig
