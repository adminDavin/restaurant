<template lang="pug">
client-only
  a-config-provider(:locale="local")
    a-layout#bk-container(style="height: 100vh; overflow:hidden")
      a-layout-sider(v-model="collapsed", :trigger="null", collapsible, :collapsedWidth="sideWidth")
        side-menu
      a-layout
        a-layout-header.nav-header-wrap(:style="{'left': collapsed ? sideWidth + 'px' : '200px'}")
          nav-header(:collapsed.sync="collapsed")
          div(style="height: 15px")  
          .full-width(style="height: 50px")
            nav-tab-menu(@select="tabMenuSelect", :tabList="tabMenuList", :activeKey.sync="activeKey")
        a-layout-content.content-box-wrap
          nuxt
</template>
<script>
import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
import jaJP from 'ant-design-vue/lib/locale-provider/ja_JP'
import moment from 'moment'
import { mapState, mapActions } from 'vuex'
import NavTabMenu from '@/components/NavTabMenu.vue'
import NavHeader from '@/components/NavHeader.vue'
import SideMenu from '@/components/SideMenu.vue'
import zh from '@/assets/langs/zh-bk'
import jp from '@/assets/langs/jp-bk'

moment.locale('zh-cn')
export default {
  components: {
    NavTabMenu,
    NavHeader,
    SideMenu
  },
  middleware: 'auth',
  data () {
    return {
      collapsed: false,
      sideWidth: 60,
      searchBar: false,
      // zhCN,
      local: {},
      tabMenuList: [],
      activeKey: '/',
      testI18: null
    }
  },
  computed: {
    ...mapState({
      currentMenu: (state) => state.currentMenu,
      currentUser: (state) => state.user.currentUser,
      localLang: (state) => state.localLang
    })
  },
  watch: {
    '$route.path': {
      handler (newVal) {
        console.log('route val:>>', newVal)
        this.activeKey = newVal
        if (
          this.currentUser.id < 1 ||
          this.currentUser.expireAt < new Date().getTime()
        ) {
          this.jump('/login')
        } else if (this.activeKey !== '/' && this.activeKey !== '/login') {
          const idx = this.tabMenuList.findIndex((item) => item.url === newVal)
          console.log('idx:>>', idx)
          if (idx === -1) {
            const obj = this.currentMenu.find((itm) => itm.url === newVal)
            this.tabMenuList.push(obj)
          }
        }
      }
    },
    localLang (newVal) {
      if (newVal === 'ja') {
        this.local = jaJP
        this.$bk18n.locale = 'ja'
      } else {
        this.local = zhCN
        this.$bk18n.locale = 'zh'
      }
    }
  },
  beforeMount () {
    if (this.localLang === 'ja') {
      this.local = jaJP
      this.$bk18n.locale = 'ja'
    } else {
      this.local = zhCN
      this.$bk18n.locale = 'zh'
    }
    // this['user/autoLogin']()
    // console.log('currentUser', this.currentUser)
    // if (
    //   this.currentUser.id < 1 ||
    //   this.currentUser.expireAt < new Date().getTime()
    // ) {
    //   this.jump('/login')
    // } else {
    //   this.activeKey = this.$route.path
    //   if (this.activeKey !== '/') {
    //     const obj = this.currentMenu.find((itm) => itm.url === this.activeKey)
    //     this.tabMenuList.push(obj)
    //   }
    // }
  },
  methods: {
    ...mapActions(['user/autoLogin']),
    tabMenuSelect (key, val) {
      console.log('key:>>', key, 'val:>>', val)
      let idx = this.tabMenuList.findIndex((itm) => itm.url === val)
      let jumpUrl = val
      switch (key) {
        case 'click':
          console.log('route push')
          break
        case 'remove':
          if (idx > 0) {
            idx = idx - 1
          } else {
            idx = 0
          }
          this.tabMenuList = this.tabMenuList.filter((itm) => itm.url !== val)
          if (this.tabMenuList.length === 0) {
            jumpUrl = '/'
          } else {
            jumpUrl = this.tabMenuList[idx].url
          }
          break
        case 'removeLeftAll':
          this.tabMenuList = this.tabMenuList.filter((itm, sidx) => sidx >= idx)
          idx = 0
          break
        case 'removeRightAll':
          this.tabMenuList = this.tabMenuList.filter((itm, sidx) => sidx <= idx)
          break
        case 'removeOther':
          this.tabMenuList = [this.tabMenuList[idx]]
          idx = 0
          break
      }
      console.log('jumpUrl:>>', jumpUrl)
      this.jump(jumpUrl)
    }
  }
}
</script>

<style lang="stylus">
@import '../assets/stylus/common'
.nav-avatar-menu
  top 73px !important
  z-index 1010
  /deep/.ant-dropdown-menu
    &::after
      content ''
      position absolute
      top -6px
      right 18px
      border-left 10px solid transparent
      border-right 10px solid transparent
      border-bottom 8px solid $color-white
  &.notice
    width 200px
    /deep/.ant-dropdown-menu
      &::after
        right 8px
#bk-container
  height 100vh
  overflow hidden
  /deep/.ant-layout
    background transparent
  /deep/.ant-layout-header
    line-height normal
  .nav-header-wrap
    position fixed
    z-index 1010
    left 200px
    right 0px
    height 140px
    line-height 20px
    background transparent
    display flex
    flex-direction column
    align-items center
    justify-content center
    padding 0 20px
    .nav-header
      width 100%
      height 50px
      border-radius 0.5rem
      background $color-white
      transition 300ms ease all
      display flex
      align-items center
      padding 0 15px
      box-sizing border-box
      .icon:hover, .avatar:hover
        cursor pointer
      .icon
        flex 0 0 35px
        font-size $font-size-large-x
      .item
        flex 1
      .avatar
        flex 0 0 120px
        display flex
        align-items center
        .left
          text-align center
          flex 1
          max-width 80px
        .right
          flex 0 0 40px
      &.nav-shadow
        -webkit-box-shadow 0 4px 20px 0 rgba(0, 0, 0, 0.05)
        box-shadow 0 4px 20px 0 rgba(0, 0, 0, 0.05)
  .content-box-wrap
    padding 0 20px
    padding-top 130px
</style>
