<template>
  <v-container class="update-container">
    <div class="page-header">
      <h1 class="page-title">📝 게시글 수정 ✏️</h1>
      <div class="wave-divider"></div>
    </div>

    <v-card class="update-card" elevation="12" rounded="xl">
      <v-card-title class="card-header"> ✏️ 게시글 수정하기 </v-card-title>

      <v-card-text class="card-content">
        <v-text-field
          v-model="title"
          label="✨ 제목"
          placeholder="제목을 입력하세요"
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
          placeholder="내용을 입력하세요"
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
          ✅ 수정 완료하기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { usePostStore } from '@/store/post'
  import { useMemberStore } from '@/store/user'
  const title = ref('')
  const content = ref('')
  const route = useRoute()
  const router = useRouter()
  const postStore = usePostStore()
  const memberStore = useMemberStore()
  const post = ref({})
  const postId = route.params.id
  const isArticle = ref(1)
  const articleTypes = [
    { label: '🌊 일반 게시글', value: 1 },
    { label: '📌 공지글', value: 0 },
  ]
  onMounted(async () => {
    const data = await postStore.selectPostById(postId)
    if (data) {
      title.value = data.data.title
      content.value = data.data.content
      isArticle.value = data.data.isArticle
    }
  })

  const submitPost = async () => {
    if (!title.value.trim() || !content.value.trim()) {
      alert('제목과 내용을 모두 입력해주세요.')
      return
    }

    const updatedPost = {
      title: title.value,
      content: content.value,
      isArticle: isArticle.value,
    }

    const success = await postStore.updatePost(postId, updatedPost)
    if (success) {
      router.push(`/board/${postId}`)
    }
  }
</script>

<style scoped>
  .update-container {
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

  .update-card {
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
