import { defineStore } from 'pinia'
import { ref } from 'vue'
import { instance } from '@/axios'

export const useCommentStore = defineStore('comment', () => {
  const comments = ref([])

  const fetchComments = async (articleNo) => {
    try {
      const { data } = await instance.get(`/v1/comment/article/${articleNo}`)

      const topLevel = data.data.filter(c => c.parentNo === null)
      console.log(topLevel)
      topLevel.forEach(comment => {
        comment.children = data.data.filter(r => r.parentNo === comment.no)
      })

      comments.value = topLevel
    } catch (err) {
      console.error('댓글 불러오기 실패', err)
    }
  }

  const addComment = async ({ content, usersNo, articleNo, parentNo = null, regDate }) => {
    try {
      await instance.post('/v1/comment/regist', {
        content,
        usersNo,
        articleNo,
        parentNo,
      })
      await fetchComments(articleNo)
    } catch (err) {
      console.error('댓글 등록 실패', err)
    }
  }

  const deleteComment = async (no, articleNo) => {
    try {
      await instance.delete(`/v1/comment/delete/${no}`)
      await fetchComments(articleNo)
    } catch (err) {
      console.error('댓글 삭제 실패', err)
    }
  }
  return {
    comments,
    fetchComments,
    addComment,
    deleteComment,
  }
})
