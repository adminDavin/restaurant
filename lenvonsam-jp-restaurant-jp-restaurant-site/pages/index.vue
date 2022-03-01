<template lang="pug">
div
</template>

<script lang="ts">
import { Vue, Component } from 'vue-property-decorator';

@Component({
  components: {},
})
class IndexPage extends Vue {
  
  mounted() {
    
    if (this.isMobile()) {
      this.redirect('/mb/')
    } else {
      if (document.body.clientWidth < document.body.clientHeight) {
        this.redirect('/mb/')
      } else {
        this.redirect('/pc/')
      }
    }
  }
}
export default IndexPage
</script>

<style lang="stylus" scoped>
.container
  width 500
</style>
