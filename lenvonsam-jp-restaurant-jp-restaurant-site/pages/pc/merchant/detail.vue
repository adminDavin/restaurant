<template lang="pug">
.pc-content.padding-lg.bg-light-green
  a-row.border-bottom-line.pb-25
    a-col(:span="12")
      .ft-24 {{merchantObj.merchantName}}
    a-col(:span="8")
    a-col(:span="4")
      a-button.no-border.full-width(type="primary", size="large" ,style="background: #8dbc3c", @click="jump('/pc/appoint?mid=' + $route.query.id)") 予約する
        a-icon(type="edit")
  .ft-24.pt-25 基本情報
  a-row.text-gray.mt-25
    a-col(:span="1")
    a-col.ft-18(:span="15")
      span 電話：{{merchantObj.phone}}
      .mt-5 住所：
      .mt-5 {{merchantObj.address}}
      .mt-5 営業時間：
      .mt-5 {{merchantObj.openTimeInfo}}
    a-col(:span="8")
      a-row.mt-15
        a-col.text-center(:span="8", v-for="n in 6", :key="n")
          img.mb-20(:src="getMerchantServices(merchantObj.infrastructureMark, n)", style="width: 50px")
      .mt-25.mb-45
        .row.text-center(style="width: 88%; margin: 0 auto")
          .col
            img(src="~assets/imgs/fb.png", style="width: 50px", @click="openNewWindow(merchantObj.facebookSite)")
          .col
            img(src="~assets/imgs/tw.png", style="width: 50px", @click="openNewWindow(merchantObj.twSite)")
          .col
            img(src="~assets/imgs/ins.png", style="width: 50px", @click="openNewWindow(merchantObj.insSite)")
          .col
            img(src="~assets/imgs/line.png", style="width: 50px", @click="openNewWindow(merchantObj.lineSite)")
      .row.mt-25
        .col.text-center
          a-button.no-border(type="primary", style="background: #8dbc3c", shape="round", @click="clipboardCb") 情報コピー
            a-icon(type="copy")
        .col.text-center
          a-button.no-border(type="primary", style="background: #8dbc3c", shape="round", @click="merchantFileDownload") メニュー
            a-icon(type="profile")
  .row.mt-25.pb-35.border-bottom-line
  .pt-25.pb-25.border-bottom-line
    .ft-24 店内写真
    a-carousel(:autoplay="merchant2DGallery.length > 1")
      .full-width.mt-25.flex(style="height: 250px", v-for="(row, idx) in merchant2DGallery", :key="idx")
        .row
          .col.flex.justify-center(v-for="(col, cidx) in row", :key="idx + '-' + cidx")
            .pc-banner-bg.merchant-gallery-card(:style="{backgroundImage: 'url(' +col.fileUrl + ')' }")
    //- .text-center.pt-25(v-for="(gallery, idx) in merchantObj.gallery", :key="idx", :class="{'mb-25': idx < (merchantObj.gallery.length - 1) }")
      img(:src="gallery.fileUrl", style="width: 80%; margin: 0px auto")
  .pt-25.pb-25.border-bottom-line  
    .ft-24 地図
    .mt-15.merchant-map-container(:style="{backgroundImage: 'url(' + merchantObj.mapUrl +')'}")
      //- GmapMap(ref="mapRef", :center="{lat:10, lng:10}", :zoom="7",map-type-id="terrain",style="width: 900px; height: 440px; margin: 0 auto")
  .pt-35.pb-35.border-bottom-line(v-for="(card, idx) in merchantCards", :key="idx")
    .ft-24 {{card.title}}
    a-row.mt-25
      a-col(:span="1")
      a-col(:span="23")
        .ft-18.text-gray(v-html="card.content")
  .pt-35.pb-35.border-bottom-line
    .ft-24 メニュー
    a-row
      a-col.mt-25(:span="8", v-for="(menu, idx) in merchantProductMenus", :key="idx")
        div(style="width: 80%;margin: 0 auto")
          img.full-width(:src="menu.productCoverUrl", style="border-radius: 14px;")
          .ft-20.mt-15(style="height: 30px;overflow:hidden") {{menu.productTitle}}
          a-tooltip(placement="right", :title="menu.productSubTitle")
            .ft-18.text-gray.ellps(style="height: 27px;overflow:hidlen;line-height: 27px") {{menu.productSubTitle}}
          a-tooltip(placement="right", :title="menu.productDesc")
            .ellps.ellps-2(style="color: #AD9751;height:48px;overflow:hidden;line-height:24px") {{menu.productDesc}}
          a-tooltip(placement="right", :title="menu.productDescUrl")
            .text-gray.ellps.ellps-3(style="overflow:hidden; height: 72px;line-height:24px") {{menu.productDescUrl}}
  .mt-35.text-center
    img(src="~assets/imgs/drink_bill.jpeg")
</template>

<script lang="ts">
import { Vue, Component, Watch } from 'vue-property-decorator'
import {
  MerchantDTO,
  MerchantCardDTO,
  MerchantProductDTO,
} from '@/models/MerchantModels'
@Component({
  layout: 'pc',
})
class PcMerchantDetailPage extends Vue {
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
    if (this.merchantDetailId !== val) {
      this.merchantDetailId = val
      this.loadMerchantDetail()
    }
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
  merchant2DGallery: any = []
  async loadMerchantGallery() {
    try {
      const { data } = await this.proxy('api/sys/merchant/gallery', 'get', {
        merchantCode: this.merchantObj.merchantCode,
      })
      console.log('gallery:>>', data.data)
      this.merchantObj.gallery = data.data
      this.merchant2DGallery = this.arr2D(data.data, true, 2)
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
}
export default PcMerchantDetailPage
</script>

<style lang="stylus" scoped>
.merchant-gallery-card
  width 380px
  height 250px
  border-radius 20px
/deep/button[ant-click-animating-without-extra-node]:after
  border 0 none
  opacity 0
  animation none
.merchant-map-container
  width 900px
  height 600px
  margin 0 auto
  background-position center
  background-size cover
</style>
