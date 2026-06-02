<template>
  <v-container class="update-user-container d-flex justify-center align-center">
    <v-card class="update-user-card" width="500" elevation="12" rounded="xl">
      <div class="card-header">
        <h2 class="update-title">👤 회원정보 수정 ⚙️</h2>
        <p class="update-subtitle">프로필 정보를 업데이트하세요!</p>
      </div>

      <v-card-text class="card-content">
        <v-form @submit.prevent="updateProfile">
          <v-text-field
            v-model="name"
            label="👤 이름"
            prepend-inner-icon="mdi-account"
            readonly
            variant="outlined"
            color="cyan"
            class="input-field readonly-field"
            rounded="lg"
          />
          <v-text-field
            v-model="email"
            label="📧 이메일"
            prepend-inner-icon="mdi-email"
            readonly
            variant="outlined"
            color="cyan"
            class="input-field readonly-field"
            rounded="lg"
          />
          <v-text-field
            v-model="password"
            label="🔒 새 비밀번호"
            prepend-inner-icon="mdi-lock"
            type="password"
            autocomplete="new-password"
            hint="비밀번호 변경 시에만 입력하세요"
            persistent-hint
            variant="outlined"
            color="cyan"
            class="input-field"
            rounded="lg"
          />
          <v-text-field
            v-model="confirm"
            label="🔐 비밀번호 확인"
            prepend-inner-icon="mdi-lock-check"
            type="password"
            autocomplete="new-password"
            variant="outlined"
            color="cyan"
            class="input-field"
            rounded="lg"
          />
          <v-btn
            block
            color="gradient"
            type="submit"
            class="update-btn"
            rounded="xl"
            elevation="6"
            size="large"
          >
            💾 정보 저장하기
          </v-btn>
        </v-form>
      </v-card-text>

      <v-card-actions class="justify-center card-actions">
        <v-btn
          variant="text"
          to="/"
          class="home-link"
          color="cyan"
        >
          🏠 홈으로 돌아가기
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { useMemberStore } from '@/store/user'
  const router = useRouter()
  const memberStore = useMemberStore()
  const name = ref(memberStore.loginUser.user.name)
  const email = ref(memberStore.loginUser.user.email)
  console.log(email)
  const password = ref('')
  const confirm = ref('')

  function updateProfile() {
    if (password.value || confirm.value) {
      // 둘 중 하나라도 입력하면 비밀번호 일치 체크
      if (password.value !== confirm.value) {
        alert('비밀번호가 일치하지 않습니다.')
        return
      } else {
        memberStore.updatePassword({
          name: name.value,
          email: email.value,
          newPassword: password.value,
        })
      }
      // TODO: 비밀번호 변경 API 호출 시 비밀번호도 포함
      // memberStore.updateProfile()
    }

    // TODO: API 연동하여 name, email, (password) 업데이트
    console.log(
      '수정된 정보:',
      password.value ? '비밀번호 변경됨' : '비밀번호 변경 안함'
    )
    router.push('/') // 필요 시 페이지 이동
  }
</script>

<style scoped>
  .update-user-container {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
    min-height: 100vh;
    position: relative;
    overflow: hidden;
  }

  .update-user-container::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 100" fill="rgba(255,255,255,0.1)"><path d="M0,0v46.29c47.79,22.2,103.59,32.17,158,28,70.36-5.37,136.33-33.31,206.8-37.5C438.64,32.43,512.34,53.67,583,72.05c69.27,18,138.3,24.88,209.4,13.08,36.15-6,69.85-17.84,104.45-29.34C989.49,25,1047.71,5.4,1100,16.43V0Z"/></svg>') repeat-x;
    background-size: 1000px 100px;
    animation: wave 20s linear infinite;
  }

  @keyframes wave {
    0% { background-position-x: 0; }
    100% { background-position-x: 1000px; }
  }

  .update-user-card {
    background: linear-gradient(135deg, rgba(255,255,255,0.95), rgba(240,248,255,0.95));
    backdrop-filter: blur(15px);
    border: 1px solid rgba(255,255,255,0.3);
    position: relative;
    z-index: 1;
  }

  .card-header {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-align: center;
    padding: 30px 20px;
    border-radius: 20px 20px 0 0;
  }

  .update-title {
    font-weight: 700;
    margin-bottom: 10px;
    text-shadow: 1px 1px 2px rgba(0,0,0,0.2);
  }

  .update-subtitle {
    opacity: 0.9;
    font-size: 1rem;
  }

  .card-content {
    padding: 40px 30px 20px;
  }

  .input-field {
    margin-bottom: 25px;
  }

  .input-field :deep(.v-field) {
    background: linear-gradient(135deg, rgba(255,255,255,0.9), rgba(240,248,255,0.9));
    border-radius: 15px;
    box-shadow: 0 4px 15px rgba(0,188,212,0.1);
  }

  .input-field :deep(.v-field:hover) {
    box-shadow: 0 6px 20px rgba(0,188,212,0.2);
  }

  .readonly-field :deep(.v-field) {
    background: linear-gradient(135deg, rgba(240,240,240,0.9), rgba(250,250,250,0.9));
    opacity: 0.8;
  }

  .update-btn {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-transform: none;
    font-weight: 700;
    font-size: 1.1rem;
    padding: 15px 0;
    margin-top: 20px;
    transition: all 0.3s ease;
  }

  .update-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0,188,212,0.3);
    background: linear-gradient(135deg, #0097a7, #00695c);
  }

  .card-actions {
    padding: 20px 30px 30px;
  }

  .home-link {
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .home-link:hover {
    transform: scale(1.05);
  }
</style>
