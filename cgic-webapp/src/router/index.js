import Vue from 'vue'
import Router from 'vue-router'
import 'nprogress/nprogress.css' // progress bar style
import { getToken } from '@/api/auth' // getToken from cookie
import { getMenuInfo } from '@/api/api' // getMenuInfo

/* Layout */
import Layout from '@/views/layout/Layout'

Vue.use(Router)

export const routerMap = [

  {path: '/login', component: () => import('@/components/loginD'), hidden: true},
  {path: '/404', component: () => import('@/views/404'), hidden: true},

  {
    path: '/',
    component: Layout,
    meta: {title: '主页', icon: 'form'},
    children: [
      {
        path: '',
        hidden: true,
        component: () => import('@/views/page/IndexPage'),
      }]
  },

]
export const notFoundRouter = [{
  path: '*',
  redirect: '/404',
  hidden: true
}, ]

const router = new Router({
  mode: 'history',
  scrollBehavior: () => ({y: 0}),
  routes: routerMap
})


export default router
