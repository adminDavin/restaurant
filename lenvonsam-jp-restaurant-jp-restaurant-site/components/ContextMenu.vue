<template lang="pug">
a-menu(:style="style",v-show="visible",class="contextmenu",:selectedKeys="selectedKeys",@click="handleClick")
  a-menu-item(v-for="item in itemList",:key="item.key")
    a-icon(v-if="item.icon",role="menuitemicon",:type="item.icon")
    | {{item.text}}
</template>

<script>
export default {
  name: 'Contextmenu',
  props: {
    visible: {
      type: Boolean,
      required: false,
      default: false
    },
    itemList: {
      type: Array,
      required: false,
      default: () => [
        {
          key: 1,
          icon: 'arrow-left',
          text: '关闭左侧'
        },
        {
          key: 2,
          icon: 'arrow-right',
          text: '关闭右侧'
        },
        {
          key: 3,
          icon: 'close',
          text: '关闭其他'
        }
      ]
    }
  },
  data() {
    return {
      left: 0,
      top: 0,
      target: null,
      selectedKeys: []
    }
  },
  computed: {
    style() {
      return {
        left: this.left + 'px',
        top: this.top + 'px'
      }
    }
  },
  beforeMount() {
    console.log('itemlist:>>', this.itemList)
    window.addEventListener('mousedown', (e) => this.closeMenu(e))
    window.addEventListener('contextmenu', (e) => this.setPosition(e))
  },
  beforeDestroy() {
    window.removeEventListener('mousedown', (e) => this.closeMenu(e))
    window.removeEventListener('contextmenu', (e) => this.setPosition(e))
  },
  methods: {
    closeMenu(e) {
      if (
        !['menuitemicon', 'menuitem'].includes(e.target.getAttribute('role'))
      ) {
        this.$emit('update:visible', false)
      }
    },
    setPosition(e) {
      this.left = e.clientX
      this.top = e.clientY
      this.target = e.target
    },
    handleClick({ key }) {
      this.$emit('select', key, this.target)
      this.$emit('update:visible', false)
    }
  }
}
</script>

<style lang="stylus" scoped>
.contextmenu
  position fixed
  z-index 999999
  border 1px solid #9e9e9e
  border-radius 4px
  box-shadow 2px 2px 10px #aaaaaa !important
</style>
