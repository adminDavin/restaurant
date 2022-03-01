<template lang="pug">
a-tooltip(:placement="placement", :title="title", overlayClassName="tooltip-pos")
  template(v-if="config")
    a-icon(v-if="config.type == 'icon'", :type="config.icon", :class="config.class")
  template(v-else)
    span {{title}}
</template>

<script>
export default {
  props: {
    title: {
      required: true,
      type: String
    },
    placement: {
      type: String,
      default: 'top'
    },
    config: {
      type: Object,
      default: null
    }
  }
}
</script>

<style lang="stylus" scoped>
.tooltip-pos
  z-index 10000
</style>
