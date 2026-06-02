/**
 * main.js
 *
 * Bootstraps Vuetify and other plugins then mounts the App`
 */

// Plugins
import { registerPlugins } from "@/plugins";
import { createPinia } from "pinia";
import piniaPluginPersistedState from "pinia-plugin-persistedstate";
import { createApp } from "vue";

// Components
import App from "./App.vue";

import VueECharts from 'vue-echarts'
import { use } from 'echarts/core'
import { CanvasRenderer } from 'echarts/renderers'
import { BarChart } from 'echarts/charts'
import { GridComponent, TooltipComponent } from 'echarts/components'

// Composables

// Styles
import "unfonts.css";

use([CanvasRenderer, BarChart, GridComponent, TooltipComponent])
const app = createApp(App);
app.component('v-chart', VueECharts)
const pinia = createPinia();
pinia.use(piniaPluginPersistedState);
app.use(pinia);

registerPlugins(app);

app.mount("#app");
