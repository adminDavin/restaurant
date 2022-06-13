<template lang="pug">
.container.bg-white.padding
  h1.ptb-xs {{$bk18n.t('account.listTitle')}}
  SearchBarFixed(:searchItems="searchItems", @search="searchDataList")
  s-button-group(:btns="buttonGroups", @click="btnGroupClick")
  basic-table.mt-15(:tableData.sync="tableData", :tableConfig="tableConfig", @rowBtnClick="rowBtnClick", @rowSelectChange="rowSelection", :total="total", @pgChange="onPgChange", @pgSizeChange="onPgSizeChange")
  a-drawer(:visible="visible", :width="600", @close="drawerClose", :title="$bk18n.t('common.edit')", :get-container="false", :style="{'position': 'absolute'}")
    a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules", ref="ruleForm")
      a-form-model-item(:label="$bk18n.t('account.form.name')", prop="loginName")
        a-input(v-model="localForm.loginName")
      a-form-model-item(:label="$bk18n.t('account.form.nickname')", prop="nickname")
        a-input(v-model="localForm.nickname")
      a-form-model-item(:label="$bk18n.t('account.form.username')", prop="userName")
        a-input(v-model="localForm.userName")
      a-form-model-item(:label="$bk18n.t('common.status')", prop="status")
        a-select(v-model="localForm.status")
          a-select-option(:value="1") {{$bk18n.t('common.start')}}
          a-select-option(:value="2") {{$bk18n.t('common.stop')}}
      a-row
        a-col(:span="12", :offset="6")
          a-button.mr-15(@click="submitForm", type="primary", style="width: 100px") {{$bk18n.t('common.save')}}
          a-button(@click="drawerClose", style="width: 100px") {{$bk18n.t('common.cancel')}}
</template>
<script lang="ts">
import { Vue, Component } from 'vue-property-decorator'
import { searchItems, buttonGroups, tableConfig } from './constant'
import { UserDTO } from '@/models/UserModels'
import SearchBarFixed from '@/components/SearchBarFixed.vue'

let me: any
@Component({
  layout: 'backend',
  components: {
    SearchBarFixed
  }
})
class BkUserIndexPage extends Vue {
  searchItems = searchItems
  buttonGroups = buttonGroups

  btnGroupClick(type: string) {
    console.log(type)
    if (type === 'add') {
      this.localForm = {
        userName: '',
        loginName: '',
        status: 1,
      }
      this.visible = true
    } else {
      if (this.choosedRowKeys.length === 0) {
        me.msgShow('編集する項目を選択してください')
        return
      }
      this.remoteBatchAction(type === 'batchUp' ? 1 : 2)
    }
  }

  chooseSections(type: string) {
    console.log('choose section:>>', type)
  }

  localForm: UserDTO = {
    userName: '',
    loginName: '',
    status: 1,
  }

  formConfig = {
    rules: {
      userName: [{ required: true, message: '必須項目です', trigger: 'blur' }],
      loginName: [{ required: true, message: '必須項目です', trigger: 'blur' }],
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

  rowBtnClick(type: string, obj: UserDTO) {
    switch (type) {
      case 'edit':
        this.localForm.id = obj.id
        this.localForm.userCode = obj.userCode
        this.localForm.userName = obj.userName
        this.localForm.nickname = obj.nickname
        this.localForm.loginName = obj.loginName
        this.localForm.status = obj.status
        this.visible = true
        break
      case 'batchUp':
      case 'batchDown':
        if (this.choosedRowKeys.length === 0) {
          this.msgShow('请选择需要操作的行')
          return
        }
        this.remoteBatchAction(type === 'batchUp' ? 1 : 2)
        break
      case 'remove':
        this.remoteRemove(obj.id!)
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
        this.msgShow('保存しました', 'success')
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
        const { data } = await me.proxy('user/account/batch/status', 'post', {
          status: mark,
          ids: idstr,
        })
        this.rowBtnEnable = true
        me.msgShow('保存しました', 'success')
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
        this.merchantSaveOrUpdate(Object.assign({}, this.localForm))
      } else {
        return false
      }
    })
  }

  async merchantSaveOrUpdate(obj: UserDTO) {
    try {
      if (this.rowBtnEnable) {
        this.rowBtnEnable = false
        const { data } = await me.proxy('user/account', 'post', obj)
        this.rowBtnEnable = true
        if (data.success) {
          me.msgShow('保存しました', 'success')
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
      const { data } = await me.proxy('user/account', 'get', this.params)
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
export default BkUserIndexPage
</script>
