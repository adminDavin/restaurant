const fs = require('fs')

const multipart = require('connect-multiparty')
const multipartMiddleware = multipart()
const rqt = require('request')
const qs = require('qs')

const express = require('express')
const bodyParser = require('body-parser')
const router = express()
const session = require('express-session')
const redis = require('redis')
const RedisStore = require('connect-redis')(session)
const sessionMiddleware = session({
  secret: 'Asecret123-',
  resave: true,
  rolling: true,
  saveUninitialized: false,
  store: new RedisStore({
    client: redis.createClient({
      host: '160.251.123.93',
      port: '6379',
      db: 8,
      password: 'jprsst888',
    }),
  }),
})

const hptl = require('../utils/httpUtil')
// const proxyUrl = 'http://172.16.120.229:9901/api/' // 马
// const proxyUrl = 'http://192.168.80.210:8188/api/oa/' // dev
// const proxyUrl = 'http://127.0.0.1:8086/' // local
// const proxyUrl = 'http://10.31.86.129:8086/' // prod
const proxyUrl = 'http://160.251.123.93:8086/' // online
// const proxyUrl = 'http://127.0.0.1:8897/api/' // local
// 银企直连型云农行查询proxy

router.use(sessionMiddleware)
router.use(bodyParser.json({ limit: '50mb' }))
router.use(bodyParser.urlencoded({ limit: '50mb', extended: true }))

router.get('/hello', (req, res) => {
  console.log('helle session', req.session)
  res.json({ text: 'hello world' })
})

router.get('/getUser', (req, res) => {
  if (req.session.currentUser) {
    res.json({ success: true, user: req.session.currentUser })
  } else {
    res.json({ success: false, message: 'user expire' })
  }
})

router.post('/logout', (req, res) => {
  console.log('user logout')
  if (req.session.currentUser) {
    delete req.session.currentUser
  }
  res.json({ success: true, message: 'success' })
})

router.post('/fileUpload', multipartMiddleware, (req, res) => {
  console.log('fileupload , ====...==')
  const url = req.body.url || 'sys/file/single/upload'
  console.log('files------', req.files)
  if (!url) {
    res.json({ success: false, message: '参数未指定' })
    return
  }
  var file = req.files.file
  if (req.files.imgFile) {
    file = req.files.imgFile
  }
  const formData = {
    file: fs.createReadStream(file.path),
  }
  if (req.files.imgFile) {
    formData.ide = 'keditor'
  }
  if (req.body.headerName) formData.headerName = req.body.headerName
  if (req.body.valueName) formData.valueName = req.body.valueName
  if (req.body.channel) formData.channel = req.body.channel
  if (req.body.busiType) formData.busiType = req.body.busiType
  formData.fileType = 1
  if (req.body.fileType) formData.fileType = Number(req.body.fileType)
  console.log('request formData:>>', formData)
  rqt.post(
    {
      url: proxyUrl + url,
      formData,
      // headers: { 'zhdcrm-proxy-token': hptl.proxyToken('zhdcrm') }
    },
    function (err, resp, body) {
      console.log('err:>>', err)
      console.log('body:>>', typeof body)
      fs.unlink(file.path, function (err) {
        if (err) console.error(err)
        else console.log('temp file delete success')
      })
      res.send(body)
    }
  )
})

function getClientIP(req) {
  return (
    req.headers['x-forwarded-for'] || // 判断是否有反向代理 IP
    req.connection.remoteAddress || // 判断 connection 的远程 IP
    req.socket.remoteAddress || // 判断后端的 socket 的 IP
    req.connection.socket.remoteAddress
  )
}

function commGetMethod(req, res, url) {
  const body = req.body
  const ipAddress = getClientIP(req)
  console.log('get ip:>>', ipAddress)
  const paramsArr = []
  for (const key in body.params) {
    paramsArr.push(key + '=' + body.params[key])
  }
  paramsArr.push('ip=' + ipAddress)
  let paramsStr =
    paramsArr.length > 0 ? '?' + paramsArr.join(',').replace(/,/g, '&') : ''
  if (body.url.indexOf('?') > 0 && paramsStr.length > 1)
    paramsStr = '&' + paramsStr.substring(1)
  console.log('get url:>>' + body.url, ',>> params:>>' + paramsStr)
  hptl.httpGet(encodeURI(url + body.url + paramsStr)).then(
    ({ data }) => {
      res.json(data)
    },
    (err) => {
      console.log(err)
      res.json({ success: false, message: '网络异常' })
    }
  )
}

router.post('/common/postjson', (req, res) => {
  const body = req.body
  console.log('common postjson body:>>', body)
  hptl.httpStreamPost(proxyUrl + body.url, body.params).then(
    ({ data }) => {
      res.json(data)
    },
    (err) => {
      console.error(err)
      res.json({ success: false, message: '网络异常' })
    }
  )
})

router.post('/common/post', (req, res) => {
  const body = req.body
  console.log('common post body:>>>', body)
  const ipAddress = getClientIP(req)
  console.log('ip:>>', ipAddress)
  body.params.ip = ipAddress
  hptl.httpPost(proxyUrl + body.url, body.params, req).then(
    ({ data }) => {
      console.log('url:>>', body.url)
      if (body.url === 'user/account/login' && data.success) {
        console.log('enter')
        const currentUser = data.data
        req.session.currentUser = currentUser
        res.json(data)
      } else {
        console.log('not enter')
        res.json(data)
      }
    },
    (err) => {
      console.log('err:>>', err)
      res.json({ success: false, message: '网络异常' })
    }
  )
})

router.post('/common/put', (req, res) => {
  const body = req.body
  console.log(body)
  hptl.httpPut(proxyUrl + body.url, body.params).then(
    ({ data }) => {
      res.json(data)
    },
    (err) => {
      console.log(err)
      res.json({ success: false, message: '网络异常' })
    }
  )
})

router.post('/common/get', (req, res) => {
  commGetMethod(req, res, proxyUrl)
})

router.post('/common/del', (req, res) => {
  const body = req.body
  hptl.httpDelete(proxyUrl + body.url, body.params).then(
    ({ data }) => {
      res.json(data)
    },
    (err) => {
      console.log(err)
      res.json({ success: false, message: '网络异常' })
    }
  )
})
export default router
