import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import axios from 'axios'
import ElementPlus from 'element-plus';
import Vuex from 'vuex'  

// 引入jQuery、bootstrap
import $ from 'jquery'
import 'bootstrap'
 
// 引入bootstrap样式
import 'bootstrap/dist/js/bootstrap.min.js'
import 'pc-bootstrap4-datetimepicker'
import 'pc-bootstrap4-datetimepicker/src/js/bootstrap-datetimepicker.js'

createApp(App).use(store).use(Vuex).use(ElementPlus).use(router).mount('#app')



//axios请求拦截器
axios.interceptors.request.use(function(request) {
	//在发起请求请做一些业务处理
	return request;
}, function(error) {
	//对请求失败做处理
	return Promise.reject(error);
});

//axios响应拦截器
axios.interceptors.response.use(function(response) {
	//对响应数据做处理
	return response;
}, function(error) {
	//对响应错误做处理
	return Promise.reject(error);
});

router.beforeEach((to, form, next) => {
    if (to.meta.title) {
        document.title = 'E-judge | ' + to.meta.title ;
    } else {
        document.title = 'E-judge' //此处写默认的title
    }
  	next()
})



