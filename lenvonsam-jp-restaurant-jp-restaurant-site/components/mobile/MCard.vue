<template lang="pug">
.m-card-container(:style="{height: boxHeight + 'px'}", :class="{'checked': choosed}")
  .cbx(@click="cbxTargetChange")
    .checked(v-if="choosed")
  .box(@click="cbxChange", :style="{backgroundImage: 'url(' + bgUrl + ')'}")
    template(v-if="collapse")
      .title {{item.id === '-1' ? $pc18n.t(item.title) : item.title}}
      div {{item.description}}
      .bottom.ft-10(@click="toggle", v-if="collapse") {{contentShow? '' : '詳しい情報を見る'}}
    template(v-else)
      .title.ft-green.mt-10 {{item.id === '-1' ? $pc18n.t(item.title):item.title}}
  .collapse(:style="{visibility: visibility}", ref="mCardContent", @click="toggle")
    slot
</template>

<script>
export default {
  model: {
    prop: 'choosed'
  },
  props: {
    choosed: Boolean,
    defaultHeight: {
      type: Number,
      default: 120
    },
    collapse: {
      type: Boolean,
      default: true
    },
    bgUrl: {
      type: String,
      default: ''
    },
    item: {
      type: Object,
      default: null
    }
  },
  data () {
    return {
      contentShow: false,
      boxHeight: this.defaultHeight,
      visibility: 'hidden',
    }
  },
  methods: {
    cbxTargetChange (ev) {
      ev.stopPropagation()
      this.cbxChange()
    },
    cbxChange () {
      const result = !this.choosed
      this.$emit('update:choosed', result)
      // const defaultText = this.$pc18n.t('appointment.text15')
      if (result) this.$emit('choose', this.collapse ? this.item : { id: '-1', code: 'P00001', title: 'appointment.text15', description: '' })
    },
    toggle (ev) {
      ev.stopPropagation()
      this.contentShow = !this.contentShow
      if (this.contentShow) {
        this.contentHeight = this.$refs.mCardContent.offsetHeight
        this.boxHeight = this.defaultHeight + this.contentHeight
        this.visibility = 'visible'
      } else {
        this.contentHeight = 0
        this.boxHeight = this.defaultHeight
        setTimeout(() => {
          this.visibility = 'hidden'
        }, 610)
      }
    }
  }
}
</script>

<style lang="stylus" scoped>
.m-card-container
  width 90%
  box-sizing border-box
  margin 0 auto
  position relative
  border-radius 10px
  background #fff
  transition height 0.6s ease-in-out
  border 2px solid #F6FAF0
  overflow hidden
  &.checked
    border 2px solid #8DBC3C
  .cbx
    position absolute
    background #fff
    width 36px
    height 36px
    right 15px
    top 20px
    z-index 10
    .checked
      width 30px
      height 30px
      margin 3px auto
      background-image url('../../assets/imgs/icon_select.png')
      background-size cover
      background-position center
  .box
    height 120px
    position relative
    text-align center
    padding-top 30px
    background-position center
    background-size cover
    .title
      font-size 20px
    .bottom
      width 100px
      text-align center
      font-size 10px
      position absolute
      left calc(((100% - 100px) / 2))
      bottom 8px
  .collapse
    padding 10px 10px
</style>


