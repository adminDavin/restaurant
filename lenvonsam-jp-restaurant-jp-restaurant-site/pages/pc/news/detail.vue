<template lang="pug">
.pc-content
  a-layout
    a-layout-header.flex.bg-white.align-center.border-bottom-line(style="padding: 0 15px;line-height: unset")
      .flex-50(@click="back")
        a-icon(type="arrow-left", style="font-size: 24px; color: #666666; font-weight:bold")
      .col.text-right.ft-24(style="color:#666666")
        div {{newsObj.publishDate}}
    a-layout-content.padding.bg-white
      div(style="font-size: 42px") {{newsObj.newsTitle}}
      .mt-15.ft-24(v-html="newsObj.newsContent", style="min-height: 250px")
    a-layout-footer.padding.ptb-0.bg-white(style="line-height: unset")
      .row.ft-24.pt-20(style="border-top: 0.5px solid #959595")
        .col << 前へ
        .col.text-right 次へ >>
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { NewsDTO } from '@/models/NewsModels'
@Component({
  layout: 'pc',
})
class PcNewsDetailPage extends Vue {
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
export default PcNewsDetailPage
</script>
