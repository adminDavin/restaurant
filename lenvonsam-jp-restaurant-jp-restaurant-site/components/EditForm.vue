<template lang="pug">
a-drawer(:visible="visible", :width="width", @close="drawerClose", :title="formConfig.title || '表单操作'")
  a-form-model(:model="localForm", v-bind="{labelCol: {span:6}, wrapperCol: {span: 16}}", :rules="formConfig.rules || null", ref="ruleForm", v-if="visible")
    a-row(v-for="(item,idx) in formArr", :key="idx")
      a-col(:span="12")
        a-form-model-item(:label="item[0].lbl", :prop="item[0].key")
          a-input(v-model="localForm[item[0].key]")
      a-col(:span="12")
        template(v-if="item[1] !== ''")
          a-form-model-item(:label="item[1].lbl", :prop="item[1].key")
            a-input(v-model="localForm[item[1].key]")
  .form-footer
    a-button.mr-15(@click="submitForm('ruleForm')", type="primary", style="width: 100px") {{formConfig.confirmText || '保存'}}
    a-button(@click="drawerClose", style="width: 100px") {{formConfig.cancelText || 'キャンセル'}}

</template>

<script>
export default {
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    width: {
      type: Number,
      default: 600
    },
    formConfig: {
      type: Object,
      required: true
    },
    originForm: {
      type: null,
      default: false
    }
  },
  data() {
    return {
      formArr: [],
      localForm: {}
    }
  },
  watch: {
    visible(newVal) {
      if (newVal) {
        if (this.originForm) {
          for (const key in this.originForm) {
            this.localForm[key] = this.originForm[key]
          }
        } else {
          this.initForm()
        }
        this.$nextTick(function() {
          if (this.$refs.ruleForm) this.$refs.ruleForm.resetFields()
        })
      }
    }
  },
  beforeMount() {
    // 一行两列显示
    this.formArr = this.arr2D(this.formConfig.items, true, 2)
    this.initForm()
  },
  methods: {
    initForm() {
      const form = {}
      for (const idx in this.formConfig.items) {
        const item = this.formConfig.items[idx]
        if (item.key) form[item.key] = ''
      }
      this.localForm = Object.assign({}, form)
    },
    drawerClose() {
      this.$emit('update:visible', false)
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.$emit('submit', this.localForm)
          this.drawerClose()
        } else {
          return false
        }
      })
    }
  }
}
</script>

<style lang="stylus" scoped>
.form-footer
  position absolute
  right 0
  bottom 0
  width 100%
  border-top 1px solid #e9e9e9
  padding 12px 25px
  z-index 1
  text-align right
</style>
