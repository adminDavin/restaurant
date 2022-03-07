<template lang="pug">
.container.bg-white.padding
  h1.ptb-xs {{$bk18n.t('merchant.listTitle')}}
  search-bar(:searchItems="searchItems", @search="searchDataList")
  s-button-group(:btns="buttonGroups", @click="btnGroupClick")
  basic-table.mt-15(:tableData.sync="tableData", :tableConfig="tableConfig", @rowBtnClick="rowBtnClick", @rowSelectChange="rowSelection", :total="total", @pgChange="onPgChange", @pgSizeChange="onPgSizeChange")
  a-drawer(:visible="visible", :width="600", @close="drawerClose", :title="$bk18n.t('merchant.form.title')", :get-container="false", :style="{'position': 'absolute'}")
    a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules", ref="ruleForm")
      a-form-model-item(:label="$bk18n.t('merchant.form.name')", prop="merchantName")
        a-input(v-model="localForm.merchantName")
      a-form-model-item(:label="$bk18n.t('merchant.form.intro')", prop="intro")
        a-textarea(v-model="localForm.intro")
      a-form-model-item(:label="$bk18n.t('merchant.form.email')", prop="email")
        a-input(v-model="localForm.email")
      a-form-model-item(:label="$bk18n.t('merchant.form.mobile')", prop="phone")
        a-input(v-model="localForm.phone")
      a-form-model-item(:label="$bk18n.t('merchant.form.address')", prop="address")
        a-input(v-model="localForm.address")
      a-form-model-item(:label="$bk18n.t('merchant.form.openTime')", prop="openTimeInfo")
        a-input(v-model="localForm.openTimeInfo")
      a-form-model-item(:label="$bk18n.t('merchant.form.uber')", prop="uberSite")
        a-input(v-model="localForm.uberSite")
      a-form-model-item(:label="$bk18n.t('merchant.form.facebook')", prop="facebookSite")
        a-input(v-model="localForm.facebookSite")
      a-form-model-item(:label="$bk18n.t('merchant.form.tw')", prop="twSite")
        a-input(v-model="localForm.twSite")
      a-form-model-item(:label="$bk18n.t('merchant.form.ins')", prop="insSite")
        a-input(v-model="localForm.insSite")
      a-form-model-item(:label="$bk18n.t('merchant.form.line')", prop="lineSite")
        a-input(v-model="localForm.lineSite")
      a-form-model-item(:label="$bk18n.t('merchant.form.facility')")
        a-checkbox-group(v-model="chooseInfrastructureMark", :options="defaultInfrastructureMark")
      a-form-model-item(:label="$bk18n.t('merchant.form.thumb')", required)
        image-upload(v-model="localForm.thumbCompt")
      a-form-model-item(label="店内写真", prop="galleryCompt")
        image-upload(type="banner", v-model="localForm.galleryCompt")
      a-row
        a-col(:span="12", :offset="6")
          a-button.mr-15(@click="submitForm", type="primary", style="width: 100px") {{$bk18n.t('common.save')}}
          a-button(@click="drawerClose", style="width: 100px") {{$bk18n.t('common.cancel')}}
  a-modal(:visible="previewVisible" :footer="null" @cancel="previewVisible = false")
    a-carousel(autoplay)
      div(v-for="(g, idx) in localForm.gallery", :key="idx")
        img.full-width(style="height: 250px", :src="g.fileUrl")
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import imageUpload from '@/components/ImageUpload.vue'
import { searchItems, buttonGroups, tableConfig } from './constant'
import { MerchantDTO, FileDTO } from '@/models/MerchantModels'

let me: any
@Component({
  layout: 'backend',
  components: {
    imageUpload,
  },
})
class BkMerchantIndexPage extends Vue {
  searchItems = searchItems
  buttonGroups = buttonGroups
  previewVisible = false
  defaultInfrastructureMark = ['uber', 'card', 'wifi', 'テイクアウト', '電子マネー', '駐車場']
  chooseInfrastructureMark = ['uber', 'card', 'wifi', 'テイクアウト', '電子マネー', '駐車場']
  btnGroupClick(type: string) {
    console.log(type)
    if (type === 'add') {
      this.localForm = {
        merchantName: '',
        intro: '',
        email: '',
        phone: '',
        address: '',
        openTimeInfo: '',
        uberSite: '',
        twSite: '',
        mapUrl: '',
        facebookSite: '',
        insSite: '',
        lineSite: '',
        thumbCompt: [],
        galleryCompt: [],
      }
      this.chooseInfrastructureMark = this.defaultInfrastructureMark
      this.visible = true
    } else {
      if (this.choosedRowKeys.length === 0) {
        me.msgShow('请选择行')
        return
      }
      this.remoteBatchAction(type === 'batchUp' ? 1 : 2)
    }
  }

  chooseSections(type: string) {
    console.log('choose section:>>', type)
  }

  localForm: MerchantDTO = {
    merchantName: '',
    intro: '',
    email: '',
    phone: '',
    address: '',
    uberSite: '',
    twSite: '',
    facebookSite: '',
    insSite: '',
    lineSite: '',
    openTimeInfo: '',
    infrastructureMark: '',
    mapUrl: '',
    thumbCompt: [],
    galleryCompt: [],
  }

  formConfig = {
    rules: {
      merchantName: [{ required: true, message: '不能为空', trigger: 'blur' }],
      intro: [{ required: true, message: '不能为空', trigger: 'blur' }],
      email: [{ required: true, message: '不能为空', trigger: 'blur' }],
      phone: [{ required: true, message: '不能为空', trigger: 'blur' }],
      address: [{ required: true, message: '不能为空', trigger: 'blur' }],
    },
  }

  tableData: any = []
  billDetailData: any = {}
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

  rowBtnClick(type: string, obj: MerchantDTO) {
    switch (type) {
      case 'edit':
        this.localForm.id = obj.id
        this.localForm.merchantCode = obj.merchantCode
        this.localForm.merchantName = obj.merchantName
        this.localForm.intro = obj.intro
        this.localForm.email = obj.email
        this.localForm.phone = obj.phone
        this.localForm.address = obj.address
        this.localForm.openTimeInfo = obj.openTimeInfo
        this.localForm.uberSite = obj.uberSite
        this.localForm.facebookSite = obj.facebookSite
        this.localForm.insSite = obj.insSite
        this.localForm.twSite = obj.twSite
        this.localForm.lineSite = obj.lineSite
        this.localForm.infrastructureMark = obj.infrastructureMark
        this.localForm.mapUrl = obj.mapUrl
        this.chooseInfrastructureMark = []
        for (var i = 0; i < 6; i++) {
          var mark = obj.infrastructureMark!.substring(i, i + 1)
          if (mark === '1') {
            this.chooseInfrastructureMark.push(
              this.defaultInfrastructureMark[i]
            )
          }
        }
        if (obj.thumbUrl && obj.thumbUrl.length > 0) {
          this.localForm.thumbCompt = [
            {
              status: 'done',
              uid: obj.id,
              fid: obj.id,
              name: obj.thumbUrl.substring(obj.thumbUrl.indexOf('img_')),
              url: obj.thumbUrl,
              response: {
                success: true,
                data: {
                  fileUrl: obj.thumbUrl,
                  fileCloudName: obj.thumbUrl.substring(
                    obj.thumbUrl.indexOf('img_')
                  ),
                },
              },
            },
          ]
        } else {
          this.localForm.thumbUrl = ''
          this.localForm.thumbCompt = []
        }
        console.log(obj)
        this.loadBusiCodeFiles(obj.merchantCode!)
        break
      case 'banner':
        this.previewVisible = false
        this.loadBusiCodeFiles(obj.merchantCode!, true)
        break
      default:
        break
    }
  }

  choosedRowKeys: any = []
  rowSelection(keys: any) {
    this.choosedRowKeys = keys
  }

  async remoteBatchAction(mark: number) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const idstr = this.choosedRowKeys.join(',')
        const { data } = await me.http(
          'product/batchAction',
          { mark, idStr: idstr },
          'post'
        )
        this.rowBtnEnable = true
        if (data.returnCode === 0) {
          me.msgShow('操作成功', 'success')
          this.loadData()
        } else {
          me.msgShow(data.message)
        }
      }
    } catch (e) {
      this.rowBtnEnable = false
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
        const copyForm = Object.assign({}, this.localForm)
        if (copyForm.thumbCompt!.length === 0) {
          this.msgShow('请上传缩略图')
          return
        }
        copyForm.thumbUrl = copyForm.thumbCompt![0].response.data.fileUrl
        console.log(this.chooseInfrastructureMark)
        copyForm.infrastructureMark = ''
        this.defaultInfrastructureMark.map((item: string) => {
          const index = this.chooseInfrastructureMark.findIndex(
            (itm: string) => itm === item
          )
          if (index < 0) {
            copyForm.infrastructureMark += '0'
          } else {
            copyForm.infrastructureMark += '1'
          }
        })
        // 保存图片
        if (copyForm.galleryCompt!.length > 0) {
          const arr = []
          for (const file of copyForm.galleryCompt!) {
            arr.push({
              fileType: 1,
              fileUrl: file.response.data.fileUrl,
              fileCloudName: file.response.data.fileCloudName,
            })
          }
          copyForm.gallery = arr
        }
        console.log(copyForm)
        this.merchantSaveOrUpdate(copyForm)
      } else {
        return false
      }
    })
  }

  async merchantSaveOrUpdate(obj: MerchantDTO) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const { data } = await me.proxy('sys/merchant', 'post', obj)
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

  async loadBusiCodeFiles(code: string, rowBanner = false) {
    try {
      const { data } = await this.proxy('/sys/file/codeList', 'get', {
        code: code,
      })
      this.localForm.gallery = []
      const arr: any = []
      for (const file of data.data) {
        arr.push({
          status: 'done',
          uid: file.id,
          fid: file.id,
          name: file.fileName,
          url: file.fileUrl,
          response: {
            success: true,
            data: {
              fileUrl: file.fileUrl,
              fileCloudName: file.fileCloudName,
            },
          },
        })
        this.localForm.gallery.push(file)
      }
      this.localForm.galleryCompt = arr
      if (rowBanner) {
        if (arr.length > 0) {
          this.previewVisible = true
        } else {
          this.msgShow('请上传图片')
        }
      } else {
        this.visible = true
      }
    } catch (e) {
      console.error('banner empty', e)
      this.localForm.galleryCompt = []
      this.localForm.gallery = []
      if (rowBanner) {
        this.previewVisible = true
      } else {
        this.visible = true
      }
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
      const { data } = await me.proxy('sys/merchant', 'get', this.params)
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
export default BkMerchantIndexPage
</script>
