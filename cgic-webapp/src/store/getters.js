import { getToken, getName } from '@/api/auth'

let getters = {
  sidebar: state => state.app.sidebar || {},
  device: state => state.app.device || '',
  token: state => state.user.token || getToken(),
  userData: state => state.user.userData || JSON.parse(sessionStorage.getItem(`userData`)) || {},
  name: state => state.user.name || getName() || '',
  roles: state => state.user.roles || JSON.parse(sessionStorage.getItem(`roles`)) || [],
  routers: state => state.user.routers || JSON.parse(sessionStorage.getItem(`routers`)) || [],
  isRefresh: state => state.user.isRefresh
}
export default getters
