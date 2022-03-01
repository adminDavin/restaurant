<template lang="pug">
.mInput
  a-select.m-select.text-center.full-width(v-if="list",:placeholder="placeholder", size="large",  @change="selectChange", :dropdownStyle="{'textAlign': 'center', 'width': '100%'}")
    a-select-option(v-for="(s,idx) in list", :key="idx", :class="{'text-center': textCenter}", :value="s") {{s}}
  a-input(v-model="localValue", size="large", @change="$emit('input', localValue)", :placeholder="placeholder", :class="{'text-center': textCenter}", @focus="$emit('focus')", @blur="$emit('blur')", :readOnly="readOnly", v-else)
    .iconfont(:class="'icon-' + icon", slot="suffix", v-if="suffix")
  slot
</template>

<script>
export default {
  props: {
    value: {
      type: String,
      default: ''
    },
    placeholder: {
      type: String,
      default: ''
    },
    textCenter: {
      type: Boolean,
      default: false
    },
    readOnly: {
      type: Boolean,
      default: false
    },
    suffix: {
      type: Boolean,
      default: false
    },
    icon: {
      type: String,
      default: 'circle'
    },
    type: {
      type: String,
      default: 'input'
    },
    list: {
      default: false
    }
  },
  data () {
    return {
      localValue: ''
    }
  },
  watch: {
    value (newVal) {
      console.log('minput value:>>', newVal)
      this.localValue = newVal
    }
  },
  beforeMount () {
    this.localValue = this.value || ''
  },
  methods: {
    selectChange (val) {
      this.localValue = val
      console.log('val:>>', val)
      this.$emit('input', val)
    }
  }
}
</script>

<style lang="stylus" scoped>
.mInput
  /deep/.ant-input:hover, /deep/.ant-input:focus
    border-color #ccc !important
    box-shadow none
  .m-select
    /deep/.ant-select-selection__placeholder
      text-align center
    /deep/.ant-select-selection-selected-value
      width 100%
      text-align center
</style>
