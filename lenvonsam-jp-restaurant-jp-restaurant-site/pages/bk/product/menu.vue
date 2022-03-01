<template lang="pug">
.container.bg-white.padding
  h1.ptb-xs {{$bk18n.t('menu.listTitle')}}
  search-bar(:searchItems="searchItems", @search="searchDataList")
  s-button-group(:btns="buttonGroups", @click="btnGroupClick", :sections="choosedRowKeys")
  basic-table.mt-15(:tableData.sync="tableData", :tableConfig="tableConfig", @rowBtnClick="rowBtnClick", @rowSelectChange="rowSelection", :total="total", @pgChange="onPgChange", @pgSizeChange="onPgSizeChange")
  a-drawer(:visible="visible", :width="600", @close="drawerClose", :title="$bk18n.t('common.edit')", :get-container="false", :style="{'position': 'absolute'}")
    a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules", ref="ruleForm")
      a-form-model-item(:label="$bk18n.t('menu.form.title')", prop="productTitle")
        a-input(v-model="localForm.productTitle")
      a-form-model-item(:label="$bk18n.t('menu.form.subTitle')", prop="productSubTitle")
        a-input(v-model="localForm.productSubTitle")
      a-form-model-item(:label="$bk18n.t('menu.form.cover')", prop="coverCompt")
        image-upload(v-model="localForm.coverCompt")
      a-form-model-item(:label="$bk18n.t('menu.form.intro')", prop="productDesc")
        a-input(v-model="localForm.productDesc")
      a-form-model-item(:label="$bk18n.t('menu.form.enIntro')", prop="productDescUrl")
        a-input(v-model="localForm.productDescUrl")
      a-form-model-item(:label="$bk18n.t('menu.form.merchant')", prop="merchantCode")
        common-remote-select(v-model="localForm.merchantCode", :remoteConfig="merchantSelectConfig", @change="chooseMerchant")
      a-row
        a-col(:span="12", :offset="6")
          a-button.mr-15(@click="submitForm", type="primary", style="width: 100px") 保存
          a-button(@click="drawerClose", style="width: 100px") 取消
  a-modal(:visible="previewVisible" :footer="null" @cancel="previewVisible = false")
    img.full-width(:src="previewContent")
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import {
  searchItems,
  buttonMenuGroups,
  tableMenuConfig,
  merchantSelectConfig,
} from './constant'
import { MerchantProductDTO, MerchantDTO } from '@/models/MerchantModels'
import imageUpload from '@/components/ImageUpload.vue'
import CommonRemoteSelect from '@/components/CommonRemoteSelect.vue'

let me: any
@Component({
  layout: 'backend',
  components: {
    imageUpload,
    CommonRemoteSelect,
  },
})
class BkProductMenuIndexPage extends Vue {
  searchItems = searchItems
  buttonGroups = buttonMenuGroups
  merchantSelectConfig = merchantSelectConfig
  previewVisible: boolean = false
  previewContent: string = ''

  btnGroupClick(type: string) {
    console.log(type)
    if (type === 'add') {
      this.localForm = {
        merchantCode: '',
        merchantName: '',
        productCode: '',
        productTitle: '',
        productSubTitle: '',
        productDesc: '',
        coverCompt: [],
        productType: 2,
        productCoverUrl: '',
        productDescUrl: '',
      }
      this.visible = true
    } else if (type === 'batchRemove') {
      this.remoteRemove()
    } else {
      if (this.choosedRowKeys.length === 0) {
        this.msgShow('请选择行')
        return
      }
      this.remoteBatchAction(type === 'batchUp' ? 1 : 2)
    }
  }

  localForm: MerchantProductDTO = {
    merchantCode: '',
    merchantName: '',
    productCode: '',
    productType: 2,
    productTitle: '',
    productSubTitle: '',
    productDesc: '',
    productCoverUrl: '',
    productDescUrl: '',
  }

  formConfig = {
    rules: {
      productTitle: [{ required: true, message: '不能为空', trigger: 'blur' }],
      productSubTitle: [
        { required: true, message: '不能为空', trigger: 'blur' },
      ],
      productDesc: [{ required: true, message: '不能为空', trigger: 'blur' }],
      productDescUrl: [
        { required: true, message: '不能为空', trigger: 'blur' },
      ],
    },
  }

  tableData: any = []
  tableConfig = tableMenuConfig
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

  rowBtnClick(type: string, obj: MerchantProductDTO) {
    switch (type) {
      case 'edit':
        if (obj.status === 1) {
          this.msgShow('已上架无法修改，请先下架')
          return
        }
        this.localForm.id = obj.id
        this.localForm.merchantCode = obj.merchantCode
        this.localForm.merchantName = obj.merchantCode
        this.localForm.coverCompt = [
          {
            status: 'done',
            uid: obj.id,
            fid: obj.id,
            name: obj.productCoverUrl.substring(
              obj.productCoverUrl.indexOf('img_')
            ),
            url: obj.productCoverUrl,
            response: {
              success: true,
              data: {
                fileUrl: obj.productCoverUrl,
                fileCloudName: obj.productCoverUrl.substring(
                  obj.productCoverUrl.indexOf('img_')
                ),
              },
            },
          },
        ]
        this.localForm.productCode = obj.productCode
        this.localForm.productTitle = obj.productTitle
        this.localForm.productSubTitle = obj.productSubTitle
        this.localForm.productCoverUrl = obj.productCoverUrl
        this.localForm.productDesc = obj.productDesc
        this.localForm.productDescUrl = obj.productDescUrl
        this.localForm.status = obj.status
        this.localForm.productType = obj.productType
        this.visible = true
        break
      case 'preview':
        this.previewContent = obj.productCoverUrl
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

  async remoteRemove() {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const idstr = this.choosedRowKeys.join(',')
        await this.proxy('sys/product/batch/remove', 'del', { ids: idstr })
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
        const { data } = await me.proxy('sys/product/batch/status', 'post', {
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
  ticketVisible = false
  drawerClose() {
    this.visible = false
  }

  submitForm() {
    me.$refs.ruleForm.validate((valid: boolean) => {
      if (valid) {
        // this.$emit('submit', this.localForm)
        console.log(this.localForm)
        const copyForm = Object.assign({}, this.localForm)
        if (copyForm.coverCompt!.length === 0) {
          this.msgShow('请上传封面图片')
          return
        }
        copyForm.productCoverUrl =
          this.localForm.coverCompt![0].response.data.fileUrl
        console.log('copyForm', copyForm)
        this.productSaveOrUpdate(copyForm)
      } else {
        return false
      }
    })
  }

  chooseMerchant(value: string, obj: MerchantDTO) {
    console.log('choose value', value)
    console.log('rowobj:>>', obj)
    this.localForm.merchantCode = value
    this.localForm.merchantName = obj.merchantName
  }

  async productSaveOrUpdate(obj: MerchantProductDTO) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const { data } = await me.proxy('sys/product', 'post', obj)
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
      this.params.productType = 2
      const { data } = await me.proxy('sys/product', 'get', this.params)
      console.log('bill data:>>', data)
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
export default BkProductMenuIndexPage
</script>

