<template lang="pug">
.calendar-container
  .title.flex.align-center
    .left(@click="changeMonth('pre')")
    .col
    .year {{textYear}}年
    .space
    .month {{textMonth}}月
    .space
    .day(:class="{'ft-green': textGreenShow}") {{textDay}}日
    .col
    .right(@click="changeMonth('next')")
  .box
    .flex.header
      .col.item.flex.align-center.justify-center(v-for="(head,index) in headers", :key="index", :class="head.class") {{$pc18n.t(head.text)}} 
    .flex.body(v-for="(row, ridx) in dates", :key="ridx")
      .col.item.flex.align-center.justify-center(v-for="(col, cidx) in row", :key="cidx", :class="{'active': (rowIndex === ridx && colIndex === cidx)}", @click="chooseItem(ridx, cidx, col)")
        div
          .text-center {{col.text}}
          .iconfont(:class="'icon-'+col.icon", style="font-size: 18px")
</template>

<script>
import moment from 'moment'
export default {
  props: {
    dataReload: {
      type: Boolean,
      default: false
    },
    changeList: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data () {
    return {
      headers: [{ text: 'appointment.text3', class: 'text-green' }, { text: 'appointment.text4', class: '' }, { text: 'appointment.text5', class: '' }, { text: 'appointment.text6', class: '' }, { text: 'appointment.text7', class: '' }, { text: 'appointment.text8', class: '' }, { text: 'appointment.text9', class: 'text-green' }],
      dates: [],
      rowIndex: -1,
      colIndex: -1,
      momentObj: null,
      textYear: '2021',
      textMonth: '11',
      textDay: '01',
      textGreenShow: false
    }
  },
  beforeMount () {
    this.textGreenShow = false
    this.momentObj = moment()
    this.setDate(this.momentObj)
  },
  watch: {
    dataReload (newVal) {
      console.log('dataReload:>>', newVal)
      if (newVal) {
        this.setDate(this.momentObj)
      }
    }
  },
  methods: {
    chooseItem (ridx, cidx, rowObj) {
      if (rowObj.icon === 'cross') return
      this.rowIndex = ridx
      this.colIndex = cidx
      this.textMonth = rowObj.date.substring(5, 7)
      this.textDay = rowObj.text
      this.textGreenShow = true
      this.$emit('choose', rowObj.date)
    },
    // 某月天数
    getMonthDays (momentObj) {
      return momentObj.daysInMonth()
    },
    // 某月第一天的星期
    getWeekDays (momentObj) {
      return momentObj.startOf('month').weekday()
    },
    getDayIcon (date) {
      const index = this.changeList.findIndex(item => item.appointTime.substring(0, 10) === date)
      if (index < 0) {
        return 'circle'
      } else {
        const item = this.changeList[index]
        return item.appointType === 1 ? 'cross' : 'angle'
      }
    },
    setDate (m) {
      this.rowIndex = -1
      this.colIndex = -1
      // 当月天数
      let curDays = this.getMonthDays(m)
      // 当月第一个星期
      let curWeek = this.getWeekDays(m.clone())
      // 上月天数
      let upDays = this.getMonthDays(m.clone().subtract(1, 'month'))
      const cldArrays = []
      const lastMonthDays = 1 + curWeek
      const curYear = m.clone().format('YYYY')
      this.textYear = curYear
      const upMonth = m.clone().subtract(1, 'month').format('MM')
      const curMonth = m.clone().format('MM')
      this.textMonth = curMonth
      this.textDay = '01'
      const nextMonth = m.clone().add(1, 'month').format('MM')
      for (var i = 0; i < lastMonthDays; i++) {
        const temp = `${curYear}-${upMonth}-${upDays - i}`
        cldArrays.unshift({
          text: `${upDays - i}`,
          icon: this.getDayIcon(temp),
          date: `${temp} 00:00`
        })
      }
      for (var i = 1; i <= 35 - lastMonthDays; i++) {
        let text = i < 10 ? `0${i}` : `${i}`
        let mText = curMonth
        if (i > curDays) {
          const diff = i - curDays
          text = diff < 10 ? `0${diff}` : `${diff}`
          mText = nextMonth
        }
        const temp = `${curYear}-${mText}-${text}`
        cldArrays.push({
          text: text,
          icon: this.getDayIcon(temp),
          date: `${temp} 00:00`
        })
      }
      this.dates = this.arr2D(cldArrays, true, 7)
      this.$emit('update:dataReload', false)
    },
    changeMonth (type) {
      this.textGreenShow = false
      if (type === 'pre') {
        this.momentObj = this.momentObj.clone().subtract(1, 'month')
      } else {
        this.momentObj = this.momentObj.clone().add(1, 'month')
      }
      this.$emit('pagination', this.momentObj.clone().format('YYYY-MM-DD'))
      this.setDate(this.momentObj)
    }
  }
}
</script>

<style lang="stylus" scoped>
@import '../../assets/stylus/iconfont'
.calendar-container
  margin 0 auto
  width 97%
  height 478px
  box-sizing border-box
  border 2px solid #ccc
  border-radius 20px
  padding 10px 3px
  color black
  font-weight bold
  background #fff
  .title
    color #CCCCCC
    width 90%
    margin 15px auto
    height 40px
    .year, .month, .day
      font-size 20px
      letter-spacing 0.5px
    .space
      width 10px
    .year
      width 82px
    .month, .day
      width 50px
    .left
      width 24.5px
      height 28px
      background-image url('../../assets/imgs/icon_cld_left.png')
      background-position center
      background-size cover
    .right
      width 25px
      height 28px
      background-image url('../../assets/imgs/icon_cld_right.png')
      background-position center
      background-size cover
  .box
    padding 0px 5px
    .header
      .item
        font-size 16px
        height 50px
    .body
      .item
        height 65px
        &.active
          background #8DBC3C
          color #fff
    .header, .body
      background #F4F5F8
      .item
        background #F4F5F8
        border-bottom 1px solid #fff
        &:not(:last-child)
          border-right 1px solid #fff
</style>

