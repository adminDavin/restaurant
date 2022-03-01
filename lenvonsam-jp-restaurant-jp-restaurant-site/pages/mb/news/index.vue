<template lang="pug">
.bg-white.padding
  .pt-30.text-center.ft-24.news-title.pc-ft-parisienne  News&Topics
  .text-center.ft-bold.mt-10.pc-ft-noto-black  お知らせ
  .mt-20.news-bg
    .row.pb-10.pt-10.border-bottom-line(v-for="(news, idx) in newsList", :key="idx", @click="jump('/mb/news/detail?id=' + news.id)")
      .flex-80.text-green {{news.publishDate.substring(0, 10).replace(/-/g, '.')}}
      .col.ellps {{news.newsTitle}}
  img.mt-25.full-width(src="~assets/imgs/news_1.jpg")
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { NewsDTO } from '@/models/NewsModels'
@Component({
  layout: 'mobile',
})
class MobileNewsIndexPage extends Vue {
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
export default MobileNewsIndexPage
</script>

<style lang="stylus" scoped>
.news-bg
  background-image url('../../../assets/imgs/news_list_bg.png')
  background-repeat no-repeat
  background-size cover
  background-position top
  min-height 500px
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
