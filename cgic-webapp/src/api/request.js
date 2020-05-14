import axios from 'axios'
import store from '@/store'

// 获取token专用
export const loginApi = axios.create({
  baseURL: process.env.BASE_SERVER_URL, // api 的 base_url
  timeout: 5000, // 请求超时时间
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
})
// 退出登录
export const logoutApi = axios.create({
  baseURL: process.env.BASE_SERVER_URL, // api 的 base_url
  timeout: 5000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})

// 获取用户信息
export const userInfoApi = axios.create({
  baseURL: process.env.BASE_SERVER_URL, // api 的 base_url
  timeout: 5000, // 请求超时时间
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
})


export const jsonApi = axios.create({
  baseURL: process.env.BASE_SERVER_URL, // api 的 base_url
  timeout: 5000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})


// 获取菜单信息
export const menuInfoApi = axios.create({
  baseURL: process.env.BASE_SERVER_URL, // api 的 base_url
  timeout: 5000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})

export const menuListApi = axios.create({
  baseURL: process.env.BASE_SERVER_URL, // api 的 base_url
  timeout: 5000, // 请求超时时间
  headers: {
    'Content-Type': 'application/json'
  }
})

// request 拦截器
const requestInterceptor = config => {
  if (store.getters.token) {
      config.headers.Authorization = `Bearer ${store.getters.token}`
  }
  return config
}

const errorRequestInterceptor = (error) => {
  Promise.reject(error)
}
//
// // login response拦截器
// const loginResponseInterceptor = response => {
//   const res = response.data
//   if (res) {
//     return res
//   }
// }
//
// const errorResponseInterceptor = (error) => {
//   console.log('err：' + JSON.stringify(error))
//   if (error.response) {
//     switch (error.response.status) {
//       // token失效 重新获取
//       case 401:
//         store.commit('REMOVE_TOKEN', true)
//         break
//       case 404:
//         Message({
//           message: `未知错误！`,
//           type: 'error',
//           duration: 5 * 1000
//         })
//         break
//       default:
//         Message({
//           message: `${error.response.message}`,
//           type: 'error',
//           duration: 5 * 1000
//         })
//     }
//   } else {
//     if (error.code === 'ECONNABORTED' && error.message.indexOf('timeout') !== -1) {
//       Message({
//         message: '网络请求超时！',
//         type: 'error',
//         duration: 5 * 1000
//       })
//     } else {
//       Message({
//         message: '发生未知错误，请联系管理员！',
//         type: 'error',
//         duration: 5 * 1000
//       })
//     }
//   }
//   return Promise.reject(error)
// }
//
// // response拦截器
// const responseInterceptor = response => {
//   const res = response.data
//   if (!res.success) {
//     console.log(res)
//     Message({
//       message: res.success === false ? `${res.message}` : '发生未知错误，请联系管理员！',
//       type: 'error',
//       duration: 5 * 1000
//     })
//     // eslint-disable-next-line
//     return Promise.reject('error')
//   } else {
//     return res
//   }
// }
//

userInfoApi.interceptors.request.use(requestInterceptor, errorRequestInterceptor);
logoutApi.interceptors.request.use(requestInterceptor, errorRequestInterceptor);

jsonApi.interceptors.request.use(requestInterceptor, errorRequestInterceptor);
// menuInfoApi.interceptors.request.use(requestInterceptor, errorRequestInterceptor);
// menuListApi.interceptors.request.use(requestInterceptor, errorRequestInterceptor);
