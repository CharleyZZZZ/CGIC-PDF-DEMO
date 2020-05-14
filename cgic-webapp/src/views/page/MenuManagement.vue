<template>
  <div id="menumgt">
    <div>
      <el-row>
        <el-table
          stripe
          :data="menuData.filter(data => !search || data.menuCode.includes(search))"
          style="width: 100%;"
          :row-class-name="tableHeaderColor"
          :cell-style="{fontSize:'12px'}">
          <el-table-column
            prop="menuId"
            label="ID"
            align="center"
            width="80">
          </el-table-column>
          <el-table-column
            prop="menuCode"
            label="菜单编码"
            align="center"
            width="140">
          </el-table-column>
          <el-table-column
            prop="menuName"
            label="菜单名称"
            align="center"
            width="140">
          </el-table-column>
          <el-table-column
            prop="resourceId"
            label="资源名称"
            align="center"
            width="180">
          </el-table-column>
          <el-table-column
            prop="parentMenuName"
            label="父级菜单"
            align="center"
            width="140">
          </el-table-column>
          <el-table-column
            prop="menuOrder"
            label="排序"
            align="center"
            width="60">
          </el-table-column>
          <el-table-column
            prop="isEnabeld"
            label="是否启用"
            align="center"
            width="120">
          </el-table-column>
          <el-table-column
            align="right">
            <template slot="header" slot-scope="scope">
              <el-input
                v-model="search"
                placeholder="菜单编码"/>
            </template>
          </el-table-column>
        </el-table>
      </el-row>

    </div>


    <el-dialog
      title="新增菜单"
      :visible.sync="dialogVisible"
      width="60%"
      :before-close="handleClose">

      <el-form>
        <el-row>
          <el-col :span="4" :offset="1">
            <label class="el-form-label">菜单编码</label>
          </el-col>
          <el-col :span="6">
            <el-input v-model="addMenu.menuCode"></el-input>
          </el-col>
          <el-col :span="4">
            <label class="el-form-label">菜单名称</label>
          </el-col>
          <el-col :span="6">
            <el-input v-model="addMenu.menuName"></el-input>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4" :offset="1">
            <label class="el-form-label">资源ID</label>
          </el-col>
          <el-col :span="6">
            <el-input v-model="addMenu.resourceId"></el-input>
          </el-col>
          <el-col :span="4">
            <label class="el-form-label">父菜单ID</label>
          </el-col>
          <el-col :span="6">
            <el-input v-model="addMenu.parentId"></el-input>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4" :offset="1">
            <label class="el-form-label">菜单排序</label>
          </el-col>
          <el-col :span="6">
            <el-input v-model="addMenu.menuOrder"></el-input>
          </el-col>
          <el-col :span="4">
            <label class="el-form-label">是否启用</label>
          </el-col>
          <el-col :span="6">
            <el-input v-model="addMenu.isEnabled" type="checkbox"></el-input>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="4" :offset="1">
            <label class="el-form-label">描述</label>
          </el-col>
          <el-col :span="16">
            <el-input type="textarea" placeholder="请输入描述" rows="12"></el-input>
          </el-col>
        </el-row>


      </el-form>

      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import VTitle from '@/components/commons/VTitle.vue'
  import ElButton from '../../../node_modules/element-ui/packages/button/src/button.vue'
  import ElDialog from '../../../node_modules/element-ui/packages/dialog/src/component.vue'
  import ElForm from '../../../node_modules/element-ui/packages/form/src/form.vue'
  import ElInput from '../../../node_modules/element-ui/packages/input/src/input.vue'
  import ElRow from 'element-ui/packages/row/src/row'
  import { getMenuList } from '@/api/api'

  export default {
    computed: {
      ...mapGetters([
        'name',
        'roles'
      ])
    },
    components: {
      ElRow,
      ElInput,
      ElForm,
      ElDialog,
      ElButton,
      VTitle
    },
    name: 'MenuManagement',
    data () {
      return {
        myTitle: '菜单管理',
        dialogVisible: false,
        addMenu: {
          menuCode: '',
          menuName: '',
          description: '',
          resourceId: null,
          parentId: null,
          menuOrder: null,
          isEnabled: ''
        },
        menuData: [],
        search: ''
      }
    },
    created: function () {
      getMenuList().then((res) => {
        if (res.data.rows && res.data.rows.length > 0) {
          this.$data.menuData = res.data.rows
        } else {
          this.$message.error('数据获取失败！')
        }
      }).catch((e) => {
        this.$message.error('数据获取失败！')
        console.log(e)
      })
    },
    methods: {
      handleClose () {
        this.dialogVisible = false
      },
      // 修改table header的背景色
      tableHeaderColor({ row, column, rowIndex, columnIndex }) {
        if (rowIndex === 0) {
          return ''
        }
      }
    }
  }
</script>
<style scoped>
  #menumgt > > > .el-dialog__header {
    border-bottom: 1px solid #ece9e9;
    margin: 0px 10px;
  }

  .el-form-label {
    color: #000;
    height: 32px;
    line-height: 32px;
    float: right;
    font-size: 16px;
    margin-right: 10px;
  }

  .el-row {
    margin: 16px 6px;
  }

  .el-table .warning-row {
    background: oldlace;
  }

  .el-table .success-row {
    background: #f0f9eb;
  }

</style>

