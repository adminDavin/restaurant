<template>
  <client-only>
    <editor :id="id"
            basePath="/kindeditor/"
            :items="items"
            uploadJson="/proxy/fileUpload"
            fileManagerJson="/proxy/fileUpload"
            @on-content-change="contentChange"
            width="100%"
            :content="outContent" />
  </client-only>
</template>

<script>
export default {
  name: 'Kindeditor',
  props: {
    content: {
      type: String,
      default: ''
    },
    id: {
      type: String,
      required: true
    },
    width: {
      type: String,
      default: ''
    },
    height: {
      type: String,
      default: ''
    },
    minWidth: {
      type: Number,
      default: 650
    },
    minHeight: {
      type: Number,
      default: 100
    },
    items: {
      type: Array,
      default () {
        return [
          'source',
          '|',
          'undo',
          'redo',
          '|',
          'preview',
          'code',
          'cut',
          'copy',
          'paste',
          'plainpaste',
          'wordpaste',
          '|',
          'justifyleft',
          'justifycenter',
          'justifyright',
          'justifyfull',
          'insertorderedlist',
          'insertunorderedlist',
          'indent',
          'outdent',
          'subscript',
          'superscript',
          'clearhtml',
          'quickformat',
          'selectall',
          '|',
          '/',
          'formatblock',
          'fontname',
          'fontsize',
          '|',
          'forecolor',
          'hilitecolor',
          'bold',
          'italic',
          'underline',
          'strikethrough',
          'lineheight',
          'removeformat',
          '|',
          'image',
          'table',
          'hr',
          'pagebreak',
          'anchor',
          'link',
          'unlink',
        ]
      }
    },
    noDisableItems: {
      type: Array,
      default () {
        return ['source', 'fullscreen']
      }
    },
    filterMode: {
      type: Boolean,
      default: true
    },
    htmlTags: {
      type: Object,
      default () {
        return {
          font: ['color', 'size', 'face', '.background-color'],
          span: ['style'],
          div: ['class', 'align', 'style'],
          table: [
            'class',
            'border',
            'cellspacing',
            'cellpadding',
            'width',
            'height',
            'align',
            'style'
          ],
          'td,th': [
            'class',
            'align',
            'valign',
            'width',
            'height',
            'colspan',
            'rowspan',
            'bgcolor',
            'style'
          ],
          a: ['class', 'href', 'target', 'name', 'style'],
          embed: [
            'src',
            'width',
            'height',
            'type',
            'loop',
            'autostart',
            'quality',
            'style',
            'align',
            'allowscriptaccess',
            '/'
          ],
          img: [
            'src',
            'width',
            'height',
            'border',
            'alt',
            'title',
            'align',
            'style',
            '/'
          ],
          hr: ['class', '/'],
          br: ['/'],
          'p,ol,ul,li,blockquote,h1,h2,h3,h4,h5,h6': ['align', 'style'],
          'tbody,tr,strong,b,sub,sup,em,i,u,strike': []
        }
      }
    },
    wellFormatMode: {
      type: Boolean,
      default: true
    },
    resizeType: {
      type: Number,
      default: 2
    },
    themeType: {
      type: String,
      default: 'default'
    },
    langType: {
      type: String,
      default: 'zh-CN'
    },
    designMode: {
      type: Boolean,
      default: true
    },
    fullscreenMode: {
      type: Boolean,
      default: false
    },
    basePath: {
      type: String,
      default: ''
    },
    themesPath: {
      type: String,
      default: ''
    },
    pluginsPath: {
      type: String,
      default: ''
    },
    langPath: {
      type: String,
      default: ''
    },
    minChangeSize: {
      type: Number,
      default: 5
    },
    loadStyleMode: {
      type: Boolean,
      default: true
    },
    urlType: {
      type: String,
      default: ''
    },
    newlineTag: {
      type: String,
      default: 'p'
    },
    pasteType: {
      type: Number,
      default: 2
    },
    dialogAlignType: {
      type: String,
      default: 'page'
    },
    shadowMode: {
      type: Boolean,
      default: true
    },
    zIndex: {
      type: Number,
      default: 811213
    },
    useContextmenu: {
      type: Boolean,
      default: true
    },
    syncType: {
      type: String,
      default: 'form'
    },
    indentChar: {
      type: String,
      default: '\t'
    },
    cssPath: {
      type: [String, Array]
    },
    cssData: {
      type: String
    },
    bodyClass: {
      type: String,
      default: 'ke-content'
    },
    colorTable: {
      type: Array
    },
    afterCreate: {
      type: Function
    },
    afterChange: {
      type: Function
    },
    afterTab: {
      type: Function
    },
    afterFocus: {
      type: Function
    },
    afterBlur: {
      type: Function
    },
    afterUpload: {
      type: Function
    },
    uploadJson: {
      type: String
    },
    fileManagerJson: {
      type: Function
    },
    allowPreviewEmoticons: {
      type: Boolean,
      default: true
    },
    allowImageUpload: {
      type: Boolean,
      default: true
    },
    allowFlashUpload: {
      type: Boolean,
      default: true
    },
    allowMediaUpload: {
      type: Boolean,
      default: true
    },
    allowFileUpload: {
      type: Boolean,
      default: true
    },
    allowFileManager: {
      type: Boolean,
      default: false
    },
    fontSizeTable: {
      type: Array,
      default () {
        return ['9px', '10px', '12px', '14px', '16px', '18px', '24px', '32px']
      }
    },
    imageTabIndex: {
      type: Number,
      default: 0
    },
    formatUploadUrl: {
      type: Boolean,
      default: true
    },
    fullscreenShortcut: {
      type: Boolean,
      default: false
    },
    extraFileUploadParams: {
      type: Array,
      default () {
        return []
      }
    },
    filePostName: {
      type: String,
      default: 'imgFile'
    },
    fillDescAfterUploadImage: {
      type: Boolean,
      default: false
    },
    afterSelectFile: {
      type: Function
    },
    pagebreakHtml: {
      type: String,
      default: '<hr style=”page-break-after: always;” class=”ke-pagebreak” />'
    },
    allowImageRemote: {
      type: Boolean,
      default: true
    },
    autoHeightMode: {
      type: Boolean,
      default: false
    },
    fixToolBar: {
      type: Boolean,
      default: false
    },
    tabIndex: {
      type: Number
    }
  },
  data () {
    return {
      outContent: this.content
    }
  },
  watch: {
    content (val) {
      this.outContent = val
    },
  },
  methods: {
    contentChange (val) {
      console.log('contentchange:>>', val)
      this.$emit('update:content', val)
    }
  }
}
</script>

<style></style>
