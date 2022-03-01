<template lang="pug">
.container.bg-white.padding
  h1.ptb-xs {{$bk18n.t('banner.listTitle')}}
  search-bar(:searchItems="searchItems", @search="searchDataList")
  s-button-group(:btns="buttonGroups", @click="btnGroupClick", :sections="choosedRowKeys")
  basic-table.mt-15(:tableData.sync="tableData", :tableConfig="tableConfig", @rowBtnClick="rowBtnClick", @rowSelectChange="rowSelection", :total="total", @pgChange="onPgChange", @pgSizeChange="onPgSizeChange")
  a-drawer(:visible="visible", :width="600", @close="drawerClose", :title="$bk18n.t('common.edit')", :get-container="false", :style="{'position': 'absolute'}")
    a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules", ref="ruleForm")
      a-form-model-item(:label="$bk18n.t('banner.form.picName')", prop="bannerName")
        a-input(v-model="localForm.bannerName")
      a-form-model-item(:label="$bk18n.t('banner.form.picUrl')", prop="imgCompt")
        image-upload(v-model="localForm.imgCompt")
      a-form-model-item(:label="$bk18n.t('banner.form.picLocation')", prop="sectionArea")
        a-select(v-model="localForm.sectionArea")
          a-select-option(value="home_1") {{$bk18n.t('banner.form.area_1')}}
          a-select-option(value="home_2") {{$bk18n.t('banner.form.area_2')}}
          a-select-option(value="home_3") {{$bk18n.t('banner.form.area_3')}}
          a-select-option(value="home_4") {{$bk18n.t('banner.form.area_4')}}
      a-form-model-item(:label="$bk18n.t('banner.form.linkFlag')", prop="linkFlag")
        a-radio-group(v-model="localForm.linkFlag")
          a-radio(:value="true") {{$bk18n.t('common.yes')}}
          a-radio(:value="false") {{$bk18n.t('common.no')}}
      a-form-model-item(:label="$bk18n.t('banner.form.linkUrl')", prop="linkUrl", v-if="localForm.linkFlag")
        a-input(v-model="localForm.linkUrl")
          a-select(slot="addonBefore", v-model="defaultHttp")
            a-select-option(value="http://") http://
            a-select-option(value="https://") https://
      a-row
        a-col(:span="12", :offset="6")
          a-button.mr-15(@click="submitForm", type="primary", style="width: 100px") {{$bk18n.t('common.save')}}
          a-button(@click="drawerClose", style="width: 100px") {{$bk18n.t('common.cancel')}}
  a-modal(:visible="previewVisible" :footer="null" @cancel="previewVisible = false")
    img.full-width(:src="previewContent")
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { searchItems, buttonGroups, tableConfig } from './constant'
import { BannerDTO } from '@/models/BannerModels'
import imageUpload from '@/components/ImageUpload.vue'

let me: any
@Component({
  layout: 'backend',
  components: {
    imageUpload,
  },
})
class BkBannerIndexPage extends Vue {
  searchItems = searchItems
  buttonGroups = buttonGroups
  previewVisible: boolean = false
  previewContent: string = ''

  btnGroupClick(type: string) {
    console.log(type)
    if (type === 'add') {
      this.localForm = {
        bannerName: '',
        bannerUrl: '',
        imgCompt: [],
        cloudName: '',
        sectionArea: '',
        status: 1,
        linkFlag: false,
        linkUrl: '',
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

  chooseSections(type: string) {
    console.log('choose section:>>', type)
  }

  localForm: BannerDTO = {
    bannerName: '',
    bannerUrl: '',
    cloudName: '',
    sectionArea: '',
    imgCompt: [],
    status: 1,
    linkFlag: false,
    linkUrl: '',
  }
  defaultHttp: string = 'http://'

  formConfig = {
    rules: {},
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

  rowBtnClick(type: string, obj: BannerDTO) {
    switch (type) {
      case 'edit':
        this.localForm.id = obj.id
        this.localForm.bannerName = obj.bannerName
        this.localForm.bannerUrl = obj.bannerUrl
        this.localForm.imgCompt = [
          {
            status: 'done',
            uid: obj.id,
            fid: obj.id,
            name: obj.cloudName,
            url: obj.bannerUrl,
            response: {
              success: true,
              data: {
                fileUrl: obj.bannerUrl,
                fileCloudName: obj.cloudName,
              },
            },
          },
        ]
        this.localForm.cloudName = obj.cloudName
        this.localForm.sectionArea = obj.sectionArea
        this.localForm.linkFlag = obj.linkFlag
        if (this.localForm.linkFlag) {
          this.localForm.linkUrl = obj.linkUrl!.substring(
            obj.linkUrl!.indexOf('//') + 2
          )
          this.defaultHttp = obj.linkUrl!.substring(
            0,
            obj.linkUrl!.indexOf('//') + 2
          )
        } else {
          this.localForm.linkUrl = ''
        }
        this.localForm.status = obj.status
        this.visible = true
        break
      case 'preview':
        this.previewContent = obj.bannerUrl
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
        await this.proxy('sys/banner/batch/remove', 'del', { ids: idstr })
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
        const { data } = await me.proxy('sys/banner/batch/status', 'post', {
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
        if (copyForm.imgCompt!.length === 0) {
          this.msgShow('请上传Banner图片')
          return
        }
        copyForm.bannerUrl = this.localForm.imgCompt![0].response.data.fileUrl
        copyForm.cloudName =
          this.localForm.imgCompt![0].response.data.fileCloudName
        if (copyForm.linkFlag) {
          copyForm.linkUrl = this.defaultHttp + this.localForm.linkUrl
        } else {
          copyForm.linkUrl = ''
        }
        console.log('copyForm', copyForm)
        this.bannerSaveOrUpdate(copyForm)
      } else {
        return false
      }
    })
  }

  async bannerSaveOrUpdate(obj: BannerDTO) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const { data } = await me.proxy('sys/banner', 'post', obj)
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
      const { data } = await me.proxy('sys/banner', 'get', this.params)
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
export default BkBannerIndexPage
</script>

