<template>
  <div class="planner-wrapper">
    <div class="page-header">
      <h1 class="page-title">🗓️ 여행 계획 수정 ✈️</h1>
      <div class="wave-divider"></div>
      <p class="page-subtitle">나만의 특별한 여행을 수정해보세요!</p>
    </div>

    <!-- 중앙 60% 컨테이너 -->
    <div class="content-container">
      <div class="main-layout">
        <!-- 왼쪽 사이드바 - 선택된 장소 -->
        <div class="sidebar">
          <v-card class="plan-card sidebar-card" elevation="8" rounded="xl">
            <v-card-title class="plan-title"> 📍 선택한 장소 </v-card-title>
            <v-card-text class="plan-content">
              <v-alert
                v-if="!plan.length"
                type="info"
                variant="tonal"
                rounded="lg"
                class="no-plan-alert"
              >
                <div class="alert-content">
                  <v-icon size="large" color="info">mdi-map-marker-off</v-icon>
                  <div>
                    <h4>선택된 장소가 없습니다</h4>
                    <p>오른쪽에서 장소를 검색하고 추가해보세요!</p>
                  </div>
                </div>
              </v-alert>

              <div v-else class="plan-list">
                <div v-for="(p, i) in plan" :key="p.placeNo" class="plan-item">
                  <div class="plan-number">{{ i + 1 }}</div>
                  <div class="plan-info">
                    <div class="plan-place-name">{{ p.placeName }}</div>
                    <div class="plan-place-address">
                      📍 {{ p.placeAddress }}
                    </div>
                  </div>
                  <v-btn
                    icon="mdi-delete"
                    color="red"
                    variant="text"
                    @click="removeFromPlan(i)"
                    class="remove-btn"
                    size="small"
                  />
                </div>
              </div>
            </v-card-text>
          </v-card>
        </div>

        <!-- 오른쪽 메인 콘텐츠 -->
        <div class="main-content">
          <!-- 지도 섹션 -->
          <v-card class="map-card" elevation="8" rounded="xl">
            <v-card-title class="map-title"> 🗺️ 여행지 지도 </v-card-title>
            <v-card-text class="map-content">
              <div id="map" ref="mapRef" class="map-container"></div>
            </v-card-text>
          </v-card>

          <!-- 검색 필터 섹션 -->
          <v-card class="filter-card" elevation="6" rounded="xl">
            <v-card-title class="filter-title"> 🔍 여행지 검색 </v-card-title>
            <v-card-text class="filter-content">
              <v-row>
                <v-col cols="12" md="4">
                  <v-select
                    v-model="selectedSido"
                    :items="sidoList"
                    label="🏙️ 시도 선택"
                    variant="outlined"
                    color="cyan"
                    rounded="lg"
                    class="filter-select"
                  />
                </v-col>
                <v-col cols="12" md="4">
                  <v-select
                    v-model="selectedGugun"
                    :disabled="!selectedSido"
                    :items="filteredGugunList"
                    label="🏘️ 구군 선택"
                    variant="outlined"
                    color="cyan"
                    rounded="lg"
                    class="filter-select"
                  />
                </v-col>
                <v-col cols="12" md="4">
                  <v-select
                    v-model="selectedCategory"
                    :items="categoryList"
                    label="🏷️ 관광지 분류"
                    variant="outlined"
                    color="cyan"
                    rounded="lg"
                    class="filter-select"
                  />
                </v-col>
              </v-row>

              <v-btn
                class="search-btn"
                color="cyan"
                @click="searchPlaces"
                rounded="xl"
                elevation="4"
                size="large"
                block
              >
                🔍 장소 검색하기
              </v-btn>
            </v-card-text>
          </v-card>

          <!-- 검색 결과 섹션 -->
          <v-card
            v-if="searchResults.length"
            class="results-card"
            elevation="6"
            rounded="xl"
          >
            <v-card-title class="results-title">📍 검색 결과</v-card-title>
            <v-card-text class="results-content">
              <v-list class="results-list">
                <v-list-item
                  v-for="(place, i) in searchResults"
                  :key="place.id"
                  class="result-item"
                  @click="addPlaceToPlan(place)"
                  rounded="lg"
                >
                  <v-list-item-content>
                    <v-list-item-title class="place-name">
                      {{ place.place_name }}
                    </v-list-item-title>
                    <v-list-item-subtitle class="place-address">
                      📍 {{ place.address_name }}
                    </v-list-item-subtitle>
                  </v-list-item-content>
                  <v-list-item-action>
                    <v-btn
                      icon="mdi-plus-circle"
                      color="cyan"
                      variant="text"
                      class="add-btn"
                    />
                  </v-list-item-action>
                </v-list-item>
              </v-list>

              <!-- 페이징 버튼 (최대 3페이지) -->
              <div
                id="pagination"
                class="pagination mt-4 d-flex justify-center"
              />
            </v-card-text>
          </v-card>

          <!-- 여행 정보 입력 섹션 -->
          <v-card class="info-card" elevation="6" rounded="xl">
            <v-card-title class="info-title"> ✏️ 여행 정보 수정 </v-card-title>
            <v-card-text class="info-content">
              <v-text-field
                v-model="planTitle"
                label="🌊 여행 계획 이름"
                placeholder="예: 2025 여름 제주여행"
                variant="outlined"
                color="cyan"
                rounded="lg"
                class="input-field"
              />

              <v-textarea
                v-model="planContent"
                label="📖 여행 상세 계획"
                placeholder="예: 첫째 날 오전엔 경복궁 관람, 오후엔 한옥마을 산책"
                auto-grow
                rows="4"
                variant="outlined"
                color="cyan"
                rounded="lg"
                class="input-field"
              />

              <v-row>
                <v-col cols="12" md="4">
                  <v-text-field
                    v-model="price"
                    label="💰 예상비용"
                    type="number"
                    hide-spin-buttons
                    variant="outlined"
                    color="cyan"
                    rounded="lg"
                    class="input-field"
                  >
                    <template #append-inner>
                      <span class="unit">원</span>
                    </template>
                  </v-text-field>
                </v-col>
                <v-col cols="12" md="4">
                  <v-text-field
                    v-model="startDate"
                    label="📅 시작 날짜"
                    required
                    type="date"
                    variant="outlined"
                    color="cyan"
                    rounded="lg"
                    class="input-field"
                    @change="onStartDateChange"
                  />
                </v-col>
                <v-col cols="12" md="4">
                  <v-text-field
                    v-model="endDate"
                    label="📅 종료 날짜"
                    required
                    type="date"
                    variant="outlined"
                    color="cyan"
                    rounded="lg"
                    class="input-field"
                    @change="onEndDateChange"
                  />
                </v-col>
              </v-row>

              <v-checkbox
                v-model="isShare"
                label="🌍 여행계획 공유하기"
                color="cyan"
                class="share-checkbox"
              />

              <v-btn
                class="save-btn"
                color="gradient"
                :disabled="!planTitle || !plan.length"
                @click="saveTripPlan"
                rounded="xl"
                elevation="6"
                size="large"
                block
              >
                ✅ 여행 계획 수정하기
              </v-btn>
            </v-card-text>
          </v-card>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
  import { useMemberStore } from '@/store/user'
  import { usePlanStore } from '@/store/plan'
  import { ref, computed, onMounted } from 'vue'
  import { useRouter, useRoute } from 'vue-router'
  // import * as kakao from 'kakao.maps'

  const memberStore = useMemberStore()
  const planStore = usePlanStore()
  // 지도 관련 변수
  const mapRef = ref(null)
  const map = ref(null)
  const markers = ref([])

  const route = useRoute()
  const router = useRouter()

  // kakao map api load
  const loadKakaoMap = () => {
    const script = document.createElement('script')
    script.src =
      '//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=6499c31f69433a99fd06c0cae9d15ffb&libraries=services,clusterer,drawing'
    script.onload = () => {
      if (window.kakao && window.kakao.maps) {
        kakao.maps.load(() => initMap())
      }
    }
    document.head.appendChild(script)
  }

  // 초기 옵션
  const mapOption = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  }

  // onMounted에서 지도 생성
  onMounted(() => {
    if (window.kakao && window.kakao.maps) {
      // mapRef.value가 실제 DOM 요소일 때만!
      if (mapRef.value && !map.value) {
        map.value = new kakao.maps.Map(mapRef.value, mapOption)
        // 이제 map.value는 진짜 kakao.maps.Map 객체!
      }
    }
  })

  // 지도 초기화 함수
  const initMap = () => {
    const mapContainer = document.getElementById('map')
    if (!mapContainer) {
      console.error('지도 컨테이너를 찾을 수 없습니다')
      return
    }

    try {
      map.value = new kakao.maps.Map(mapContainer, mapOption)
      console.log('지도가 성공적으로 초기화되었습니다', map.value)
    } catch (error) {
      console.error('지도 초기화 중 오류 발생:', error)
    }
  }

  // 시도/구군/카테고리
  const sidoList = [
    '서울',
    '부산',
    '대구',
    '대전',
    '광주',
    '울산',
    '인천',
    '경기',
    '강원',
    '충북',
    '충남',
    '전북',
    '전남',
    '경북',
    '경남',
    '제주',
  ]

  const gugunMap = {
    서울: [
      '종로구',
      '중구',
      '용산구',
      '성동구',
      '광진구',
      '동대문구',
      '중랑구',
      '성북구',
      '강북구',
      '도봉구',
      '노원구',
      '은평구',
      '서대문구',
      '마포구',
      '양천구',
      '강서구',
      '구로구',
      '금천구',
      '영등포구',
      '동작구',
      '관악구',
      '서초구',
      '강남구',
      '송파구',
      '강동구',
    ],
    부산: [
      '중구',
      '서구',
      '동구',
      '영도구',
      '부산진구',
      '동래구',
      '남구',
      '북구',
      '해운대구',
      '사하구',
      '금정구',
      '강서구',
      '연제구',
      '수영구',
      '사상구',
      '기장군',
    ],
    대구: [
      '중구',
      '동구',
      '서구',
      '남구',
      '북구',
      '수성구',
      '달서구',
      '달성군',
    ],
    대전: ['동구', '중구', '서구', '유성구', '대덕구'],
    광주: ['동구', '서구', '남구', '북구', '광산구'],
    울산: ['중구', '남구', '동구', '북구', '울주군'],
    인천: [
      '중구',
      '동구',
      '미추홀구',
      '연수구',
      '남동구',
      '부평구',
      '계양구',
      '서구',
      '강화군',
      '옹진군',
    ],
    경기: [
      '수원시',
      '성남시',
      '의정부시',
      '안양시',
      '부천시',
      '광명시',
      '평택시',
      '동두천시',
      '안산시',
      '고양시',
      '과천시',
      '구리시',
      '남양주시',
      '오산시',
      '시흥시',
      '군포시',
      '의왕시',
      '하남시',
      '용인시',
      '파주시',
      '연천군',
      '가평군',
      '양평군',
    ],
    강원: [
      '춘천시',
      '원주시',
      '강릉시',
      '동해시',
      '태백시',
      '속초시',
      '삼척시',
      '홍천군',
      '횡성군',
      '영월군',
      '평창군',
      '정선군',
      '철원군',
      '화천군',
      '양구군',
      '인제군',
      '고성군',
      '양양군',
    ],
    충북: [
      '청주시',
      '충주시',
      '제천시',
      '보은군',
      '옥천군',
      '영동군',
      '증평군',
      '진천군',
      '괴산군',
      '음성군',
      '단양군',
    ],
    충남: [
      '천안시',
      '공주시',
      '보령시',
      '아산시',
      '서산시',
      '논산시',
      '계룡시',
      '당진시',
      '금산군',
      '부여군',
      '서천군',
      '청양군',
      '홍성군',
      '예산군',
      '태안군',
    ],
    전북: [
      '전주시',
      '군산시',
      '익산시',
      '정읍시',
      '남원시',
      '김제시',
      '완주군',
      '진안군',
      '무주군',
      '장수군',
      '임실군',
      '순창군',
      '고창군',
      '부안군',
    ],
    전남: [
      '목포시',
      '여수시',
      '순천시',
      '나주시',
      '광양시',
      '담양군',
      '곡성군',
      '구례군',
      '고흥군',
      '보성군',
      '화순군',
      '장흥군',
      '강진군',
      '해남군',
      '영암군',
      '무안군',
      '함평군',
      '영광군',
      '장성군',
      '완도군',
      '진도군',
      '신안군',
    ],
    경북: [
      '포항시',
      '경주시',
      '김천시',
      '안동시',
      '구미시',
      '영주시',
      '영천시',
      '상주시',
      '문경시',
      '경산시',
      '군위군',
      '의성군',
      '청송군',
      '영양군',
      '영덕군',
      '청도군',
      '고령군',
      '성주군',
      '칠곡군',
      '예천군',
      '봉화군',
      '울진군',
      '울릉군',
    ],
    경남: [
      '창원시',
      '진주시',
      '통영시',
      '사천시',
      '김해시',
      '밀양시',
      '거제시',
      '양산시',
      '의령군',
      '함안군',
      '창녕군',
      '고성군',
      '남해군',
      '하동군',
      '산청군',
      '함양군',
      '거창군',
      '합천군',
    ],
    제주: ['제주시', '서귀포시'],
  }

  const categoryList = [
    '관광명소', // 관광지
    '숙박', // 숙박
    '음식점', // 음식점
    '문화시설', // 문화시설
    '공연/전시', // 공연
    '여행코스', // 여행코스
    '쇼핑', // 쇼핑
  ]

  const selectedSido = ref('')
  const selectedGugun = ref('')
  const selectedCategory = ref('')
  const filteredGugunList = computed(() => gugunMap[selectedSido.value] || [])

  // 검색 결과 저장
  const searchResults = ref([])

  // 장소 검색
  const searchPlaces = () => {
    const keyword = `${selectedSido.value} ${selectedGugun.value} ${selectedCategory.value}`
    console.log('검색 키워드:', keyword)

    if (!window.kakao || !window.kakao.maps || !window.kakao.maps.services) {
      alert('Kakao Maps API가 로드되지 않았습니다.')
      return
    }

    try {
      const ps = new kakao.maps.services.Places()
      ps.keywordSearch(keyword, (data, status, pagination) => {
        if (status === kakao.maps.services.Status.OK) {
          searchResults.value = data.map((item) => ({
            id: item.id,
            place_name: item.place_name,
            place_url: item.place_url,
            address_name: item.address_name,
            lat: parseFloat(item.y),
            lng: parseFloat(item.x),
          }))
          console.log('검색 결과:', searchResults.value)

          if (searchResults.value.length > 0) {
            showMarkers(searchResults.value)
          }

          // 페이징 버튼 생성 (최대 3페이지로 제한)
          displayPagination(pagination)
        } else {
          alert('검색 결과가 없습니다.')
          searchResults.value = []
        }
      })
    } catch (error) {
      console.error('장소 검색 중 오류 발생:', error)
      alert('장소 검색 중 오류가 발생했습니다.')
    }
  }

  // 페이지네이션 표시 함수 (최대 3페이지로 제한)
  function displayPagination(pagination) {
    const paginationEl = document.getElementById('pagination')
    if (!paginationEl) return

    paginationEl.innerHTML = ''

    // 최대 3페이지까지만 표시
    const maxPages = Math.min(pagination.last, 3)

    for (let i = 1; i <= maxPages; i++) {
      const el = document.createElement('a')
      el.href = '#'
      el.innerHTML = i
      el.className = i === pagination.current ? 'on' : ''
      el.style.cssText = `
      display: inline-block;
      margin: 0 5px;
      padding: 8px 12px;
      background: ${i === pagination.current ? '#00bcd4' : '#f5f5f5'};
      color: ${i === pagination.current ? 'white' : '#333'};
      text-decoration: none;
      border-radius: 8px;
      font-weight: 600;
      transition: all 0.3s ease;
    `

      el.onclick = (e) => {
        e.preventDefault()
        pagination.gotoPage(i)
      }

      el.onmouseover = () => {
        if (i !== pagination.current) {
          el.style.background = '#e0f2f1'
          el.style.color = '#00695c'
        }
      }

      el.onmouseout = () => {
        if (i !== pagination.current) {
          el.style.background = '#f5f5f5'
          el.style.color = '#333'
        }
      }

      paginationEl.appendChild(el)
    }

    // 3페이지를 넘는 경우 안내 메시지 추가
    if (pagination.last > 3) {
      const notice = document.createElement('span')
      notice.innerHTML = ' (최대 3페이지까지 표시)'
      notice.style.cssText = `
      color: #666;
      font-size: 0.9rem;
      margin-left: 10px;
    `
      paginationEl.appendChild(notice)
    }
  }

  const isKakaoMapObject = (obj) => {
    return (
      obj &&
      typeof obj.setCenter === 'function' &&
      typeof obj.panTo === 'function'
    )
  }
  let activeInfowindow = null
  // 마커 표시 및 지도 중심 이동
  const showMarkers = (places) => {
    if (!isKakaoMapObject(map.value)) return

    // 마커 초기화
    markers.value.forEach((marker) => marker.setMap(null))
    markers.value = []

    places.forEach((place) => {
      if (!isValidPosition(place)) return

      const position = new kakao.maps.LatLng(place.lat, place.lng)
      const marker = new kakao.maps.Marker({
        position,
        map: map.value,
        clickable: true,
      })

      // 🔧 여기서 infowindow는 함수 안에서 생성되므로 동일 마커 클릭 시 새로 생성됨
      kakao.maps.event.addListener(marker, 'click', () => {
        // 이전 인포윈도우 닫기
        if (activeInfowindow) {
          activeInfowindow.close()
        }

        // 새 인포윈도우 생성
        const infowindow = new kakao.maps.InfoWindow({
          content: `
          <div style="padding:5px; color:black;">
            <a href="${place.place_url}" target="_blank" style="color:#007BFF;">
              ${place.place_name}
            </a><br/>
            <small style="color:gray;">${place.address_name}</small>
          </div>`,
          removable: true,
        })

        // 새로 열기
        infowindow.open(map.value, marker)
        activeInfowindow = infowindow
      })

      markers.value.push(marker)
    })

    if (places.length > 0 && isValidPosition(places[0])) {
      map.value.setCenter(new kakao.maps.LatLng(places[0].lat, places[0].lng))
    }
  }
  const isValidPosition = (place) =>
    place &&
    typeof place.lat === 'number' &&
    typeof place.lng === 'number' &&
    !isNaN(place.lat) &&
    !isNaN(place.lng)

  // 일정
  const plan = ref([])
  const planContent = ref('')
  const isShare = ref(false)
  const price = ref(0)

  const addPlaceToPlan = (place) => {
    console.log(place)
    console.log(plan.value)
    if (!plan.value.find((p) => p.placeNo === place.id)) {
      plan.value.push({
        planId: Number(planId),
        placeNo: Number(place.id),
        placeUrl: place.place_url,
        placeName: place.place_name,
        placeAddress: place.address_name,
        seq: plan.value.length + 1, // 순서 명시적으로 부여
      })
    }
  }
  const removeFromPlan = (idx) => {
    plan.value.splice(idx, 1)
  }

  // 여행 계획 저장
  const planTitle = ref('')
  const getTodayString = () => {
    const today = new Date()
    const yyyy = today.getFullYear()
    const mm = String(today.getMonth() + 1).padStart(2, '0')
    const dd = String(today.getDate()).padStart(2, '0')
    return `${yyyy}-${mm}-${dd}`
  }
  const onStartDateChange = () => {
    if (startDate.value > endDate.value) {
      endDate.value = startDate.value
    }
  }

  const onEndDateChange = () => {
    if (endDate.value < startDate.value) {
      alert('종료 날짜는 시작 날짜 이후여야 합니다.')
      endDate.value = startDate.value
    }
  }

  const startDate = ref(getTodayString())
  const endDate = ref(getTodayString())

  const planId = route.params.id

  onMounted(async () => {
    const data = await planStore.selectPlanByNo(planId)
    if (data) {
      planTitle.value = data.title
      planContent.value = data.desc
      isShare.value = data.share
      price.value = data.expense
    }
    const detail = await planStore.getPlanDetails(planId)
    console.log(detail)
    plan.value = detail.map((d) => ({
      ...d,
      placeName: d.placeName,
      placeAddress: d.placeAddress,
    }))
    console.log('asd', plan.value)
  })

  const saveTripPlan = async () => {
    console.log(plan.value)
    console.log(planId)
    const planData = {
      no: planId,
      title: planTitle.value,
      usersNo: memberStore.loginUser.user.no,
      desc: planContent.value,
      expense: price.value,
      startDate: startDate.value,
      endDate: endDate.value,
      share: isShare.value,
    }
    await planStore.updatePlan(planData)

    console.log(plan.value)

    await planStore.updatePlanDetails(Number(planId), plan.value)
    router.push('/plan')
    planTitle.value = ''
    planContent.value = ''
    plan.value = []
  }
</script>

<style scoped>
  .planner-wrapper {
    background: linear-gradient(135deg, #e3f2fd 0%, #f1f8e9 100%);
    min-height: 100vh;
    padding: 20px;
  }

  .page-header {
    text-align: center;
    margin-bottom: 30px;
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

  /* 중앙 60% 컨테이너 */
  .content-container {
    width: 60%;
    margin: 0 auto;
    padding: 0 24px;
  }

  .main-layout {
    display: flex;
    gap: 20px;
    max-width: 1400px;
    margin: 0 auto;
  }

  .sidebar {
    width: 350px;
    flex-shrink: 0;
  }

  .main-content {
    flex: 1;
    min-width: 0;
  }

  .sidebar-card {
    position: sticky;
    top: 20px;
    max-height: calc(100vh - 120px);
  }

  .plan-content {
    max-height: calc(100vh - 200px);
    overflow-y: auto;
  }

  .plan-list {
    display: flex;
    flex-direction: column;
    gap: 12px;
  }

  .plan-item {
    display: flex;
    align-items: center;
    gap: 12px;
    padding: 16px;
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.05),
      rgba(0, 150, 136, 0.05)
    );
    border: 1px solid rgba(0, 188, 212, 0.2);
    border-left: 4px solid #00bcd4;
    border-radius: 12px;
    transition: all 0.3s ease;
  }

  .plan-item:hover {
    transform: translateX(8px);
    box-shadow: 0 4px 12px rgba(0, 188, 212, 0.2);
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.1),
      rgba(0, 150, 136, 0.1)
    );
  }

  .plan-number {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    display: flex;
    align-items: center;
    justify-content: center;
    font-weight: 700;
    font-size: 0.9rem;
    flex-shrink: 0;
  }

  .plan-info {
    flex: 1;
    min-width: 0;
  }

  .plan-place-name {
    color: #0277bd;
    font-weight: 600;
    font-size: 0.95rem;
    margin-bottom: 4px;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .plan-place-address {
    color: #00695c;
    font-size: 0.85rem;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
  }

  .remove-btn {
    flex-shrink: 0;
    transition: all 0.3s ease;
  }

  .remove-btn:hover {
    transform: scale(1.1);
  }

  .alert-content {
    display: flex;
    align-items: center;
    gap: 15px;
    text-align: left;
  }

  .alert-content h4 {
    margin-bottom: 5px;
    color: #1976d2;
  }

  .alert-content p {
    margin: 0;
    opacity: 0.8;
  }

  .map-card,
  .filter-card,
  .results-card,
  .plan-card,
  .info-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.95),
      rgba(240, 248, 255, 0.95)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    margin-bottom: 30px;
  }

  .map-title,
  .filter-title,
  .results-title,
  .plan-title,
  .info-title {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    font-weight: 700;
    padding: 20px;
  }

  .map-content,
  .filter-content,
  .results-content,
  .info-content {
    padding: 30px;
  }

  .map-container {
    width: 100%;
    height: 400px;
    border-radius: 15px;
    overflow: hidden;
    box-shadow: 0 4px 15px rgba(0, 188, 212, 0.2);
  }

  .filter-select {
    margin-bottom: 20px;
  }

  .filter-select :deep(.v-field) {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(240, 248, 255, 0.9)
    );
    border-radius: 15px;
    box-shadow: 0 4px 15px rgba(0, 188, 212, 0.1);
  }

  .search-btn {
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
    margin-top: 10px;
  }

  .search-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
  }

  .results-list {
    background: transparent;
  }

  .result-item {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(248, 251, 255, 0.9)
    );
    border: 1px solid rgba(0, 188, 212, 0.2);
    margin-bottom: 10px;
    transition: all 0.3s ease;
    cursor: pointer;
  }

  .result-item:hover {
    transform: translateX(10px);
    box-shadow: 0 5px 15px rgba(0, 188, 212, 0.2);
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.1),
      rgba(0, 150, 136, 0.1)
    );
  }

  .place-name {
    color: #0277bd;
    font-weight: 600;
  }

  .place-address {
    color: #00695c;
  }

  .add-btn {
    transition: all 0.3s ease;
  }

  .add-btn:hover {
    transform: scale(1.2);
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

  .unit {
    color: #00695c;
    font-weight: 600;
  }

  .share-checkbox {
    margin-bottom: 25px;
  }

  .save-btn {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-transform: none;
    font-weight: 700;
    font-size: 1.1rem;
    padding: 15px 0;
    transition: all 0.3s ease;
  }

  .save-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
    background: linear-gradient(135deg, #0097a7, #00695c);
  }

  .save-btn:disabled {
    opacity: 0.6;
    transform: none;
    box-shadow: none;
  }

  .pagination {
    text-align: center;
    margin-top: 20px;
  }

  /* 반응형 처리 */
  @media (max-width: 1200px) {
    .content-container {
      width: 80%;
    }
  }

  @media (max-width: 1024px) {
    .content-container {
      width: 95%;
      padding: 0 16px;
    }

    .main-layout {
      flex-direction: column;
      gap: 20px;
    }

    .sidebar {
      width: 100%;
      order: 2;
    }

    .main-content {
      order: 1;
    }

    .sidebar-card {
      position: relative;
      max-height: none;
    }

    .plan-content {
      max-height: none;
    }
  }

  @media (max-width: 768px) {
    .planner-wrapper {
      padding: 10px;
    }

    .map-container {
      height: 300px;
    }

    .filter-content,
    .results-content,
    .plan-content,
    .info-content {
      padding: 20px;
    }

    .plan-item {
      padding: 12px;
    }

    .plan-number {
      width: 28px;
      height: 28px;
      font-size: 0.8rem;
    }

    .plan-place-name {
      font-size: 0.9rem;
    }

    .plan-place-address {
      font-size: 0.8rem;
    }
  }
</style>
