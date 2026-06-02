<template>
  <v-app>
    <v-main>
      <v-container class="planner-container" fluid>
        <!-- Page Header -->
        <div class="text-center mb-8">
          <v-row justify="center" class="mb-4">
            <v-col cols="auto">
              <v-icon size="48" color="cyan-darken-2" class="me-3">
                mdi-map-marker-star
              </v-icon>
              <span class="page-title">AI 장소 추천 서비스</span>
              <v-icon size="48" color="blue-darken-2" class="ms-3">
                mdi-airplane
              </v-icon>
            </v-col>
          </v-row>

          <v-divider
            class="mx-auto mb-4"
            :thickness="4"
            color="cyan-accent-3"
            :length="100"
          ></v-divider>

          <p
            class="page-subtitle text-h6 text-grey-darken-1 font-weight-medium"
          >
            인공지능에게 최적의 여행 장소를 추천받아 보세요!
          </p>
        </div>

        <v-row justify="center">
          <v-col cols="12" lg="10" xl="8">
            <!-- AI Response Card -->
            <v-card
              class="response-card mb-8"
              elevation="12"
              rounded="xl"
              :loading="isLoading"
            >
              <v-card-title class="response-title pa-6">
                <v-icon size="28" color="white" class="me-3">
                  mdi-robot-excited
                </v-icon>
                <span class="text-h5 font-weight-bold">AI 추천 결과</span>
              </v-card-title>

              <v-card-text class="response-content pa-6">
                <div v-if="chatMessages.trim()" class="ai-response">
                  <div class="simple-text-container">
                    <pre class="response-text">{{ chatMessages }}</pre>
                  </div>
                </div>

                <div v-else class="empty-state text-center py-8">
                  <v-icon size="64" color="grey-lighten-1" class="mb-4">
                    mdi-compass-outline
                  </v-icon>
                  <p class="text-h6 text-grey-darken-1 mb-2">
                    AI에게 질문하고 결과를 받아보세요!
                  </p>
                  <p class="text-body-2 text-grey">
                    여행지 정보를 입력하고 검색 버튼을 눌러주세요
                  </p>
                </div>
              </v-card-text>
            </v-card>

            <!-- Search Filter Card -->
            <v-card class="filter-card" elevation="12" rounded="xl">
              <v-card-title class="filter-title pa-6">
                <v-icon size="28" color="white" class="me-3">
                  mdi-magnify
                </v-icon>
                <span class="text-h5 font-weight-bold">여행지 검색</span>
              </v-card-title>

              <v-card-text class="filter-content pa-6">
                <v-row>
                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="place"
                      label="관광지"
                      placeholder="예: 숭례문, 불국사, 홍대"
                      variant="outlined"
                      color="cyan-darken-2"
                      rounded="lg"
                      class="input-field"
                      :rules="[rules.required]"
                      prepend-inner-icon="mdi-map-marker"
                      clearable
                    />
                  </v-col>

                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="numPlaces"
                      label="추천지 개수"
                      type="number"
                      placeholder="예: 5"
                      variant="outlined"
                      color="cyan-darken-2"
                      rounded="lg"
                      class="input-field"
                      prepend-inner-icon="mdi-numeric"
                      clearable
                      :min="1"
                      :max="5"
                    />
                  </v-col>

                  <v-col cols="12" md="6">
                    <v-text-field
                      v-model="price"
                      label="예상비용 (원)"
                      type="number"
                      placeholder="예: 100,000"
                      variant="outlined"
                      color="cyan-darken-2"
                      rounded="lg"
                      class="input-field"
                      prepend-inner-icon="mdi-currency-krw"
                      clearable
                    />
                  </v-col>

                  <v-col cols="12" md="6">
                    <v-select
                      v-model="selectedCategory"
                      :items="categoryList"
                      label="관광지 분류"
                      variant="outlined"
                      color="cyan-darken-2"
                      rounded="lg"
                      class="filter-select"
                      prepend-inner-icon="mdi-tag"
                      clearable
                    />
                  </v-col>
                </v-row>

                <v-btn
                  class="search-btn mt-4"
                  color="cyan-darken-2"
                  @click="searchPlaces"
                  :loading="isLoading"
                  rounded="xl"
                  elevation="8"
                  size="x-large"
                  block
                  variant="elevated"
                >
                  <template v-slot:prepend>
                    <v-icon size="24">mdi-magnify</v-icon>
                  </template>

                  <span class="text-h6 font-weight-bold">
                    {{ isLoading ? 'AI가 분석 중입니다...' : '장소 검색하기' }}
                  </span>

                  <template v-slot:append>
                    <v-icon size="24">mdi-sparkles</v-icon>
                  </template>
                </v-btn>
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup>
  import { ref } from 'vue'
  import { instance } from '@/axios/index'

  const categoryList = [
    { title: '🏛️ 관광명소', value: '관광명소' },
    { title: '🏨 숙박', value: '숙박' },
    { title: '🍽️ 음식점', value: '음식점' },
  ]

  const place = ref('')
  const numPlaces = ref('')
  const price = ref('')
  const selectedCategory = ref('')
  const chatMessages = ref('')
  const isLoading = ref(false)

  const rules = {
    required: (value) => !!value || '필수 입력 항목입니다.',
  }

  const searchPlaces = async () => {
    if (!place.value.trim()) {
      chatMessages.value = '여행지를 입력해주세요.'
      return
    }

    isLoading.value = true

    const prompt = `
    (여행지: ${place.value},
    예상비용: ${price.value}원,
    추천 장소 수: ${numPlaces.value}개,
    분류: ${selectedCategory.value})
  `

    console.log(prompt)

    try {
      // 실제 API 호출
      const { data } = await instance.post('/v1/ai', { prompt })

      // 2초 대기 (시뮬레이션)
      await new Promise((resolve) => setTimeout(resolve, 2000))

      // AI 응답을 그대로 표시
      chatMessages.value = data.response || '추천 결과가 없습니다.'
    } catch (err) {
      console.error('AI 요청 실패:', err)
      chatMessages.value = 'AI 요청에 실패했습니다. 다시 시도해주세요.'
    } finally {
      isLoading.value = false
    }
  }
</script>

<style scoped>
  .planner-container {
    background: linear-gradient(135deg, #e3f2fd 0%, #ffffff 50%, #e0f7fa 100%);
    min-height: 100vh;
    padding: 2rem 1rem;
  }

  .page-title {
    font-size: 2.5rem;
    font-weight: 800;
    background: linear-gradient(45deg, #00acc1, #1976d2);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
    background-clip: text;
    display: inline-block;
  }

  .page-subtitle {
    max-width: 600px;
    margin: 0 auto;
  }

  .response-card {
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.2);
  }

  .response-title {
    background: linear-gradient(135deg, #00acc1, #0288d1);
    color: white;
    border-radius: 12px 12px 0 0;
  }

  .filter-card {
    background: rgba(255, 255, 255, 0.95);
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.2);
  }

  .filter-title {
    background: linear-gradient(135deg, #1976d2, #00acc1);
    color: white;
    border-radius: 12px 12px 0 0;
  }

  /* 단순한 텍스트 표시 스타일 */
  .ai-response {
    font-family: 'Noto Sans KR', sans-serif;
  }

  .simple-text-container {
    background: #f8f9fa;
    border-radius: 12px;
    padding: 1.5rem;
    border: 1px solid #e9ecef;
    box-shadow: inset 0 2px 4px rgba(0, 0, 0, 0.05);
  }

  .response-text {
    font-family: 'Noto Sans KR', -apple-system, BlinkMacSystemFont, 'Segoe UI',
      Roboto, sans-serif;
    font-size: 1rem;
    line-height: 1.6;
    color: #2c3e50;
    margin: 0;
    white-space: pre-wrap;
    word-wrap: break-word;
    background: transparent;
    border: none;
    padding: 0;
  }

  .input-field :deep(.v-field) {
    border-radius: 12px;
    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.1);
  }

  .input-field :deep(.v-field--focused) {
    box-shadow: 0 4px 16px rgba(0, 172, 193, 0.3);
  }

  .search-btn {
    background: linear-gradient(135deg, #00acc1, #1976d2) !important;
    color: white;
    text-transform: none;
    letter-spacing: 0.5px;
    transition: all 0.3s ease;
  }

  .search-btn:hover {
    transform: translateY(-2px);
    box-shadow: 0 8px 25px rgba(0, 172, 193, 0.4);
  }

  .empty-state {
    padding: 3rem 1rem;
  }

  /* 반응형 디자인 */
  @media (max-width: 960px) {
    .page-title {
      font-size: 2rem;
    }

    .planner-container {
      padding: 1rem 0.5rem;
    }

    .simple-text-container {
      padding: 1rem;
    }

    .response-text {
      font-size: 0.95rem;
    }
  }

  @media (max-width: 600px) {
    .page-title {
      font-size: 1.75rem;
    }

    .simple-text-container {
      padding: 0.75rem;
    }

    .response-text {
      font-size: 0.9rem;
      line-height: 1.5;
    }
  }

  /* 애니메이션 */
  .response-card,
  .filter-card {
    animation: slideUp 0.6s ease-out;
  }

  @keyframes slideUp {
    from {
      opacity: 0;
      transform: translateY(30px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  /* 로딩 상태 스타일 */
  .v-card--loading {
    pointer-events: none;
  }

  .v-card--loading .v-card-text {
    position: relative;
  }

  .v-card--loading .v-card-text::before {
    content: '';
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: rgba(255, 255, 255, 0.8);
    z-index: 1;
  }
</style>
