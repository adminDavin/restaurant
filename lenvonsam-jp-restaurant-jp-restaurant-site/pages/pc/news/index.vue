<template lang="pug">
.bg-white.padding
  .pc-content
    .pt-30.text-center.news-title.pc-ft-parisienne News&Topics
    .text-center.ft-bold.mt-10.pc-ft-noto-black.ft-24 お知らせ
    .mt-20.news-bg
      .row.pb-10.pt-10.border-bottom-line(v-for="(news, idx) in newsList", :key="idx", @click="jump('/pc/news/detail?id=' + news.id)")
        .flex-80.ft-24.text-green {{news.publishDate.substring(0, 10).replace(/-/g, '.')}}
        .col.ellps.ft-24.pl-20(style="line-height: 30px") {{news.newsTitle}}
      .row.ft-24.pt-20(style="border-top: 0.5px solid #959595")
        .col << {{$pc18n.t('common.prev')}}
        .col.text-right {{$pc18n.t('common.next')}}
    img.mt-25.full-width(src="~assets/imgs/news_1.jpg")
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { NewsDTO } from '@/models/NewsModels'
@Component({
  layout: 'pc',
})
class PcNewsIndexPage extends Vue {
  textCount = 10
  beforeMount() {
    this.loadNews()
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
}
export default PcNewsIndexPage
</script>

<style lang="stylus" scoped>
.news-bg
  background-image url('../../../assets/imgs/news_list_bg.png')
  background-repeat no-repeat
  background-size cover
  background-position top
  min-height 800px
  padding 30px
.news-title
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
