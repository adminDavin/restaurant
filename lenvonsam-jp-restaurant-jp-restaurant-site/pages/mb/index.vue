<template lang="pug">
a-layout(style="background: #fff")
  a-carousel(autoplay)
    div(v-for="(item,idx) in home_1", :key="idx", @click="clickBanner(item)")
      img.full-width(:src="item.bannerUrl", style="height: 250px")
  div(style="height: 200px")
    a-row.mb-ft-noto-medium(style="background:#EDF1E8;border-bottom: 1px solid #DAE2D2")
      a-col.home-card.flex.align-center.justify-center(:span="12")
        a.text-center(href="#mbMainIntro")
          div 店舗紹介
          div Store Information
      a-col.home-card.flex.align-center.justify-center(:span="12")
        a.text-center(href='https://sitadinning.base.shop/', target="_blank")
          div オンラインショップ
          div Online shop
    a-row.mb-ft-noto-medium(style="height: 100px;background:#EDF1E8")
      a-col.home-card.flex.align-center.justify-center(:span="12", @click="jump('/mb/appoint')")
        .text-center
          div 予    約
          div Reserve
      a-col.home-card.flex.align-center.justify-center(:span="12", @click="jump('/mb/intro')")
        .text-center
          div 会社紹介
          div Concept
  a-carousel.mt-25
    div(v-for="(item,idx) in home_2", :key="idx", @click="clickBanner(item)")
      img.full-width(:src="item.bannerUrl")
  .pt-30.text-center.ft-24.news-title.pc-ft-parisienne News&Topics
  .text-center.ft-bold.mt-10.pc-ft-noto-black お知らせ
  .padding
    .row.pb-10.pt-10.border-bottom-line(v-for="(news, idx) in newsList", :key="idx")
      .flex-80.text-green {{news.publishDate.substring(0, 10).replace(/-/g, '.')}}
      .col.ellps {{news.newsTitle}}
  .mt-20.text-center
    .m-reverse-button(@click="jump('/mb/news')") {{$pc18n.t('common.more')}}
  img.full-width.mt-35(src="~assets/imgs/intro_6.png")
  .mb-home-intro-bg(:style="{'height': homeIntroHeight + 'px'}")
    .box
      a-carousel.mt-25(autoplay)
        div(v-for="(item,idx) in home_3", :key="idx", @click="clickBanner(item)")
          img.full-width(:src="item.bannerUrl", style="height: 330px")
  .pt-30.text-center.ft-24.news-title.pc-ft-parisienne Concept
  .text-center.ft-bold.mt-10.pc-ft-noto-black 会社紹介
  .mt-25.text-center.ft-bold.text-green
    span {{$pc18n.t('home.section2.title_mb')}}
    br
    span {{$pc18n.t('home.section2.title_mb_1')}}
  .padding-lg
    .text-center.pt-25
        img(src="~assets/imgs/intro_2.png", style="width: 90%;")
    .text-66.mt-20
      span.ft-16.ft-bold たち
      span の料理は香辛料を多用した料理が多いのですが、そこは日本でも⻑期に腕を振るうシェフたちが日本の皆様の好みのさじ加減を十分に理解しておりますのできっと気に入って頂ける味だと信じています。
    .text-center.pt-25
      img(src="~assets/imgs/intro_3.png", style="width: 90%;")
    .text-66.mt-20
      span.ft-16.ft-bold 私たち
      span Sita Dining'sはスパイス料理を通して、皆さまに新しい感動を提供したく日々、研究・開発致しております。
      br
      br
      span.ft-16.ft-bold 料理
      span の出会いも一期一会。是非Sita Dining'sのお料理をお楽しみ下さい。
      //- span にも季節の野菜など多くの野菜を使い、健康食材とされる商品を多数使用して調理いたしております。
    .text-center.pt-25
      img(src="~assets/imgs/intro_4.png", style="width: 90%;")
    .text-66.mt-20
      span.ft-16.ft-bold 安心・
      span 安全にも取り組みます。料理の検体検査を始めとして、スタッフの健康検査なども定期的に実施。食品衛生管理の徹底に従事された作業習慣。皆様に安心・安全も提供します。
  .text-center
    .m-reverse-button(@click="jump('/mb/intro')") もっと詳しく
  .mb-home-rsst-bg.mt-35(:style="{'height': homeRsstHeight + 'px'}")
    .box
      a-carousel.mt-25(autoplay)
        .pc-banner-bg(v-for="(item,idx) in home_4", :key="idx", :style="{'height': '227px', backgroundImage: 'url(' + item.bannerUrl + ')'}", @click="clickBanner(item)")
  #mbMainIntro.pt-30.text-center.ft-24.news-title.pc-ft-parisienne Store Introduce
  .text-center.ft-bold.mt-10.pc-ft-noto-black 店舗紹介
  .padding-lg
    .mb-30.pb-25(v-for="(m, idx) in merchantList", style="border-bottom: 1px solid #959595")    
      .ft-18 {{m.merchantName}}
      .row(style="height: 40px")
        .flex-30(v-for="n in 6")
          img(:src="getMerchantServices(m.infrastructureMark, n)", style="width: 20px")
        .col.text-right
          img(src="~assets/imgs/icon_ubereats.png", style="height: 20px", @click="openNewWindow(m.uberSite)")
      .padding.mt-10(style="border: 2px solid #8DBC3C; border-radius: 4px;")
        img.full-width(:src="m.thumbUrl")
      .text-gray.pt-15
        span {{$pc18n.t('home.section3.card.phone')}}： 
        br
        span {{m.phone}}
        .mt-10
        span {{$pc18n.t('home.section3.card.address')}}：
        br
        span {{m.address}}
        .mt-10
        span {{$pc18n.t('home.section3.card.openTime')}}：
        br
        span {{m.openTimeInfo}}
      .mt-20
        .row
          .col
            .m-reverse-button(style="width: 80%", @click="jump('/mb/merchant/detail?id=' + m.id)") {{$pc18n.t('common.more')}}
          .col
            .m-button(style="width: 80%; height: 38px; font-size: 16px; border-radius: 20px", @click="jump('/mb/appoint?mid=' + m.id)") {{$pc18n.t('common.appoint')}}
    .mt-30.text-center
      img(src="~assets/imgs/onlineshop.png", style="margin: 0px auto; width: 80%")
      br
      img.mt-20(src="~assets/imgs/onlineshop_text.png", style="margin: 0px auto; width: 60%")
    .text-center.mt-25
      .m-reverse-button(@click="openNewWindow('https://sitadinning.base.shop')") {{$pc18n.t('common.more')}}
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import Calendar from '@/components/mobile/Calendar.vue'
import MInput from '@/components/mobile/MInput.vue'
import MCard from '@/components/mobile/MCard.vue'
import { BannerDTO } from '@/models/BannerModels'
import { NewsDTO } from '@/models/NewsModels'
import { MerchantDTO } from '@/models/MerchantModels'
@Component({
  layout: 'mobile',
  components: {
    Calendar,
    MInput,
    MCard,
  },
})
class MobileIndexPage extends Vue {
  visible: boolean = false
  homeIntroHeight: number = 1024
  homeRsstHeight: number = 987
  openMenu() {
    this.visible = !this.visible
  }
  getMerchantServices(infrastructureMark: string, index: number) {
    const mark = infrastructureMark.substring(index - 1, index)
    if (mark === '1') {
      return require('../../assets/imgs/icon_' + (index - 1) + '_on.png')
    } else {
      return require('../../assets/imgs/icon_' + (index - 1) + '_off.png')
    }
  }
  beforeMount() {
    this.loadBanner('home_1')
    this.loadBanner('home_2')
    this.loadBanner('home_3')
    this.loadBanner('home_4')
    this.loadNews()
    this.loadMerchants()
  }
  mounted() {
    // w 1125 h 1430
    const clientWidth = window.document.body.clientWidth
    this.homeIntroHeight = (1430 / 1125) * clientWidth
    // w 1125 830
    this.homeRsstHeight = (830 / 1125) * clientWidth
    console.log(window.document.body.clientWidth)
  }
  home_1: Array<BannerDTO> = []
  home_2: Array<BannerDTO> = []
  home_3: Array<BannerDTO> = []
  home_4: Array<BannerDTO> = []
  async loadBanner(area: string) {
    try {
      const me = this as any
      const { data } = await this.proxy('api/sys/banner', 'get', {
        sectionArea: area,
      })
      me[area] = data.data
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }
  newsList: Array<NewsDTO> = []
  async loadNews() {
    try {
      const { data } = await this.proxy('api/sys/news')
      this.newsList = data.data
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }
  merchantList: Array<MerchantDTO> = []
  async loadMerchants() {
    try {
      const { data } = await this.proxy('api/sys/merchants', 'get', {
        pageSize: 2,
      })
      this.merchantList = data.data
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }
  clickBanner(item: BannerDTO) {
    if (item.linkFlag) {
      this.openNewWindow(item.linkUrl!)
    }
  }
}
export default MobileIndexPage
</script>

<style lang="stylus" scoped>
.ant-carousel >>> .slick-slide
  text-align center
  // height 300px
  // line-height 300px
  // background #364d79
  overflow hidden
.ant-carousel >>> .slick-slide h3
  color #fff
.home-card
  color #53792A
  height 100px
  border-right 1px solid #DAE2D2
  &:hover
    background #DAE2D2
  a
    color #53792A
.mb-home-intro-bg
  width 100%
  display flex
  align-items center
  justify-content center
  background-image url('../../assets/imgs/home_2.png')
  background-position center
  background-size cover
  .box
    width 330px
    height 330px
    margin 0 auto
    overflow hidden
.mb-home-rsst-bg
  width 100%
  display flex
  align-items center
  justify-content center
  background-image url('../../assets/imgs/home_3.png')
  background-position center
  background-size cover
  .box
    margin-top -10%
    width 320px
    height 227px
.news-title
  position relative
  letter-spacing 1.5px
  &::after
    position absolute
    display block
    content ''
    width 80px
    height 2px
    background-color #8DBC3C
    left calc(((100% - 80px) / 2))
</style>
