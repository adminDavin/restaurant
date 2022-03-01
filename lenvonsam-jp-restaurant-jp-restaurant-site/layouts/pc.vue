<template lang="pug">
client-only
  .bg-white(style="padding-top: 186px;font-size: 16px;font-family: NotoSansJP-Medium")
    .m-pc-header.bg-white
      .content.flex
        .flex-150.text-center
          img.logo(src="~assets/imgs/logo.png")
          span.ft-20.ft-bold(style="line-height: 30px") SitaDining's
        .col.text-right.flex.relative(style="align-items: flex-end; margin-bottom: 5px; padding-left: 15px")
          .pc-lang-icon(@click="changeLang")
            img(src="~assets/imgs/lang_zh.png", v-if="localLang === 'ja'")
            img(src="~assets/imgs/lang_en.png", v-if="localLang === 'zh'")
            img(src="~assets/imgs/lang_jp.png", v-if="localLang === 'en'")
          a-menu.jp-pc-top-menu(mode="horizontal", v-model="currentMenu", @click="clickTopMenu")
            template(v-for="(menu, index) in topMenus")
              a-sub-menu(v-if="index === 3", key="#", popupClassName="top-menu-sub")
                span(slot="title")
                  i.iconfont.ft-18.mr-5(:class="'icon-' + menu.icon")
                  | {{$pc18n.t(menu.title)}}
                a-menu-item(v-for="(m, midx) in merchantList", :key="'/pc/merchant/detail?id=' + m.id") {{m.merchantName}}
              a-menu-item(v-else, :key="menu.url")
                i.iconfont.ft-18.mr-5(:class="'icon-' + menu.icon")
                | {{$pc18n.t(menu.title)}}
    .pc-fix-appoint-btn(@click="jump2AppointPage", :class="{'small': scale}", v-if="fixBtnShow")
    a-layout-content
      nuxt
    a-layout-footer(:style="{'background': 'black', 'color': 'white', 'padding-top': '70px', 'padding-bottom': '50px'}")
      a-row
        a-col(:span="18")
          a-row(v-for="(row, ridx) in bottom2D", :key="ridx")
            a-col.pb-30(:span="(cidx === row.length - 1) ? 4 : 5", v-for="(col, cidx) in row", :key="ridx + '' + cidx", :class="{'home-bottom-right-line': cidx === row.length - 1}")
              .ft-16.ft-bold.mb-20 {{col.title}}
              .pc-bottom-item(v-for="(m, midx) in col.menus") {{$pc18n.t(m)}}
          .mt-25 {{$pc18n.t('footer.version')}} 
          .mt-5.mb-15.ft-10 {{$pc18n.t('footer.info')}}
        a-col.pl-25(:span="6")
          div(style="width: 80%;margin: 0 auto")
            a-row
              a-col.mb-20(:span="8")
                img(src="~assets/imgs/icon_foot_twitter.png", style="width:35px")
              a-col.mb-20(:span="8")
                img(src="~assets/imgs/icon_foot_face.png", style="width:35px")
              a-col.mb-20(:span="8")
                img(src="~assets/imgs/icon_foot_ins.png", style="width:35px")
              a-col.mb-20(:span="8")
                img(src="~assets/imgs/icon_foot_line.png", style="width:35px")
      
</template>

<script>
import zhCN from 'ant-design-vue/lib/locale-provider/zh_CN'
import jaJP from 'ant-design-vue/lib/locale-provider/ja_JP'
export default {
  data () {
    return {
      localLang: 'ja',
      local: jaJP,
      visible: false,
      scale: false,
      merchantList: [],
      currentMenu: [],
      topMenus: [{
        icon: 'home',
        title: 'navHead.home',
        url: '/pc/'
      }, {
        icon: 'bag',
        title: 'navHead.intro',
        url: '/pc/intro'
      }, {
        icon: 'bell',
        title: 'navHead.news',
        url: '/pc/news/'
      }, {
        icon: 'location',
        title: 'navHead.merchant',
        url: '#'
      }, {
        icon: 'calendar',
        title: 'navHead.appoint',
        url: '/pc/appoint'
      }, {
        icon: 'cart',
        title: 'navHead.onlineShop',
        url: 'https://sitadinning.base.shop'
      }],
      bottomMenus: [{
        title: 'Sita 中目黑店',
        menus: ['footer.menu1', 'footer.menu2', 'footer.menu4', '中目黑', 'footer.menu6']
      }, {
        title: 'Sita 学芸大学店',
        menus: ['footer.menu1', 'footer.menu2', 'footer.menu4', '学芸大学駅', 'footer.menu7']
      },
      {
        title: 'Sita 祐天寺店',
        menus: ['footer.menu1', 'footer.menu2', 'footer.menu4', '祐天寺', 'footer.menu8']
      },
      {
        title: 'Sita 八丁堀店',
        menus: ['footer.menu1', 'footer.menu2', 'footer.menu4', '八丁堀', 'footer.menu7']
      }, {
        title: 'SURYA 中目黑店',
        menus: ['footer.menu3', 'footer.menu1', 'footer.menu5', '中目黑', 'footer.menu7']
      }],
    }
  },
  computed: {
    bottom2D () {
      return this.arr2D(this.bottomMenus, true, 5)
    },
    fixBtnShow () {
      return (this.$route.path !== '/pc/appoint' && this.$route.path !== '/pc/merchant/detail')
    }
  },
  beforeMount () {
    this.localLang = 'ja'
    this.loadMerchants()
    this.initCurrentMenu()
  },
  watch: {
    '$route.path' (newVal) {
      const menu = this.currentMenu[0]
      console.log('path:>>', newVal)
      console.log('menu:>>', menu)
      if (menu !== newVal) {
        this.currentMenu = []
        this.initCurrentMenu()
      }
    }
  },
  methods: {
    changeLang () {
      if (this.localLang === 'ja') {
        this.localLang = 'zh'
      } else if (this.localLang === 'zh') {
        this.localLang = 'en'
      } else {
        this.localLang = 'ja'
      }
      this.$pc18n.locale = this.localLang
    },
    jump2AppointPage () {
      const me = this
      if (!this.scale) {
        this.scale = true
        setTimeout(function () {
          me.scale = false
        }, 160)
      }
      setTimeout(function () {
        me.redirect('/pc/appoint')
      }, 180)
    },
    initCurrentMenu () {
      if (this.$route.path === '/pc/merchant/detail') {
        this.currentMenu.push(this.$route.path + '?id=' + this.$route.query.id)
      } else if (this.$route.path === '/pc/news/detail') {
        this.currentMenu.push('/pc/news/')
      } else {
        this.currentMenu.push(this.$route.path)
      }
      console.log('this.currentMenu:>>', this.currentMenu)
    },
    openMenu () {
      this.visible = !this.visible
    },
    jump2Page (url) {
      console.log('url:>>>', url)
      if (url.startsWith('http:')) {
        window.open(url)
      } else if (url === '#') {
      } else {
        this.redirect(url)
      }
      this.visible = false
    },
    click2Merchant (url) {
      console.log(this.$route.path)
      this.redirect(url)
      this.visible = false
      if (this.$route.path === '/mb/merchant/detail') {
        window.location.href = window.location.origin + url
      }
    },
    async loadMerchants () {
      try {
        const { data } = await this.proxy('api/sys/merchants')
        this.merchantList = data.data
      } catch (e) {
        this.msgShow(e.message || '网络异常')
      }
    },
    clickTopMenu ({ key }) {
      if (key.startsWith('https')) {
        this.openNewWindow(key)
        return
      }
      if (key !== this.$route.path) {
        this.redirect(key)
        // if (this.$route.path === '/pc/merchant/detail') {
        //   window.location.href = window.location.origin + key
        // }
      }
    }
  }
}
</script>

<style lang="stylus">
@import '../assets/stylus/common'
.top-menu-sub .ant-menu-item:hover, .top-menu-sub .ant-menu-item.ant-menu-item-selected
  color #8DBC3C
.jp-pc-top-menu
  color #6D8B4C
  width 100%
  &.ant-menu-horizontal
    border 0px
  .ant-menu-submenu-title
    color #6D8B4C
    padding 0 24px
  .ant-menu-submenu-title:hover
    color #8DBC3C
  .ant-menu-item:hover, .ant-menu-item.ant-menu-item-selected, .ant-menu-submenu:hover, .ant-menu-submenu-active, .ant-menu-submenu-open, .ant-menu-submenu-selected
    color #8DBC3C
    border-bottom 2px solid #8DBC3C
.m-pc-header
  position fixed
  z-index 1024
  overflow hidden
  height 166px
  padding-top 10px
  top 0px
  width 100%
  box-sizing border-box
  .content
    width 1024px
    margin 0px auto
    line-height 60px
  .logo
    width 100px
    height 100px
.pc-bottom-item
  padding-left 6px
  position relative
  line-height 36px
  &::before
    position absolute
    left -2px
    top 17px
    content ''
    display block
    width 1px
    height 1px
    border-radius 0.5px
    background white
.left-menu-panel.ant-collapse-borderless > .ant-collapse-item
  border-bottom 0px
.left-menu-panel.ant-collapse > .ant-collapse-item > .ant-collapse-header
  padding-left 0px
  padding-top 3px
  color #6d8b4c
  font-size 16px
.left-menu-panel
  .ant-collapse-content > .ant-collapse-content-box
    padding-left 0px
.home-bottom-right-line
  border-right 1px dashed #fff
.pc-lang-icon
  position absolute
  top -12px
  right 2%
  box-sizing border-box
  width 30px
  height 30px
  &:hover
    cursor pointer
</style>

