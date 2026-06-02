<template>
  <div class="home-wrapper">
    <!-- 히어로 섹션 -->
    <section class="hero-section">
      <div class="hero-background">
        <div class="hero-overlay">
          <!-- 중앙 60% 컨테이너 -->
          <div class="hero-container">
            <div class="hero-content">
              <div class="hero-badge">
                <v-icon color="#26c6da" size="24">mdi-airplane</v-icon>
                <span>WANDARY</span>
              </div>

              <h1 class="hero-title">🌊 여행의 새로운 시작 🏖️</h1>

              <p class="hero-subtitle">
                전국 관광지를 편하게 찾아보고 나만의 여행 계획을 세워보세요 ✈️
              </p>

              <div class="hero-buttons">
                <v-btn
                  class="hero-btn primary-btn"
                  @click="plnner()"
                  rounded="xl"
                  elevation="8"
                  size="large"
                >
                  <v-icon left>mdi-calendar-plus</v-icon>
                  여행 계획 세우기
                </v-btn>

                <v-btn
                  class="hero-btn secondary-btn"
                  to="/trip"
                  rounded="xl"
                  elevation="4"
                  size="large"
                  variant="outlined"
                >
                  <v-icon left>mdi-map-search</v-icon>
                  여행지 둘러보기
                </v-btn>
                <v-btn
                  class="hero-btn secondary-btn"
                  @click="useAi()"
                  rounded="xl"
                  elevation="4"
                  size="large"
                  variant="outlined"
                >
                  <v-icon left>mdi-robot</v-icon>
                  AI 추천받기
                </v-btn>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 웨이브 효과 -->
      <div class="hero-wave">
        <svg viewBox="0 0 1200 120" preserveAspectRatio="none">
          <path
            d="M0,0V46.29c47.79,22.2,103.59,32.17,158,28,70.36-5.37,136.33-33.31,206.8-37.5C438.64,32.43,512.34,53.67,583,72.05c69.27,18,138.3,24.88,209.4,13.08,36.15-6,69.85-17.84,104.45-29.34C989.49,25,1047.71,5.4,1100,16.43V0Z"
            fill="rgba(255,255,255,0.1)"
          ></path>
        </svg>
      </div>
    </section>

    <!-- 메인 콘텐츠 -->
    <div class="main-content">
      <!-- 빠른 액세스 카드들 -->
      <section class="quick-access-section">
        <!-- 중앙 60% 컨테이너 -->
        <div class="content-container">
          <div class="section-header">
            <h2 class="section-title">🚀 빠른 시작</h2>
            <p class="section-subtitle">원하는 서비스를 바로 이용해보세요</p>
          </div>

          <v-row>
            <v-col
              cols="12"
              md="3"
              sm="6"
              v-for="(item, index) in quickAccessItems"
              :key="index"
            >
              <v-card
                class="quick-card"
                :to="item.requiresLogin ? undefined : item.route"
                @click="
                  item.requiresLogin ? handleQuickAccess(item) : undefined
                "
                elevation="8"
                rounded="xl"
                hover
              >
                <v-card-text class="quick-card-content">
                  <div class="quick-icon">
                    <v-icon :color="item.color" size="40">{{
                      item.icon
                    }}</v-icon>
                  </div>
                  <h3 class="quick-title">{{ item.title }}</h3>
                  <p class="quick-description">{{ item.description }}</p>
                </v-card-text>
              </v-card>
            </v-col>
          </v-row>
        </div>
      </section>

      <!-- 최신 게시글 섹션 -->
      <section class="posts-section">
        <div class="content-container">
          <div class="section-header">
            <h2 class="section-title">📢 최신 게시글</h2>
            <p class="section-subtitle">
              커뮤니티의 새로운 소식을 확인해보세요
            </p>
          </div>

          <v-card class="posts-card" elevation="8" rounded="xl">
            <v-card-text class="pa-0">
              <div v-if="recentPosts.length > 0">
                <div
                  v-for="(post, index) in recentPosts"
                  :key="post.no"
                  class="post-item"
                  @click="postdetail(post.no)"
                >
                  <div class="post-content">
                    <div class="post-header">
                      <h4 class="post-title">{{ post.title }}</h4>
                      <v-chip size="small" color="cyan" variant="tonal">
                        NEW
                      </v-chip>
                    </div>
                    <div class="post-meta">
                      <span class="post-author">
                        <v-icon size="16" color="#26c6da">mdi-account</v-icon>
                        {{ post.name }}
                      </span>
                      <span class="post-date">
                        <v-icon size="16" color="#26c6da">mdi-calendar</v-icon>
                        {{ post.regDate }}
                      </span>
                    </div>
                  </div>
                  <v-icon color="#26c6da">mdi-chevron-right</v-icon>
                </div>
              </div>

              <div v-else class="no-posts">
                <v-icon size="48" color="#b0bec5">mdi-post-outline</v-icon>
                <h4>게시글이 없습니다</h4>
                <p>첫 번째 게시글을 작성해보세요!</p>
              </div>
            </v-card-text>
          </v-card>
        </div>
      </section>

      <!-- 핫플레이스 섹션 -->
      <section class="hotplaces-section">
        <div class="content-container">
          <div class="section-header">
            <h2 class="section-title">🔥 인기 여행지</h2>
            <p class="section-subtitle">지금 가장 핫한 여행지를 만나보세요</p>
          </div>

          <v-carousel
            class="hotplace-carousel"
            height="450"
            hide-delimiters
            show-arrows="hover"
            cycle
            interval="5000"
          >
            <v-carousel-item v-for="(place, i) in hotPlaces" :key="i">
              <div class="carousel-item">
                <v-img
                  :src="place.image"
                  height="100%"
                  cover
                  class="carousel-image"
                >
                  <div class="carousel-overlay">
                    <div class="place-info">
                      <v-chip
                        color="cyan"
                        variant="elevated"
                        class="place-badge"
                      >
                        <v-icon left size="16">mdi-fire</v-icon>
                        HOT PLACE
                      </v-chip>
                      <h3 class="place-name">{{ place.name }}</h3>
                      <p class="place-description">{{ place.description }}</p>
                      <v-btn
                        color="white"
                        variant="elevated"
                        rounded="xl"
                        class="place-btn"
                      >
                        <v-icon left color="#26c6da">mdi-map-marker</v-icon>
                        위치 보기
                      </v-btn>
                    </div>
                  </div>
                </v-img>
              </div>
            </v-carousel-item>
          </v-carousel>
        </div>
      </section>

      <!-- 통계 섹션 -->
      <section class="stats-section">
        <div class="content-container">
          <v-card class="stats-card" elevation="8" rounded="xl">
            <v-card-text>
              <v-row>
                <v-col
                  cols="6"
                  md="3"
                  v-for="(stat, index) in stats"
                  :key="index"
                >
                  <div class="stat-item">
                    <div class="stat-icon">
                      <v-icon :color="stat.color" size="32">{{
                        stat.icon
                      }}</v-icon>
                    </div>
                    <div class="stat-content">
                      <h3 class="stat-number">{{ stat.number }}</h3>
                      <p class="stat-label">{{ stat.label }}</p>
                    </div>
                  </div>
                </v-col>
              </v-row>
            </v-card-text>
          </v-card>
        </div>
      </section>
    </div>
  </div>
</template>

<script setup>
  import { ref, onMounted, computed } from 'vue'
  import { usePostStore } from '@/store/post'
  import { useMemberStore } from '@/store/user'
  import { useRouter } from 'vue-router'

  const router = useRouter()
  const memberStore = useMemberStore()
  const postStore = usePostStore()

  const postdetail = (no) => {
    if (checkLogin()) {
      router.push({ name: 'PostDetail', params: { no } })
    }
  }

  const plnner = () => {
    if (checkLogin()) {
      router.push('/planner')
    }
  }

  const useAi = () => {
    if (checkLogin()) {
      router.push('/ai')
    }
  }

  const checkLogin = () => {
    if (!memberStore.isLoggedIn) {
      alert('로그인 후 이용하세요')
      return false
    } else {
      return true
    }
  }

  // 빠른 액세스 카드 클릭 핸들러
  const handleQuickAccess = (item) => {
    if (checkLogin()) {
      router.push(item.route)
    }
  }

  const recentPosts = computed(() => postStore.recentPosts)

  const quickAccessItems = ref([
    {
      title: '여행 계획',
      description: '나만의 여행 일정을 세워보세요',
      icon: 'mdi-calendar-plus',
      color: '#26c6da',
      route: '/planner',
      requiresLogin: true, // 로그인 필요
    },
    {
      title: '여행지 탐색',
      description: '다양한 여행 계획을 둘러보세요',
      icon: 'mdi-map-search',
      color: '#00acc1',
      route: '/plan',
      requiresLogin: true, // 로그인 필요
    },
    {
      title: '커뮤니티',
      description: '여행 후기를 공유해보세요',
      icon: 'mdi-forum',
      color: '#0097a7',
      route: '/board',
      requiresLogin: false, // 로그인 불필요
    },
    {
      title: '인기 차트',
      description: '인기 여행지를 확인해보세요',
      icon: 'mdi-chart-line',
      color: '#00838f',
      route: '/chart',
      requiresLogin: true, // 로그인 필요
    },
  ])

  const stats = ref([
    {
      number: '1,234+',
      label: '등록된 여행지',
      icon: 'mdi-map-marker',
      color: '#26c6da',
    },
    {
      number: '567+',
      label: '여행 계획',
      icon: 'mdi-calendar-check',
      color: '#00acc1',
    },
    {
      number: '890+',
      label: '커뮤니티 글',
      icon: 'mdi-post',
      color: '#0097a7',
    },
    {
      number: '2,345+',
      label: '활성 사용자',
      icon: 'mdi-account-group',
      color: '#00838f',
    },
  ])

  const hotPlaces = ref([
    {
      id: 1,
      name: '부산 해운대 해수욕장',
      description: '부산의 대표적인 바다 관광지로 여름 휴가철 최고의 선택',
      image:
        'https://cdn.pixabay.com/photo/2022/02/08/06/18/bird-7000840_1280.jpg',
    },
    {
      id: 2,
      name: '강릉 안목해변',
      description: '커피 거리로 유명한 강릉의 핫플레이스',
      image:
        'https://cdn.pixabay.com/photo/2017/04/03/11/14/gangneung-2198026_1280.jpg',
    },
    {
      id: 3,
      name: '여수 해상케이블카',
      description: '여수 바다를 공중에서 감상할 수 있는 명소',
      image:
        'https://lh3.googleusercontent.com/proxy/nsKYLVXVdOeZs48x2SEZPbRnk73LmjD7fGtKH3BSNUp0R79GcJcDGJJn2vF7ymiPJFg88j8kP0Shdk-sMVL0rxk6qjPDccCy2d1b',
    },
    {
      id: 4,
      name: '전주 한옥마을',
      description: '전통과 현대가 공존하는 아름다운 공간',
      image:
        'https://cdn.pixabay.com/photo/2015/04/02/14/15/hanok-village-703824_1280.jpg',
    },
    {
      id: 5,
      name: '춘천 남이섬',
      description: '자연과 낭만이 어우러진 섬',
      image:
        'https://cdn.pixabay.com/photo/2015/05/14/15/39/nami-766908_1280.jpg',
    },
  ])

  onMounted(() => {
    postStore.getRecentPost()
  })
</script>

<style scoped>
  .home-wrapper {
    background: linear-gradient(180deg, #f8fdff 0%, #e3f2fd 100%);
    min-height: 100vh;
  }

  /* 히어로 섹션 */
  .hero-section {
    position: relative;
    height: 600px;
    overflow: hidden;
  }

  .hero-background {
    background: linear-gradient(135deg, #667eea 0%, #764ba2 50%, #26c6da 100%);
    height: 100%;
    position: relative;
  }

  .hero-overlay {
    background: linear-gradient(
      45deg,
      rgba(0, 188, 212, 0.2),
      rgba(0, 150, 136, 0.2)
    );
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    position: relative;
    z-index: 2;
  }

  /* 중앙 60% 컨테이너 */
  .hero-container {
    width: 60%;
    margin: 0 auto;
    text-align: center;
  }

  .content-container {
    width: 60%;
    margin: 0 auto;
    padding: 0 24px;
  }

  .hero-content {
    animation: fadeInUp 1s ease-out;
  }

  .hero-badge {
    display: inline-flex;
    align-items: center;
    gap: 8px;
    background: rgba(255, 255, 255, 0.2);
    backdrop-filter: blur(10px);
    padding: 8px 16px;
    border-radius: 20px;
    color: white;
    font-weight: 600;
    margin-bottom: 24px;
    border: 1px solid rgba(255, 255, 255, 0.3);
  }

  .hero-title {
    font-size: 3.5rem;
    font-weight: 800;
    color: white;
    text-shadow: 2px 2px 8px rgba(0, 0, 0, 0.3);
    margin-bottom: 24px;
    line-height: 1.2;
  }

  .hero-subtitle {
    font-size: 1.3rem;
    color: rgba(255, 255, 255, 0.9);
    text-shadow: 1px 1px 4px rgba(0, 0, 0, 0.3);
    margin-bottom: 40px;
    max-width: 600px;
    margin-left: auto;
    margin-right: auto;
  }

  .hero-buttons {
    display: flex;
    gap: 16px;
    justify-content: center;
    flex-wrap: wrap;
  }

  .hero-btn {
    text-transform: none;
    font-weight: 600;
    font-size: 1.1rem;
    padding: 12px 32px !important;
    transition: all 0.3s ease;
  }

  .primary-btn {
    background: linear-gradient(135deg, #26c6da, #00acc1) !important;
    color: white !important;
  }

  .primary-btn:hover {
    background: linear-gradient(135deg, #00acc1, #0097a7) !important;
    transform: translateY(-4px);
    box-shadow: 0 8px 25px rgba(38, 198, 218, 0.4);
  }

  .secondary-btn {
    border: 2px solid white !important;
    color: white !important;
    background: rgba(255, 255, 255, 0.1) !important;
    backdrop-filter: blur(10px);
  }

  .secondary-btn:hover {
    background: rgba(255, 255, 255, 0.2) !important;
    transform: translateY(-4px);
    box-shadow: 0 8px 25px rgba(255, 255, 255, 0.2);
  }

  .hero-wave {
    position: absolute;
    bottom: 0;
    left: 0;
    width: 100%;
    overflow: hidden;
    line-height: 0;
    z-index: 3;
  }

  .hero-wave svg {
    position: relative;
    display: block;
    width: calc(100% + 1.3px);
    height: 60px;
    fill: #f8fdff;
  }

  /* 메인 콘텐츠 */
  .main-content {
    position: relative;
    z-index: 4;
    margin-top: -1px;
  }

  .section-header {
    text-align: center;
    margin-bottom: 48px;
  }

  .section-title {
    font-size: 2.5rem;
    font-weight: 700;
    color: #0097a7;
    margin-bottom: 12px;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
  }

  .section-subtitle {
    font-size: 1.1rem;
    color: #00838f;
    opacity: 0.8;
  }

  /* 빠른 액세스 섹션 */
  .quick-access-section {
    padding: 80px 0 60px;
  }

  .quick-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(240, 248, 255, 0.9)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    transition: all 0.3s ease;
    cursor: pointer;
    height: 100%;
  }

  .quick-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 12px 40px rgba(38, 198, 218, 0.2);
  }

  .quick-card-content {
    text-align: center;
    padding: 32px 24px;
  }

  .quick-icon {
    background: linear-gradient(
      135deg,
      rgba(38, 198, 218, 0.1),
      rgba(0, 172, 193, 0.1)
    );
    border-radius: 20px;
    width: 80px;
    height: 80px;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 auto 20px;
  }

  .quick-title {
    color: #0097a7;
    font-weight: 600;
    font-size: 1.3rem;
    margin-bottom: 12px;
  }

  .quick-description {
    color: #00838f;
    font-size: 0.95rem;
    opacity: 0.8;
    margin: 0;
  }

  /* 게시글 섹션 */
  .posts-section {
    padding: 60px 0;
  }

  .posts-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(240, 248, 255, 0.9)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
  }

  .post-item {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 20px 24px;
    border-bottom: 1px solid rgba(38, 198, 218, 0.1);
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .post-item:last-child {
    border-bottom: none;
  }

  .post-item:hover {
    background: rgba(38, 198, 218, 0.05);
    transform: translateX(8px);
  }

  .post-content {
    flex: 1;
  }

  .post-header {
    display: flex;
    align-items: center;
    gap: 12px;
    margin-bottom: 8px;
  }

  .post-title {
    color: #0097a7;
    font-weight: 600;
    font-size: 1.1rem;
    margin: 0;
  }

  .post-meta {
    display: flex;
    gap: 16px;
    font-size: 0.9rem;
    color: #00838f;
  }

  .post-author,
  .post-date {
    display: flex;
    align-items: center;
    gap: 4px;
  }

  .no-posts {
    text-align: center;
    padding: 60px 24px;
    color: #b0bec5;
  }

  .no-posts h4 {
    margin: 16px 0 8px;
    color: #90a4ae;
  }

  .no-posts p {
    margin: 0;
    opacity: 0.8;
  }

  /* 핫플레이스 섹션 */
  .hotplaces-section {
    padding: 60px 0;
  }

  .hotplace-carousel {
    border-radius: 24px;
    overflow: hidden;
    box-shadow: 0 12px 40px rgba(0, 0, 0, 0.15);
  }

  .carousel-item {
    position: relative;
    height: 100%;
  }

  .carousel-image {
    height: 100%;
  }

  .carousel-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(transparent 40%, rgba(0, 0, 0, 0.7));
    display: flex;
    align-items: flex-end;
    padding: 40px;
  }

  .place-info {
    color: white;
    max-width: 500px;
  }

  .place-badge {
    margin-bottom: 16px;
  }

  .place-name {
    font-size: 2.2rem;
    font-weight: 700;
    margin-bottom: 12px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  }

  .place-description {
    font-size: 1.1rem;
    margin-bottom: 24px;
    opacity: 0.9;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
  }

  .place-btn {
    color: #26c6da !important;
    font-weight: 600;
  }

  /* 통계 섹션 */
  .stats-section {
    padding: 60px 0 80px;
  }

  .stats-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(240, 248, 255, 0.9)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
  }

  .stat-item {
    display: flex;
    align-items: center;
    gap: 16px;
    text-align: left;
    padding: 20px;
  }

  .stat-icon {
    background: linear-gradient(
      135deg,
      rgba(38, 198, 218, 0.1),
      rgba(0, 172, 193, 0.1)
    );
    border-radius: 16px;
    width: 64px;
    height: 64px;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-shrink: 0;
  }

  .stat-number {
    font-size: 1.8rem;
    font-weight: 700;
    color: #0097a7;
    margin-bottom: 4px;
  }

  .stat-label {
    color: #00838f;
    font-size: 0.9rem;
    margin: 0;
    opacity: 0.8;
  }

  /* 애니메이션 */
  @keyframes fadeInUp {
    from {
      opacity: 0;
      transform: translateY(40px);
    }
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

  /* 반응형 */
  @media (max-width: 1200px) {
    .hero-container,
    .content-container {
      width: 80%;
    }
  }

  @media (max-width: 768px) {
    .hero-container,
    .content-container {
      width: 95%;
      padding: 0 16px;
    }

    .hero-title {
      font-size: 2.5rem;
    }

    .hero-subtitle {
      font-size: 1.1rem;
    }

    .hero-buttons {
      flex-direction: column;
      align-items: center;
    }

    .hero-btn {
      width: 100%;
      max-width: 280px;
    }

    .section-title {
      font-size: 2rem;
    }

    .quick-access-section {
      padding: 60px 0 40px;
    }

    .stat-item {
      flex-direction: column;
      text-align: center;
      gap: 12px;
    }

    .carousel-overlay {
      padding: 24px;
    }

    .place-name {
      font-size: 1.8rem;
    }

    .place-description {
      font-size: 1rem;
    }
  }

  @media (max-width: 480px) {
    .hero-title {
      font-size: 2rem;
    }

    .post-meta {
      flex-direction: column;
      gap: 8px;
    }

    .post-item {
      padding: 16px;
    }
  }
</style>
