/**
 * api接口统一管理
 */
// 导入封装的GET\POST方法
import { get, post } from './http'
// 导入配置
import { CLIENT_ID, CLIENT_SECRET } from './config'
import { loginApi, logoutApi, userInfoApi } from './request'
import { requestData } from './query-utils-api'

//登录
// 获取 token
export const login = (data) => {
  let listPromise = new Promise((resolve, reject) => {
    loginApi.post(`/oauth/oauth/token?client_id=` + CLIENT_ID + `&client_secret=` + CLIENT_SECRET + `&grant_type=password&username=${data.username}&password=${data.password}`).then((rst) => {
      resolve(rst)
    }).catch(error => {
      reject(error)
    })
  })
  return listPromise
}

// 获取 用户信息
export const getUserDetail = (data) => {
  let listPromise = new Promise((resolve, reject) => {
    userInfoApi.get(`/oauth/api/user/info`).then((rst) => {
      resolve(rst)
    }).catch(error => {
      reject(error)
    })
  })
  return listPromise
}

// 退出登录
export const logout = () => {
  return requestData(logoutApi, 'get', `/oauth/logout`)
}

