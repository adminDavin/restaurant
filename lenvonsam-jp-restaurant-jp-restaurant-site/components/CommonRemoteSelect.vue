<template lang="pug">
//- a-form.full-width(:rules="rules")
//- a-form-model.full-width(:rules="rules")
div
  a-select(
    show-search
    style="width: 175px"
    @change="selectChange"
    v-model="localValue", 
    :placeholder="filterable ? '请输入关键词' : placeholder",
    :size="size",
    :disabled="disabled",
    :multiple="multiple",
    :loading="loading",
  )
    a-select-option(v-for="(item, idx) in selectDatas", :key="idx"
    :value="item[remoteConfig.valueKey || 'merchantCode']") {{item[remoteConfig.labelKey || 'merchantName']}}
</template>

<script>
export default {
  model: {
    event: 'change'
  },
  props: {
    value: {
      type: [String, Array],
      default: ''
    },
    choosedValue: {
      type: String,
      default: ''
    },
    choosedObject: {
      type: Object,
      default: null
    },
    rules: {
      type: Object,
      default: () => {
        return {}
      }
    },
    size: {
      type: String,
      default: 'default'
    },
    disabled: {
      type: Boolean,
      default: false
    },
    placeholder: {
      type: String,
      default: '请输入搜索内容'
    },
    multiple: {
      type: Boolean,
      default: false
    },
    filterable: {
      type: Boolean,
      default: false
    },
    remoteConfig: {
      type: Object,
      default: () => {
        return {}
      }
    }
  },
  data () {
    return {
      localValue: '',
      selectDatas: [],
      loading: false,
      nameArr: []
    }
  },
  watch: {
    value: {
      handler (newVal) {
        console.log('common remote value:>>', newVal)
        this.localValue = newVal
      },
      immediate: true
    },
    choosedValue: {
      handler (newVal) {
        if (newVal !== '') {
          this.loading = true
          this.selectDatas.push(this.choosedObject)
          this.localValue = newVal
          this.loading = false
          this.$emit('update:choosedValue', '')
        }
      },
      immediate: true
    }
  },
  mounted () {
    this.remoteMethod()
  },
  methods: {
    selectChange (value) {
      if (this.multiple) {
        this.nameArr = []
        for (const item of value) {
          this.selectDatas.map((cell) => {
            if (item === cell[this.remoteConfig.valueKey || 'dictKey']) {
              this.nameArr.push(cell)
            }
          })
        }
        console.log(this.nameArr)
        this.$emit('input', value)
        this.$emit('change', value, this.nameArr)
      } else {
        const row = this.selectDatas.find(
          (item) => item[this.remoteConfig.valueKey || 'dictKey'] === value
        )
        this.$emit('input', value)
        this.$emit('change', value, row)
      }
    },
    async remoteMethod (query) {
      console.log('remoteConfig+++', this.remoteConfig)
      try {
        console.log('query:>>', query)
        this.loading = true
        const remoteUrl = this.remoteConfig.url || 'user/account'
        const params = {
          currentPage: 1,
          pageSize: 10
        }
        if (this.remoteConfig.fixQueryKey) {
          params[
            this.remoteConfig.fixQueryKey
          ] = this.remoteConfig.fixQueryValue
        }
        if (query !== '' && query !== undefined) {
          params[this.remoteConfig.searchKey || 'eqdictCodeName'] = query
        }
        const { data } = await this.proxy(
          remoteUrl,
          this.remoteConfig.type || 'get',
          params
        )
        this.selectDatas = data.data
        console.log('remote data:>>', this.selectDatas)
        this.loading = false
      } catch (err) {
        this.loading = false
        console.error(err)
      }
    }
  }
}
</script>
