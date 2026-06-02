<template>
  <v-container class="hotplace-container">
    <div class="page-header">
      <h1 class="page-title">🔥 여름 핫플레이스 🏖️</h1>
      <div class="wave-divider"></div>
      <p class="page-subtitle">시원한 여름 여행지를 찾아보세요!</p>
    </div>

    <v-row dense class="places-grid">
      <v-col
        v-for="place in hotPlaces"
        :key="place.id"
        cols="12"
        md="4"
        sm="6"
      >
        <v-card
          class="place-card"
          elevation="8"
          rounded="xl"
          @click="$router.push(`/hotplace/${place.id}`)"
        >
          <div class="image-container">
            <v-img
              cover
              height="220px"
              :src="place.image"
              class="place-image"
            >
              <div class="image-overlay">
                <v-icon class="overlay-icon" color="white">mdi-map-marker</v-icon>
              </div>
            </v-img>
          </div>

          <v-card-title class="place-title">
            {{ place.name }}
          </v-card-title>

          <v-card-text class="place-description">
            {{ place.description }}
          </v-card-text>

          <v-card-actions class="card-actions">
            <v-btn
              color="cyan"
              variant="elevated"
              rounded="xl"
              class="detail-btn"
              block
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
  import { ref } from 'vue'

  const hotPlaces = ref([
    { id: 1, name: '부산 해운대 해수욕장', description: '부산의 대표적인 바다 관광지', image: 'https://cdn.pixabay.com/photo/2022/02/08/06/18/bird-7000840_1280.jpg' },
    { id: 2, name: '강릉 안목해변', description: '커피 거리로 유명한 강릉 핫플레이스', image: 'https://cdn.pixabay.com/photo/2017/04/03/11/14/gangneung-2198026_1280.jpg' },
    { id: 3, name: '여수 해상케이블카', description: '여수 바다를 공중에서 감상할 수 있는 명소', image: 'https://lh3.googleusercontent.com/proxy/nsKYLVXVdOeZs48x2SEZPbRnk73LmjD7fGtKH3BSNUp0R79GcJcDGJJn2vF7ymiPJFg88j8kP0Shdk-sMVL0rxk6qjPDccCy2d1b' },
    { id: 4, name: '인천 차이나타운', description: '한국 속 중국 문화거리', image: 'https://i.namu.wiki/i/09U0IQ9nJXQku8-0WLAngFijEsEwZezg_k0027Niw5VbCLmw209xAE4jw2op1k2euxIo-s9IDLWN76cjcisnEA.webp' },
    { id: 5, name: '춘천 남이섬', description: '자연과 낭만이 어우러진 섬', image: 'https://cdn.pixabay.com/photo/2015/05/14/15/39/nami-766908_1280.jpg' },
    { id: 6, name: '대구 근대골목', description: '과거와 현재가 공존하는 골목', image: 'https://datacdn.ibtravel.co.kr/files/2024/03/25180832/d64dba0f836cd7379ec2f29018a08fb8_img-1.jpeg' },
    { id: 7, name: '전주 한옥마을', description: '전통과 현대가 공존하는 공간', image: 'https://cdn.pixabay.com/photo/2015/04/02/14/15/hanok-village-703824_1280.jpg' },
    { id: 8, name: '속초 아바이마을', description: '갯배 체험이 가능한 이색 마을', image: 'https://www.gangwon.to/upload/board/BDMAIN03/59084dc0-7a87-456a-94f0-15d5e977c542.jpg' },
    { id: 9, name: '수원 화성', description: '유네스코 세계문화유산', image: 'https://cdn.pixabay.com/photo/2019/11/01/05/50/suwon-4593383_1280.jpg' },
    { id: 10, name: '태안 꽃지해수욕장', description: '낙조로 유명한 태안의 해변', image: 'https://media.triple.guide/triple-cms/c_limit,f_auto,h_1024,w_1024/3efabfde-6a2e-42a4-9ec4-8ef0c6c2b7b3.jpeg' },
  ])
</script>

<style scoped>
  .hotplace-container {
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
    text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
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

  .places-grid {
    max-width: 1200px;
    margin: 0 auto;
  }

  .place-card {
    background: linear-gradient(135deg, rgba(255,255,255,0.95), rgba(240,248,255,0.95));
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255,255,255,0.3);
    transition: all 0.3s ease;
    cursor: pointer;
    height: 100%;
    display: flex;
    flex-direction: column;
  }

  .place-card:hover {
    transform: translateY(-8px);
    box-shadow: 0 15px 35px rgba(0,188,212,0.3);
  }

  .image-container {
    position: relative;
    overflow: hidden;
  }

  .place-image {
    transition: transform 0.3s ease;
  }

  .place-card:hover .place-image {
    transform: scale(1.05);
  }

  .image-overlay {
    position: absolute;
    top: 15px;
    right: 15px;
    background: rgba(0,188,212,0.8);
    border-radius: 50%;
    width: 40px;
    height: 40px;
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s ease;
  }

  .place-card:hover .image-overlay {
    opacity: 1;
  }

  .overlay-icon {
    font-size: 20px;
  }

  .place-title {
    color: #01579b;
    font-weight: 700;
    font-size: 1.2rem;
    padding: 20px 20px 10px;
  }

  .place-description {
    color: #0277bd;
    font-size: 0.95rem;
    line-height: 1.5;
    padding: 0 20px 20px;
    flex-grow: 1;
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
</style>
