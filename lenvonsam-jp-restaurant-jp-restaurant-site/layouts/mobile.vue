<template lang="pug">
client-only
  a-layout.mb-layout(style="font-family: NotoSansJP-Regular")
    a-drawer.mb-drawer(placement="left", :visible="visible", @close="openMenu")
      div(style="padding-top: 15%")
        a-row(style="height: 50px;font-size: 16px", v-for="(menu, index) in sideMenus", :key="index")
          a-col(:span="4")
            i.iconfont.ft-20(:class="'icon-'+menu.icon")
          a-col(:span="18", v-if="index === 3")
            a-collapse.left-menu-panel(:bordered="false", style="background: transparent")
              a-collapse-panel.text-green(:header="$pc18n.t(menu.title)", :showArrow="false", :forceRender="true")
                .border-bottom-line(v-for="(m, midx) in merchantList", :key="midx", style="line-height: 40px;position: relative; z-index: 20", @click="click2Merchant('/mb/merchant/detail?id=' + m.id)") {{m.merchantName}}
          a-col.text-green(:span="18", @click="jump2Page(menu.url)", v-else, style="padding-top: 3px") {{$pc18n.t(menu.title)}}
    .mb-fix-appoint-btn(@click="fixAppointBtn", :class="{'full': slide, 'small': scale}", v-if="fixAppointShow")
      .container
        .dot(@click="fixAppointBack")
    a-layout
      a-layout-header.m-header.flex(:style="{padding: '0 15px'}")
        .flex-20
          a-icon(type="menu", :style="{'color': '#8DBC3C', fontSize: '20px', fontWeight: 'bold'}", @click="openMenu")
        .col.text-center 
          img.logo(src="~assets/imgs/logo.png")
          span.ft-20 SitaDining's
        .flex-20(@click="changeLang")
          img(src="~/assets/imgs/lang_cn_sp.png", style="width: 20px", v-if="localLang === 'ja'")
          img(src="~/assets/imgs/lang_en_sp.png", style="width: 20px", v-if="localLang === 'zh'")
          img(src="~/assets/imgs/lang_jp_sp.png", style="width: 20px", v-if="localLang === 'en'")
      a-layout-content(style="padding-top: 64px")
        nuxt
      a-layout-footer(:style="{'background': 'black', 'color': 'white'}")
        a-row.mb-30(v-for="(row, ridx) in bottom2D", :key="ridx")
          template(v-for="(col, cidx) in row")
            a-col.pl-25.pr-10(:span="12", v-if="col.title === 'icons'")
              .row.mb-15.text-center
                .col
                  img(src="~assets/imgs/icon_foot_twitter.png", style="width:35px")
                .col
                  img(src="~assets/imgs/icon_foot_face.png", style="width:35px")
              .row.text-center
                .col
                  img(src="~assets/imgs/icon_foot_ins.png", style="width:35px")
                .col
                  img(src="~assets/imgs/icon_foot_line.png", style="width:35px")
            a-col(:span="12", v-else, :style="{paddingLeft: cidx === 1 ? '20px' : '0px'}")
              .ft-16.ft-bold.mb-20 {{col.title}}
              .mb-bottom-item(v-for="(m, midx) in col.menus") {{$pc18n.t(m)}}
        .text-center.mt-75  {{$pc18n.t('footer.version')}}
        .text-center.mb-15.ft-10 {{$pc18n.t('footer.info')}}
</template>

<script>
export default {
  data () {
    return {
      localLang: 'ja',
      visible: false,
      slide: false,
      slideClick: false,
      scale: false,
      merchantList: [],
      sideMenus: [{
        icon: 'home',
        title: 'navHead.home',
        url: '/mb/'
      }, {
        icon: 'bag',
        title: 'navHead.intro',
        url: '/mb/intro/'
      }, {
        icon: 'bell',
        title: 'navHead.news',
        url: '/mb/news/'
      }, {
        icon: 'location',
        title: 'navHead.merchant',
        url: '#'
      }, {
        icon: 'calendar',
        title: 'navHead.appoint',
        url: '/mb/appoint/'
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
      return this.arr2D(this.bottomMenus, true, 2)
    },
    fixAppointShow () {
      console.log(this.$route.path)
      return (this.$route.path === '/mb/' || this.$route.path === '/mb/news/' || this.$route.path === '/mb/intro/')
    }
  },
  beforeMount () {
    this.localLang = 'ja'
    this.loadMerchants()
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
    fixAppointBtn () {
      const me = this
      if (!this.slide) {
        this.slideClick = false
        this.slide = true
        setTimeout(function () {
          me.slideClick = true
        }, 250)
      }
      if (this.slideClick) {
        this.scale = true
        setTimeout(function () {
          me.scale = false
        }, 200)
      }
      if (this.slideClick) {
        setTimeout(function () {
          me.redirect('/mb/appoint')
          setTimout(function () {
            me.slide = false
          }, 50)
        }, 250)
      }
    },
    fixAppointBack (evt) {
      evt.stopPropagation()
      this.slide = false
    },
    openMenu () {
      this.visible = !this.visible
    },
    jump2Page (url) {
      console.log('url:>>>', url)
      if (url.startsWith('https:')) {
        this.openNewWindow(url)
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
      // if (this.$route.path === '/mb/merchant/detail') {
      // window.location.href = window.location.origin + url
      // }
    },
    async loadMerchants () {
      try {
        const { data } = await this.proxy('api/sys/merchants')
        this.merchantList = data.data
      } catch (e) {
        this.msgShow(e.message || '网络异常')
      }
    }
  }
}
</script>

<style lang="stylus">
@import '../assets/stylus/common'
.mb-layout
  -moz-user-select none
  -ms-user-select none
  -webkit-user-select none
  user-select none
.m-header
  background #fff
  position fixed
  z-index 10
  width 100%
  overflow hidden
  box-sizing border-box
  display flex
  align-items center
  .logo
    width 25px
    height 25px
    margin-top -5px
    margin-right 10px
.mb-drawer
  .ant-drawer-close
    font-weight bold
    font-size 20px
    color #8DBC3C !important
.mb-bottom-item
  padding-left 6px
  font-size 12px
  line-height 32px
  position relative
  &::before
    position absolute
    left -2px
    top 16px
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
</style>

