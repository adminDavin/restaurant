<template lang="pug">
.full-width
  context-menu(:visible.sync="contextMenuShow", @select="onMenuSelect")
  a-tabs(type="editable-card", @contextmenu.prevent.native="showContextMenuBar($event)", size="small", :tabBarGutter="tabGutter", hide-add, @change="tabChange", @edit="tabEdit", :activeKey="activeKey") 
    a-tab-pane(key="/", :closable="false", :tab="$bk18n.t('sys.home')")
    a-tab-pane(v-for="(tab,idx) in tabList", :key="tab.url", v-if="tabList.length > 0")
      span(slot="tab", :url="tab.url") {{$bk18n.t(tab.name)}}
</template>

<script>
import contextMenu from '@/components/ContextMenu.vue'
export default {
  components: {
    contextMenu
  },
  props: {
    activeKey: {
      type: String,
      default: '/'
    },
    tabList: {
      type: Array,
      default: () => []
    },
    tabGutter: {
      type: Number,
      default: 4
    }
  },
  data () {
    return {
      contextMenuShow: false
    }
  },
  methods: {
    /**
     * 由于ant-design-vue组件库的TabPane组件暂不支持自定义监听器，无法直接获取到右键target所在标签页的 url 。故增加此方法用于
     * 查询右键target所在标签页的标识 url ，以用于自定义右键菜单的事件处理。
     * 注：TabPane组件支持自定义监听器后可去除该方法并重构 ‘自定义右键菜单的事件处理’
     * @param target 查询开始目标
     * @param count 查询层级深度 （查找层级最多不超过3层，超过3层深度直接返回 null）
     * @return {String}
     */
    getPageKey (target, depth) {
      depth = depth || 0
      if (depth > 2) {
        return null
      }
      let pageKey = target.getAttribute('url')
      pageKey =
        pageKey ||
        (target.previousElementSibling
          ? target.previousElementSibling.getAttribute('url')
          : null)
      return (
        pageKey ||
        (target.firstElementChild
          ? this.getPageKey(target.firstElementChild, ++depth)
          : null)
      )
    },
    onMenuSelect (key, target) {
      console.log('key:>>', key)
      console.log('select target:>>', target)
      const url = this.getPageKey(target)
      switch (key) {
        case 1:
          this.$emit('select', 'removeLeftAll', url)
          break
        case 2:
          this.$emit('select', 'removeRightAll', url)
          break
        default:
          this.$emit('select', 'removeOther', url)
          break
      }
    },
    showContextMenuBar (e) {
      console.log('event:>>', e)
      const translateIndex = this.$bk18n.t('首页')
      if (e.target.textContent && e.target.textContent !== translateIndex)
        this.contextMenuShow = true
    },
    tabChange (key) {
      console.log('tab key:>>', key)
      this.$emit('update:activeKey', key)
      this.$emit('select', 'click', key)
    },
    tabEdit (targetKey, action) {
      console.log('targetKey:>>', targetKey, 'action:>>', action)
      this.$emit('select', action, targetKey)
    }
  }
}
</script>
