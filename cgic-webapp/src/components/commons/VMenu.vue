<template>
  <div class="nav">
    <el-menu
      :default-active="active"
      class="el-menu-vertical"
      unique-opened
      active-text-color='#409EFF'
      background-color="#272d34"
      text-color="#ffffff"
      @select="handleSelect">

      <div v-for="(item,i) in menus" :key="i">

        <el-submenu :index="item.resourceUrl" v-if="item.children">
          <template slot="title">
            <i :class="item.menuIcon"></i>
            <span class="tohide">{{item.menuName}}</span>
          </template>
          <el-menu-item :index="val.resourceUrl" v-for="(val,index) in item.children" :key="index">
            <i :class="val.menuIcon"></i>
            <span slot="title">{{val.menuName}}</span>
          </el-menu-item>
        </el-submenu>

        <el-menu-item :index="item.resourceUrl" v-else>
          <i :class="item.menuIcon"></i>
          <span slot="title">{{item.menuName}}</span>
        </el-menu-item>
      </div>

    </el-menu>
  </div>
</template>
<script>
  import { getMenuInfo } from '@/api/api'
  import { getUser } from '@/api/auth'
  export default {
    name: 'VMenu',
    data () {
      return {
        defaultActive: '/main',
        menus: []
      }
    },
    created: function () {
      getMenuInfo().then((res) => {
        if (res.data.rows && res.data.rows.length > 0) {
          this.$data.menus = res.data.rows;
        } else {
          this.$message.error('用户菜单获取失败！')
        }
      }).catch((e) => {
        this.$message.error('用户菜单获取失败！')
        console.log(e)
      })
    },
    props: {
      active: {
        type: String,
        required: true
      }
    },
    methods: {
      handleSelect (key, keyPath) {
        this.$emit('update:active', key)
        this.$emit('update:title', key)
      }
    }
  }
</script>
<style>
  .nav {

  }

</style>
