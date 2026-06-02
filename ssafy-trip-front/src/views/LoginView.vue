<template>
  <div class="login-container">
    <v-container class="fill-height">
      <v-row justify="center" align="center">
        <v-col cols="12" sm="8" md="6" lg="4">
          <v-card class="login-card" elevation="8">
            <!-- Header -->
            <div class="login-header">
              <v-icon size="48" color="primary" class="mb-4">mdi-airplane</v-icon>
              <h2 class="text-h4 font-weight-bold mb-2">로그인</h2>
              <p class="text-body-1 text-medium-emphasis">SSAFY Trip에 오신 것을 환영합니다</p>
            </div>

            <v-card-text class="pa-8">
              <v-form @submit.prevent="submit" ref="form">
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
                  class="mb-6"
                  :rules="passwordRules"
                  @click:append-inner="showPassword = !showPassword"
                  required
                />

                <v-btn
                  type="submit"
                  color="primary"
                  size="large"
                  block
                  class="mb-4"
                  :loading="loading"
                >
                  로그인
                </v-btn>

                <div class="text-center">
                  <v-btn
                    variant="text"
                    color="primary"
                    to="/register"
                  >
                    계정이 없으신가요? 회원가입
                  </v-btn>
                </div>
              </v-form>
            </v-card-text>

            <!-- Social Login -->
            <v-divider class="mx-8"></v-divider>
            <v-card-text class="pa-8 pt-4">
              <p class="text-center text-caption text-medium-emphasis mb-4">
                또는 소셜 계정으로 로그인
              </p>
              <v-row>
                <v-col cols="6">
                  <v-btn
                    variant="outlined"
                    block
                    prepend-icon="mdi-google"
                    @click="socialLogin('google')"
                  >
                    Google
                  </v-btn>
                </v-col>
                <v-col cols="6">
                  <v-btn
                    variant="outlined"
                    block
                    prepend-icon="mdi-github"
                    @click="socialLogin('github')"
                  >
                    GitHub
                  </v-btn>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useMemberStore } from '@/store/user'

const memberStore = useMemberStore()

const email = ref('')
const password = ref('')
const showPassword = ref(false)
const loading = ref(false)
const form = ref(null)

const emailRules = [
  v => !!v || '이메일을 입력해주세요',
  v => /.+@.+\..+/.test(v) || '올바른 이메일 형식이 아닙니다',
]

const passwordRules = [
  v => !!v || '비밀번호를 입력해주세요',
  v => (v && v.length >= 6) || '비밀번호는 최소 6자 이상이어야 합니다',
]

const submit = async () => {
  const { valid } = await form.value.validate()
  if (!valid) return

  loading.value = true
  try {
    await memberStore.login({
      email: email.value,
      password: password.value
    })
  } finally {
    loading.value = false
  }
}

const socialLogin = (provider) => {
  console.log(`${provider} 로그인`)
  // 소셜 로그인 로직 구현
}
</script>

<style scoped>
.login-container {
  min-height: 100vh;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.login-card {
  border-radius: 16px;
  overflow: hidden;
}

.login-header {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
  text-align: center;
  padding: 32px 32px 24px;
}
</style>
