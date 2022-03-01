import Vue from 'vue'
import axios from 'axios'
let msgInstance = null
function formatNumber(n) {
  n = n.toString()
  return n[1] ? n : '0' + n
}
const mixins = {
  methods: {
    isMobile() {
      const flag = navigator.userAgent.match(
        /(phone|pad|pod|iPhone|iPod|ios|iPad|Android|Mobile|BlackBerry|IEMobile|MQQBrowser|JUC|Fennec|wOSBrowser|BrowserNG|WebOS|Symbian|Windows Phone)/i
      )
      return flag
    },
    fullScreen() {
      const elem = document.documentElement
      if (elem.requestFullscreen) {
        elem.requestFullscreen()
      } else if (elem.webkitRequestFullScreen) {
        elem.webkitRequestFullScreen()
      } else if (elem.mozRequestFullScreen) {
        elem.mozRequestFullScreen()
      } else if (elem.msRequestFullscreen) {
        elem.msRequestFullscreen()
      } else {
        console.error('全屏操作失败')
      }
    },
    date2Str(date) {
      if (date) {
        let n = date
        if (typeof date === 'number') {
          n = new Date(date)
        }
        const years = n.getFullYear()
        const month = n.getMonth() + 1
        const day = n.getDate()
        return [years, month, day].map(formatNumber).join('-')
      } else {
        return ''
      }
    },
    jump(to) {
      if (this.$router) this.$router.push(to)
    },
    back() {
      if (this.$router) this.$router.go(-1)
    },
    redirect(to) {
      if (this.$router) this.$router.replace(to)
    },
    msgShow(text, type = 'error', duration = 3) {
      const typeArr = ['success', 'error', 'info', 'warning', 'warn', 'loading']
      const msgType = typeArr.includes(type) ? type : 'error'
      if (msgInstance) return
      if (this.$message) {
        msgInstance = this.$message[msgType](text, duration, function () {
          msgInstance = null
        })
      }
    },
    confirm(config) {
      if (this.$confirm) {
        const me = this
        const dialogConfig = Object.assign({}, config)
        delete dialogConfig.onOk
        delete dialogConfig.onCancel
        return new Promise((resolve, reject) => {
          dialogConfig.onOk = function () {
            resolve('confirm')
          }
          dialogConfig.onCancel = function () {
            resolve('cancel')
          }
          me.$confirm(dialogConfig)
        })
      } else {
        return null
      }
    },
    arr2D(array, full = true, cols = 3) {
      const row = Math.ceil(array.length / cols)
      const doubleArr = []
      for (let i = 0; i < row; i++) {
        const tempRow = []
        for (let j = i * cols; j < (i + 1) * cols; j++) {
          if (j < array.length) {
            tempRow.push(array[j])
          } else if (full) tempRow.push('')
        }
        doubleArr.push(tempRow)
      }
      return doubleArr
    },
    request(config) {
      return new Promise((resolve, reject) => {
        let load = null
        if (config.params && config.params.loading && this.$loading) {
          load = this.$loading({
            lock: true,
            text: '',
            spinner: 'el-icon-loading',
            background: 'rgba(0,0,0,.7)',
          })
        }

        axios({
          method: 'post',
          url:
            document.location.protocol +
            '//' +
            window.location.host +
            config.url,
          data: config.params,
        })
          .then((res) => {
            console.log('request res:>>', res)
            if (res.data.success) {
              if (load) {
                load.close()
                load = null
              }
              resolve(res)
            } else {
              if (load) {
                load.close()
                load = null
              }
              // this.$message.error(res.data.message)
              reject(res.data)
            }
          })
          .catch((err) => {
            console.log(err)
            if (load) {
              load.close()
              load = null
            }
            reject(err)
          })
      })
    },
    proxy(url, method = 'get', params = {}) {
      return this.request({
        url: '/proxy/common/' + method,
        params: {
          method,
          url,
          params,
        },
      })
    },
    openNewWindow(href) {
      window.open(href, '_blank')
    },
  },
}

Vue.mixin(mixins)
