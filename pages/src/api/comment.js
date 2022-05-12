import request from '@/utils/request'

export function createCommet(comment, articleId, userId) {
  return request({
    url: '/comment/create',
    method: 'post',
    params: { comment, articleId, userId }
  })
}

export function findAllByArticle(id) {
  return request({
    url: '/comment/findAllByArticle',
    method: 'post',
    params: { id }
  })
}

export function deleteComment(id) {
  return request({
    url: '/comment/delete',
    method: 'post',
    params: { id }
  })
}