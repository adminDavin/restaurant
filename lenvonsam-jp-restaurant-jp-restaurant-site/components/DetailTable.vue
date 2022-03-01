<template lang="pug">
a-drawer(:visible="visible", :width="width", @close="drawerClose", :title="formConfig.title || '表单详情'", :closable="false")
  a-descriptions(bordered, :column="{xxl: 4, xl: 3, lg:3, md:3, sm:2, xs:1}")
    a-descriptions-item(v-for="(item, idx) in formConfig.items", :key="idx", :label="item.lbl") {{item.render ? item.render(detailObj): detailObj[item.key]}}
</template>

<script>
export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    width: {
      type: Number,
      default: 600
    },
    formConfig: {
      type: Object,
      required: true
    },
    detailObj: {
      type: Object,
      required: true
    }
  },
  methods: {
    drawerClose() {
      this.$emit('update:visible', false)
    }
  }
}
</script>
