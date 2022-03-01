<template lang="pug">
.nav-header.nav-shadow
  template(v-if="searchBar")
    a-icon.icon(type="search")
    .item.pl-10.pr-10
      a-input.full-width(placeholder="请输入查询内容", allow-clear)
    a-icon.icon(type="close", @click="searchBar = false")
  template(v-else)
    a-icon.icon(:type="collapsed ? 'menu-unfold': 'menu-fold'", @click="toggleSideBar")
    .item
    s-tooltip(title="全屏", :config="{type: 'icon', icon: 'fullscreen', class: 'icon'}", @click.native="fullScreen")
    //- s-tooltip(title="搜索", :config="{type: 'icon', icon: 'search', class: 'icon'}", @click.native="searchBar = true")
    //- a-dropdown.icon(overlayClassName="nav-avatar-menu notice")
      a-menu(slot="overlay")
        a-menu-item 123
        a-menu-item 123
        a-menu-item 123
      a-badge(count="1",dot, :offset="[-19, 0]")
        a-icon(type="bell")
    img(:src="lang === 'ja' ? zhLang : jpLang", width="24px", @click="switchLang")
    a-dropdown(overlayClassName="nav-avatar-menu")
      a-menu(slot="overlay")
        a-menu-item(@click="exitAction") {{$bk18n.t('sys.exit')}}
      .avatar
        .left
          .ft-13.ft-bold.ellps-row {{currentUser.userName}}
          //- .ft-10.text-gray 总监
        .right
          a-avatar(icon="user")
</template>
<script>
import { mapState, mapActions } from 'vuex'
export default {
  props: {
    collapsed: {
      type: Boolean,
      default: false
    }
  },
  computed: {
    ...mapState({
      currentUser: (state) => state.user.currentUser
    })
  },
  beforeMount () {
    console.log('user', this.currentUser)
  },
  data () {
    return {
      searchBar: false,
      lang: 'ja',
      jpLang: require('../assets/imgs/lang_jp.png'),
      zhLang: require('../assets/imgs/lang_zh.png')
    }
  },
  methods: {
    ...mapActions(['user/exitUser', 'configVal']),
    toggleSideBar () {
      this.$emit('update:collapsed', !this.collapsed)
    },
    switchLang () {
      if (this.lang === 'ja') {
        this.lang = 'zh'
      } else {
        this.lang = 'ja'
      }
      this.configVal({
        key: 'localLang',
        val: this.lang
      })
    },
    exitAction () {
      const me = this
      this.confirm({
        title: '您确定要退出吗？'
      }).then((res) => {
        if (res === 'confirm') {
          this.request({ url: '/proxy/logout', params: {} })
          me['user/exitUser']()
          me.redirect('/bk/login')
        }
      })
    }
  }
}
</script>
