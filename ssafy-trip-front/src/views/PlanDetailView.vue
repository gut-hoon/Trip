<template>
  <v-container class="plan-detail-container">
    <div class="action-buttons">
      <v-btn
        @click="$router.back()"
        color="cyan"
        class="action-btn"
        rounded="xl"
        elevation="4"
      >
        🔙 목록으로
      </v-btn>
      <v-btn
        v-if="isAuthor"
        color="teal"
        @click="$router.push(`/plan/update/${id}`)"
        class="action-btn"
        rounded="xl"
        elevation="4"
      >
        ✏️ 수정하기
      </v-btn>
      <v-btn
        v-if="isAuthor"
        color="teal"
        @click="deletePlan(id)"
        class="action-btn"
        rounded="xl"
        elevation="4"
      >
        ✏️ 삭제하기
      </v-btn>
    </div>

    <v-card v-if="trip" class="plan-card" elevation="12" rounded="xl">
      <!-- 헤더 섹션 -->
      <div class="plan-header">
        <h1 class="plan-title">{{ trip.title }}</h1>
        <div class="plan-meta">
          <span class="meta-item">👤 {{ trip.userName }}</span>
          <span class="meta-item">📅 {{ trip.startDate }}</span
          >~
          <span class="meta-item">📅 {{ trip.endDate }}</span>
          <v-chip
            :color="trip.share ? 'green' : 'grey'"
            class="share-chip"
            rounded="xl"
          >
            {{ trip.share ? '🌍 공유됨' : '🔒 비공개' }}
          </v-chip>
        </div>
      </div>

      <v-card-text class="plan-content">
        <!-- 기본 정보 섹션 -->
        <v-card class="info-section" elevation="4" rounded="lg">
          <v-card-title class="section-title"> 📖 여행 정보 </v-card-title>
          <v-card-text class="section-content">
            <div class="info-item">
              <h4 class="info-label">✨ 여행 설명</h4>
              <p class="info-value">{{ trip.desc }}</p>
            </div>
            <div class="info-item">
              <h4 class="info-label">💰 예상 비용</h4>
              <p class="info-value price">
                {{ trip.expense?.toLocaleString() }} 원
              </p>
            </div>
          </v-card-text>
        </v-card>

        <!-- 방문 장소 섹션 -->
        <v-card class="places-section" elevation="4" rounded="lg">
          <v-card-title class="section-title"> 📍 방문 장소 </v-card-title>
          <v-card-text class="section-content">
            <div class="places-list">
              <v-card
                v-for="(place, idx) in places"
                :key="idx"
                class="place-item"
                elevation="2"
                rounded="lg"
              >
                <v-card-text>
                  <div class="place-info">
                    <div class="place-number">{{ idx + 1 }}</div>
                    <div class="place-details">
                      <h4 class="place-name">{{ place.placeName }}</h4>
                      <p class="place-address">📍 {{ place.placeAddress }}</p>
                      <a
                        :href="place.placeUrl"
                        target="_blank"
                        rel="noopener noreferrer"
                      >
                        🔗 링크 열기
                      </a>
                    </div>
                  </div>
                </v-card-text>
              </v-card>
            </div>
          </v-card-text>
        </v-card>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
  import { onMounted, ref, computed } from 'vue'
  import { useRoute, useRouter } from 'vue-router'
  import { useMemberStore } from '@/store/user'
  import { usePlanStore } from '@/store/plan'

  const dummyTripPlans = [
    {
      plan_id: 1,
      title: '2025 제주 여름 여행',
      author: '김싸피',
      registTime: '2025.05.15',
      shared: true,
      content: '제주도 해변과 맛집을 중심으로 계획했어요!',
      price: 450000,
      places: [
        {
          place_name: '협재 해수욕장',
          address_name: '제주특별자치도 제주시 한림읍 협재리',
        },
        {
          place_name: '오설록 티 뮤지엄',
          address_name: '제주특별자치도 서귀포시 안덕면',
        },
      ],
    },
    {
      plan_id: 2,
      title: '서울 당일치기',
      author: '이싸피',
      registTime: '2025.05.10',
      shared: false,
      content: '서울 주요 관광지와 맛집 방문!',
      price: 120000,
      places: [
        {
          place_name: '경복궁',
          address_name: '서울 종로구 사직로 161',
        },
        {
          place_name: '광장시장',
          address_name: '서울 종로구 창경궁로 88',
        },
      ],
    },
  ]

  // 라우터 파라미터로 받은 id
  const route = useRoute()
  const router = useRouter()
  const id = Number(route.params.id)
  const trip = ref(null)
  const places = ref(null)
  const memberStore = useMemberStore()
  const planStore = usePlanStore()

  const isAuthor = computed(() => {
    return (
      trip.value?.usersNo === memberStore.loginUser.user.no ||
      memberStore.loginUser.user.role === 'ADMIN'
    )
  })

  // 마운트 시 해당 여행계획을 가져옴
  onMounted(async () => {
    const data = await planStore.selectPlanByNo(id)
    const detail = await planStore.getPlanDetails(id)
    if (data) {
      trip.value = data
    } else {
      console.warn('여행 계획 불러오기 실패')
    }
    if (detail) {
      places.value = detail
    } else {
      console.warn('장소 불러오기 실패')
    }
  })

  const deletePlan = async () => {
    const ok = confirm('정말 삭제하시겠습니까?')
    if (!ok) return

    const success = await planStore.deletePlan(id) // id는 route에서 받은 숫자
    if (success) {
      router.push('/plan')
    }
  }
</script>

<style scoped>
  .plan-detail-container {
    background: linear-gradient(135deg, #e3f2fd 0%, #f1f8e9 100%);
    min-height: 100vh;
    padding: 40px 20px;
  }

  .action-buttons {
    display: flex;
    gap: 15px;
    margin-bottom: 30px;
    flex-wrap: wrap;
  }

  .action-btn {
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .action-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
  }

  .plan-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.95),
      rgba(240, 248, 255, 0.95)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    max-width: 900px;
    margin: 0 auto;
  }

  .plan-header {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    padding: 40px;
    text-align: center;
  }

  .plan-title {
    font-size: 2.2rem;
    font-weight: 700;
    margin-bottom: 20px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);
  }

  .plan-meta {
    display: flex;
    justify-content: center;
    align-items: center;
    gap: 20px;
    flex-wrap: wrap;
  }

  .meta-item {
    font-size: 1.1rem;
    opacity: 0.9;
  }

  .share-chip {
    font-weight: 600;
  }

  .plan-content {
    padding: 40px;
  }

  .info-section,
  .places-section {
    margin-bottom: 30px;
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(248, 251, 255, 0.9)
    );
    border: 1px solid rgba(0, 188, 212, 0.2);
  }

  .section-title {
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.1),
      rgba(0, 150, 136, 0.1)
    );
    color: #0277bd;
    font-weight: 700;
    padding: 20px;
    border-bottom: 1px solid rgba(0, 188, 212, 0.2);
  }

  .section-content {
    padding: 30px;
  }

  .info-item {
    margin-bottom: 25px;
  }

  .info-item:last-child {
    margin-bottom: 0;
  }

  .info-label {
    color: #0277bd;
    font-weight: 700;
    margin-bottom: 10px;
  }

  .info-value {
    color: #01579b;
    font-size: 1.1rem;
    line-height: 1.6;
  }

  .price {
    font-weight: 700;
    font-size: 1.3rem;
    color: #00695c;
  }

  .places-list {
    display: flex;
    flex-direction: column;
    gap: 15px;
  }

  .place-item {
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.05),
      rgba(0, 150, 136, 0.05)
    );
    border: 1px solid rgba(0, 188, 212, 0.2);
    border-left: 4px solid #00bcd4;
    transition: all 0.3s ease;
  }

  .place-item:hover {
    transform: translateX(10px);
    box-shadow: 0 5px 15px rgba(0, 188, 212, 0.2);
  }

  .place-info {
    display: flex;
    align-items: center;
    gap: 20px;
  }

  .place-number {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 700;
    font-size: 1.1rem;
    flex-shrink: 0;
  }

  .place-details {
    flex: 1;
  }

  .place-name {
    color: #0277bd;
    font-weight: 700;
    font-size: 1.2rem;
    margin-bottom: 8px;
  }

  .place-address {
    color: #00695c;
    font-size: 1rem;
  }

  @media (max-width: 768px) {
    .plan-title {
      font-size: 1.8rem;
    }

    .plan-meta {
      flex-direction: column;
      gap: 10px;
    }

    .action-buttons {
      justify-content: center;
    }

    .place-info {
      flex-direction: column;
      text-align: center;
      gap: 15px;
    }
  }
</style>
