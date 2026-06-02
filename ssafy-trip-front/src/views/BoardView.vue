<template>
  <v-container class="board-container">
    <div class="page-header">
      <h1 class="page-title">🌊 여행 게시판 🏖️</h1>
      <div class="wave-divider"></div>
    </div>

    <!-- 글쓰기 버튼 -->
    <div class="action-section">
      <v-btn
        class="write-btn"
        color="cyan"
        @click="goToWrite"
        rounded="xl"
        elevation="6"
        size="large"
      >
        ✏️ 새 글 작성하기
      </v-btn>
    </div>

    <!-- 게시글 목록 -->
    <v-card class="board-card" elevation="12" rounded="xl">
      <v-card-title class="board-card-title"> 📝 게시글 목록 </v-card-title>
      <v-table class="summer-table">
        <thead>
          <tr class="table-header">
            <th class="header-cell">번호</th>
            <th class="header-cell">제목</th>
            <th class="header-cell">작성자</th>
            <th class="header-cell">작성일</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(post, idx) in posts"
            :key="idx"
            :class="!post.isArticle ? 'notice-row' : 'normal-row'"
            @click="postdetail(post.no)"
            class="table-row"
          >
            <td class="table-cell">
              <v-chip
                v-if="!post.isArticle"
                class="notice-chip"
                color="gradient"
                label
                size="small"
              >
                🔥 공지
              </v-chip>
              <span v-else class="post-number">{{ post.no }}</span>
            </td>

            <td
              class="table-cell title-cell"
              :class="!post.isArticle ? 'notice-title' : ''"
            >
              {{ post.title }}
            </td>

            <td class="table-cell author-cell">👤 {{ post.name }}</td>
            <td class="table-cell date-cell">📅 {{ post.regDate }}</td>
          </tr>
        </tbody>
      </v-table>
    </v-card>
  </v-container>
</template>

<script setup>
  import { computed, ref, onMounted } from 'vue'
  import { usePostStore } from '@/store/post'
  import { useMemberStore } from '@/store/user'
  import { useRouter } from 'vue-router'

  const memberStore = useMemberStore()
  const postStore = usePostStore()
  const router = useRouter()
  const postdetail = (no) => {
    if (memberStore.isLoggedIn) {
      router.push({ name: 'PostDetail', params: { id: no } })
    } else {
      alert('로그인 후 이용하세요')
    }
  }
  onMounted(() => {
    postStore.selectPosts()
    console.log(posts.value)
  })
  const posts = computed(() => postStore.posts)

  const goToWrite = () => {
    if (!memberStore.isLoggedIn) {
      alert('로그인 후 글쓰기가 가능합니다.')
      return
    }
    router.push('/board/write')
  }
  const isArticle = computed(() => {
    return posts.value.filter((i) => i.isArticle)
  })
</script>

<style scoped>
  .board-container {
    background: linear-gradient(135deg, #e3f2fd 0%, #f1f8e9 100%);
    min-height: 100vh;
    padding: 40px 20px;
  }

  .page-header {
    text-align: center;
    margin-bottom: 40px;
  }

  .page-title {
    color: #0277bd;
    font-weight: 700;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
    margin-bottom: 20px;
  }

  .wave-divider {
    width: 120px;
    height: 4px;
    background: linear-gradient(90deg, #00bcd4, #009688, #4caf50);
    margin: 0 auto;
    border-radius: 2px;
  }

  .action-section {
    text-align: center;
    margin-bottom: 30px;
  }

  .write-btn {
    text-transform: none;
    font-weight: 600;
    padding: 12px 30px;
    transition: all 0.3s ease;
  }

  .write-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
  }

  .board-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.95),
      rgba(240, 248, 255, 0.95)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
  }

  .board-card-title {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-align: center;
    font-weight: 700;
    padding: 20px;
  }

  .summer-table {
    background: transparent;
  }

  .table-header {
    background: linear-gradient(135deg, #e0f2f1, #e8f5e8);
  }

  .header-cell {
    color: #00695c;
    font-weight: 700;
    text-align: center;
    padding: 20px 16px;
    border-bottom: 2px solid #00bcd4;
  }

  .table-row {
    transition: all 0.3s ease;
    cursor: pointer;
  }

  .table-row:hover {
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.1),
      rgba(76, 175, 80, 0.1)
    );
    transform: scale(1.02);
  }

  .table-cell {
    padding: 16px;
    text-align: center;
    border-bottom: 1px solid rgba(0, 188, 212, 0.2);
  }

  .notice-row {
    background: linear-gradient(
      135deg,
      rgba(255, 193, 7, 0.1),
      rgba(255, 152, 0, 0.1)
    );
  }

  .notice-row:hover {
    background: linear-gradient(
      135deg,
      rgba(255, 193, 7, 0.2),
      rgba(255, 152, 0, 0.2)
    );
  }

  .notice-chip {
    background: linear-gradient(135deg, #ff9800, #f57c00);
    color: white;
    font-weight: 700;
  }

  .notice-title {
    color: #e65100;
    font-weight: 700;
  }

  .post-number {
    color: #0277bd;
    font-weight: 600;
  }

  .title-cell {
    text-align: left;
    color: #01579b;
    font-weight: 600;
  }

  .author-cell {
    color: #00695c;
  }

  .date-cell {
    color: #0277bd;
  }

  .normal-row {
    background: rgba(255, 255, 255, 0.7);
  }
</style>
