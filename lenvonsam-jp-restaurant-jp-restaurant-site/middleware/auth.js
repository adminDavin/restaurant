export default function ({ redirect, store, route }) {
  if (route.path.startsWith('/bk')) {
    const userCode = store.state.user.currentUser.userCode || ''
    if (userCode === '') {
      return route.path !== '/bk/login' ? redirect('/bk/login') : true
    } else {
      return true
    }
  } else {
    return true
  }
}
