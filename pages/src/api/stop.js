import request from '@/utils/request'
import qs from 'qs'

export function CreateOrUpdateStop(formData) {
  return request({
    url: '/stopandrestore/CreateOrUpdate',
    method: 'post',
    data: qs.stringify(formData)
  })
}

export function find(id) {
  return request({
    url: '/stopandrestore/findMyStop',
    method: 'get',
    params: {id}
  })
}

export function findAll() {
  return request({
    url: '/stopandrestore/all',
    method: 'get',
    data: {}
  })
}