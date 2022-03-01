import { GetterTree, ActionTree, MutationTree } from 'vuex'
import { RootState } from './index'

export const state = () => ({
  currentUser: {
    userCode: '',
    userName: '游客'
  }
})

export type UserState = ReturnType<typeof state>

export const getters: GetterTree<UserState, RootState> = {}
export const mutations: MutationTree<UserState> = {
  SETUSER(state, userObj) {
    state.currentUser = userObj
  },
  EXITUSER(state) {
    state.currentUser = {
      userCode: '',
      userName: ''
    }
  }
}
export const actions: ActionTree<UserState, RootState> = {
  setUser({ commit }, userObj) {
    commit('SETUSER', userObj)
  },
  exitUser({ commit }) {
    commit('EXITUSER')
  }
}
