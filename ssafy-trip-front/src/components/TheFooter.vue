<template>
  <v-footer app class="footer-summer">
    <!-- 중앙 60% 컨테이너 -->
    <div class="footer-container">
      <v-row align="center" justify="center">
        <!-- 로고 및 브랜드 -->
        <v-col cols="10" md="4" class="text-center text-md-left">
          <div class="footer-brand">
            <div class="footer-logo">
              <v-icon color="#26c6da" size="24">mdi-airplane</v-icon>
              <span class="footer-brand-name">WANDARY</span>
            </div>
            <p class="footer-tagline">여행의 새로운 시작</p>
          </div>
        </v-col>

        <!-- 링크 메뉴 -->
        <v-col cols="12" md="4" class="text-center">
          <div class="footer-links">
            <a href="/" class="footer-link">홈</a>
            <a href="/trip" class="footer-link">관광지</a>
            <a href="/about" class="footer-link">소개</a>
          </div>
        </v-col>

        <!-- 소셜 미디어 및 정보 -->
        <v-col cols="12" md="4" class="text-center text-md-right">
          <div class="footer-social">
            <v-btn
              icon
              size="small"
              class="social-btn"
              href="https://github.com/ssafy"
              target="_blank"
            >
              <v-icon size="20">mdi-github</v-icon>
            </v-btn>
            <v-btn
              icon
              size="small"
              class="social-btn"
              href="https://www.ssafy.com"
              target="_blank"
            >
              <v-icon size="20">mdi-web</v-icon>
            </v-btn>
            <v-btn
              icon
              size="small"
              class="social-btn"
              href="mailto:ssafy@ssafy.com"
            >
              <v-icon size="20">mdi-email</v-icon>
            </v-btn>
          </div>
        </v-col>
      </v-row>

      <!-- 구분선 -->
      <v-divider class="footer-divider" />

      <!-- 저작권 정보 -->
      <v-row>
        <v-col cols="12" class="text-center">
          <div class="footer-copyright">
            <v-icon left color="#4dd0e1" size="16">mdi-palm-tree</v-icon>
            <span class="copyright-text"
              >ⓒ 2025 SSAFY Trip. All rights reserved.</span
            >
            <div class="footer-info">
              <span class="info-item">개인정보처리방침</span>
              <span class="info-divider">|</span>
              <span class="info-item">이용약관</span>
              <span class="info-divider">|</span>
              <span class="info-item">고객센터</span>
            </div>
          </div>
        </v-col>
      </v-row>
    </div>
  </v-footer>
</template>

<style scoped>
  .footer-summer {
    background: linear-gradient(135deg, #e0f7fa 0%, #b2ebf2 50%, #80deea 100%);
    border-top-left-radius: 32px;
    border-top-right-radius: 32px;
    box-shadow: 0 -4px 20px rgba(0, 180, 216, 0.15);
    backdrop-filter: blur(10px);
    margin-top: 60px;
  }

  /* 중앙 60% 컨테이너 */
  .footer-container {
    width: 60%;
    margin: 0 auto;
    padding: 40px 20px 20px;
  }

  .footer-brand {
    margin-bottom: 16px;
  }

  .footer-logo {
    display: flex;
    align-items: center;
    justify-content: center;
    justify-content: flex-start;
    gap: 8px;
    margin-bottom: 8px;
  }

  .footer-brand-name {
    color: #0097a7;
    font-size: 1.3rem;
    font-weight: 700;
    letter-spacing: 1px;
  }

  .footer-tagline {
    color: #00838f;
    font-size: 0.9rem;
    margin: 0;
    opacity: 0.8;
  }

  .footer-links {
    display: flex;
    justify-content: center;
    gap: 24px;
    flex-wrap: wrap;
    margin-bottom: 16px;
  }

  .footer-link {
    color: #0097a7;
    text-decoration: none;
    font-weight: 500;
    font-size: 0.95rem;
    transition: all 0.3s ease;
    position: relative;
  }

  .footer-link::after {
    content: '';
    position: absolute;
    bottom: -4px;
    left: 0;
    width: 0;
    height: 2px;
    background: linear-gradient(90deg, #26c6da, #00acc1);
    transition: width 0.3s ease;
  }

  .footer-link:hover {
    color: #00695c;
    transform: translateY(-2px);
  }

  .footer-link:hover::after {
    width: 100%;
  }

  .footer-social {
    display: flex;
    justify-content: center;
    justify-content: flex-end;
    gap: 8px;
    margin-bottom: 16px;
  }

  .social-btn {
    background: rgba(255, 255, 255, 0.3);
    color: #0097a7 !important;
    border-radius: 12px;
    transition: all 0.3s ease;
  }

  .social-btn:hover {
    background: rgba(38, 198, 218, 0.2) !important;
    transform: translateY(-2px) scale(1.1);
    box-shadow: 0 4px 12px rgba(38, 198, 218, 0.3);
  }

  .footer-divider {
    margin: 24px 0;
    background: linear-gradient(90deg, transparent, #26c6da, transparent);
    height: 1px;
    border: none;
  }

  .footer-copyright {
    display: flex;
    flex-direction: column;
    align-items: center;
    gap: 12px;
  }

  .copyright-text {
    color: #0097a7;
    font-size: 0.9rem;
    font-weight: 500;
    letter-spacing: 0.5px;
  }

  .footer-info {
    display: flex;
    align-items: center;
    gap: 12px;
    flex-wrap: wrap;
    justify-content: center;
  }

  .info-item {
    color: #00838f;
    font-size: 0.8rem;
    cursor: pointer;
    transition: color 0.3s ease;
  }

  .info-item:hover {
    color: #0097a7;
  }

  .info-divider {
    color: #b0bec5;
    font-size: 0.8rem;
  }

  /* 반응형 처리 */
  @media (max-width: 1200px) {
    .footer-container {
      width: 80%;
    }
  }

  @media (max-width: 768px) {
    .footer-container {
      width: 95%;
      padding: 30px 16px 16px;
    }

    .footer-logo {
      justify-content: center;
    }

    .footer-social {
      justify-content: center;
    }

    .footer-links {
      gap: 16px;
    }

    .footer-link {
      font-size: 0.9rem;
    }

    .footer-info {
      gap: 8px;
    }

    .info-item {
      font-size: 0.75rem;
    }
  }

  @media (max-width: 480px) {
    .footer-links {
      flex-direction: column;
      gap: 12px;
    }

    .footer-info {
      flex-direction: column;
      gap: 8px;
    }

    .info-divider {
      display: none;
    }
  }
</style>
