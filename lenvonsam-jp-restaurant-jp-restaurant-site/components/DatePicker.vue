<template lang="pug">
div
  a-month-picker(v-if="mode === 'month'", :format="localFormat", :value-format="localFormat", @change="pickerChange", v-model="localValue")
  a-range-picker(v-else-if="mode === 'range'", :format="localFormat", :value-format="localFormat", @change="pickerChange", v-model="localValue")
  a-date-picker(v-else, :format="localFormat", :value-format="localFormat", @change="pickerChange", v-model="localValue")
</template>

<script>
export default {
  props: {
    mode: {
      // date month range
      type: String,
      default: 'date'
    },
    format: {
      type: String,
      default: 'YYYY-MM-DD HH:mm'
    },
    value: {
      type: String,
      default: null
    }
  },
  data () {
    return {
      localValue: null,
      localFormat: null
    }
  },
  watch: {
    value (newVal) {
      this.localValue = newVal
    }
  },
  beforeMount () {
    if (this.value) this.localValue = this.value
    this.localFormat = this.format
    if (this.mode === 'month')
      this.localFormat = this.format.substring(0, this.format.indexOf('MM') + 2)
  },
  methods: {
    pickerChange (date, dateStr) {
      this.$emit('input', dateStr)
      this.$emit('change', date, dateStr)
    }
  }
}
</script>
