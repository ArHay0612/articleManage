import request from '@/utils/request'
import qs from 'qs'

export function createOrUpdate(formData) {
  return request({
    url: '/busi/CreateOrUpdate',
    method: 'post',
    data: qs.stringify(formData)
  })
}

export function find(id) {
  return request({
    url: '/busi/find',
    method: 'get',
    params: {id}
  })
}

export function findMyAll(id) {
  return request({
    url: '/busi/findMyAll',
    method: 'post',
    params: {id}
  })
}

export function findstop(id) {
  return request({
    url: '/busi/findstop',
    method: 'post',
    params: {id}
  })
}

export function checkBusi(id) {
  return request({
    url: '/busi/checkBusi',
    method: 'post',
    params: {id}
  })
}

export function findAll() {
  return request({
    url: '/busi/findAll',
    method: 'post',
    data: {}
  })
}