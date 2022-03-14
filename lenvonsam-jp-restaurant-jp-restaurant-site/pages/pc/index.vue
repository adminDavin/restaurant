<template lang="pug">
a-layout(style="background: #fff")
  .pc-content
    a-carousel(autoplay)
      .pc-banner-bg(v-for="(item,idx) in home_1", :key="idx", :style="{'height': '550px', backgroundImage: 'url(' + item.bannerUrl + ')'}", @click="clickBanner(item)")
    div(style="height: 120px")
      a-row.pc-ft-noto-black(style="background:#EDF1E8;border-bottom: 1px solid #DAE2D2")
        a-col.home-card.flex.align-center.justify-center(:span="6")
          a.text-center(href="#pcMainIntro")
            .ft-18 {{$pc18n.t('pcIndex.header1.storeInfo.above')}}
            div {{$pc18n.t('pcIndex.header1.storeInfo.below')}}
        a-col.home-card.flex.align-center.justify-center(:span="6")
          a.text-center(href='https://sitadinning.base.shop/', target="_blank")
            .ft-18 {{$pc18n.t('pcIndex.header1.onlineShop.above')}}
            div {{$pc18n.t('pcIndex.header1.onlineShop.below')}}
        a-col.home-card.flex.align-center.justify-center(:span="6", @click="jump('/pc/appoint')")
          .text-center
            .ft-18 {{$pc18n.t('pcIndex.header1.reserve.above')}}
            div {{$pc18n.t('pcIndex.header1.reserve.below')}}
        a-col.home-card.flex.align-center.justify-center(:span="6", @click="jump('/pc/intro')")
          .text-center
            .ft-18 {{$pc18n.t('pcIndex.header1.concept.above')}}
            div {{$pc18n.t('pcIndex.header1.concept.below')}}
    a-carousel.mt-25
      .pc-banner-bg(v-for="(item,idx) in home_2", :key="idx", :style="{'height': '235px', backgroundImage: 'url(' + item.bannerUrl + ')'}", @click="clickBanner(item)")
        //- img.full-width(:src="item.bannerUrl")
    .padding.mt-15
      .row.pb-10.pt-10.border-bottom-line(v-for="(news, idx) in newsList", :key="idx")
        .flex-80.text-green.ft-20 {{news.publishDate.substring(0, 10).replace(/-/g, '.')}}
        .col.ellps.ft-20.pl-20(style="line-height: 22px") {{news.newsTitle}}
    .mt-20.text-right
      .m-reverse-button.pc-detail-button(@click="jump('/pc/news')") {{$pc18n.t('common.more')}}
  //- .pt-30.text-center.ft-24.news-title News&Topics
  //- .text-center.ft-bold.mt-10 お知らせ
    
  img.full-width.mt-35(src="~assets/imgs/intro_6.png")
  .home-intro-bg(:style="{'height': homeIntroHeight + 'px'}")
    .box
      a-carousel.mt-25(autoplay)
        .pc-banner-bg(v-for="(item,idx) in home_3", :key="idx", :style="{'height': '800px', backgroundImage: 'url(' + item.bannerUrl + ')'}", @click="clickBanner(item)")
      //- img.full-width(:src="item.bannerUrl", style="height: 650px")
  .pc-content
    .pt-20.text-center.intro-title.pc-ft-parisienne {{$pc18n.t('pcIndex.header1.concept.below')}}
    .text-center.ft-bold.mt-10.ft-24.pc-ft-noto-black {{$pc18n.t('pcIndex.header1.concept.above')}}
    .mt-25.text-center.ft-bold.text-green.ft-24
      span {{$pc18n.t('home.section2.title')}}
    a-row.text-66.mt-35
      a-col.pr-25(:span="12", style="padding-top: 10%")
        .intro-text-box
          span.ft-24.ft-bold {{$pc18n.t('home.section2.part1Bold')}}
          span.ft-20 {{$pc18n.t('home.section2.part1')}}
      a-col(:span="12")
        img(src="~assets/imgs/intro_1.png", style="width: 90%;")
    a-row.text-66.mt-20
      a-col(:span="12")
        img(src="~assets/imgs/intro_2.png", style="width: 90%;")
      a-col(:span="12", style="padding-top: 10%")
        .intro-text-box
          span.ft-24.ft-bold {{$pc18n.t('home.section2.part2Bold')}}
          span.ft-20 {{$pc18n.t('home.section2.part2')}}
    a-row.text-66.mt-20
      a-col.pr-25(:span="12", style="padding-top: 5.5%")
        .intro-text-box
          span.ft-24.ft-bold {{$pc18n.t('concept.part4.code1_1')}}
          span.ft-20 {{$pc18n.t('concept.part4.code1_2')}}
          br
          span.ft-20 {{$pc18n.t('concept.part4.code1_3')}}
          br
          span.ft-20 {{$pc18n.t('concept.part4.code1_4')}}
          br
          br
          br
          span.ft-24.ft-bold {{$pc18n.t('concept.part4.code2_1')}}
          span.ft-20 {{$pc18n.t('concept.part4.code1_2')}}
          br
          span.ft-20 {{$pc18n.t('concept.part4.code1_3')}}
          //- span.ft-20 にも季節の野菜など多くの野菜を使い、健康食材とされる商品を多数使用して調理いたしております。
      a-col(:span="12")
        img(src="~assets/imgs/intro_3.png", style="width: 90%;")
    a-row.text-66.mt-20
      a-col(:span="12")
        img(src="~assets/imgs/intro_4.png", style="width: 90%;")
      a-col(:span="12", style="padding-top: 10%")
        .intro-text-box
          span.ft-24.ft-bold {{$pc18n.t('concept.part5.code1_1')}}
          span.ft-20 {{$pc18n.t('concept.part5.code1_2')}}
    .text-right.mt-20
      .m-reverse-button.pc-detail-button(@click="jump('/pc/intro')") {{$pc18n.t('common.more')}}
  //- img.full-width.mt-35(src="~assets/imgs/home_1.png")
  .home-rsst-bg(:style="{'height': homeRsstHeight + 'px'}")
    .box
      a-carousel.mt-25(autoplay)
        .pc-banner-bg(v-for="(item,idx) in home_4", :key="idx", :style="{'height': '768px', backgroundImage: 'url(' + item.bannerUrl + ')'}", @click="clickBanner(item)")
      //- img.full-width(:src="item.bannerUrl", style="height: 650px")
  #pcMainIntro.pt-30.text-center.intro-title.pc-ft-parisienne {{$pc18n.t('pcIndex.header1.storeInfo.below')}}
  .text-center.ft-bold.mt-10.ft-24.pc-ft-noto-black {{$pc18n.t('pcIndex.header1.storeInfo.above')}}
  .pc-content
    .padding-lg
      a-row.pt-35(v-for="(row, idx) in merchant2DList", :key="idx", style="border-bottom: 1px solid #959595")
        a-col.pb-25(:span="8", v-for="(m, cidx) in row" v-if="m !== ''", :key="idx + '-' + cidx", :class="{'pr-20': cidx <= 2}")    
          .flex.flex-column
            .row.mb-15
              .col
                .ft-24 {{m.merchantName}}
              .flex-100
                img(src="~assets/imgs/icon_ubereats.png", style="height: 26px", @click="openNewWindow(m.uberSite)")
            .padding.mt-10(style="border: 2px solid #8DBC3C; border-radius: 4px;")
              img.full-width(:src="m.thumbUrl", style="height: 270px")
            .row(style="height: 60px")
              .col.text-center(v-for="n in 6")
                img(:src="getMerchantServices(m.infrastructureMark, n)", style="width: 40px")
            .row(style="flex: 0 0 310px")
              .text-gray.pt-15(style="width: 90%; margin: 0 auto")
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
            .flex-50
              .row
                .col
                  .m-reverse-button(style="width: 80%", @click="jump('/pc/merchant/detail?id=' + m.id)") {{$pc18n.t('common.more')}}
                .col
                  .m-button(style="width: 80%; height: 38px; font-size: 16px; border-radius: 20px", @click="jump('/pc/appoint?mid=' + m.id)") {{$pc18n.t('common.appoint')}}
        a-col.text-center(:span="8" v-else)
          .flex.flex-column(style="height: 793px")
            .col(style="flex: 0 0 80px")
            .row
              img(src="~assets/imgs/onlineshop.png", style="margin: 0px auto; width: 70%;")
            .col
              img(src="~assets/imgs/onlineshop_text.png", style="margin: 0px auto; width: 60%; margin-top:200px")
            .col
            .flex-50
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
  layout: 'pc',
  components: {
    Calendar,
    MInput,
    MCard,
  },
})
class PcIndexPage extends Vue {
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
    // 1444 1325
    const clientWidth = window.document.body.clientWidth
    this.homeIntroHeight = (1325 / 1444) * clientWidth
    // 1440 1064
    this.homeRsstHeight = (1064 / 1440) * clientWidth
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
  merchant2DList: any = []
  async loadMerchants() {
    try {
      const { data } = await this.proxy('api/sys/merchants')
      this.merchantList = data.data
      this.merchant2DList = this.arr2D(this.merchantList, true, 3)
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
export default PcIndexPage
</script>

<style lang="stylus" scoped>
.m-reverse-button
  &:hover
    cursor pointer
.pc-detail-button
  &:hover
    cursor pointer
  display inline-block
  width 160px
  text-align center
  padding-top 7px
.ant-carousel >>> .slick-slide
  text-align center
  overflow hidden
.ant-carousel >>> .slick-slide h3
  color #fff
.home-intro-bg
  height 1200px
  width 100%
  padding-top 200px
  background-image url('../../assets/imgs/home_2.png')
  background-position center
  background-size cover
  .box
    width 800px
    height 800px
    margin 0 auto
    overflow hidden
.home-rsst-bg
  width 100%
  display flex
  align-items center
  justify-content center
  background-image url('../../assets/imgs/home_3.jpg')
  background-position center
  background-size cover
  .box
    width 1024px
    height 768px
.home-card
  color #53792A
  height 100px
  border-right 1px solid #DAE2D2
  &:hover
    background #DAE2D2
  a
    color #53792A
.intro-title
  position relative
  letter-spacing 5px
  font-size 75px
  &::after
    position absolute
    display block
    content ''
    width 350px
    height 2px
    background-color #8DBC3C
    left calc(((100% - 350px) / 2))
</style>
