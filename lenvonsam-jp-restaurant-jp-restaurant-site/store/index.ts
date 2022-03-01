import { GetterTree, ActionTree, MutationTree } from 'vuex'
export const state = () => ({
  localLang: 'ja',
  currentMenu: [
    {
      name: 'sys.merchant',
      icon: 'desktop',
      url: '/bk/merchant'
    },
    {
      name: 'sys.merchantCard',
      icon: 'book',
      url: '/bk/merchant/card'
    },
    {
      name: 'sys.banner',
      icon: 'container',
      url: '/bk/banner'
    },
    {
      name: 'sys.appoint',
      icon: 'shop',
      url: '/bk/appoint'
    },
    {
      name: 'sys.product',
      icon: 'gift',
      url: '/bk/product'
    },
    {
      name: 'sys.menu',
      icon: 'menu',
      url: '/bk/product/menu'
    },
    {
      name: 'sys.account',
      icon: 'contacts',
      url: '/bk/user'
    },
    {
      name: 'sys.news',
      icon: 'snippets',
      url:  '/bk/news'
    }
  ]
})

export type RootState = ReturnType<typeof state>

export const getters: GetterTree<RootState, RootState> = {}

export const mutations: MutationTree<RootState> = {
  CONFIGVAL(state, {key, val}) {
    if (key === 'localLang') {
      state.localLang = val
    }
    // state[`${key}`] = val
  },
}

export const actions: ActionTree<RootState, RootState> = {
  async nuxtServerInit({ commit }, { req, $axios }) {
    const { data } = await $axios('/proxy/getUser')
    if(data.success && data.user) {
      commit('user/SETUSER', data.user)
    } else {
      commit('user/EXITUSER')
    }
  },
  configVal({ commit }, {key, val}) {
    commit('CONFIGVAL', {key: key, val: val})
  }
}
