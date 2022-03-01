import VueI18 from 'vue-i18n'
import Vue from 'vue'
import jpbk from '../assets/langs/jp-bk'
import zhbk from '../assets/langs/zh-bk'
import enft from '../assets/langs/en-ft'
import zhft from '../assets/langs/zh-ft'
import jpft from '../assets/langs/jp-ft'

Vue.use(VueI18)
// 后台管理国际化
const bk18n = new VueI18({
  locale: 'ja',
  fallbackLocale: 'ja',
  messages: {
    zh: zhbk,
    ja: jpbk,
  },
})
Vue.prototype.$bk18n = bk18n

const pc18n = new VueI18({
  locale: 'ja',
  fallbackLocale: 'ja',
  messages: {
    zh: zhft,
    en: enft,
    ja: jpft,
  },
})
Vue.prototype.$pc18n = pc18n
