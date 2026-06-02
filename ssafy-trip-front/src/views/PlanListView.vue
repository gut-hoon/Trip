<template>
  <v-container class="plan-list-container">
    <div class="page-header">
      <h1 class="page-title">📋 여행 계획 목록 ✈️</h1>
      <div class="wave-divider"></div>
      <p class="page-subtitle">다양한 여행 계획을 둘러보세요!</p>
    </div>

    <v-row class="plans-grid">
      <v-col
        v-for="trip in visiblePlans"
        :key="trip.no"
        cols="12"
        lg="4"
        md="6"
      >
        <v-card
          class="plan-card"
          elevation="8"
          rounded="xl"
          @click="goToDetail(trip.no)"
        >
          <!-- 카드 헤더 -->

          <v-card-title class="plan-title">
            {{ trip.title }}
          </v-card-title>

          <v-card-subtitle class="plan-meta">
            <div class="meta-row">
              <span class="meta-item">✏️ {{ trip.userName }}</span>
              <span class="meta-item">🕒 {{ trip.startDate }}</span>
            </div>
          </v-card-subtitle>

          <v-card-text class="plan-description">
            <p>{{ trip.content }}</p>

            <!-- 가격 정보 -->
            <div class="price-info">
              <span class="price-label">💰 예상 비용</span>
              <span class="price-value"
                >{{ trip.expense?.toLocaleString() }}원</span
              >
            </div>

            <!-- 장소 개수 -->
            <div class="places-info">
              <v-icon color="cyan" size="small">mdi-map-marker</v-icon>
              <span class="places-count"
                >{{ planStore.planDetailsMap[trip.no] }}개 장소</span
              >
            </div>
          </v-card-text>

          <v-card-actions class="card-actions">
            <v-btn
              block
              class="detail-btn"
              color="cyan"
              rounded="xl"
              variant="elevated"
            >
              🌊 자세히 보기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
  import { ref, onMounted, computed } from 'vue'
  import { useRouter } from 'vue-router'
  import { usePlanStore } from '@/store/plan'
  import { useMemberStore } from '@/store/user'
  const memberStore = useMemberStore()
  const router = useRouter()
  const planStore = usePlanStore()

  const goToDetail = (id) => {
    if (memberStore.isLoggedIn) {
      router.push({ name: 'PlanDetailView', params: { id } })
    } else {
      alert('로그인 후 이용하세요')
    }
  }
  onMounted(async () => {
    await planStore.selectPlans()
    await planStore.preloadAllPlanDetails()
  })

  const visiblePlans = computed(() => {
    if (memberStore.loginUser.user.role != 'ADMIN') {
      return planStore.plans.filter((trip) => {
        return (
          trip.share === true || trip.usersNo === memberStore.loginUser.user.no
        )
      })
    } else {
      return planStore.plans
    }
  })
</script>

<style scoped>
  .plan-list-container {
    background: linear-gradient(135deg, #e3f2fd 0%, #f1f8e9 100%);
    min-height: 100vh;
    padding: 40px 20px;
  }

  .page-header {
    text-align: center;
    margin-bottom: 50px;
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
    margin: 0 auto 20px;
    border-radius: 2px;
  }

  .page-subtitle {
    color: #0277bd;
    font-size: 1.2rem;
    font-weight: 500;
  }

  .plans-grid {
    max-width: 1200px;
    margin: 0 auto;
  }

  .plan-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.95),
      rgba(240, 248, 255, 0.95)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: all 0.3s ease;
    cursor: pointer;
    height: 100%;
    display: flex;
    flex-direction: column;
    position: relative;
  }

  .plan-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 15px 35px rgba(0, 188, 212, 0.3);
  }

  .card-header {
    position: absolute;
    top: 15px;
    right: 15px;
    z-index: 1;
  }

  .share-status {
    font-weight: 600;
    font-size: 0.8rem;
  }

  .plan-title {
    color: #01579b;
    font-weight: 700;
    font-size: 1.3rem;
    padding: 25px 20px 10px;
    line-height: 1.3;
  }

  .plan-meta {
    padding: 0 20px 15px;
  }

  .meta-row {
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: wrap;
    gap: 10px;
  }

  .meta-item {
    color: #0277bd;
    font-size: 0.9rem;
    font-weight: 500;
  }

  .plan-description {
    color: #0277bd;
    padding: 0 20px 20px;
    flex-grow: 1;
    display: flex;
    flex-direction: column;
    gap: 15px;
  }

  .plan-description p {
    line-height: 1.6;
    margin-bottom: 0;
  }

  .price-info {
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 12px 15px;
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.1),
      rgba(0, 150, 136, 0.1)
    );
    border-radius: 12px;
    border: 1px solid rgba(0, 188, 212, 0.2);
  }

  .price-label {
    color: #00695c;
    font-weight: 600;
    font-size: 0.9rem;
  }

  .price-value {
    color: #0277bd;
    font-weight: 700;
    font-size: 1.1rem;
  }

  .places-info {
    display: flex;
    align-items: center;
    gap: 8px;
    color: #00695c;
    font-weight: 500;
  }

  .places-count {
    font-size: 0.95rem;
  }

  .card-actions {
    padding: 0 20px 20px;
  }

  .detail-btn {
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .detail-btn:hover {
    background: linear-gradient(135deg, #0097a7, #00695c);
    transform: translateY(-2px);
  }

  @media (max-width: 768px) {
    .meta-row {
      flex-direction: column;
      align-items: flex-start;
      gap: 5px;
    }

    .price-info {
      flex-direction: column;
      gap: 5px;
      text-align: center;
    }
  }
</style>
