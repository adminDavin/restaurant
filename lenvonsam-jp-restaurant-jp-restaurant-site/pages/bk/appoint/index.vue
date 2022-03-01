<template lang="pug">
.container.bg-white.padding
  h1.ptb-xs {{$bk18n.t('merchantAppoint.listTitle')}}
  search-bar(:searchItems="searchItems", @search="searchDataList")
  s-button-group(:btns="buttonGroups", @click="btnGroupClick")
  basic-table.mt-15(:tableData.sync="tableData", :tableConfig="tableConfig", @rowBtnClick="rowBtnClick", @rowSelectChange="rowSelection", :total="total", @pgChange="onPgChange", @pgSizeChange="onPgSizeChange")
  a-drawer(:visible="visible", :width="600", @close="drawerClose", :title="$bk18n.t('common.edit')", :get-container="false", :style="{'position': 'absolute'}")
    a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules", ref="ruleForm")
      a-form-model-item(:label="$bk18n.t('merchantAppoint.search.chooseMerchant')", prop="merchantCode")
        common-remote-select(v-model="localForm.merchantCode", :remoteConfig="merchantSelectConfig", @change="chooseMerchant")
      a-form-model-item(:label="$bk18n.t('merchantAppoint.form.appointTime')", prop="appointTime")
        s-date-picker(v-model="localForm.appointTime")
      a-form-model-item(:label="$bk18n.t('merchantAppoint.form.appointType')", prop="appointType")
        a-select(v-model="localForm.appointType")
          a-select-option(:value="1") ×
          a-select-option(:value="2") △
      a-row
        a-col(:span="12", :offset="6")
          a-button.mr-15(@click="submitForm", type="primary", style="width: 100px") {{$bk18n.t('common.save')}}
          a-button(@click="drawerClose", style="width: 100px") {{$bk18n.t('common.cancel')}}
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import {
  searchItems,
  buttonGroups,
  tableConfig,
  merchantSelectConfig,
} from './constant'
import { MerchantAppiontDTO, MerchantDTO } from '@/models/MerchantModels'
import CommonRemoteSelect from '@/components/CommonRemoteSelect.vue'

let me: any
@Component({
  layout: 'backend',
  components: {
    CommonRemoteSelect,
  },
})
class BkAppointIndexPage extends Vue {
  searchItems = searchItems
  buttonGroups = buttonGroups
  merchantSelectConfig = merchantSelectConfig

  btnGroupClick(type: string) {
    console.log(type)
    if (type === 'add') {
      this.localForm = {
        merchantCode: '',
        merchantName: '',
        appointTime: '',
        appointType: 1,
      }
      this.visible = true
    } else {
      if (this.choosedRowKeys.length === 0) {
        me.msgShow('请选择行')
        return
      }
      this.remoteBatchAction()
    }
  }

  chooseSections(type: string) {
    console.log('choose section:>>', type)
  }

  localForm: MerchantAppiontDTO = {
    merchantCode: '',
    merchantName: '',
    appointTime: '',
    appointType: 1,
  }

  formConfig = {
    rules: {
      merchantCode: [{ required: true, message: '不能为空', trigger: 'blur' }],
      appointTime: [{ required: true, message: '不能为空', trigger: 'blur' }],
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

  rowBtnClick(type: string, obj: MerchantAppiontDTO) {
    switch (type) {
      case 'edit':
        this.localForm.id = obj.id
        this.localForm.merchantCode = obj.merchantCode
        this.localForm.merchantName = obj.merchantName
        this.localForm.appointTime = obj.appointTime
        this.localForm.appointType = obj.appointType
        this.visible = true
        break
      case 'batchDel':
        if (this.choosedRowKeys.length === 0) {
          this.msgShow('请选择需要操作的行')
          return
        }
        this.remoteBatchAction()
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

  async remoteRemove(id: string) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        await this.proxy('user/account/batch/remove', 'del', { ids: id })
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

  async remoteBatchAction() {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const idstr = this.choosedRowKeys.join(',')
        const { data } = await me.proxy(
          'sys/merchantAppoint/batch/remove',
          'del',
          {
            ids: idstr,
          }
        )
        this.rowBtnEnable = true
        me.msgShow('操作成功', 'success')
        this.params = {
          currentPage: 1,
        }
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
        console.log(this.localForm)
        this.merchantAppointSaveOrUpdate(Object.assign({}, this.localForm))
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

  async merchantAppointSaveOrUpdate(obj: MerchantAppiontDTO) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const { data } = await me.proxy('sys/merchantAppoint', 'post', obj)
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
      const { data } = await me.proxy('sys/merchantAppoint', 'get', this.params)
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
export default BkAppointIndexPage
</script>
