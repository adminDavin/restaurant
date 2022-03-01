<template lang="pug">
div
   s-date-picker(v-if="type === 'date'", v-model="localValue", :mode="mode || 'date'")
   a-input.full-width(v-model="localValue", v-else)
</template>

<script>
export default {
  props: {
    value: {
      type: null,
      default: false
    },
    type: {
      type: String,
      default: 'text'
    },
    mode: {
      type: String,
      default: 'date'
    }
  },
  data() {
    return {
      localValue: ''
    }
  },
  watch: {
    value: {
      handler(newVal) {
        if (newVal !== this.localValue) this.localValue = newVal
      },
      immediate: true
    },
    localValue(newVal) {
      this.$emit('input', newVal)
      this.$emit('change', newVal)
    }
  }
}
</script>
