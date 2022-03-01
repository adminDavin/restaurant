import { Vue, Component } from 'vue-property-decorator'

export declare class GlobalUtil {
  jump(to: any):void
  back():void
  redirect(to: any): void
}

export default  { 
  install (Vue: any)  {
  const self:any = Vue
  Vue.mixin({
    methods: {
      jump(to: any) {
        if (self.$router) {
          self.$router.push(to)
        } else {
          console.log('this.$route 不存在', self.$router)
        }
      },
      back() {
        if (self.$router) self.$router.go(-1)
      },
      redirect(to: any) {
        if (self.$router) self.$router.replace(to)
      }
    }
  })
}
}

// export class GlobalUtilImpl extends Vue implements GlobalUtil{
//   vue_?: Vue
//   constructor(vue: Vue) {
//     super()
//     if(vue) {
//       this.vue_ = vue
//     }
//   }
//   jump(to: any) {
//     console.log('vue install', this.vue_)
//     if (this.vue_!!.$router) {
//       this.$router.push(to)
//     } else {
//       console.log('this.$route 不存在', this.$router)
//     }
//   }
//   back() {
//     if (this.$router) this.$router.go(-1)
//   }
//   redirect(to: any) {
//     if (this.$router) this.$router.replace(to)
//   }
// }
