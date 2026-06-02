<template>
  <v-container v-if="place.info" class="detail-container">
    <v-row justify="center">
      <v-col cols="12" md="10" lg="8">
        <!-- 뒤로가기 버튼 -->
        <v-btn
          @click="$router.back()"
          color="cyan"
          class="back-btn"
          rounded="xl"
          elevation="4"
        >
          🔙 목록으로 돌아가기
        </v-btn>

        <v-card class="place-detail-card" elevation="12" rounded="xl">
          <!-- 이미지 섹션 -->
          <div class="image-section">
            <v-img
              cover
              height="400px"
              :src="place.image"
              class="place-main-image"
            >
              <div class="image-overlay">
                <div class="overlay-content">
                  <h1 class="place-main-title">{{ place.name }}</h1>
                  <p class="place-main-description">{{ place.description }}</p>
                </div>
              </div>
            </v-img>
          </div>

          <!-- 정보 섹션 -->
          <v-card-text class="detail-content">
            <div class="info-grid">
              <v-card class="info-card" elevation="4" rounded="lg">
                <v-card-text>
                  <div class="info-item">
                    <v-icon color="cyan" class="info-icon"
                      >mdi-map-marker</v-icon
                    >
                    <div class="info-text">
                      <h4 class="info-title">📍 위치</h4>
                      <p class="info-value">{{ place.info.location }}</p>
                    </div>
                  </div>
                </v-card-text>
              </v-card>

              <v-card class="info-card" elevation="4" rounded="lg">
                <v-card-text>
                  <div class="info-item">
                    <v-icon color="teal" class="info-icon">mdi-clock</v-icon>
                    <div class="info-text">
                      <h4 class="info-title">⏰ 운영 시간</h4>
                      <p class="info-value">{{ place.info.openHours }}</p>
                    </div>
                  </div>
                </v-card-text>
              </v-card>

              <v-card class="info-card" elevation="4" rounded="lg">
                <v-card-text>
                  <div class="info-item">
                    <v-icon color="green" class="info-icon">mdi-cash</v-icon>
                    <div class="info-text">
                      <h4 class="info-title">💰 이용 요금</h4>
                      <p class="info-value">{{ place.info.fee }}</p>
                    </div>
                  </div>
                </v-card-text>
              </v-card>

              <v-card class="info-card" elevation="4" rounded="lg">
                <v-card-text>
                  <div class="info-item">
                    <v-icon color="blue" class="info-icon">mdi-tag</v-icon>
                    <div class="info-text">
                      <h4 class="info-title">🏷️ 분류</h4>
                      <p class="info-value">{{ place.info.category }}</p>
                    </div>
                  </div>
                </v-card-text>
              </v-card>
            </div>

            <v-divider class="detail-divider" />

            <!-- 상세 설명 -->
            <v-card class="description-card" elevation="4" rounded="lg">
              <v-card-title class="description-title">
                📖 상세 정보
              </v-card-title>
              <v-card-text class="description-content">
                {{ place.details }}
              </v-card-text>
            </v-card>
          </v-card-text>

          <v-card-actions class="card-actions">
            <v-btn
              color="black"
              @click="$router.back()"
              rounded="xl"
              elevation="6"
              size="large"
              block
              class="action-btn"
            >
              🌊 목록으로 돌아가기
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script setup>
  import { onMounted, ref } from 'vue'
  import { useRoute } from 'vue-router'

  const route = useRoute()
  const place = ref({})
  const id = route.params.id

  const hotPlaces = [
    {
      id: 1,
      name: '부산 해운대 해수욕장',
      description: '부산의 대표적인 바다 관광지',
      image:
        'https://cdn.pixabay.com/photo/2022/02/08/06/18/bird-7000840_1280.jpg',
      details: `해운대는 맑은 바다와 넓은 백사장으로 유명한 휴양지로, 부산의 대표 관광지야. 여름엔 해수욕장, 겨울엔 야경이 아름다워.`,
      info: {
        location: '부산광역시 해운대구 해운대해변로',
        openHours: '24시간',
        fee: '무료',
        category: '자연 / 해변',
      },
    },
    {
      id: 2,
      name: '강릉 안목해변',
      description: '커피 거리로 유명한 강릉 핫플레이스',
      image:
        'https://cdn.pixabay.com/photo/2017/04/03/11/14/gangneung-2198026_1280.jpg',
      details: `안목해변은 해변 따라 있는 카페거리로 유명하고, 조용한 휴식을 원하는 사람들에게 딱이야.`,
      info: {
        location: '강원도 강릉시 창해로14번길',
        openHours: '24시간',
        fee: '무료',
        category: '자연 / 해변 / 카페거리',
      },
    },
    {
      id: 3,
      name: '여수 해상케이블카',
      description: '여수 바다를 공중에서 감상할 수 있는 명소',
      image:
        'https://lh3.googleusercontent.com/proxy/nsKYLVXVdOeZs48x2SEZPbRnk73LmjD7fGtKH3BSNUp0R79GcJcDGJJn2vF7ymiPJFg88j8kP0Shdk-sMVL0rxk6qjPDccCy2d1b',
      details:
        '여수 해상케이블카는 바다 위를 가로지르며 돌산과 자산을 연결해줘. 여수밤바다의 아름다움을 감상할 수 있는 최고의 스팟이야.',
      info: {
        location: '전라남도 여수시 돌산로',
        openHours: '09:00 ~ 22:00',
        fee: '왕복 15,000원',
        category: '체험 / 케이블카',
      },
    },
    {
      id: 4,
      name: '인천 차이나타운',
      description: '한국 속 중국 문화거리',
      image:
        'https://i.namu.wiki/i/09U0IQ9nJXQku8-0WLAngFijEsEwZezg_k0027Niw5VbCLmw209xAE4jw2op1k2euxIo-s9IDLWN76cjcisnEA.webp',
      details:
        '1884년에 생긴 한국 유일의 차이나타운으로 다양한 중국 음식을 즐길 수 있고, 중국풍 건축물로 가득한 특색 있는 거리야.',
      info: {
        location: '인천광역시 중구 차이나타운로',
        openHours: '10:00 ~ 22:00',
        fee: '무료',
        category: '문화 / 거리',
      },
    },
    {
      id: 5,
      name: '춘천 남이섬',
      description: '자연과 낭만이 어우러진 섬',
      image:
        'https://cdn.pixabay.com/photo/2015/05/14/15/39/nami-766908_1280.jpg',
      details:
        '남이섬은 드라마 촬영지로 유명하고, 사계절 내내 자연이 아름다운 섬이야. 배 타고 들어가며 색다른 재미도 있어.',
      info: {
        location: '강원도 춘천시 남산면',
        openHours: '08:00 ~ 21:00',
        fee: '입장료 13,000원 (왕복 배편 포함)',
        category: '자연 / 섬 / 관광지',
      },
    },
    {
      id: 6,
      name: '대구 근대골목',
      description: '과거와 현재가 공존하는 골목',
      image:
        'https://datacdn.ibtravel.co.kr/files/2024/03/25180832/d64dba0f836cd7379ec2f29018a08fb8_img-1.jpeg',
      details:
        '대구 근대골목은 일제강점기와 해방 이후의 흔적이 남아 있는 공간으로, 문화 해설사와 함께 걷는 투어가 인기야.',
      info: {
        location: '대구광역시 중구 종로 일대',
        openHours: '상시 개방',
        fee: '무료',
        category: '역사 / 골목길',
      },
    },
    {
      id: 7,
      name: '전주 한옥마을',
      description: '전통과 현대가 공존하는 공간',
      image:
        'https://cdn.pixabay.com/photo/2015/04/02/14/15/hanok-village-703824_1280.jpg',
      details:
        '700여 채의 한옥이 모여있는 전통 마을이야. 전통 음식, 한복체험, 공예품까지 즐길 수 있는 전주 대표 관광지.',
      info: {
        location: '전라북도 전주시 완산구 교동',
        openHours: '상시 개방',
        fee: '무료 (체험은 유료)',
        category: '문화 / 전통마을',
      },
    },
    {
      id: 8,
      name: '속초 아바이마을',
      description: '갯배 체험이 가능한 이색 마을',
      image:
        'https://www.gangwon.to/upload/board/BDMAIN03/59084dc0-7a87-456a-94f0-15d5e977c542.jpg',
      details:
        '속초의 아바이마을은 함경도 실향민들이 모여 살던 동네로, 직접 갯배를 끌고 타보는 독특한 체험이 있어.',
      info: {
        location: '강원도 속초시 청호동',
        openHours: '09:00 ~ 18:00',
        fee: '갯배 500원',
        category: '역사 / 체험마을',
      },
    },
    {
      id: 9,
      name: '수원 화성',
      description: '유네스코 세계문화유산',
      image:
        'https://cdn.pixabay.com/photo/2019/11/01/05/50/suwon-4593383_1280.jpg',
      details:
        '정조대왕이 아버지 사도세자를 기리기 위해 만든 성곽으로, 세계문화유산으로 지정된 소중한 역사 유적이야.',
      info: {
        location: '경기도 수원시 팔달구',
        openHours: '09:00 ~ 18:00',
        fee: '입장료 1,000원',
        category: '역사 / 성곽 / 유산',
      },
    },
    {
      id: 10,
      name: '태안 꽃지해수욕장',
      description: '낙조로 유명한 태안의 해변',
      image:
        'https://media.triple.guide/triple-cms/c_limit,f_auto,h_1024,w_1024/3efabfde-6a2e-42a4-9ec4-8ef0c6c2b7b3.jpeg',
      details:
        '석양이 아름답기로 유명한 해수욕장이야. 할미바위와 할아비바위 사이로 떨어지는 해는 전국에서 사진 명소로 알려져 있어.',
      info: {
        location: '충청남도 태안군 안면읍',
        openHours: '24시간',
        fee: '무료',
        category: '자연 / 해변 / 일몰명소',
      },
    },

    // 나머지 8개도 같은 형식으로 추가 가능
  ]

  onMounted(() => {
    const target = hotPlaces.find((p) => p.id === parseInt(id))
    if (target) {
      place.value = target
    }
  })
</script>

<style scoped>
  .detail-container {
    background: linear-gradient(135deg, #e3f2fd 0%, #f1f8e9 100%);
    min-height: 100vh;
    padding: 40px 20px;
  }

  .back-btn {
    margin-bottom: 30px;
    text-transform: none;
    font-weight: 600;
    transition: all 0.3s ease;
  }

  .back-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
  }

  .place-detail-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.95),
      rgba(240, 248, 255, 0.95)
    );
    backdrop-filter: blur(10px);
    border: 1px solid rgba(255, 255, 255, 0.3);
    overflow: hidden;
  }

  .image-section {
    position: relative;
    overflow: hidden;
  }

  .place-main-image {
    transition: transform 0.3s ease;
  }

  .image-overlay {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background: linear-gradient(transparent, rgba(0, 0, 0, 0.7));
    display: flex;
    align-items: flex-end;
  }

  .overlay-content {
    padding: 40px;
    color: white;
    width: 100%;
  }

  .place-main-title {
    font-size: 2.5rem;
    font-weight: 700;
    margin-bottom: 15px;
    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
  }

  .place-main-description {
    font-size: 1.2rem;
    opacity: 0.9;
    text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.5);
  }

  .detail-content {
    padding: 40px;
  }

  .info-grid {
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
    gap: 20px;
    margin-bottom: 40px;
  }

  .info-card {
    background: linear-gradient(
      135deg,
      rgba(255, 255, 255, 0.9),
      rgba(248, 251, 255, 0.9)
    );
    border: 1px solid rgba(0, 188, 212, 0.2);
    transition: all 0.3s ease;
  }

  .info-card:hover {
    transform: translateY(-5px);
    box-shadow: 0 10px 25px rgba(0, 188, 212, 0.2);
  }

  .info-item {
    display: flex;
    align-items: center;
    gap: 15px;
  }

  .info-icon {
    font-size: 2rem;
  }

  .info-text {
    flex: 1;
  }

  .info-title {
    color: #0277bd;
    font-weight: 700;
    margin-bottom: 8px;
  }

  .info-value {
    color: #01579b;
    font-size: 1rem;
    line-height: 1.5;
  }

  .detail-divider {
    margin: 40px 0;
    border-color: rgba(0, 188, 212, 0.3);
    border-width: 2px;
  }

  .description-card {
    background: linear-gradient(
      135deg,
      rgba(0, 188, 212, 0.05),
      rgba(0, 150, 136, 0.05)
    );
    border: 1px solid rgba(0, 188, 212, 0.2);
  }

  .description-title {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    font-weight: 700;
    padding: 20px;
  }

  .description-content {
    color: #01579b;
    font-size: 1.1rem;
    line-height: 1.8;
    padding: 30px;
  }

  .card-actions {
    padding: 30px 40px 40px;
  }

  .action-btn {
    background: linear-gradient(135deg, #00bcd4, #009688);
    color: white;
    text-transform: none;
    font-weight: 700;
    font-size: 1.1rem;
    padding: 15px 0;
    transition: all 0.3s ease;
  }

  .action-btn:hover {
    transform: translateY(-3px);
    box-shadow: 0 8px 25px rgba(0, 188, 212, 0.3);
    background: linear-gradient(135deg, #0097a7, #00695c);
  }

  @media (max-width: 768px) {
    .place-main-title {
      font-size: 2rem;
    }

    .place-main-description {
      font-size: 1rem;
    }

    .overlay-content {
      padding: 20px;
    }

    .detail-content {
      padding: 20px;
    }

    .info-grid {
      grid-template-columns: 1fr;
    }
  }
</style>
