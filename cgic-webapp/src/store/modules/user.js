import {
  setToken,
  removeToken,
  setUser,
  removeUser,
  setName,
  removeName,
  setRoleIds,
  removeRoleIds
} from '@/api/auth'
import { login, getUserDetail, logout } from '@/api/login-api'
import { getMenuInfo } from '@/api/api'
import routerFormat from '@/utils/router'

function clearCookie () {
  let date = new Date()
  date.setTime(date.getTime() - 10000)
  let keys = document.cookie.match(/[^ =;]+(?=\=)/g)
  if (keys) {
    for (var i = keys.length; i--;)
      document.cookie = keys[i] + '=0; expire=' + date.toGMTString() + '; path=/'
  }
}

import {
  routerMap,
  notFoundRouter
} from '@/router'

let user = {
  state: {
    token: '',
    userData: null,
    name: '',
    roles: [],
    avatar: '',
    routers: routerMap,
    addRouters: [],
    isRefresh: ''

  },
  mutations: {
    SET_TOKEN: (state, token) => {
      setToken(token)
      state.token = token
    },
    SET_USERDATA: (state, userData) => {
      sessionStorage.setItem(`userData`, JSON.stringify(userData))
      state.userData = userData
    },
    SET_NAME: (state, name) => {
      setName(name)
      state.name = name
    },
    SET_ROLES: (state, roles) => {
      sessionStorage.setItem(`roles`, JSON.stringify(roles))
      state.roles = roles
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers //路由访问
      state.routers = routerMap.concat(routers).concat(notFoundRouter)
      sessionStorage.setItem(`routers`, JSON.stringify(state.routers))
    },
    SET_IS_REFRESH: (state, isRefresh) => {
      state.isRefresh = isRefresh
    },
    REMOVE_TOKEN: (state) => {
      state.token = ''
      state.name = ''
      state.userData = null
      state.roles = []
      state.routers = []
      state.addRouters = []
      sessionStorage.clear()
      // let url = `${POR_BASE_URL}/logout`
      // if (skip) {
      //   logoutByForm(url)
      // }
    }
  },
  actions: {
    // 登录
    Login ({commit}, userInfo) {
      const username = userInfo.username.trim()
      let _data = {
        username: userInfo.username.trim(),
        password: userInfo.password
      }
      return new Promise((resolve, reject) => {
        login(_data).then(response => {
          const data = response.data
          commit('SET_TOKEN', data.access_token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetUserInfo ({commit, state}) {
      return new Promise((resolve, reject) => {
        getUserDetail().then(response => {
          const data = response.data
          // if (data.roles && data.roles.length > 0) { // 验证返回的roles是否是一个非空数组
          //   commit('SET_ROLES', data.roles)
          // } else {
          //   reject('getInfo: roles must be a non-null array !')
          // }
          if (data.rows && data.rows.length > 0) {
            console.log('get UserInfo...')
            commit('SET_USERDATA', data.rows[0])
            commit('SET_NAME', data.rows[0].displayName)
            commit('SET_AVATAR', '@/assets/user-01.png')
            commit('SET_ROUTERS', routerFormat(data.rows[0].routers))
            commit('SET_IS_REFRESH', 'N')
          }
          resolve(response)
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 登出
    LogOut ({commit, state}) {
      return new Promise((resolve, reject) => {
        logout().then(() => {
          removeToken()
          removeName()
          clearCookie()
          commit('REMOVE_TOKEN', '')
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出,获取用户信息失败
    FedLogOut ({commit}) {
      return new Promise(resolve => {
        removeToken()
        removeName()
        clearCookie()
        commit('REMOVE_TOKEN', '')
        resolve()
      })
    }
  }
}

export default user
