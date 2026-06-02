// stores/plan.js
import { defineStore } from 'pinia'
import { ref } from 'vue'
import { instance } from '@/axios/index'

export const usePlanStore = defineStore('plan', () => {
  const plans = ref([])
  const createPlan = async (plan) => {
    try {
      const { data } = await instance.post('/v1/plan/insert', plan)
      console.log('Plan 저장 완료, ID:', data.data)
      return data.data // planId 반환
    } catch (error) {
      console.error('Plan 저장 실패:', error)
      throw error
    }
  }

  // PlanDetail 배열만 저장
  const createPlanDetails = async (planDetails) => {
    try {
      // 필요한 필드만 선택해서 새로운 배열 생성
      const filteredPlanDetails = planDetails.map((item) => ({
        placeNo: item.place_no,
        seq: item.seq,
        placeUrl: item.place_url,
        placeName: item.place_name,
        placeAddress: item.place_address,
        // lat, lng 제외
      }))

      const { data } = await instance.post(
        '/v1/plan/details',
        filteredPlanDetails
      )
      console.log('PlanDetails 저장 완료', filteredPlanDetails)
      return data
    } catch (error) {
      console.error('PlanDetails 저장 실패:', error)
      throw error
    }
  }

  const selectPlans = async () => {
    try {
      const { data } = await instance.get('/v1/plan')
      console.log('전체 계획 조회 결과:', data)
      plans.value = data.data || data // 응답 구조에 따라 조정
      return data
    } catch (error) {
      console.error('전체 여행계획 불러오기 실패', error)
      plans.value = []
      return null
    }
  }

  const selectPlanByNo = async (no) => {
    try {
      const { data } = await instance.get(`/v1/plan/${no}`)
      console.log('계획 상세 조회 결과:', data)
      return data.data || data // 응답 구조에 따라 조정
    } catch (err) {
      console.error('여행계획 조회 실패', err)
      return null
    }
  }

  const updatePlan = async (updatePlan) => {
    try {
      const { data } = await instance.put(`/v1/plan/update`, updatePlan)
      console.log('계획 수정 결과:', data)
      alert('✅ 여행계획 수정 완료')
      return true
    } catch (error) {
      alert('❌ 여행계획 수정 실패')
      console.error('계획 수정 오류:', error.response?.data || error)
      return false
    }
  }

  const deletePlan = async (no) => {
    try {
      const { data } = await instance.delete(`/v1/plan/delete/${no}`)
      console.log('계획 삭제 결과:', data)
      alert('✅ 삭제 완료!')
      return true
    } catch (err) {
      alert('❌ 삭제 실패')
      console.error('계획 삭제 오류:', err.response?.data || err)
      return false
    }
  }
  const planDetails = ref([])
  const getPlanDetails = async (planId) => {
    try {
      const { data } = await instance.get(`/v1/plan/details/${planId}`)
      console.log('🧭 장소 목록:', data.data)
      planDetails.value = data.data
      return data.data
    } catch (error) {
      console.error('❌ 장소 정보 불러오기 실패', error)
      planDetails.value = []
      return null
    }
  }

  const updatePlanDetails = async (planId, details) => {
    console.log(details)

    try {
      console.log('plan.js', details)

      const { data } = await instance.put(
        `/v1/plan/details/update/${planId}`,
        details
      )
      console.log('🛠 장소 수정 결과:', data)
      return true
    } catch (error) {
      console.error('❌ 장소 수정 실패', error)
      return false
    }
  }

  const planDetailsMap = ref({})

  const preloadAllPlanDetails = async () => {
    planDetailsMap.value = {} // 초기화

    for (const plan of plans.value) {
      const detail = await getPlanDetails(plan.no)
      planDetailsMap.value[plan.no] = detail.length
    }
  }

  const getPlaceCount = async () => {
    try {
      const { data } = await instance.get(`/v1/plan/place-count`)
      console.log('차트 정보', data)
      return data.data
    } catch (error) {
      console.log('차트 불러오기 실패', error)
      return null
    }
  }

  const selectMyPlan = async (userNo) => {
    try {
      const { data } = await instance.get(`/v1/plan/myplan/${userNo}`)
      plans.value = data.data || data // 응답 구조에 따라 조정
      console.log('내 여행 계획 조회 결과:', data)
      return data
    } catch (error) {
      console.error('내 여행계획 불러오기 실패', error)
      plans.value = []
      return null
    }
  }

  return {
    plans,
    planDetailsMap,
    selectMyPlan,
    createPlan,
    selectPlans,
    selectPlanByNo,
    deletePlan,
    updatePlan,
    createPlanDetails,
    getPlanDetails,
    updatePlanDetails,
    preloadAllPlanDetails,
    getPlaceCount,
  }
})
