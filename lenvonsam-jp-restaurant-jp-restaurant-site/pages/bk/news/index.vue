<template lang="pug">
.container.bg-white.padding
  h1.ptb-xs {{$bk18n.t('news.listTitle')}}
  SearchBarFixed(:searchItems="searchItems", @search="searchDataList")
  s-button-group(:btns="buttonGroups", @click="btnGroupClick", :sections="choosedRowKeys")
  basic-table.mt-15(:tableData.sync="tableData", :tableConfig="tableConfig", @rowBtnClick="rowBtnClick", @rowSelectChange="rowSelection", :total="total", @pgChange="onPgChange", @pgSizeChange="onPgSizeChange")
  a-drawer(:visible="visible", :width="600", @close="drawerClose", :title="$bk18n.t('common.edit')", :get-container="false", :style="{'position': 'absolute'}")
    a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules", ref="ruleForm")
      a-form-model-item(:label="$bk18n.t('news.form.title')", prop="newsTitle")
        a-input(v-model="localForm.newsTitle")
      a-form-model-item(:label="$bk18n.t('news.form.publishTime')", prop="publishDate")
        s-date-picker(v-model="localForm.publishDate")
      a-form-model-item(:label="$bk18n.t('news.form.author')", prop="newsAuthor")
        a-input(v-model="localForm.newsAuthor")
      a-form-model-item(:label="$bk18n.t('news.form.content')", prop="newsContent")
        kind-editor(id="newsIndexKeditor", :content.sync="localForm.newsContent")
      a-row
        a-col(:span="12", :offset="6")
          a-button.mr-15(@click="submitForm", type="primary", style="width: 100px") {{$bk18n.t('common.save')}}
          a-button(@click="drawerClose", style="width: 100px") {{$bk18n.t('common.cancel')}}
  a-modal(:visible="previewVisible" :footer="null" @cancel="previewVisible = false")
    .full-width(v-html="previewNewsContent")
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { searchItems, buttonGroups, tableConfig } from './constant'
import { NewsDTO } from '@/models/NewsModels'
import imageUpload from '@/components/ImageUpload.vue'
import kindEditor from '@/components/Kindeditor.vue'
import SearchBarFixed from '@/components/SearchBarFixed.vue'

let me: any
@Component({
  layout: 'backend',
  components: {
    imageUpload,
    kindEditor,
    SearchBarFixed
  },
})
class BkNewsIndexPage extends Vue {
  searchItems = searchItems
  buttonGroups = buttonGroups

  btnGroupClick(type: string) {
    console.log(type)
    if (type === 'add') {
      this.localForm = {
        newsTitle: '',
        newsContent: '',
        newsAuthor: '',
        publishDate: '',
        newsProperty: 1,
        newsThumbContent: '',
      }
      this.visible = true
    } else if (type === 'batchRemove') {
      this.remoteRemove()
    } else {
      if (this.choosedRowKeys.length === 0) {
        this.msgShow('请选择需要操作的行')
        return
      }
      this.remoteBatchAction(type === 'batchUp' ? 1 : 2)
    }
  }

  previewNewsContent: string = ''
  previewVisible: boolean = false

  localForm: NewsDTO = {
    newsTitle: '',
    newsContent: '',
    newsAuthor: '',
    publishDate: '',
    newsProperty: 1,
    newsThumbContent: '',
  }

  formConfig = {
    rules: {
      newsTitle: [{ required: true, message: '不能为空', trigger: 'blur' }],
      newsContent: [{ required: true, message: '不能为空', trigger: 'blur' }],
      publishDate: [{ required: true, message: '不能为空', trigger: 'blur' }],
    },
  }

  tableData: any = []
  tableConfig = tableConfig
  params: any = {}
  total = 0
  beforeMount() {
    me = this
    this.loadData()
  }

  onPgChange(page: number) {
    this.params.currentPage = page
    this.loadData()
  }

  onPgSizeChange(size: number) {
    this.params.pageSize = size
    this.loadData()
  }

  rowBtnEnable = true

  rowBtnClick(type: string, obj: NewsDTO) {
    switch (type) {
      case 'edit':
        this.localForm.id = obj.id
        this.localForm.newsTitle = obj.newsTitle
        this.localForm.newsContent = obj.newsContent
        this.localForm.newsAuthor = obj.newsAuthor
        this.localForm.publishDate = obj.publishDate
        this.localForm.newsProperty = obj.newsProperty
        this.localForm.newsThumbContent = obj.newsThumbContent
        this.visible = true
        break
      case 'preview':
        this.previewNewsContent = obj.newsContent
        this.previewVisible = true
        break
      default:
        break
    }
  }

  choosedRowKeys: any = []
  rowSelection(keys: any) {
    console.log('choosedRowKeys:>>', keys)
    this.choosedRowKeys = keys
  }

  async remoteRemove() {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const idstr = this.choosedRowKeys.join(',')
        await this.proxy('sys/news/batch/remove', 'del', { ids: idstr })
        this.rowBtnEnable = true
        this.msgShow('操作成功', 'success')
        this.params = {
          currentPage: 1,
        }
        this.loadData()
      }
    } catch (e) {
      this.rowBtnEnable = true
      this.msgShow(e.message || '网络异常')
    }
  }

  async remoteBatchAction(mark: number) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const idstr = this.choosedRowKeys.join(',')
        const { data } = await me.proxy('sys/news/batch/status', 'post', {
          status: mark,
          ids: idstr,
        })
        me.msgShow('操作成功', 'success')
        this.params = {
          currentPage: 1,
        }
        this.rowBtnEnable = true
        this.loadData()
      }
    } catch (e) {
      this.rowBtnEnable = true
      me.msgShow(e.message)
      console.error(e)
    }
  }

  visible = false
  drawerClose() {
    this.visible = false
  }

  submitForm() {
    me.$refs.ruleForm.validate((valid: boolean) => {
      if (valid) {
        // this.$emit('submit', this.localForm)
        console.log(this.localForm)
        const copyForm = Object.assign({}, this.localForm)
        console.log('copyForm', copyForm)
        this.newsSaveOrUpdate(copyForm)
      } else {
        return false
      }
    })
  }

  async newsSaveOrUpdate(obj: NewsDTO) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const { data } = await me.proxy('sys/news', 'post', obj)
        this.rowBtnEnable = true
        if (data.success) {
          me.msgShow('操作成功', 'success')
          this.loadData()
          this.drawerClose()
        } else {
          me.msgShow(data.message)
        }
      }
    } catch (e) {
      this.rowBtnEnable = true
      me.msgShow(e.message || '操作失败')
    }
  }

  searchDataList(items: any) {
    console.log('search content:>>', items)
    this.params = {
      currentPage: 1,
    }
    items.map((item: any) => {
      this.params[item.key] = item.val
    })
    this.loadData()
  }

  async loadData() {
    try {
      const { data } = await me.proxy('sys/news', 'get', this.params)
      if (data.success) {
        this.tableData = data.data
        this.total = data.total
      } else {
        me.msgShow(data.message)
        this.tableData = []
        this.total = 0
      }
    } catch (e) {
      this.total = 0
      this.tableData = []
      me.msgShow(e.message || '网络异常')
    }
  }
}
export default BkNewsIndexPage
</script>

