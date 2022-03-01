export const merchantSelectConfig = {
  url: 'sys/merchant/dropList',
  searchKey: 'merchantCode',
  labelKey: 'merchantName',
  valueKey: 'merchantCode'
}
// 搜索组件数据
export const searchItems = [
  {
    lbl: 'merchantAppoint.search.chooseMerchant',
    i18: true,
    val: '',
    key: 'merchantCode',
    type: 'remoteSelect',
    remoteConfig: merchantSelectConfig
  },
  {
    lbl: 'common.startTime',
    i18: true,
    val: null,
    type: 'date',
    key: 'appointTimeStart',
  },
  {
    lbl: 'common.endTime',
    i18: true,
    val: '',
    type: 'date',
    key: 'appointTimeEnd'
  }
]

// 按钮组组件
export const buttonGroups = [
  {
    lbl: 'merchantAppoint.btnGroup.add',
    i18: true,
    action: 'add',
  },
  {
    lbl: 'merchantAppoint.btnGroup.batchRemove',
    i18: true,
    action: 'batchRemove'
  }
]

// 表格配置
export const tableConfig = {
  rowKey: 'id',
  cbx: true,
  columns: [
    {
      title: 'merchantAppoint.form.merchantName',
      i18: true,
      key: 'merchantName',
    },
    {
      title: 'merchantAppoint.form.appointTime',
      i18: true,
      key: 'appointTime',
      render(row: any) {
        return row.appointTime.substring(0,10)
      }
    },
    {
      title: 'merchantAppoint.form.appointType',
      i18: true,
      key: 'value',
      render(row: any) {
        if (row.appointType === 1) {
          return '×'
        } else if (row.appointType === 2) {
          return '△'
        } else {
          return 'o'
        }
      }
    },
    {
      title: 'common.updateTime',
      i18: true,
      key: 'updateTime',
    },
    {
      type: 'action',
      width: '150px',
      actions: [
        {
          type: 'edit',
          i18: true,
          title: 'common.edit',
        },
        // {
        //   type: 'banner',
        //   title: '轮播图'
        // }
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}
