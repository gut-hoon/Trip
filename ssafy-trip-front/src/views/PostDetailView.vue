<template>
  <v-container class="detail-container">
    <v-row justify="center">
      <v-col cols="12" md="10" lg="8">
        <v-btn
          @click="$router.push('/board')"
          color="cyan"
          class="back-btn"
          rounded="xl"
          elevation="4"
        >
          🔙 목록으로 돌아가기
        </v-btn>

        <v-card class="post-detail-card" elevation="12" rounded="xl">
          <!-- 헤더 섹션 -->
          <div class="post-header">
            <h1 class="post-title">{{ post.title }}</h1>
            <div class="post-meta">
              <span class="meta-item">✍️ {{ post.author }}</span>
              <span class="meta-item">🕒 {{ post.date }}</span>
            </div>
          </div>

          <!-- 내용 섹션 -->
          <v-card-text class="post-content">
            <div class="content-text">{{ post.content }}</div>
          </v-card-text>

          <!-- 액션 버튼 섹션 -->
          <v-card-actions class="post-actions">
            <v-btn
              color="cyan"
              @click="$router.push('/board')"
              rounded="xl"
              elevation="4"
              class="action-btn"
            >
              🔙 목록으로
            </v-btn>
            <v-spacer />
            <v-btn
              v-if="isAuthor"
              color="teal"
              @click="updatePost"
              rounded="xl"
              elevation="4"
              class="action-btn"
            >
              ✏️ 수정
            </v-btn>
            <v-btn
              v-if="isAuthor"
              color="red"
              @click="deletePost"
              rounded="xl"
              elevation="4"
              class="action-btn"
            >
              🗑️ 삭제
            </v-btn>
          </v-card-actions>
        </v-card>

        <!-- 댓글 섹션 -->
        <CommentList />
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
  import { ref, onMounted, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { usePostStore } from '@/store/post'
  import { useMemberStore } from '@/store/user'
  import CommentList from '@/views/CommentListView.vue'

  // 라우트 파라미터에서 게시글 ID를 가져옴
  const route = useRoute()
  const router = useRouter()
  const postId = route.params.id
  const postStore = usePostStore()
  const memberStore = useMemberStore()

  // 게시글 정보
  const post = ref({
    id: postId,
    title: '',
    author: '',
    date: '',
    content: '',
  })

  // 마운트 시 게시글 ID에 맞는 데이터 로드
  onMounted(async () => {
    const result = await postStore.selectPostById(postId)
    if (result) {
      post.value = {
        title: result.data.title,
        content: result.data.content,
        date: result.data.regDate?.split('T')[0] ?? '', // ISO 형식 자르기
        author: result.data.name ?? '알 수 없음', // 필요시 user 객체로 바꾸기
        usersNo: result.data.usersNo,
      }
    }
  })

  const isAuthor = computed(() => {
    if (memberStore.isLoggedIn) {
      return (
        memberStore.loginUser.user.no === post.value.usersNo ||
        memberStore.loginUser.user.role == 'ADMIN'
      )
    } else {
      return
    }
  })

  const updatePost = () => {
    router.push(`/board/update/${postId}`)
  }
  const deletePost = async () => {
    const ok = confirm('정말 삭제하시겠습니까?')
    if (ok) {
      await postStore.deletePost(postId)
      router.push('/board')
    }
  }
</script>

<style scoped>
  .detail-container {
    background: linear-gradient(135deg, #e3f2fd 0%, #f1f8e9 100%);
    min-height: 100vh;
    padding: 40px 20px;
  }

  .back-btn {
    margin-bottom: 30px;
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .back-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
  }

  .post-detail-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.95),
      rgba(240, 248, 255, 0.95)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    margin-bottom: 30px;
  }

  .post-header {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    padding: 40px;
    text-align: center;
  }

  .post-title {
    font-size: 2rem;
    font-weight: 700;
    margin-bottom: 20px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
    line-height: 1.3;
  }

  .post-meta {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 30px;
    flex-wrap: wrap;
  }

  .meta-item {
    font-size: 1.1rem;
    opacity: 0.9;
  }

  .post-content {
    padding: 40px;
  }

  .content-text {
    color: #01579b;
    font-size: 1.1rem;
    line-height: 1.8;
    white-space: pre-wrap;
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.05),
      rgba(0, 150, 136, 0.05)
    );
    padding: 30px;
    border-radius: 15px;
    border: 1px solid rgba(0, 188, 212, 0.2);
  }

  .post-actions {
    padding: 30px 40px 40px;
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.05),
      rgba(0, 150, 136, 0.05)
    );
  }

  .action-btn {
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .action-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 0, 0, 0.2);
  }

  @media (max-width: 768px) {
    .post-title {
      font-size: 1.5rem;
    }

    .post-meta {
      flex-direction: column;
      gap: 10px;
    }

    .post-content,
    .post-actions {
      padding: 20px;
    }

    .content-text {
      padding: 20px;
    }
  }
</style>
