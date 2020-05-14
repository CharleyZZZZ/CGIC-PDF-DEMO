import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
import { Message } from 'element-ui'
import { getToken } from '@/api/auth'


NProgress.configure({showSpinner: false})// NProgress configuration

const whiteList = ['/login'] // 不重定向白名单

// // 每次路由跳转 检查token
// router.beforeEach((to, from, next) => {
//   NProgress.start()
//   let token = store.getters.token
//   if (!token || 0 == token) {
//     if (whiteList.indexOf(to.path) !== -1) {
//       next()
//     } else {
//       next(`/login?redirect=${to.path}`) // 否则全部重定向到登录页
//       NProgress.done()
//     }
//   } else {
//     if (to.path === '/login') {
//       next({path: '/'})
//       NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
//     } else {
//       let userData = store.getters.userData
//       if (!userData) {
//         store.dispatch('GetUserInfo').then(res => { // 拉取用户信息
//           router.addRoutes(store.getters.routers)
//           next()
//         }).catch((err) => {
//           console.log(err)
//           store.dispatch('FedLogOut').then(() => {
//             Message.error(err || 'Verification failed, please login again')
//             next({path: '/'})
//           })
//         })
//       } else {
//         next()
//       }
//     }
//   }
// })


router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken() && getToken()!=0) {
    if (to.path === '/login') {
      next({ path: '/' })
    } else {
      if (store.getters.name.length === 0) {
        store.dispatch('GetUserInfo').then(() => { // 拉取用户信息
          console.log('<<<<<<<<<<<<<<<<<<<<<----------------------')
          console.log('============= try add routers ============>')
          console.log('--------------------->>>>>>>>>>>>>>>>>>>>>>')
          console.log(JSON.stringify(store.getters.routers))
          router.addRoutes(store.getters.routers)//添加后台路由表
          next({ ...to, replace: true })
        }).catch(e => {
          store.dispatch('FedLogOut').then(() => {
            Message.error('验证失败,请重新登录'+e.message)
            next({ path: '/login' })
          })
        })
      } else {
        if(store.getters.isRefresh.length === 0){
          console.log('<<<<<<<<<<<<<<<<<<-------------------------')
          console.log('============= page is refresh ============>')
          console.log('============= try add routers ============>')
          console.log('>>>>>>>>>>>>>>>>>>-------------------------')
          console.log(JSON.stringify(store.getters.routers))
          router.addRoutes(store.getters.routers)//添加后台路由表
          store.commit('SET_IS_REFRESH', 'N')
          next({ ...to, replace: true })
        }else{
          console.log('<<<<<<<<<<<<<<<<<<------------------------')
          console.log('============= page is not refresh ========>')
          console.log('============= next() ============>')
          console.log('>>>>>>>>>>>>>>>>>>-------------------------')
          next()
        }
      }
    }
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      next('/login')
      NProgress.done()
    }
  }
})


router.afterEach(() => {
  NProgress.done() // 结束Progress
})
