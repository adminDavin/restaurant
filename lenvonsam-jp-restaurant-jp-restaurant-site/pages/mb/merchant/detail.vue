<template lang="pug">
.padding-lg.bg-light-green
  .ft-24.ft-bold.mt-15 {{merchantObj.merchantName}}
  a-button.no-border.full-width.mt-25(type="primary", size="large" ,style="background: #8dbc3c", @click="jump('/mb/appoint?mid=' + $route.query.id)") 予約する
    a-icon(type="edit")
  .text-gray.mt-25
    span 電話：{{merchantObj.phone}}
    .mt-5 住所：
    .mt-5 {{merchantObj.address}}
    .mt-5 営業時間：
    .mt-5 {{merchantObj.openTimeInfo}}
  .row.mt-15(style="height: 42px")
    .col(v-for="n in 6")
      img(:src="getMerchantServices(merchantObj.infrastructureMark, n)", style="width: 36px")
  .row.mt-25.pb-35.border-bottom-line
    .col.text-center
      a-button.no-border(type="primary", style="background: #8dbc3c", shape="round", @click="clipboardCb") 情報コピー
        a-icon(type="copy")
    .col.text-center
      a-button.no-border(type="primary", style="background: #8dbc3c", shape="round", @click="merchantFileDownload") メニュー
        a-icon(type="profile")
  .pt-25.pb-25.border-bottom-line
    .text-center(v-for="(gallery, idx) in merchantObj.gallery", :key="idx", :class="{'mb-25': idx < (merchantObj.gallery.length - 1) }")
      img(:src="gallery.fileUrl", style="width: 80%; margin: 0px auto")
    .pt-25
      .row.text-center(style="width: 84%; margin: 0 auto")
        .col
          img(src="~assets/imgs/fb.png", style="width: 50px", @click="openNewWindow(merchantObj.facebookSite)")
        .col
          img(src="~assets/imgs/tw.png", style="width: 50px", @click="openNewWindow(merchantObj.twSite)")
        .col
          img(src="~assets/imgs/ins.png", style="width: 50px", @click="openNewWindow(merchantObj.insSite)")
        .col
          img(src="~assets/imgs/line.png", style="width: 50px", @click="openNewWindow(merchantObj.lineSite)")
  .pt-35.pb-35.border-bottom-line
    .ft-18.ft-bold メニュー
    .mt-25(v-for="(menu, idx) in merchantProductMenus", :key="idx")
      img.full-width(:src="menu.productCoverUrl", style="border-radius: 14px")
      .ft-16.mt-15 {{menu.productTitle}}
      .ft-16.text-gray {{menu.productSubTitle}}
      div(style="color: #AD9751") {{menu.productDesc}}
      .ft-12.text-gray {{menu.productDescUrl}}
  .pt-35.pb-35.border-bottom-line  
    .ft-18.ft-bold 地図
    .text-center.mt-25
      img(:src="merchantObj.mapUrl", style="width: 94%")
  .pt-35.pb-35.border-bottom-line(v-for="(card, idx) in merchantCards", :key="idx")
      .ft-18.ft-bold {{card.title}}
      .mt-25.ft-10.text-gray(v-html="card.content")
  
  .mt-35.text-center
    img.full-width(src="~assets/imgs/drink_bill.jpeg")
</template>

<script lang="ts">
import { Vue, Component, Watch } from 'vue-property-decorator'
import {
  MerchantDTO,
  MerchantCardDTO,
  MerchantProductDTO,
} from '@/models/MerchantModels'
@Component({
  layout: 'mobile',
})
class MobileMerchantDetailPage extends Vue {
  merchantDetailId: string = ''
  merchantObj: MerchantDTO = {
    id: '',
    merchantCode: '',
    merchantName: '',
    email: '',
    thumbUrl: '',
    phone: '',
    address: '',
    uberSite: '',
    infrastructureMark: '',
    intro: '',
    openTimeInfo: '',
    busiType: '',
    gallery: [],
  }
  merchantCards: Array<MerchantCardDTO> = []
  beforeMount() {
    this.merchantDetailId = this.$route.query.id as string
    this.loadMerchantDetail()
  }

  @Watch('$route.query.id')
  onMerchantIdChange(val: string) {
    if (val !== this.merchantDetailId) {
      this.merchantDetailId = val
      this.loadMerchantDetail()
    }
  }

  get clipMessage() {
    return `電話：${this.merchantObj.phone}\n住所：\n${this.merchantObj.address}\n営業時間：\n${this.merchantObj.openTimeInfo}`
  }

  clipboardCb() {
    const me = this as any
    me.$copyText(this.clipMessage).then(
      function (e: any) {
        me.msgShow('copy success', 'success')
      },
      function (err: any) {
        me.msgShow('copy fail')
      }
    )
  }

  merchantFileDownload() {
    window.location.href = 'http://jprestaurant.thinkingsam.cn/jprst_file.pdf'
  }

  getMerchantServices(infrastructureMark: string, index: number) {
    const mark = infrastructureMark.substring(index - 1, index)
    if (mark === '1') {
      return require('../../../assets/imgs/icon_' + (index - 1) + '_on.png')
    } else {
      return require('../../../assets/imgs/icon_' + (index - 1) + '_off.png')
    }
  }

  async loadMerchantDetail() {
    try {
      const { data } = await this.proxy(
        'api/sys/merchant/info/' + this.merchantDetailId
      )
      this.merchantObj = data.data
      this.merchantObj.gallery = []
      this.loadMerchantGallery()
      this.loadMerchantCards()
      this.loadMerchantMenus()
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }
  merchantProductMenus: Array<MerchantProductDTO> = []
  async loadMerchantMenus() {
    try {
      const { data } = await this.proxy('api/sys/merchant/menuList/', 'get', {
        merchantCode: this.merchantObj.merchantCode,
      })
      this.merchantProductMenus = data.data
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }

  async loadMerchantGallery() {
    try {
      const { data } = await this.proxy('api/sys/merchant/gallery', 'get', {
        merchantCode: this.merchantObj.merchantCode,
      })
      console.log('gallery:>>', data.data)
      this.merchantObj.gallery = data.data
      this.$forceUpdate()
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }

  async loadMerchantCards() {
    try {
      const { data } = await this.proxy('api/sys/merchant/cards', 'get', {
        merchantCode: this.merchantObj.merchantCode,
      })
      this.merchantCards = data.data
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }
}
export default MobileMerchantDetailPage
</script>

<style lang="stylus" scoped>
/deep/button[ant-click-animating-without-extra-node]:after
  border 0 none
  opacity 0
  animation none
</style>
