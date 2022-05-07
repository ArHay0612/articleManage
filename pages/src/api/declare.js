import request from '@/utils/request'
import qs from 'qs'

export function confirmDeclare(id) {
  return request({
    url: '/declare/confirmDeclare',
    method: 'post',
    params: {id}
  })
}
export function findTaxById(id) {
  return request({
    url: '/declare/findTaxById',
    method: 'post',
    params: {id}
  })
}
export function findConById(id) {
  return request({
    url: '/declare/findConById',
    method: 'post',
    params: {id}
  })
}
export function findEntById(id) {
  return request({
    url: '/declare/findEntById',
    method: 'post',
    params: {id}
  })
}
export function findAll() {
  return request({
    url: '/declare/findAll',
    method: 'post',
    params: {}
  })
}
export function deleteTax(id) {
  return request({
    url: '/declare/deleteTax',
    method: 'post',
    params: {id}
  })
}
export function deleteCon(id) {
  return request({
    url: '/declare/deleteCon',
    method: 'post',
    params: {id}
  })
}
export function deleteEnt(id) {
  return request({
    url: '/declare/deleteEnt',
    method: 'post',
    params: {id}
  })
}
export function findMyDeclare(id) {
  return request({
    url: '/declare/findMyDeclare',
    method: 'post',
    params: {id}
  })
}

export function createContruction(form) {
  return request({
    url: '/declare/createContruction',
    method: 'post',
    data: qs.stringify(form)
  })
}

export function createEnterprise(form) {
  return request({
    url: '/declare/createEnterprise',
    method: 'post',
    data: qs.stringify(form)
  })
}

export function createTaxTotal(form) {
  
var data = {}
data.userid = form.userid
data.inputvat = form.inputvat
data.outputvat = form.outputvat
data.lasttax = form.lasttax
data.inputvatout = form.inputvatout
data.freetax = form.freetax
data.counttax = form.counttax
data.totalfreetax = form.totalfreetax
data.actualtotalfreetax = form.actualtotalfreetax
data.paytax = form.paytax
data.enddaytax = form.enddaytax
data.easycounttax = form.easycounttax
data.easycountchecktax = form.easycountchecktax
data.payfreetax = form.payfreetax
data.totaltax = form.totaltax
data.applytype = form.applytype
data.begindate = form.begindate
data.enddate = form.enddate
data.taxiptotal = form.taxiptotal
for (let i=0; i<form.taxopIns.length; i++) {
  data['taxopIns[' + i + '].sales'] = form.taxopIns[i].sales
  data['taxopIns[' + i + '].salestax'] = form.taxopIns[i].salestax
  data['taxopIns[' + i + '].rate'] = form.taxopIns[i].rate
  data['taxopIns[' + i + '].type'] = form.taxopIns[i].type
}
for (let i=0; i<form.taxipIns.length; i++) {
  data['taxipIns[' + i + '].money'] = form.taxipIns[i].money
  data['taxipIns[' + i + '].amount'] = form.taxipIns[i].amount
  data['taxipIns[' + i + '].tax'] = form.taxipIns[i].tax
  data['taxipIns[' + i + '].type'] = form.taxipIns[i].type
}
  
  return request({
    url: '/declare/createTaxTotal',
    method: 'post',
    data: qs.stringify(data)
  })
}

