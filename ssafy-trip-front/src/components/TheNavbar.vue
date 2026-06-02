<template>
  <v-app-bar app flat class="navbar-summer" height="70">
    <!-- 중앙 60% 컨테이너 -->
    <div class="navbar-container">
      <!-- 로고 및 브랜드명 -->
      <v-toolbar-title class="navbar-brand" @click="$router.push('/')">
        <div class="brand-container">
          <div class="logo-wrapper">
            <v-icon color="#26c6da" size="32">mdi-airplane</v-icon>
          </div>
          <div class="brand-text">
            <span class="brand-name">WANDARY</span>
            <span class="brand-subtitle">여행의 새로운 시작</span>
          </div>
        </div>
      </v-toolbar-title>

      <v-spacer />

      <!-- 네비게이션 메뉴 -->
      <div class="nav-menu d-none d-md-flex">
        <v-btn
          text
          to="/"
          class="nav-btn"
          :class="{ 'nav-btn-active': $route.path === '/' }"
        >
          <v-icon left size="18">mdi-home</v-icon>
          홈
        </v-btn>

        <v-btn
          text
          to="/trip"
          class="nav-btn"
          :class="{ 'nav-btn-active': $route.path === '/trip' }"
        >
          <v-icon left size="18">mdi-camera</v-icon>
          관광지
        </v-btn>
      </div>

      <!-- 사용자 메뉴 -->
      <div class="user-menu d-none d-md-flex">
        <template v-if="memberStore.isLoggedIn">
          <!-- 사용자 정보 드롭다운 -->
          <v-menu offset-y>
            <template v-slot:activator="{ props }">
              <v-btn text class="user-btn" v-bind="props">
                <v-avatar size="32" class="mr-2">
                  <v-icon color="#26c6da">mdi-account-circle</v-icon>
                </v-avatar>
                <span class="user-name"
                  >{{ memberStore.loginUser.user.name }}님</span
                >
                <v-icon right size="18">mdi-chevron-down</v-icon>
              </v-btn>
            </template>

            <v-list class="user-dropdown">
              <v-list-item @click="$router.push('/profile')">
                <v-list-item-content>
                  <v-list-item-title>
                    <v-icon left size="18" color="#26c6da"
                      >mdi-account-edit</v-icon
                    >
                    회원정보 수정
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>

              <v-list-item @click="$router.push(`/my-plans`)">
                <v-list-item-content>
                  <v-list-item-title>
                    <v-icon left size="18" color="#26c6da">mdi-bookmark</v-icon>
                    내 여행계획
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>

              <v-divider />

              <v-list-item @click="logout">
                <v-list-item-content>
                  <v-list-item-title class="text-red-600">
                    <v-icon left size="18" color="red">mdi-logout</v-icon>
                    로그아웃
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
          </v-menu>
        </template>

        <template v-else>
          <v-btn text to="/login" class="auth-btn login-btn">
            <v-icon left size="18">mdi-login</v-icon>
            로그인
          </v-btn>

          <v-btn elevated to="/register" class="auth-btn register-btn">
            <v-icon left size="18">mdi-account-plus</v-icon>
            회원가입
          </v-btn>
        </template>
      </div>

      <!-- 모바일 메뉴 버튼 -->
      <v-btn
        icon
        class="d-md-none mobile-menu-btn"
        @click="mobileDrawer = !mobileDrawer"
      >
        <v-icon>mdi-menu</v-icon>
      </v-btn>
    </div>
  </v-app-bar>

  <!-- 모바일 네비게이션 드로어 -->
  <v-navigation-drawer
    v-model="mobileDrawer"
    temporary
    location="right"
    class="mobile-drawer"
  >
    <v-list class="mobile-nav-list">
      <v-list-item class="mobile-user-info" v-if="memberStore.isLoggedIn">
        <v-list-item-content>
          <v-list-item-title class="mobile-user-name">
            <v-avatar size="40" class="mr-3">
              <v-icon color="#26c6da">mdi-account-circle</v-icon>
            </v-avatar>
            {{ memberStore.loginUser.user.name }}님
          </v-list-item-title>
          <v-list-item-subtitle class="mobile-user-subtitle">
            환영합니다!
          </v-list-item-subtitle>
        </v-list-item-content>
      </v-list-item>

      <v-divider v-if="memberStore.isLoggedIn" />

      <v-list-item to="/" @click="mobileDrawer = false">
        <v-list-item-content>
          <v-list-item-title>
            <v-icon left color="#26c6da">mdi-home</v-icon>
            홈
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item to="/plan" @click="mobileDrawer = false">
        <v-list-item-content>
          <v-list-item-title>
            <v-icon left color="#26c6da">mdi-map</v-icon>
            여행계획
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <v-list-item to="/attractions" @click="mobileDrawer = false">
        <v-list-item-content>
          <v-list-item-title>
            <v-icon left color="#26c6da">mdi-camera</v-icon>
            관광지
          </v-list-item-title>
        </v-list-item-content>
      </v-list-item>

      <template v-if="memberStore.isLoggedIn">
        <v-divider />

        <v-list-item to="/profile" @click="mobileDrawer = false">
          <v-list-item-content>
            <v-list-item-title>
              <v-icon left color="#26c6da">mdi-account-edit</v-icon>
              회원정보 수정
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item to="/my-plans" @click="mobileDrawer = false">
          <v-list-item-content>
            <v-list-item-title>
              <v-icon left color="#26c6da">mdi-bookmark</v-icon>
              내 여행계획
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item @click="logout">
          <v-list-item-content>
            <v-list-item-title class="text-red-600">
              <v-icon left color="red">mdi-logout</v-icon>
              로그아웃
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>

      <template v-else>
        <v-divider />

        <v-list-item to="/login" @click="mobileDrawer = false">
          <v-list-item-content>
            <v-list-item-title>
              <v-icon left color="#26c6da">mdi-login</v-icon>
              로그인
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>

        <v-list-item to="/register" @click="mobileDrawer = false">
          <v-list-item-content>
            <v-list-item-title>
              <v-icon left color="#26c6da">mdi-account-plus</v-icon>
              회원가입
            </v-list-item-title>
          </v-list-item-content>
        </v-list-item>
      </template>
    </v-list>
  </v-navigation-drawer>
</template>

<script setup>
  import { ref } from 'vue'
  import { useMemberStore } from '@/store/user'

  const memberStore = useMemberStore()
  const mobileDrawer = ref(false)

  const logout = () => {
    memberStore.logout()
    mobileDrawer.value = false
  }
</script>

<style scoped>
  .navbar-summer {
    background: linear-gradient(135deg, #e0f7fa 0%, #b2ebf2 50%, #80deea 100%);
    border-bottom-left-radius: 24px;
    border-bottom-right-radius: 24px;
    box-shadow: 0 4px 20px rgba(0, 180, 216, 0.15);
    backdrop-filter: blur(10px);
  }

  /* 중앙 60% 컨테이너 */
  .navbar-container {
    width: 60%;
    margin: 0 auto;
    display: flex;
    align-items: center;
    height: 100%;
    padding: 0 24px;
  }

  .brand-container {
    display: flex;
    align-items: center;
    gap: 12px;
    cursor: pointer;
    transition: all 0.3s ease;
  }

  .brand-container:hover {
    transform: translateY(-2px);
  }

  .logo-wrapper {
    background: linear-gradient(135deg, #26c6da, #00acc1);
    border-radius: 12px;
    padding: 8px;
    box-shadow: 0 4px 12px rgba(38, 198, 218, 0.3);
  }

  .brand-text {
    display: flex;
    flex-direction: column;
  }

  .brand-name {
    color: #0097a7;
    font-size: 1.5rem;
    font-weight: 700;
    letter-spacing: 1px;
    line-height: 1.2;
  }

  .brand-subtitle {
    color: #00838f;
    font-size: 0.75rem;
    font-weight: 400;
    opacity: 0.8;
  }

  .nav-menu {
    display: flex;
    gap: 8px;
    margin-right: 24px;
  }

  .nav-btn {
    color: #0097a7 !important;
    font-weight: 500;
    border-radius: 20px;
    padding: 8px 16px !important;
    transition: all 0.3s ease;
    position: relative;
    overflow: hidden;
  }

  .nav-btn::before {
    content: '';
    position: absolute;
    top: 0;
    left: -100%;
    width: 100%;
    height: 100%;
    background: linear-gradient(
      90deg,
      transparent,
      rgba(255, 255, 255, 0.3),
      transparent
    );
    transition: left 0.5s;
  }

  .nav-btn:hover::before {
    left: 100%;
  }

  .nav-btn:hover {
    background: rgba(178, 235, 242, 0.5) !important;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 151, 167, 0.2);
  }

  .nav-btn-active {
    background: rgba(38, 198, 218, 0.2) !important;
    color: #00695c !important;
    font-weight: 600;
  }

  .user-menu {
    display: flex;
    align-items: center;
    gap: 12px;
  }

  .user-btn {
    color: #0097a7 !important;
    font-weight: 500;
    border-radius: 25px;
    padding: 8px 16px !important;
    background: rgba(255, 255, 255, 0.3);
    backdrop-filter: blur(5px);
    transition: all 0.3s ease;
  }

  .user-btn:hover {
    background: rgba(255, 255, 255, 0.5) !important;
    transform: translateY(-2px);
    box-shadow: 0 4px 12px rgba(0, 151, 167, 0.2);
  }

  .user-name {
    font-weight: 600;
    margin-left: 4px;
  }

  .auth-btn {
    font-weight: 500;
    border-radius: 20px;
    padding: 8px 20px !important;
    transition: all 0.3s ease;
  }

  .login-btn {
    color: #0097a7 !important;
  }

  .login-btn:hover {
    background: rgba(178, 235, 242, 0.5) !important;
    transform: translateY(-2px);
  }

  .register-btn {
    background: linear-gradient(135deg, #26c6da, #00acc1) !important;
    color: white !important;
    margin-left: 8px;
    box-shadow: 0 4px 12px rgba(38, 198, 218, 0.3);
  }

  .register-btn:hover {
    background: linear-gradient(135deg, #00acc1, #0097a7) !important;
    transform: translateY(-2px);
    box-shadow: 0 6px 16px rgba(38, 198, 218, 0.4);
  }

  .mobile-menu-btn {
    color: #0097a7 !important;
    background: rgba(255, 255, 255, 0.3);
    border-radius: 12px;
  }

  .user-dropdown {
    border-radius: 16px;
    box-shadow: 0 8px 32px rgba(0, 0, 0, 0.1);
    backdrop-filter: blur(10px);
    background: rgba(255, 255, 255, 0.95);
  }

  .mobile-drawer {
    background: linear-gradient(180deg, #e0f7fa 0%, #f0f9ff 100%);
  }

  .mobile-nav-list {
    padding: 16px 0;
  }

  .mobile-user-info {
    background: rgba(38, 198, 218, 0.1);
    margin: 0 16px 16px;
    border-radius: 16px;
    padding: 16px;
  }

  .mobile-user-name {
    color: #0097a7;
    font-weight: 600;
    display: flex;
    align-items: center;
  }

  .mobile-user-subtitle {
    color: #00838f;
    margin-top: 4px;
  }

  .mobile-nav-list .v-list-item {
    border-radius: 12px;
    margin: 4px 16px;
    transition: all 0.3s ease;
  }

  .mobile-nav-list .v-list-item:hover {
    background: rgba(38, 198, 218, 0.1);
    transform: translateX(8px);
  }

  .mobile-nav-list .v-list-item-title {
    color: #0097a7;
    font-weight: 500;
    display: flex;
    align-items: center;
    gap: 12px;
  }

  /* 반응형 처리 */
  @media (max-width: 1200px) {
    .navbar-container {
      width: 80%;
    }
  }

  @media (max-width: 768px) {
    .navbar-container {
      width: 95%;
      padding: 0 16px;
    }

    .brand-name {
      font-size: 1.2rem;
    }

    .brand-subtitle {
      display: none;
    }

    .logo-wrapper {
      padding: 6px;
    }
  }
</style>
