<template lang="pug">
.bg-white.pb-20(style="box-sizing: border-box")
  .padding-lg
    .ft-24.text-green.ft-bold {{$pc18n.t('concept.part1.code1_1')}}
    .ft24.text-green {{$pc18n.t('concept.part1.code1_2')}}
    .text-66.mt-25 {{$pc18n.t('concept.part1.code3')}}
    .mt-25.text-right {{$pc18n.t('concept.part1.code4')}}
    .mt-50.text-green {{$pc18n.t('concept.mb.intro.subtitle.code1_1')}}
    .text-green {{$pc18n.t('concept.mb.intro.subtitle.code1_2')}}
    .text-center.pt-25
      img(src="~assets/imgs/intro_1.png", style="width: 90%;")
    .text-66.mt-20
      span.ft-16.ft-bold {{$pc18n.t('concept.part2.code1_1')}}
      span {{$pc18n.t('concept.part2.code1_2')}}
    .text-center.pt-25
      img(src="~assets/imgs/intro_2.png", style="width: 90%;")
    .text-66.mt-20
      span.ft-16.ft-bold {{$pc18n.t('concept.part3.code1_1')}}
      span {{$pc18n.t('concept.part3.code1_2')}}
    .text-center.pt-25
      img(src="~assets/imgs/intro_3.png", style="width: 90%;")
    .text-66.mt-20
      span.ft-16.ft-bold {{$pc18n.t('concept.part4.code1_1')}}
      span {{$pc18n.t('concept.part4.code1_2')}}{{$pc18n.t('concept.part4.code1_3')}}{{$pc18n.t('concept.part4.code1_4')}}
      br
      br
      span.ft-16.ft-bold {{$pc18n.t('concept.part4.code2_1')}}
      span {{$pc18n.t('concept.part4.code2_2')}}{{$pc18n.t('concept.part4.code2_3')}}
    .text-center.pt-25
      img(src="~assets/imgs/intro_4.png", style="width: 90%;")
    .text-66.mt-20
      span.ft-16.ft-bold {{$pc18n.t('concept.part5.code1_1')}}
      span {{$pc18n.t('concept.part5.code1_2')}}
    img.full-width.mt-45(src="~assets/imgs/intro_5.png")
  img.full-width.mt-35(src="~assets/imgs/intro_6.png")
  .padding-lg.pt-15
    .text-center
      .ft-24.text-green.ft-bold {{$pc18n.t('intro.text1')}}
      .text-66 {{$pc18n.t('intro.text2')}}
    .mt-35
      .text-green {{$pc18n.t('intro.text3')}}
      .text-66 
        span {{$pc18n.t('intro.text4')}}
        br
        span {{$pc18n.t('intro.text4_1')}}
    .text-center.mt-35
      img(src="~/assets/imgs/intro_7.png", style="width: 90%")
    .text-green.ft-bold.mt-25 {{$pc18n.t('intro.text5')}}
    .text-66.mt-5
      span {{$pc18n.t('intro.text6')}}
      br
      span {{$pc18n.t('intro.text7')}}
      br
      span {{$pc18n.t('intro.text8')}}
      br
      span {{$pc18n.t('intro.text9')}}
      br
      span {{$pc18n.t('intro.text10')}}
    .text-center.mt-35
      img(src="~/assets/imgs/intro_8.png", style="width: 90%")
    .text-green.ft-bold.mt-25 {{$pc18n.t('intro.text11')}}
    .text-66.mt-5
      span {{$pc18n.t('intro.text12')}}
      br
      span {{$pc18n.t('intro.text12_1')}}
      br
      span {{$pc18n.t('intro.text12_2')}}
      br
      span {{$pc18n.t('intro.text12_3')}}
  .intro-timeline-bg
    .padding-lg
      a-timeline
        a-timeline-item.intro-timeline-item(v-for="(item, index) in timelineArray", :key="index", style="background: transparent")
          a-icon(slot="dot", type="clock-circle" theme="filled", style="color: rgba(0, 0, 0, 0.65)")
          .text-green.ft-16.ft-bold {{$pc18n.t(item.title)}}
          .text-66 {{$pc18n.t(item.content)}}
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
@Component({
  layout: 'mobile',
})
class MobileIntroIndexPage extends Vue {
  timelineArray = [
    {
      title: 'intro.timeline.d0.time',
      content: 'intro.timeline.d0.text',
    },
    {
      title: 'intro.timeline.d1.time',
      content: 'intro.timeline.d1.text',
    },
    {
      title: 'intro.timeline.d2.time',
      content: 'intro.timeline.d2.text',
    },
    {
      title: 'intro.timeline.d3.time',
      content: 'intro.timeline.d3.text',
    },
    {
      title: 'intro.timeline.d4.time',
      content: 'intro.timeline.d4.text',
    },
    {
      title: 'intro.timeline.d5.time',
      content: 'intro.timeline.d5.text',
    },
    {
      title: 'intro.timeline.d6.time',
      content: 'intro.timeline.d6.text',
    },
    {
      title: 'intro.timeline.d7.time',
      content: 'intro.timeline.d7.text',
    },
  ]
}
export default MobileIntroIndexPage
</script>

<style lang="stylus" scoped>
.intro-timeline-bg
  background-image url('../../../assets/imgs/intro_bg.png')
  background-size cover
  background-position center
  min-height 100px
  padding-top 10%
.intro-timeline-item
  /deep/.ant-timeline-item-head
    background transparent
</style>
