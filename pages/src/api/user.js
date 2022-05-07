import request from '@/utils/request'
import qs from 'qs'

export function createOrUpdateUser(formData) {
  formData.psw = btoa(formData.psw)
  return request({
    url: '/user/CreateOrUpdate',
    method: 'post',
    data: qs.stringify(formData)
  })
}

export function findAllUser() {
  return request({
    url: '/user/all',
    method: 'get',
  })
}

export function deleteUser(id) {
  return request({
    url: '/user/delete',
    method: 'post',
    params: {id}
  })
}