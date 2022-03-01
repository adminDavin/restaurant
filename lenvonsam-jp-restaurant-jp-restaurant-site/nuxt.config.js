export default {
  mode: 'universal',
  // Global page headers: https://go.nuxtjs.dev/config-head
  server: {
    // port: config.port,
    host: '0.0.0.0',
    port: 6818,
  },
  head: {
    title: 'SitaDining',
    meta: [
      { charset: 'utf-8' },
      { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      {
        hid: 'description',
        name: 'description',
        content:
          '健康とスパイスを中心に考えた料理を提供するSita Dining’sは東京目黒を中心にレストラン営業展開中。デリバリーや各種祭事なども積極的に参加いたします。そして今、お客様からの多くのお声をいただき、新たにカレーの通販サイトも開設。全国各地の皆様にもおうちなどでSita Dining’sの味をお楽しみ頂ければと思います！',
      },
      { name: 'format-detection', content: 'telephone=no' },
    ],
    link: [{ rel: 'icon', type: 'image/x-icon', href: '/favicon.ico' }],
  },

  // Global CSS: https://go.nuxtjs.dev/config-css
  css: ['ant-design-vue/dist/antd.css'],

  // Plugins to run before rendering page: https://go.nuxtjs.dev/config-plugins
  plugins: [
    '@/plugins/antd-ui',
    '@/plugins/mixins',
    '@/plugins/components',
    '@/plugins/i18n',
    { src: '@/plugins/kindeditor', ssr: false },
    // { src: '@/plugins/googlemap', ssr: false },
    { src: '@/plugins/clipboard', ssr: false },
  ],

  // Auto import components: https://go.nuxtjs.dev/config-components
  components: false,

  // Modules for dev and build (recommended): https://go.nuxtjs.dev/config-modules
  buildModules: [
    // https://go.nuxtjs.dev/typescript
    '@nuxt/typescript-build',
  ],
  serverMiddleware: [{ path: '/proxy', handler: '~/server/proxy.js' }],

  // Modules: https://go.nuxtjs.dev/config-modules
  modules: [
    // https://go.nuxtjs.dev/axios
    '@nuxtjs/axios',
    // https://go.nuxtjs.dev/pwa
    '@nuxtjs/pwa',
  ],

  // Axios module configuration: https://go.nuxtjs.dev/config-axios
  axios: {},

  // PWA module configuration: https://go.nuxtjs.dev/pwa
  pwa: {
    manifest: {
      lang: 'en',
    },
  },

  // Build Configuration: https://go.nuxtjs.dev/config-build
  build: {
    transpile: [/^vue2-google-maps($|\/)/],
  },
}
