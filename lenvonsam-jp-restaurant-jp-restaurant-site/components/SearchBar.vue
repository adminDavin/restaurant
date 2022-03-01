<template lang="pug">
div(:style="{marginBottom: bottom + 'px'}")
  a-form.search-bar(layout="inline", :class="toggle ? 'toggle': ''")
    a-row(v-for="(row,ridx) in localSearchItems", :key="ridx")
      a-col(:span="localGutter", v-for="(col, cidx) in row", :key="cidx")
        template(v-if="col !== ''")
          a-form-item(:label="col.i18 ? $bk18n.t(col.lbl) : col.lbl")
            common-remote-select(v-if="col.type === 'remoteSelect'", :remoteConfig="col.remoteConfig", placeholder="请输入..", v-model="col.val")
            a-select(v-else-if="col.type === 'select'", placeholder="请选择", v-model="col.val", style="width: 120px")
              a-select-option(v-for="(itm, idx) in col.selectList", :value="itm.val", :key="idx") {{itm.i18 ? $bk18n.t(itm.lbl) : itm.lbl}}
            edit-input(v-model="col.val", :mode="col.mode", :type="col.type", v-else)
            //- s-date-picker(v-if="col.type === 'date'", v-model="col.val", :mode="col.mode || 'date'")
            //- a-input.full-width(v-model="col.val", v-else)
  a-row.mt-10
    a-col(:span="14")
      slot(name="left")
    a-col.text-right(:span="10")
      a-button.mr-5(type="primary", @click="search") {{$bk18n.t('common.search')}}
      a-button(@click="reset") {{$bk18n.t('common.reset')}}
      a-button(type="link", @click="collapse", v-if="showCollapse")
        | 展开
        a-icon(:type="toggle ? 'up-circle': 'down-circle'")
</template>

<script>
import CommonRemoteSelect from '@/components/CommonRemoteSelect.vue'
export default {
  props: {
    searchItems: {
      type: Array,
      required: true
    },
    gutter: {
      type: Number,
      default: 4
    },
    bottom: {
      type: Number,
      default: 20
    }
  },
  data () {
    return {
      toggle: false,
      localGutter: 6,
      localSearchItems: [],
      showCollapse: true
    }
  },
  components: {
    CommonRemoteSelect
  },
  beforeMount () {
    this.localGutter = 24 / this.gutter
    this.localSearchItems = this.arr2D(this.searchItems, true, this.gutter)
    if (this.searchItems.length <= this.gutter) this.showCollapse = false
  },
  methods: {
    collapse () {
      this.toggle = !this.toggle
    },
    search () {
      const arr = []
      for (let i = 0; i < this.localSearchItems.length; i++) {
        const row = this.localSearchItems[i]
        for (let j = 0; j < row.length; j++) {
          const col = row[j]
          if (col !== '' && col.val && col.val.toString().trim().length > 0) {
            let val = col.val
            if (val && val.length > 0) {
              if (col.key.endsWith('Start')) {
                val = col.val.substring(0, 10) + ' 00:00'
              }
              if (col.key.endsWith('End')) {
                val = col.val.substring(0, 10) + ' 23:00'
              }
            }
            arr.push({
              key: col.key,
              val: val
            })
          }
        }
      }
      console.log('search arr', arr)
      this.$emit('search', arr)
    },
    dateChange (date, dateString, r, i) {
      console.log(
        'date:>>',
        date,
        'dateString:>>',
        dateString,
        'r:>>',
        r,
        'i:>>',
        i
      )
    },
    reset () {
      for (let i = 0; i < this.localSearchItems.length; i++) {
        const row = this.localSearchItems[i]
        for (let j = 0; j < row.length; j++) {
          const col = row[j]
          if (col !== '' && col.val && col.val.toString().trim().length > 0) {
            col.val = ''
          }
        }
      }
      this.$forceUpdate()
    }
  }
}
</script>

<style lang="stylus" scoped>
.search-bar
  overflow hidden
  height 40px
  transition all 1s
  &.toggle
    height auto
</style>
