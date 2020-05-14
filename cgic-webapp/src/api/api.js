/**
 * api接口统一管理
 */
// 导入封装的GET\POST方法
import { get, post } from './http'
// 导入配置
import {  jsonApi } from './request'
import { requestData, queryData } from './query-utils-api'


// 获取 权限下菜单
export const getMenuInfo = () => {
  return requestData(jsonApi, 'get', `/cgpf/api/menu/info`)
}

// 获取菜单列表
export const getMenuList = (page = 1, pageSize = 10) => {
  return requestData(jsonApi, 'get', `/cgpf/api/menu/list?page=${page}&pageSize=${pageSize}`)
}
