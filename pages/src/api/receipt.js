import request from '@/utils/request'
import qs from 'qs'

export function buy(formData) {
  return request({
    url: '/receipt/buy',
    method: 'post',
    data: qs.stringify(formData)
  })
}

export function checkReceipt(id) {
  return request({
    url: '/receipt/checkReceipt',
    method: 'post',
    params: {id}
  })
}

export function cancel(formData) {
  return request({
    url: '/receipt/cancel',
    method: 'post',
    data: qs.stringify(formData)
  })
}

export function findAll() {
  return request({
    url: '/receipt/findAll',
    method: 'post',
    params: {}
  })
}
export function find(id) {
  return request({
    url: '/receipt/find',
    method: 'post',
    params: {id}
  })
}

export function deleteReceipt(id) {
  return request({
    url: '/receipt/deleteReceipt',
    method: 'post',
    params: {id}
  })
}
