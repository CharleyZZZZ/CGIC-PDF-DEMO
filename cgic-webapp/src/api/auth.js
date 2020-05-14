import Cookies from 'js-cookie'

const TokenKey = 'UserToken'
const NameKey = 'UserName'

export function getToken () {
  return Cookies.get(TokenKey)
}

export function setToken (token) {
  return Cookies.set(TokenKey, token, { path: '' })
}

export function removeToken () {
  return Cookies.remove(TokenKey, { path: '' })
}

export function getName () {
  return Cookies.get(NameKey)
}

export function setName (token) {
  return Cookies.set(NameKey, token, { path: '' })
}

export function removeName () {
  return Cookies.remove(NameKey, { path: '' })
}
