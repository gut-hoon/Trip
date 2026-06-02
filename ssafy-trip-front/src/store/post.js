// stores/post.js
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { instance } from '@/axios/index'

export const usePostStore = defineStore('post', () => {
  const posts = ref([])
  const recentPosts = ref([])
  const createPost = async ({
    title,
    usersNo,
    content,
    regDate,
    viewCnt,
    isArticle,
  }) => {
    try {
      const { data } = await instance.post('/v1/article/insert', {
        title,
        usersNo,
        content,
        regDate,
        viewCnt,
        isArticle,
      })
      alert('✅ 게시글이 작성되었습니다.')
      return true
    } catch (error) {
      alert('❌ 게시글 작성 실패')
      console.error(error)
      return false
    }
  }

  const selectPosts = async () => {
    try {
      const { data } = await instance.get('/v1/article/select')
      console.log(data) // 서버 주소에 맞게 변경
      posts.value = data
    } catch (error) {
      console.error('전체 게시글 불러오기 실패', error)
    }
  }

  const getRecentPost = async () => {
    try {
      const { data } = await instance.get('/v1/article/recentPost')
      recentPosts.value = data

    } catch (error) {
      console.error('최근 게시글 불러오기 실패', error)
    }
  }

  const selectPostById = async (id) => {
    try {
      const { data } = await instance.get(`/v1/article/${id}`)
      // console.log(data)
      return data
    } catch (err) {
      console.error('게시글 조회 실패', err)
      return null
    }
  }

  const updatePost = async (id, updatedPost) => {
    try {
      console.log('업데이트할 게시글:', updatedPost, '게시글번호', id)
      await instance.put(`/v1/article/${id}`, updatedPost)
      alert('✅ 게시글이 수정되었습니다.')
      return true
    } catch (error) {
      alert('❌ 게시글 수정 실패')
      console.error(error)
      return false
    }
  }

  const deletePost = async (id) => {
    try {
      await instance.delete(`/v1/article/${id}/delete`)
      alert('삭제 완료!')
    } catch (err) {
      alert('삭제 실패')
      console.error(err)
    }
  }

  return {
    posts,
    recentPosts,
    createPost,
    selectPosts,
    selectPostById,
    deletePost,
    updatePost,
    getRecentPost,
  }
})
