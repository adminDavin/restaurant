<template lang="pug">
.bg-white(style="box-sizing: border-box")
  .pc-content
    .pt-20.text-center.intro-title.pc-ft-parisienne {{$pc18n.t('pcIndex.header1.concept.below')}}
    .text-center.ft-bold.mt-10.ft-24.pc-ft-noto-black {{$pc18n.t('pcIndex.header1.concept.above')}}
    .ft-24.text-green.pt-30
      span.ft-bold {{$pc18n.t('concept.part1.code1_1')}}
      span {{$pc18n.t('concept.part1.code1_2')}}
    .text-66.mt-25.ft-18 {{$pc18n.t('concept.part1.code3')}}
    .mt-25.text-right.ft-24 {{$pc18n.t('concept.part1.code4')}}
    img.full-width.mt-45(src="~assets/imgs/intro_5.png")
    .mt-50.text-green.ft-bold.text-center.ft-24 {{$pc18n.t('concept.subTitle')}}
    a-row.text-66.mt-20
      a-col.pr-25(:span="12", style="padding-top: 10%")
        .intro-text-box
          span.ft-24.ft-bold {{$pc18n.t('concept.part2.code1_1')}}
          span.ft-20 {{$pc18n.t('concept.part2.code1_2')}}
      a-col(:span="12")
        img(src="~assets/imgs/intro_1.png", style="width: 90%;")
    a-row.text-66.mt-20
      a-col(:span="12")
        img(src="~assets/imgs/intro_2.png", style="width: 90%;")
      a-col(:span="12", style="padding-top: 10%")
        .intro-text-box
          span.ft-24.ft-bold {{$pc18n.t('concept.part3.code1_1')}}
          span.ft-20 {{$pc18n.t('concept.part3.code1_2')}}
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
          span.ft-24.ft-bold {{$pc18n.t('concept.part4.code2_1')}}
          span.ft-20 {{$pc18n.t('concept.part4.code2_2')}}
          br
          span.ft-20 {{$pc18n.t('concept.part4.code2_3')}}
      a-col(:span="12")
        img(src="~assets/imgs/intro_3.png", style="width: 90%;")
    a-row.text-66.mt-20
      a-col(:span="12")
        img(src="~assets/imgs/intro_4.png", style="width: 90%;")
      a-col(:span="12", style="padding-top: 10%")
        .intro-text-box
          span.ft-24.ft-bold {{$pc18n.t('concept.part5.code1_1')}}
          span.ft-20 {{$pc18n.t('concept.part5.code1_2')}}
  img.full-width.mt-50(src="~assets/imgs/intro_6.png")
  .padding-lg.pt-15
    .text-center
      .ft-36.text-green.ft-bold {{$pc18n.t('intro.text1')}}
      .text-66.ft-24 {{$pc18n.t('intro.text2')}}
  .pc-content
    .mt-35
      .text-green.ft-bold.ft-24 {{$pc18n.t('intro.text3')}}
      .text-66.ft-18 
        span {{$pc18n.t('intro.text4')}}
        span {{$pc18n.t('intro.text4_1')}}
    a-row.mt-35.mb-20
      a-col(:span="8")
        img(src="~/assets/imgs/intro_7.png", style="width: 95%")
      a-col(:span="16")
        div(style="width: 450px; margin: 0 auto;")
          .text-green.ft-bold.mt-15.ft-24 {{$pc18n.t('intro.text5')}}
          .text-66.mt-5.ft-18
            span {{$pc18n.t('intro.text6')}}
            br
            span {{$pc18n.t('intro.text7')}}
            br
            span {{$pc18n.t('intro.text8')}}
            br
            span {{$pc18n.t('intro.text9')}}
            br
            span {{$pc18n.t('intro.text10')}}
    a-row
      a-col.pt-50(:span="8")
        .text-green.ft-bold.ft-24 {{$pc18n.t('intro.text11')}}
        .text-66.mt-5.ft-18
          span {{$pc18n.t('intro.text12')}}
          span {{$pc18n.t('intro.text12_1')}}
          span {{$pc18n.t('intro.text12_2')}}
          span {{$pc18n.t('intro.text12_3')}}
      a-col.text-center(:span="16")
        img(src="~/assets/imgs/intro_8.png", style="width: 40%")
  .intro-timeline-bg.mt-30
      a-timeline(mode="alternate", style="margin: 0px auto")
        a-timeline-item.intro-timeline-item(v-for="(item, index) in timelineArray", :key="index", style="background: transparent")
          a-icon(slot="dot", type="clock-circle" theme="filled", style="color: rgba(0, 0, 0, 0.65)")
          .text-green.ft-24.ft-bold {{$pc18n.t(item.title)}}
          .text-66.ft-18 {{$pc18n.t(item.content)}}
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
@Component({
  layout: 'pc',
})
class PcIntroIndexPage extends Vue {
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
export default PcIntroIndexPage
</script>

<style lang="stylus" scoped>
.intro-text-box
  width 400px
  margin 0 auto
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
.intro-timeline-bg
  background-image url('../../../assets/imgs/intro_bg.png')
  background-size cover
  background-position center
  min-height 100px
  padding-top 10%
  padding-bottom 50px
.intro-timeline-item
  /deep/.ant-timeline-item-head
    background transparent
</style>
