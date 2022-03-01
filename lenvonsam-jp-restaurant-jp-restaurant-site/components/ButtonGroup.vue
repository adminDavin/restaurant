<template lang="pug">
a-button-group
  template(v-for="btn in btns")
    template(v-if="btn.action == 'remove' || btn.action == 'batchRemove'")
      a-popconfirm(v-if="sections.length > 0", title="您确认要删除吗？", @confirm="clickHandler(btn)")
        a-button(:type="getDefaultBtnType(btn)")
          a-icon(v-if="btn.icon")
          | {{btn.i18? $bk18n.t(btn.lbl) : btn.lbl}}
      a-button(:type="getDefaultBtnType(btn)", @click="msgShow('请选择删除项')", v-else)
          a-icon(v-if="btn.icon")
          | {{btn.i18? $bk18n.t(btn.lbl) : btn.lbl}}
    a-button(:type="getDefaultBtnType(btn)", v-else, @click="clickHandler(btn)")
      a-icon(v-if="btn.icon")
      | {{btn.i18? $bk18n.t(btn.lbl) : btn.lbl}}
</template>
<script>
export default {
  props: {
    btns: {
      required: true,
      type: Array
    },
    sections: {
      type: Array,
      default: () => []
    }
  },
  methods: {
    getDefaultBtnType (btn) {
      if (btn.type) {
        return btn.type
      } else {
        switch (btn.action) {
          case 'create':
            return 'primary'
          case 'remove':
          case 'batchRemove':
            return 'danger'
          default:
            return 'default'
        }
      }
    },
    clickHandler (btn) {
      this.$emit('click', btn.action)
    }
  }
}
</script>
