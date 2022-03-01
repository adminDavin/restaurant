<template lang="pug">
a-layout
  a-layout-header.flex.bg-white.align-center(style="padding: 0 15px;line-height: unset")
    .flex-50(@click="back")
      a-icon(type="arrow-left", style="font-size: 22px; color: #8DBC3C; font-weight:bold")
    .col(style="color:#666666")
      .ft-20 {{newsObj.newsTitle}}
      div {{newsObj.publishDate}}
  a-layout-content.padding.bg-white
    div(v-html="newsObj.newsContent", style="min-height: 150px")
  a-layout-footer.padding.ptb-0.bg-white(style="line-height: unset")
    .row.pt-10(style="border-top: 0.5px solid #959595")
      .col << 前へ
      .col.text-right 次へ >>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { NewsDTO } from '@/models/NewsModels'
@Component({
  layout: 'mobile',
})
class MobileNewsDetailPage extends Vue {
  newsId: string = ''
  newsObj: NewsDTO = {
    id: '',
    newsTitle: '',
    newsContent: '',
    newsAuthor: '',
    publishDate: '',
    newsProperty: 1,
  }
  beforeMount() {
    this.newsId = this.$route.query.id as string
    this.loadNewsDetail()
  }
  async loadNewsDetail() {
    try {
      const { data } = await this.proxy('api/sys/news/' + this.newsId)
      this.newsObj = data.data
    } catch (e) {
      this.msgShow(e.message || '网络异常')
    }
  }
}
export default MobileNewsDetailPage
</script>
