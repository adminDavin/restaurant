<template lang="pug">
h1 {{$bk18n.t('sys.index')}}
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
@Component({
  layout: 'backend',
})
class BkIndexPage extends Vue {}
export default BkIndexPage
</script>

