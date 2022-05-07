import request from '@/utils/request'
import qs from 'qs'

export function createDerate(formData) {
  return request({
    url: '/derate/createDerate',
    method: 'post',
    data: qs.stringify(formData)
  })
}

export function findAll() {
  return request({
    url: '/derate/findAll',
    method: 'post',
    params: {}
  })
}

export function findMyDerate(id) {
  return request({
    url: '/derate/findMyDerate',
    method: 'post',
    params: {id}
  })
}


export function confirmDerate(userid, derateid) {
  const data = {
    userid: userid,
    derateid: derateid
  }
  return request({
    url: '/derate/confirmDerate',
    method: 'post',
    data: qs.stringify(data)
  })
}

export function deleteDerate(id) {
  return request({
    url: '/derate/deleteDerate',
    method: 'post',
    params: {id}
  })
}
