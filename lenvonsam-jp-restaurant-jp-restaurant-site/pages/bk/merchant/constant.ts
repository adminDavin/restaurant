// 搜索组件数据
export const searchItems = [
  {
    lbl: 'merchant.search.name',
    i18: true,
    val: '',
    key: 'merchantName',
  },
  // {
  //   lbl: '时间',
  //   val: null,
  //   type: 'date',
  //   key: 'createAt',
  // },
]

// 按钮组组件
export const buttonGroups = [
  {
    lbl: 'merchant.add',
    i18: true,
    action: 'add',
  }
]

// 表格配置
export const tableConfig = {
  rowKey: 'id',
  cbx: true,
  columns: [
    {
      // title: '商户名称',
      title: 'merchant.form.name',
      i18: true,
      key: 'merchantName',
    },
    {
      title: 'merchant.form.email',
      i18: true,
      key: 'email'
    },
    {
      title: 'merchant.form.intro',
      i18: true,
      key: 'intro',
    },
    {
      title: 'merchant.form.openTime',
      i18: true,
      key: 'openTimeInfo',
    },
    {
      title: 'merchant.form.mobile',
      i18: true,
      key: 'phone',
    },
    {
      title: 'merchant.form.address',
      i18: true,
      key: 'address'
    },
    // {
    //   title: '状态',
    //   key: 'value',
    //   render(row: any) {
    //     let result = '草稿'
    //     switch (row.status) {
    //       case 1:
    //         result = '上架'
    //         break
    //       case 2:
    //         result = '下架'
    //         break
    //       default:
    //         break
    //     }
    //     return result
    //   },
    // },
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
        {
          type: 'banner',
          i18: true,
          title: 'merchant.form.thumb'
        }
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}

export const merchantSelectConfig = {
  url: 'sys/merchant/dropList',
  searchKey: 'merchantCode',
  labelKey: 'merchantName',
  valueKey: 'merchantCode'
}

// 卡片页面配置
export const cardSearchItems = [
  {
    lbl: 'merchantCard.search.name',
    i18: true,
    val: '',
    key: 'busiCode',
    type: 'remoteSelect',
    remoteConfig: merchantSelectConfig
  },
]

export const cardButtonGroups =  [
  {
    lbl: '卡片新增',
    action: 'add',
  }
]

export const cardTableConfig = {
  rowKey: 'id',
  cbx: true,
  columns: [
    {
      title: 'merchantCard.listItemTitle',
      i18: true,
      key: 'title',
    },
    {
      title: 'merchantCard.form.merchant',
      i18: true,
      key: 'busiName',
    },
    {
      title: 'common.updateTime',
      i18:true,
      key: 'updateTime',
    },
    {
      type: 'action',
      width: '150px',
      actions: [
        {
          type: 'edit',
          i18:true,
          title: 'common.edit',
        },
        {
          type: 'preview',
          i18: true,
          title: 'merchantCard.row.preview'
        }
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}
