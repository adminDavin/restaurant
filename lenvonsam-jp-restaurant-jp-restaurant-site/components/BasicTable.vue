<template lang="pug">
a-table(:data-source="currentData", border, :rowKey="rowKey", :pagination="defaultPgConfig", :size="tableConfig.size || 'middle'", :rowSelection="tableConfig.cbx ? {selectRowKeys, onChange: rowSelectChange} : null", @change="tableChange")
  span(slot="title", v-if="tableConfig.showTitle")
    slot(name="title")
  template(v-for="column in tableConfig.columns")
    a-table-column(v-if="column.type === 'action'", :data-index="column.key || 'action'", :title="$bk18n.t('common.handle')", :width="column.width || ((column.actions.length * 60) + 'px')", :fixed="column.fixed")
      template(slot-scope="text, record")
        template(v-for="(actionBtn,idx) in column.actions")
          template(v-if="record.editable")
            template(v-if="actionBtn.type == 'edit'")
              a-button.mr-5(size="small", @click="rowCancel(record[rowKey])") 取消
              a-button(size="small", type="primary", @click="actionsClick('save', record)") 确定
          template(v-else)
            a-popconfirm(v-if="actionBtn.type === 'remove'", title="您确定要删除吗？", @confirm="actionsClick('remove', record)")
              a-button(:size="actionBtn.size || 'small'", type="danger") {{ actionBtn.i18? $bk18n.t(actionBtn.title): (actionBtn.title || '删除')}}
            a-button(:disabled="editKey != '' && actionBtn.type === 'edit'", :size="actionBtn.size || 'small'", :type="actionBtn.color || 'default'", @click="actionsClick(actionBtn.type, record)",:class="{'mr-5': (idx < column.actions.length - 1)}",v-else) {{actionBtn.i18 ? $bk18n.t(actionBtn.title) : actionBtn.title}}
    a-table-column(v-else-if="column.type === 'avatar'", :data-index="column.key", :title="column.title")
      template(slot-scope="text, record")
        a-avatar(:src="text", shape="square", :size="column.width || 80")
    a-table-column(:data-index="column.key", :title="column.i18 ? $bk18n.t(column.title) : column.title", :width="column.width || 'auto'",v-else)
      template(slot-scope="text, record")
        template(v-if="record.editable && column.type === 'edit'")
          edit-input(:value="text", @change="e => rowInputChange(e, record[rowKey], column.key)", :type="column.editType", :mode="column.mode")
          //- a-input(:value="text", @change="e => rowInputChange(e.target.value, record[rowKey], column.key)")
        template(v-else)
          span(v-if="column.render") {{column.render(record, $bk18n)}}
          span(v-else) {{text}}
</template>

<script>
export default {
  props: {
    tableData: {
      type: Array,
      default: () => []
    },
    tableConfig: {
      type: Object,
      required: true
    },
    currentPage: {
      type: Number,
      default: 1
    },
    total: {
      type: Number,
      default: 0
    },
    pageSize: {
      type: Number,
      default: 10
    },
    pgChange: {
      type: Function,
      default: null
    }
  },
  data () {
    return {
      rowEdit: false,
      rowKey: 'id',
      defaultPgConfig: {
        size: 'small',
        pageSizeOptions: ['10', '30', '50'],
        hideOnSinglePage: true,
        showSizeChanger: true,
        showQuickJumper: true,
        total: 0,
        current: 1,
      },
      currentData: [],
      // 行编辑的缓存
      originEditObj: {},
      editKey: '',
      selectRowKeys: []
    }
  },
  watch: {
    total (newVal) {
      this.defaultPgConfig.total = newVal
    },
    tableData: {
      handler (newVal) {
        console.log(newVal)
        if (this.rowEdit) {
          console.log('rowKey:>>', this.rowKey)
          const newArr = newVal.filter((item) => item[this.rowKey] === '')
          const editArr = this.currentData.filter(
            (item) => item.editable === true
          )
          console.log(
            'newArr length:>>',
            newArr.length,
            ';editArr:>>',
            editArr.length
          )
          if (newArr.length > 0 && editArr.length > 0) {
            if (
              newArr.length !== editArr.length ||
              (newArr.length === editArr.length && newArr.length > 1)
            )
              this.msgShow('请先保存新增')
            if (editArr.length === 1) {
              this.originEditObj = editArr[0]
            }
            if (editArr.length === 2) {
              const originArr = this.currentData.filter(
                (item) => item.editable !== true
              )
              originArr.push(editArr[0])
              this.currentData = originArr
            }
            this.$emit('update:tableData', this.currentData)
          } else {
            if (newArr.length === 1) {
              this.editKey = 'newRow'
            }
            this.currentData = Object.assign([], newVal)
          }
        } else {
          this.currentData = Object.assign([], newVal)
        }
        // this.$emit('update:tableData', this.currentData)
      },
      immediate: true
    }
  },
  beforeMount () {
    this.$nextTick(function () {
      if (this.tableConfig.rowKey) this.rowKey = this.tableConfig.rowKey
      if (this.tableConfig.tablePgConfig) {
        for (const key in this.tableConfig.tablePgConfig) {
          this.defaultPgConfig[key] = this.tableConfig.tablePgConfig[key]
        }
      }
      this.defaultPgConfig.current = this.currentPage
      this.defaultPgConfig.total = this.total
      this.defaultPgConfig.pageSize = this.pageSize
      // this.defaultPgConfig.onChange = this.paginationChange
      // this.defaultPgConfig.showSizeChange = this.showSizeChange
      this.rowEdit =
        this.tableConfig.columns.filter((item) => item.type === 'edit').length >
        0
    })
  },
  methods: {
    tableChange (pagination, filters, sorter, { currentDataSource }) {
      if (this.defaultPgConfig.current !== pagination.current) {
        this.defaultPgConfig.current = pagination.current
        this.$emit('pgChange', pagination.current)
      }
      if (this.defaultPgConfig.pageSize !== pagination.pageSize) {
        this.defaultPgConfig.pageSize = pagination.pageSize
        this.$emit('pgSizeChange', pagination.pageSize)
      }
    },
    actionsClick (type, rowObj) {
      if (this.rowEdit) {
        if (type === 'edit') {
          this.originEditObj = Object.assign({}, rowObj)
          this.editKey = rowObj[this.rowKey]
          rowObj.editable = true
        } else {
          this.editKey = ''
        }
      }
      if (!this.rowEdit || (this.rowEdit && type !== 'edit'))
        this.$emit('rowBtnClick', type, rowObj)
      this.$forceUpdate()
    },
    rowCancel (key) {
      let data = this.currentData
      if (key === '') {
        data = data.filter((item) => item[this.rowKey] !== key)
        this.editKey = ''
        this.$emit('update:tableData', data)
      } else {
        const index = data.findIndex((itm) => itm[this.rowKey] === key)
        if (index >= 0) {
          this.editKey = ''
          delete this.originEditObj.editable
          data[index] = this.originEditObj
          this.$emit('update:tableData', data)
        }
      }
    },
    rowInputChange (value, key, column) {
      const data = Object.assign([], this.currentData)
      const obj = data.filter((item) => item[this.rowKey] === key)[0]
      console.log('change obj:>>', obj, ';value:>>', value)
      if (obj) {
        obj[column] = value
        this.$emit('update:tableData', data)
      }
    },
    rowSelectChange (keys) {
      this.selectRowKeys = keys
      this.$emit('rowSelectChange', keys)
    },
    paginationChange (page) {
      console.log('pagination change:>>', page)
      this.defaultPgConfig.current = page
      // this.$emit('paginationChange', page)
      this.$emit('pgChange', page)
    },
    paginationSizeChange (current, size) {
      console.log('pagination size change:>>', current, size)
      this.$emit('pgSizeChange', current, size)
    }
  }
}
</script>
