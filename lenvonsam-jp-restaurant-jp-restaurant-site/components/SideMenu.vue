<template lang="pug">
div
  .row.text-white(style="height: 50px; font-size: 16px; padding-left: 12%; overflow: hidden") {{$bk18n.t('sys.bkTitle')}}
  a-menu(:default-selected-keys="[$route.path]",mode="inline",theme="dark", @click="menuItemClick")
    a-menu-item(v-for="(menu, idx) in currentMenu", :key="menu.url")
      a-icon(:type="menu.icon")
      span {{$bk18n.t(menu.name)}}
    //- a-sub-menu(key="sub1")
      span(slot="title")
        a-icon(type="mail")
        span Navigation One
      a-menu-item(key="5") Option 5
      a-menu-item(key="6") Option 6
</template>

<script>
import { mapState } from 'vuex'
export default {
  computed: {
    ...mapState({
      currentMenu: (state) => state.currentMenu
    })
  },
  methods: {
    menuItemClick ({ item, key, keyPath }) {
      console.log('item', item)
      console.log('key', key)
      console.log('keypath', keyPath)
      this.jump(key)
    }
  }
}
</script>

<style lang="stylus" scoped></style>
