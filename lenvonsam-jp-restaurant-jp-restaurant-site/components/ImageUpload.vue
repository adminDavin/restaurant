<template lang="pug">
.clearfix
  a-upload(list-type="picture-card", action="/proxy/fileUpload", :multiple="false", @change="fileChange",
  :file-list="localFiles", @preview="handlePreview", :before-upload="beforeUpload")
    div(v-if="localFiles.length < limitCount")
      a-icon(type="plus")
  a-modal(:visible="previewVisible" :footer="null" @cancel="previewVisible = false")
    img.full-width(:src="previewUrl")
</template>

<script>
export default {
  props: {
    type: {
      // 头像 avatar 轮播 banner
      type: String,
      default: 'avatar'
    },
    value: {
      type: Array,
      default: () => []
    }
  },
  data () {
    return {
      localFiles: [],
      previewVisible: false,
      previewUrl: ''
    }
  },
  computed: {
    limitCount () {
      return this.type === 'avatar' ? 1 : 5
    }
  },
  watch: {
    value: {
      handler (newVal) {
        console.log('newval:>>', newVal)
        if (newVal.length > 0) {
          this.localFiles = newVal
        } else {
          this.localFiles = []
        }
      },
      deep: true
    }
  },
  beforeMount () {
    this.localFiles = this.value.length > 0 ? [...this.value] : []
    console.log('localFiles:>>', this.localFiles)
    console.log('default list:>>', this.defaultList)
  },
  methods: {
    handlePreview (file) {
      console.log('preview file', file)
      this.previewUrl = file.response.data.fileUrl
      this.previewVisible = true
    },
    beforeUpload (file) {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      if (!isJpgOrPng) {
        this.$message.error('You can only upload JPG file!')
      }
      const isLt3M = file.size / 1024 / 1024 < 3
      if (!isLt3M) {
        this.$message.error('Image must smaller than 3MB!')
      }
      return isJpgOrPng && isLt3M
    },
    fileChange ({ file, fileList }) {
      console.log('filechange', fileList, file)
      if (file.status === 'removed') {
        // 文件上传删除
        this.remoteRemoveFile(file, fileList)
      } else if (file.status === 'done' || file.status === 'uploading') {
        // 文件上传成功
        this.localFiles = fileList
        this.$emit('input', this.localFiles)
      }
      console.log('localFiles:>>', this.localFiles)
    },
    async remoteRemoveFile (file, fileList) {
      try {
        const reqBody = {
          url: file.response.data.fileCloudName
        }
        const { data } = await this.proxy('sys/file/single', 'del', reqBody)
        console.log('delete resp:>>', data)
        this.localFiles = fileList
        this.$emit('input', this.localFiles)
      } catch (e) {
        console.error('remote file fail:>>', e)
      }
    }
  }
}
</script>
