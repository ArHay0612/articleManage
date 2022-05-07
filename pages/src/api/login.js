import request from '@/utils/request'
import qs from 'qs'

export function loginByEmail(email, psw) {
  const data = {
    email: email,
    psw: btoa(psw)
  }
  return request({
    url: '/user/login',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function logout() {
  return request({
    url: '/user/logout',
    method: 'get'
  })
}

