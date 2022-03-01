const axios = require('axios')
const Qs = require('qs')
const cookie = 'JSESSIONID=CFD195FB182E02ADE7366E1DB86758E4'
const headConfig = {
  Connection: 'keep-alive',
  Cookie: cookie
}
function configAuthorization(req) {
  delete headConfig.authorization
  if (req && req.session.currentUser) {
    headConfig.authorization = 'u_' + req.session.currentUser.userCode
  }
}
module.exports = {
  httpGet(url) {
    return axios({
      method: 'get',
      url,
      headers: headConfig
    })
  },
  httpPost(url, body, req) {
    configAuthorization(req)
    return axios({
      method: 'post',
      url,
      headers: headConfig,
      data: body
    })
  },
  httpPostForm(url, body, req) {
    headConfig.ContentType = 'multipart/form-data'
    configAuthorization(req)
    return axios({
      method: 'post',
      url,
      params: body,
      headers: headConfig,
      paramsSerializer: (params) => {
        return Qs.stringify(params, { arrayFormat: 'brackets' })
      }
    })
  },
  httpDelete(url, body, req) {
    configAuthorization(req)
    return axios({
      method: 'delete',
      url,
      params: body,
      headers: headConfig,
      paramsSerializer: (params) => {
        return Qs.stringify(params, { arrayFormat: 'brackets' })
      }
    })
  },
  httpStreamPost(url, body) {
    return axios.post(url, body)
  },
  httpPut(url, body, req) {
    configAuthorization(req)
    return axios({
      method: 'put',
      url,
      params: body,
      headers: headConfig,
      paramsSerializer: (params) => {
        return Qs.stringify(params, { arrayFormat: 'brackets' })
      }
    })
  }
}
