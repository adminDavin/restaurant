<template lang="pug">
.container.bg-white.padding
  h1.ptb-xs {{$bk18n.t('merchantCard.listTitle')}}
  search-bar(:searchItems="searchItems", @search="searchDataList")
  //- s-button-group(:btns="buttonGroups", @click="btnGroupClick")
  basic-table.mt-15(:tableData.sync="tableData", :tableConfig="tableConfig", @rowBtnClick="rowBtnClick", @rowSelectChange="rowSelection", :total="total", , @pgChange="onPgChange", @pgSizeChange="onPgSizeChange")
  a-drawer(:visible="visible", :width="600", @close="drawerClose", :title="$bk18n.t('merchantCard.form.title')", :get-container="false", :style="{'position': 'absolute'}")
    a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules", ref="ruleForm")
      a-form-model-item(:label="$bk18n.t('merchantCard.form.chooseMerchant')", prop="busiCode")
        common-remote-select(v-model="localForm.busiCode", :remoteConfig="merchantSelectConfig", @change="chooseMerchant")
      a-form-model-item(:label="$bk18n.t('merchantCard.listItemTitle')", prop="title")
        a-input(v-model="localForm.title")
      a-form-model-item(label="内容", prop="content")
        kind-editor(id="merchantCardKeditor", :content.sync="localForm.content")
      a-row
        a-col(:span="12", :offset="6")
          a-button.mr-15(@click="submitForm", type="primary", style="width: 100px") {{$bk18n.t('common.save')}}
          a-button(@click="drawerClose", style="width: 100px") {{$bk18n.t('common.cancel')}}
  a-modal(:visible="previewVisible" :footer="null" @cancel="previewVisible = false")
    .full-width(v-html="previewContent")
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import imageUpload from '@/components/ImageUpload.vue'
import {
  cardSearchItems,
  cardButtonGroups,
  cardTableConfig,
  merchantSelectConfig,
} from './constant'
import { MerchantDTO, MerchantCardDTO } from '@/models/MerchantModels'
import CommonRemoteSelect from '@/components/CommonRemoteSelect.vue'
import kindEditor from '@/components/Kindeditor.vue'

@Component({
  layout: 'backend',
  components: {
    CommonRemoteSelect,
    kindEditor,
  },
})
class BkMerchantCardPage extends Vue {
  searchItems = cardSearchItems
  buttonGroups = cardButtonGroups
  merchantSelectConfig = merchantSelectConfig
  previewVisible: boolean = false
  previewContent: string = ''

  btnGroupClick(type: string) {
    console.log(type)
    if (type === 'add') {
      this.localForm = {
        busiCode: '',
        busiName: '',
        busiType: '',
        title: '',
        content: '',
      }
      this.visible = true
    } else {
      if (this.choosedRowKeys.length === 0) {
        this.msgShow('请选择行')
        return
      }
      this.remoteRemove()
    }
  }

  chooseSections(type: string) {
    console.log('choose section:>>', type)
  }

  localForm: MerchantCardDTO = {
    busiCode: '',
    busiName: '',
    busiType: '',
    title: '',
    content: '',
  }

  formConfig = {
    rules: {
      title: [{ required: true, message: '不能为空', trigger: 'blur' }],
      content: [{ required: true, message: '不能为空', trigger: 'blur' }],
      busiCode: [{ required: true, message: '不能为空', trigger: 'blur' }],
    },
  }

  tableData: any = []
  tableConfig = cardTableConfig
  params: any = {}
  total = 0
  beforeMount() {
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

  rowBtnClick(type: string, obj: MerchantCardDTO) {
    switch (type) {
      case 'edit':
        this.localForm.id = obj.id
        this.localForm.busiCode = obj.busiCode
        this.localForm.busiName = obj.busiName
        this.localForm.busiType = obj.busiType
        this.localForm.title = obj.title
        this.localForm.content = obj.content
        this.visible = true
        break
      case 'preview':
        this.previewContent = obj.content
        this.previewVisible = true
        break
      default:
        break
    }
  }

  choosedRowKeys: any = []
  rowSelection(keys: any) {
    this.choosedRowKeys = keys
  }

  visible = false
  drawerClose() {
    this.visible = false
  }

  chooseMerchant(value: string, obj: MerchantDTO) {
    console.log('choose value', value)
    console.log('rowobj:>>', obj)
    this.localForm.busiCode = value
    this.localForm.busiName = obj.merchantName
    this.localForm.busiType = obj.busiType!
  }

  submitForm() {
    let me = this as any
    me.$refs.ruleForm.validate((valid: boolean) => {
      if (valid) {
        // this.$emit('submit', this.localForm)
        console.log(this.localForm)
        this.merchantCardSaveOrUpdate(Object.assign({}, this.localForm))
      } else {
        return false
      }
    })
  }

  async remoteRemove() {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const idstr = this.choosedRowKeys.join(',')
        await this.proxy('sys/card/batch/remove', 'del', { ids: idstr })
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

  async merchantCardSaveOrUpdate(obj: MerchantCardDTO) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const { data } = await this.proxy('sys/card', 'post', obj)
        this.rowBtnEnable = true
        this.msgShow('操作成功', 'success')
        this.loadData()
        this.drawerClose()
      }
    } catch (e) {
      this.rowBtnEnable = true
      this.msgShow(e.message || '操作失败')
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
      const { data } = await this.proxy('sys/card', 'get', this.params)
      if (data.success) {
        this.tableData = data.data
        this.total = data.total
      } else {
        this.msgShow(data.message)
        this.tableData = []
        this.total = 0
      }
    } catch (e) {
      this.total = 0
      this.tableData = []
      this.msgShow(e.message || '网络异常')
    }
  }
}
export default BkMerchantCardPage
</script>

