import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '@/views/HomeView.vue'
import LoginView from '@/views/LoginView.vue'
import RegisterView from '@/views/RegisterView.vue'
import HotPlaceListView from '@/views/HotPlaceListView.vue'
import TripPlannerView from '@/views/TripPlannerView.vue'
import BoardView from '@/views/BoardView.vue'
import PostCreateView from '@/views/PostCreateView.vue'
import PostDetailView from '@/views/PostDetailView.vue'
import PlanListView from '@/views/PlanListView.vue'
import PlanDetailView from '@/views/PlanDetailView.vue'
import UpdateUserView from '@/views/UpdateUserView.vue'
import PostUpdateView from '@/views/PostUpdateView.vue'
import PlanUpdateView from '@/views/TripPlanUpdateView.vue'
import PopularDestinationsChart from '@/views/PopularDestinationsChart.vue'
import AISuggestionView from '@/views/AISuggestionView.vue'
import MyPlanView from '@/views/MyPlanView.vue'
const routes = [
  { path: '/', name: 'Home', component: HomeView },
  { path: '/login', name: 'Login', component: LoginView },
  { path: '/register', name: 'Register', component: RegisterView },
  { path: '/trip', name: 'TripList', component: HotPlaceListView },
  { path: '/planner', name: 'Planner', component: TripPlannerView },
  { path: '/board', name: 'Board', component: BoardView },
  { path: '/board/write', name: 'PostCreateView', component: PostCreateView },
  { path: '/board/:id', name: 'PostDetail', component: PostDetailView },
  { path: '/plan', name: 'PlanListView', component: PlanListView },
  { path: '/plan/:id', name: 'PlanDetailView', component: PlanDetailView },
  { path: '/profile', name: 'UpdateUserView', component: UpdateUserView },
  { path: '/my-plans', name: 'MyPlanView', component: MyPlanView },
  {
    path: '/board/update/:id',
    name: 'PostUpdateView',
    component: PostUpdateView,
  },
  {
    path: '/plan/update/:id',
    name: 'PlanUpdateView',
    component: PlanUpdateView,
  },
  {
    path: '/hotplace/:id',
    name: 'HotplaceDetail',
    component: () => import('@/views/HotplaceDetailView.vue'),
  },
  {
    path: '/chart',
    name: 'PopularDestinationsChart',
    component: PopularDestinationsChart,
  },
  { path: '/ai', name: 'AISuggestionView', component: AISuggestionView },
]

const router = createRouter({
  history: createWebHistory(),
  routes,
})

export default router
