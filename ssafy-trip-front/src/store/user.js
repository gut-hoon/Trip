import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { instance } from '@/axios/index'
import { useRouter } from 'vue-router'
import { jwtDecode } from 'jwt-decode'

export const useMemberStore = defineStore(
  'user',
  () => {
    const _isLoggedIn = ref(false)
    const _loginUser = ref({})
    const isLoggedIn = computed(() => _isLoggedIn.value)
    const loginUser = computed(() => _loginUser.value)

    const router = useRouter()

    const login = async ({ email, password }) => {
      try {
        const { data } = await instance.post('/auth/login', { email, password })

        const accessToken = data.accessToken
        const decoded = jwtDecode(accessToken)

        _loginUser.value = {
          user: {
            name: decoded.name,
            email: decoded.email,
            role: decoded.role,
            no: decoded.no,
          },
          accessToken,
          refreshToken: data.refreshToken,
        }
        instance.defaults.headers.common['Authorization'] = `Bearer ${accessToken}`;
        _isLoggedIn.value = true
        sessionStorage.setItem('accessToken', accessToken) // 저장 선택적
        router.push('/')
      } catch (err) {
        alert('아이디 또는 비밀번호가 틀렸습니다.')
      }
      console.log(_loginUser.value.user)
    }

    const logout = async () => {
      try {
        await instance.post('/auth/logout', null, {
          headers: {
            Authorization: `Bearer ${sessionStorage.getItem('accessToken')}`,
          },
        })

        // 토큰 제거
        sessionStorage.removeItem('accessToken');
        sessionStorage.removeItem('refreshToken');

        // axios 같은 경우엔 기본 헤더 제거도 필요
        delete instance.defaults.headers.common['Authorization'];
        _isLoggedIn.value = false
        _loginUser.value = {}
        alert('로그아웃 되었습니다.')
        router.push('/')
      } catch (error) {
        console.error('로그아웃 실패:', error)
      }
    }


    const register = async ({ name, email, password }) => {
      try {
        await instance.post('/v1/users/register', { name, email, password })
        alert('회원가입 성공! 이제 로그인해 주세요')
        router.push('/login')
      } catch (err) {
        alert('회원가입 실패: ' + err.response?.data?.message || err.message)
      }
    }

    const updatePassword = async ({ name, email, newPassword }) => {
      try {
        const updatedUser = {
          name,
          email,
          password: newPassword,
        }

        await instance.put(`/v1/users/update/${email}`, updatedUser)
        alert('비밀번호가 성공적으로 변경되었습니다.')
        return true
      } catch (err) {
        alert(
          '비밀번호 변경 실패: ' + (err.response?.data?.message || err.message),
        )
        return false
      }
    }

    return {
      isLoggedIn,
      loginUser,
      login,
      logout,
      register,
      updatePassword,
      _isLoggedIn,
      _loginUser,
    }
  },
  {
    persist: { storage: sessionStorage },
  },
)
