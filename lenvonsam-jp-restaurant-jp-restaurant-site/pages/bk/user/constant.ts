// 搜索组件数据
export const searchItems = [
  {
    lbl: 'account.search.name',
    i18: true,
    val: '',
    key: 'loginName',
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
    lbl: 'account.btnGroup.add',
    i18: true,
    action: 'add',
  },
  {
    lbl: 'account.btnGroup.batchUp',
    i18: true,
    action: 'batchUp'
  },
  {
    lbl: 'account.btnGroup.batchDown',
    i18: true,
    action: 'batchDown'
  }
]

// 表格配置
export const tableConfig = {
  rowKey: 'id',
  cbx: true,
  columns: [
    {
      title: 'account.form.name',
      i18: true,
      key: 'loginName',
    },
    {
      title: 'account.form.nickname',
      i18: true,
      key: 'nickname'
    },
    {
      title: 'account.form.username',
      i18: true,
      key: 'userName',
    },
    {
      title: 'common.status',
      i18: true,
      key: 'value',
      render(row: any, i18n: any) {
        let result = '冻结'
        switch (row.status) {
          case 1:
            result = i18n.t('common.start')
            break
          case 2:
            result = i18n.t('common.stop')
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
          i18: true,
          title: 'common.edit',
        },
        {
          type: 'remove',
          i18: true,
          title: 'common.remove'
        }
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}
