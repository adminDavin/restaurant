// 搜索组件数据
export const searchItems = [
  {
    lbl: 'banner.search.name',
    i18: true,
    val: '',
    type: 'select',
    selectList: [{
      lbl: 'banner.form.area_1',
      i18: true,
      val: 'home_1'
    }, {
      lbl: 'banner.form.area_2',
      i18: true,
      val: 'home_2'
    }, {
      lbl: 'banner.form.area_3',
      i18: true,
      val: 'home_3'
    }, {
      lbl: 'banner.form.area_4',
      i18: true,
      val: 'home_4'
    }],
    key: 'sectionArea',
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
    lbl: 'banner.btnGroup.add',
    i18: true,
    action: 'add',
  },
  {
    lbl: 'banner.btnGroup.batchUp',
    i18: true,
    action: 'batchUp'
  },
  {
    lbl: 'banner.btnGroup.batchDown',
    i18: true,
    action: 'batchDown'
  },
  {
    lbl: 'banner.btnGroup.batchRemove',
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
      title: 'banner.form.picName',
      i18: true,
      key: 'bannerName',
    },
    {
      title: 'banner.form.region',
      i18: true,
      key: 'sectionArea',
      render(row: any, t18: any) {
        if (row.sectionArea === 'home_1') {
          return t18.t('banner.form.area_1')
        } else if (row.sectionArea === 'home_2') {
          return t18.t('banner.form.area_2')
        } else if (row.sectionArea === 'home_3') {
          return t18.t('banner.form.area_3')
        }else {
          return t18.t('banner.form.area_4')
        }
      }
    },
    {
      title: 'banner.form.linkFlag',
      i18:true,
      key: 'linkFlag',
      render(row:any, t18: any) {
        return row.linkFlag ? t18.t('common.yes') : t18.t('common.no')
      }
    },
    {
      title: 'common.status',
      i18: true,
      key: 'status',
      render(row: any, t18: any) {
        let result = '冻结'
        switch (row.status) {
          case 1:
            result = t18.t('common.start')
            break
          case 2:
            result = t18.t('common.stop')
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
          title: 'common.preview'
        }
      ],
    },
  ],
  tablePgConfig: {
    defaultPageSize: 3,
    total: 100,
  },
}
