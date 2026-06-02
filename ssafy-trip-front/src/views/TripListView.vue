<template>
  <v-container class="py-8">
    <!-- Header -->
    <div class="page-header mb-8">
      <h1 class="text-h3 font-weight-bold text-center mb-4">
        <v-icon size="40" class="mr-3" color="primary">mdi-map-marker-multiple</v-icon>
        관광지 목록
      </h1>
      <p class="text-h6 text-center text-medium-emphasis mb-6">
        전국의 아름다운 관광지를 둘러보세요
      </p>

      <!-- Search and Filter -->
      <v-card class="filter-card pa-4 mb-6" elevation="2">
        <v-row style="align-content: center">
          <v-col cols="12" md="8">
            <v-text-field
              v-model="searchQuery"
              clearable
              density="compact"
              hide-details
              label="관광지 검색"
              prepend-inner-icon="mdi-magnify"
              variant="outlined"
            />
          </v-col>
          <v-col cols="12" md="4">
            <v-select
              v-model="selectedRegion"
              :items="regions"
              label="지역 선택"
              variant="outlined"
              density="compact"
              hide-details
              clearable
            />
          </v-col>
        </v-row>
      </v-card>
    </div>

    <!-- Trip Cards Grid -->
    <v-row>
      <v-col
        v-for="trip in filteredTrips"
        :key="trip.id"
        cols="12"
        sm="6"
        md="4"
        lg="3"
      >
        <v-card
          class="trip-card"
          elevation="4"
          @click="$router.push(`/trip/${trip.id}`)"
        >
          <div class="image-container">
            <v-img
              :src="trip.image"
              height="200"
              cover
              class="trip-image"
            >
              <div class="image-overlay">
                <v-chip
                  color="primary"
                  variant="elevated"
                  size="small"
                  prepend-icon="mdi-star"
                  class="rating-badge"
                >
                  {{ trip.rating || '4.5' }}
                </v-chip>
              </div>
            </v-img>
          </div>

          <v-card-title class="text-h6 font-weight-bold pa-4 pb-2">
            {{ trip.name }}
          </v-card-title>

          <v-card-text class="pa-4 pt-0">
            <p class="text-body-2 text-medium-emphasis mb-3 line-clamp-2">
              {{ trip.description }}
            </p>

            <div class="trip-info">
              <v-chip
                size="small"
                variant="outlined"
                prepend-icon="mdi-map-marker"
                class="mr-2 mb-2"
              >
                {{ getRegionFromName(trip.name) }}
              </v-chip>
              <v-chip
                size="small"
                variant="outlined"
                prepend-icon="mdi-camera"
                color="green"
              >
                포토스팟
              </v-chip>
            </div>
          </v-card-text>

          <v-card-actions class="pa-4 pt-0">
            <v-btn
              color="primary"
              variant="text"
              prepend-icon="mdi-information"
              block
            >
              자세히 보기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>

    <!-- Empty State -->
    <div v-if="filteredTrips.length === 0" class="text-center py-12">
      <v-icon size="64" color="grey-lighten-1" class="mb-4">mdi-map-search</v-icon>
      <h3 class="text-h5 text-medium-emphasis mb-2">검색 결과가 없습니다</h3>
      <p class="text-body-1 text-medium-emphasis">다른 검색어나 지역을 선택해보세요</p>
    </div>

    <!-- Load More Button -->
    <div v-if="hasMore" class="text-center mt-8">
      <v-btn
        color="primary"
        variant="outlined"
        size="large"
        @click="loadMore"
        :loading="loadingMore"
      >
        더 많은 관광지 보기
      </v-btn>
    </div>
  </v-container>
</template>

<script setup>
  import { ref, computed } from 'vue'

  const searchQuery = ref('')
  const selectedRegion = ref('')
  const loadingMore = ref(false)
  const hasMore = ref(true)

  const regions = [
    '서울', '부산', '대구', '인천', '광주', '대전', '울산',
    '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'
  ]

  const trips = ref([
    {
      id: 1,
      name: '경주 불국사',
      description: '신라 천년의 역사가 살아 숨쉬는 유네스코 세계문화유산',
      image: 'https://cdn.pixabay.com/photo/2022/10/08/14/03/gyeongbokgung-palace-7507028_1280.jpg',
      rating: 4.8,
      region: '경북'
    },
    {
      id: 2,
      name: '제주 성산일출봉',
      description: '제주의 아름다운 일출 명소, 자연이 만든 완벽한 원형 분화구',
      image: 'https://cdn.pixabay.com/photo/2023/02/21/11/40/jeju-island-7804099_1280.jpg',
      rating: 4.9,
      region: '제주'
    },
    {
      id: 3,
      name: '서울 남산타워',
      description: '서울의 대표 전망대, 도심 속에서 만나는 낭만적인 야경',
      image: 'https://cdn.pixabay.com/photo/2021/09/07/11/53/car-6603726_1280.jpg',
      rating: 4.6,
      region: '서울'
    },
    {
      id: 4,
      name: '부산 해운대',
      description: '부산의 대표 해수욕장, 도시와 바다가 어우러진 아름다운 풍경',
      image: 'https://cdn.pixabay.com/photo/2022/02/08/06/18/bird-7000840_1280.jpg',
      rating: 4.7,
      region: '부산'
    },
    {
      id: 5,
      name: '강릉 안목해변',
      description: '커피의 도시 강릉, 바다와 커피가 만나는 특별한 공간',
      image: 'https://cdn.pixabay.com/photo/2017/04/03/11/14/gangneung-2198026_1280.jpg',
      rating: 4.5,
      region: '강원'
    },
    {
      id: 6,
      name: '전주 한옥마을',
      description: '전통과 현대가 공존하는 아름다운 한옥마을',
      image: 'https://cdn.pixabay.com/photo/2015/04/02/14/15/hanok-village-703824_1280.jpg',
      rating: 4.4,
      region: '전북'
    },
  ])

  const filteredTrips = computed(() => {
    let filtered = trips.value

    if (searchQuery.value) {
      filtered = filtered.filter(trip =>
        trip.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
        trip.description.toLowerCase().includes(searchQuery.value.toLowerCase())
      )
    }

    if (selectedRegion.value) {
      filtered = filtered.filter(trip => trip.region === selectedRegion.value)
    }

    return filtered
  })

  const getRegionFromName = (name) => {
    const trip = trips.value.find(t => t.name === name)
    return trip?.region || name.split(' ')[0]
  }

  const loadMore = () => {
    loadingMore.value = true
    // 더 많은 데이터 로드 로직
    setTimeout(() => {
      loadingMore.value = false
      hasMore.value = false // 예시로 더 이상 로드할 데이터가 없다고 설정
    }, 1000)
  }
</script>

<style scoped>
.page-header {
  text-align: center;
}

.filter-card {
  background: linear-gradient(135deg, #f5f7fa 0%, #c3cfe2 100%);
}

.trip-card {
  cursor: pointer;
  transition: all 0.3s ease;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.trip-card:hover {
  transform: translateY(-8px);
  box-shadow: 0 12px 40px rgba(0,0,0,0.15) !important;
}

.image-container {
  position: relative;
  overflow: hidden;
}

.trip-image {
  transition: transform 0.3s ease;
}

.trip-card:hover .trip-image {
  transform: scale(1.05);
}

.image-overlay {
  position: absolute;
  top: 12px;
  right: 12px;
}

.rating-badge {
  backdrop-filter: blur(10px);
}

.trip-info {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

.line-clamp-2 {
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}
</style>
