<template>
  <div class="register-container">
    <v-container class="fill-height">
      <v-row justify="center" align="center">
        <v-col cols="12" sm="8" md="6" lg="5">
          <v-card class="register-card" elevation="12">
            <!-- Header -->
            <div class="register-header">
              <v-icon size="48" color="primary" class="mb-4">mdi-account-plus</v-icon>
              <h2 class="text-h4 font-weight-bold mb-2">회원가입</h2>
              <p class="text-body-1 text-medium-emphasis">SSAFY Trip과 함께 여행을 시작하세요</p>
            </div>

            <v-card-text class="pa-8">
              <v-form @submit.prevent="submit" ref="form">
                <v-text-field
                  v-model="name"
                  label="이름"
                  prepend-inner-icon="mdi-account"
                  variant="outlined"
                  class="mb-4"
                  :rules="nameRules"
                  required
                />

                <v-text-field
                  v-model="email"
                  label="이메일"
                  prepend-inner-icon="mdi-email"
                  variant="outlined"
                  class="mb-4"
                  :rules="emailRules"
                  required
                />

                <v-text-field
                  v-model="password"
                  label="비밀번호"
                  prepend-inner-icon="mdi-lock"
                  :append-inner-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showPassword ? 'text' : 'password'"
                  variant="outlined"
                  class="mb-4"
                  :rules="passwordRules"
                  @click:append-inner="showPassword = !showPassword"
                  required
                />

                <v-text-field
                  v-model="confirm"
                  label="비밀번호 확인"
                  prepend-inner-icon="mdi-lock-check"
                  :append-inner-icon="showConfirm ? 'mdi-eye' : 'mdi-eye-off'"
                  :type="showConfirm ? 'text' : 'password'"
                  variant="outlined"
                  class="mb-6"
                  :rules="confirmRules"
                  @click:append-inner="showConfirm = !showConfirm"
                  required
                />

                <!-- Terms Agreement -->
                <v-checkbox
                  v-model="agreeTerms"
                  class="mb-4"
                  :rules="[v => !!v || '이용약관에 동의해주세요']"
                >
                  <template v-slot:label>
                    <span class="text-body-2">
                      <a href="#" class="text-primary">이용약관</a> 및
                      <a href="#" class="text-primary">개인정보처리방침</a>에 동의합니다
                    </span>
                  </template>
                </v-checkbox>

                <v-btn
                  type="submit"
                  color="primary"
                  size="large"
                  block
                  class="mb-4"
                  :loading="loading"
                >
                  회원가입
                </v-btn>

                <div class="text-center">
                  <v-btn
                    variant="text"
                    color="primary"
                    to="/login"
                  >
                    이미 계정이 있으신가요? 로그인
                  </v-btn>
                </div>
              </v-form>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup>
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { useMemberStore } from '@/store/user'

  const router = useRouter()
  const memberStore = useMemberStore()

  const name = ref('')
  const email = ref('')
  const password = ref('')
  const confirm = ref('')
  const showPassword = ref(false)
  const showConfirm = ref(false)
  const agreeTerms = ref(false)
  const loading = ref(false)
  const form = ref(null)

  const nameRules = [
    v => !!v || '이름을 입력해주세요',
    v => (v && v.length >= 2) || '이름은 최소 2자 이상이어야 합니다',
  ]

  const emailRules = [
    v => !!v || '이메일을 입력해주세요',
    v => /.+@.+\..+/.test(v) || '올바른 이메일 형식이 아닙니다',
  ]

  const passwordRules = [
    v => !!v || '비밀번호를 입력해주세요',
    v => (v && v.length >= 8) || '비밀번호는 최소 8자 이상이어야 합니다',
    v => /(?=.*[a-z])(?=.*[A-Z])(?=.*\d)/.test(v) || '영문 대소문자, 숫자를 포함해야 합니다',
  ]

  const confirmRules = [
    v => !!v || '비밀번호 확인을 입력해주세요',
    v => v === password.value || '비밀번호가 일치하지 않습니다',
  ]

  const submit = async () => {
    const { valid } = await form.value.validate()
    if (!valid) return

    if (password.value !== confirm.value) {
      alert('비밀번호가 일치하지 않습니다.')
      return
    }

    loading.value = true
    try {
      await memberStore.register({
        name: name.value,
        email: email.value,
        password: password.value,
      })
      router.push('/login')
    } catch (error) {
      alert('회원가입에 실패했습니다.')
    } finally {
      loading.value = false
    }
  }
</script>

<style scoped>
.register-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.register-card {
  border-radius: 16px;
  overflow: hidden;
}

.register-header {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  text-align: center;
  padding: 32px 32px 24px;
}
</style>
