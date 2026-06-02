<template>
  <v-container class="create-container">
    <div class="page-header">
      <h1 class="page-title">✏️ 새로운 여행 이야기 작성 🌴</h1>
      <div class="wave-divider"></div>
    </div>

    <v-card class="create-card" elevation="12" rounded="xl">
      <v-card-title class="card-header"> 📝 게시글 작성 </v-card-title>

      <v-card-text class="card-content">
        <v-text-field
          v-model="title"
          label="✨ 제목"
          placeholder="멋진 여행 제목을 입력하세요"
          required
          variant="outlined"
          color="cyan"
          class="input-field"
          rounded="lg"
        />

        <v-select
          v-if="memberStore.loginUser.user.role === 'ADMIN'"
          v-model="isArticle"
          item-title="label"
          item-value="value"
          :items="articleTypes"
          label="📌 게시글 유형"
          placeholder="게시글 유형을 선택하세요"
          required
          variant="outlined"
          color="cyan"
          class="input-field"
          rounded="lg"
        />

        <v-textarea
          v-model="content"
          auto-grow
          label="📖 내용"
          placeholder="여행의 추억을 자세히 들려주세요..."
          required
          rows="12"
          variant="outlined"
          color="cyan"
          class="input-field content-area"
          rounded="lg"
        />
      </v-card-text>

      <v-card-actions class="card-actions">
        <v-btn
          class="submit-btn"
          color="gradient"
          @click="submitPost"
          rounded="xl"
          elevation="6"
          size="large"
          block
        >
          🚀 작성 완료하기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { usePostStore } from '@/store/post'
  import { useMemberStore } from '@/store/user'
  const title = ref('')
  const content = ref('')
  const router = useRouter()
  const postStore = usePostStore()
  const memberStore = useMemberStore()
  const post = ref({})
  const isArticle = ref(1)

  const articleTypes = [
    { label: '🌊 일반 게시글', value: 1 },
    { label: '📌 공지글', value: 0 },
  ]

  const submitPost = async () => {
    if (!title.value.trim() || !content.value.trim()) {
      alert('제목과 내용을 모두 입력해주세요.')
      return
    }

    post.value = {
      title: title.value,
      usersNo: memberStore.loginUser.user.no,
      content: content.value,
      regDate: new Date().toISOString(),
      viewCnt: 0,
      isArticle: isArticle.value,
    }
    console.log(post.value)

    const success = await postStore.createPost(post.value)
    if (success) {
      router.push('/board')
    }
  }
</script>

<style scoped>
  .create-container {
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

  .create-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.95),
      rgba(240, 248, 255, 0.95)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    max-width: 800px;
    margin: 0 auto;
  }

  .card-header {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-align: center;
    font-weight: 700;
    padding: 25px;
    font-size: 1.5rem;
  }

  .card-content {
    padding: 40px;
  }

  .input-field {
    margin-bottom: 25px;
  }

  .input-field :deep(.v-field) {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(240, 248, 255, 0.9)
    );
    border-radius: 15px;
    box-shadow: 0 4px 15px rgba(0, 188, 212, 0.1);
  }

  .input-field :deep(.v-field:hover) {
    box-shadow: 0 6px 20px rgba(0, 188, 212, 0.2);
  }

  .content-area :deep(.v-field) {
    min-height: 200px;
  }

  .card-actions {
    padding: 30px 40px 40px;
  }

  .submit-btn {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-transform: none;
    font-weight: 700;
    font-size: 1.1rem;
    padding: 15px 0;
    transition: all 0.3s ease;
  }

  .submit-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
    background: linear-gradient(135deg, #0097a7, #00695c);
  }
</style>
