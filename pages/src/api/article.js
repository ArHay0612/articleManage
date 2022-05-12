import request from '@/utils/request'
import qs from 'qs'

export function create(formData) {
  return request({
    url: '/article/create',
    method: 'post',
    data: qs.stringify(formData)
  })
}

export function getAll() {
  return request({
    url: '/article/all',
    method: 'get',
  })
}

export function findContent(id) {
  return request({
    url: '/article/findContent',
    method: 'post',
    params: {id}
  })
}

export function deleteArticle(id) {
  return request({
    url: '/article/delete',
    method: 'post',
    params: {id}
  })
}

export function uploadFile(formData) {
  return request({
    url: '/article/uploadPicture',
    method: 'post',
    data: formData
  })
}