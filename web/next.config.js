/** @type {import('next').NextConfig} */
const nextConfig = {
  // Ensure JSON files are served correctly
  async headers() {
    return [
      {
        source: '/(.*\\.json)',
        headers: [
          {
            key: 'Content-Type',
            value: 'application/json',
          },
        ],
      },
    ]
  },
}

module.exports = nextConfig
