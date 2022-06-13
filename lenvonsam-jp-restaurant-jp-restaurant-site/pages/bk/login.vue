<template lang="pug">
.login-container.banner-gradient
  .login-box
    .title 用户登录
    a-form(:label-col="{ span: 5 }", :wrapper-col="{ span: 16 }")
      a-form-item(label="用户名")
        a-input(v-model="name") 密码
      a-form-item(label="密码")
        a-input-password(v-model="pwd") 密码
      a-form-item(:wrapper-col="{ span: 12, offset: 9 }")
        a-button(@click="userLogin", type="primary") 用户登录
</template>

<script lang="ts">
import { Component, Vue } from 'vue-property-decorator'
import sha1 from 'sha1'
import { State, namespace } from 'vuex-class'
const userMoudle = namespace('user')

let me: any = null
@Component({})
class LoginPage extends Vue {
  @State((state: any) => state.user.isLogin) isLogin!: boolean
  @userMoudle.Action('setUser') setUser!: Function
  name: string = ''
  pwd: string = ''
  sha1: any = sha1
  beforeMount() {
    me = this
  }

  async userLogin() {
    try {
      const { data }: any = await me.proxy('user/account/login', 'post', {
        loginName: this.name,
        userPwd: sha1(this.pwd),
      })
      console.log('data:>>', data)
      this.setUser(data.data)
      me.msgShow('ログインしました。', 'success')
      me.jump('/bk/')
    } catch (e) {
      console.log('is login:>>.', this.isLogin)
      console.log('pwd:>>', this.sha1(this.pwd))
    }
  }
}
export default LoginPage
</script>

<style lang="stylus" scoped>
@keyframes gradient
  50%
    background-position 100% 0
.login-container
  height 100vh
  overflow hidden
  position relative
  &.banner-gradient
    background #0299d8
    background -webkit-linear-gradient(
      45deg,
      #5a3694 0,
      #13bdce 33%,
      #0094d9 66%,
      #6fc7b5 100%
    )
    background linear-gradient(
      45deg,
      #5a3694 0,
      #13bdce 33%,
      #0094d9 66%,
      #6fc7b5 100%
    )
    background-size 400%
    background-position 0 100%
    -webkit-animation gradient 12s ease-in-out infinite
    animation gradient 12s ease-in-out infinite
  .login-box
    .title
      font-size 22px
      margin-bottom 25px
    background white
    width 500px
    height 300px
    margin 0 auto
    margin-top calc(((100vh - 300px) / 2))
    border-radius 20px
    padding 20px
    padding-top 25px
    padding-bottom 0px
    box-sizing border-box
    display flex
    flex-direction column
    align-items center
    justify-content center
    form
      width 80%
      margin 0 auto
</style>
