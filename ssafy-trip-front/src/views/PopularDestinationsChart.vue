<template>
  <v-container>
    <v-card
      class="pa-4"
      elevation="4"
      style="
        background: linear-gradient(90deg, #e0f7fa 0%, #b2ebf2 100%);
        border-radius: 24px;
      "
    >
      <v-card-title
        class="font-weight-bold"
        style="color: #0097a7; letter-spacing: 1px"
      >
        인기 여행지 막대 차트
      </v-card-title>
      <v-card-text>
        <!-- 여기! -->
        <v-chart :option="chartOptions" style="height: 350px"></v-chart>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script setup>
  import { ref, onMounted } from 'vue'
  import { usePlanStore } from '@/store/plan'
  import VChart from 'vue-echarts'
  import { BarChart } from 'echarts/charts'
  import { use } from 'echarts/core'
  import {
    GridComponent,
    LegendComponent,
    TitleComponent,
    TooltipComponent,
  } from 'echarts/components'
  import { CanvasRenderer } from 'echarts/renderers'

  // ECharts 구성 요소 등록
  use([
    BarChart,
    TitleComponent,
    TooltipComponent,
    GridComponent,
    LegendComponent,
    CanvasRenderer,
  ])

  // Store 및 상태 선언
  const planStore = usePlanStore()
  const placeCount = ref([]) // 기본값 빈 배열
  const chartOptions = ref({}) // 기본값 빈 객체

  onMounted(async () => {
    // 비동기로 place count 받아오기
    const data = await planStore.getPlaceCount()
    if (!data) return

    placeCount.value = data

    // 받아온 데이터를 기반으로 chartOptions 구성
    chartOptions.value = {
      title: {
        text: '인기 여행지',
        left: 'center',
        textStyle: {
          color: '#0097a7',
          fontWeight: 'bold',
          fontSize: 20,
        },
      },
      tooltip: {},
      xAxis: {
        type: 'category',
        data: placeCount.value.map((item) => item.placeName),
        axisLine: {
          lineStyle: {
            color: '#26c6da',
          },
        },
        axisLabel: {
          color: '#0097a7',
          fontWeight: 'bold',
        },
      },
      yAxis: {
        type: 'value',
        axisLine: {
          lineStyle: {
            color: '#26c6da',
          },
        },
        splitLine: {
          lineStyle: {
            color: '#b2ebf2',
          },
        },
        axisLabel: {
          color: '#0097a7',
        },
      },
      series: [
        {
          name: '방문자 수',
          type: 'bar',
          data: placeCount.value.map((item) => item.count),
          itemStyle: {
            color: '#26c6da',
          },
          emphasis: {
            itemStyle: {
              color: '#0097a7',
            },
          },
        },
      ],
    }
  })
</script>

<style scoped>
  .v-card {
    border-radius: 24px;
  }
</style>
