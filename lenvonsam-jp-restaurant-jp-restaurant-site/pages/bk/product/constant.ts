export const merchantSelectConfig = {
  url: 'sys/merchant/dropList',
  searchKey: 'merchantCode',
  labelKey: 'merchantName',
  valueKey: 'merchantCode'
}
// 搜索组件数据
export const searchItems = [
  {
    lbl: 'product.search.chooseMerchant',
    i18: true,
    val: '',
    key: 'merchantCode',
    type: 'remoteSelect',
    remoteConfig: merchantSelectConfig
  },
  {
    lbl: 'product.search.productName',
    i18: true,
    val: '',
    key: 'productTitle',
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
    lbl: 'product.btnGroup.add',
    i18: true,
    action: 'add',
  },
  {
    lbl: 'product.btnGroup.batchUp',
    i18: true,
    action: 'batchUp'
  },
  {
    lbl: 'product.btnGroup.batchDown',
    i18: true,
    action: 'batchDown'
  },
  {
    lbl: 'product.btnGroup.batchRemove',
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
      title: 'product.form.title',
      i18: true,
      key: 'productTitle',
    },
    {
      title: 'product.form.subTitle',
      i18: true,
      key: 'productSubTitle'
    },
    {
      title: 'product.form.merchant',
      i18: true,
      key: 'merchantName',
    },
    {
      title: 'common.status',
      i18: true,
      key: 'status',
      render(row: any, i18n: any) {
        let result = '草稿'
        switch (row.status) {
          case 1:
            result = i18n.t('common.up')
            break
          case 2:
            result = i18n.t('common.down')
            break
          default:
            break
        }
        return result
      },
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
          i18: true,
          title: 'common.edit',
        },
        {
          type: 'preview',
          i18: true,
          title: 'product.row.preview'
        },
        {
          title: 'product.row.detailPreview',
          i18: true,
          type: 'detailPreview'
        }
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}

// 按钮菜单组组件
export const buttonMenuGroups = [
  {
    lbl: 'product.btnMenuGroup.add',
    i18: true,
    action: 'add',
  },
  {
    lbl: 'product.btnMenuGroup.batchUp',
    i18: true,
    action: 'batchUp'
  },
  {
    lbl: 'product.btnMenuGroup.batchDown',
    i18: true,
    action: 'batchDown'
  },
  {
    lbl: 'product.btnMenuGroup.batchRemove',
    i18: true,
    action: 'batchRemove'
  }
]
// 表格菜单配置
export const tableMenuConfig = {
  rowKey: 'id',
  cbx: true,
  columns: [
    {
      title: 'product.formMenu.title',
      i18: true,
      key: 'productTitle',
    },
    {
      title: 'product.formMenu.subTitle',
      i18: true,
      key: 'productSubTitle'
    },
    {
      title: 'product.formMenu.merchant',
      i18: true,
      key: 'merchantName',
    },
    {
      title: 'common.status',
      i18: true,
      key: 'status',
      render(row: any) {
        let result = '草稿'
        switch (row.status) {
          case 1:
            result = '上架'
            break
          case 2:
            result = '下架'
            break
          default:
            break
        }
        return result
      },
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
          title: '编辑',
        },
        {
          type: 'preview',
          title: '封面预览'
        },
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}
