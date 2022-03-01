
// 搜索组件数据
export const searchItems = [
  {
    lbl: 'news.newsTitle',
    i18: true,
    val: '',
    key: 'newsTitle',
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
    lbl: 'news.btnGroup.add',
    i18: true,
    action: 'add',
  },
  {
    lbl: 'news.btnGroup.batchUp',
    i18: true,
    action: 'batchUp'
  },
  {
    lbl: 'news.btnGroup.batchDown',
    i18: true,
    action: 'batchDown'
  },
  {
    lbl: 'news.btnGroup.batchRemove',
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
      title: 'news.form.title',
      i18: true,
      key: 'newsTitle',
    },
    {
      title: 'news.form.publishTime',
      i18: true,
      key: 'publishDate',
    },
    {
      title: 'news.form.author',
      i18: true,
      key: 'newsAuthor',
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
          type: 'preview',
          i18: true,
          title: 'news.row.preview'
        }
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}
