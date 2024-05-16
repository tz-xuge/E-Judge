<template>
  <!-- WRAPPER -->
		<div id="wrapper" v-cloak>
			<div class="vertical-align-wrap">
				<div class="vertical-align-middle">
					<div class="auth-box ">
						<div class="left">
							<div class="content">
								<div class="header">
									<div class="logo text-center"><img src="../assets/img/image.png" alt="Klorofil Logo"></div>
									<p class="lead">Login to your account.</p>
								</div>
								<el-form class="form-auth-small" @submit.prevent="onSubmit()">
									<div class="form-group">
										<input type="text" v-model="username" class="form-control" id="username" placeholder="昵称/邮箱" autocomplete="off">
									</div>
									<div class="form-group">
										<input type="password" v-model="password" class="form-control" id="password" placeholder="密码" autocomplete="off">
									</div>
									<div class="form-group clearfix">
										<label class="fancy-checkbox element-left">
											<input type="checkbox" checked id="customCheck">
											<span>同意《使用条款》</span>
										</label>
									</div>
									<button @click="login()" class="btn btn-primary btn-lg btn-block">登录</button>
								</el-form>
							</div>
						</div>
						<div class="right">
							<div class="overlay"></div>
							<div class="content text">
								<h1 class="heading">Economy determines how you can bear life. </h1>
								<p>The fact that people are full of greed, fear, or folly is predictable. The sequence is not predictabl.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
</template>

<script>

import { mixin } from '../assets/js/util'
import axios from 'axios'

export default {

	name:"Login",
   	mixins: [mixin],

	data(){

		return{
			username: "",
			password: ""
		}

	},
	
	methods: {
		login() {
			var _this = this;

			if(_this.validityForStringNull(_this.$data.username)){
				_this.notify('昵称/邮箱不能为空','error');
				return false;
			}

			if(_this.validityForStringNull(_this.$data.password)){
				_this.notify("密码不能为空",'error');
				return false;
			}

			if(!$("#customCheck").is(':checked')){
				_this.notify("请先同意《使用条款》",'error')
				return false;
			}
			_this.$router.push('/undergraduate');

			//取消vue的自动转换
			let param = new URLSearchParams()
			param.append('username', _this.$data.username)
			param.append('password', _this.$data.password)
			axios({
				method: 'post',
				url: this.getUSRootURL() + 'user/login',
				data: param,
				headers: {
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(res=> {
				console.log(res);
				if (res.data.status >= 0) {
					var authorization = res.headers["authorization"];
					_this.$store.commit('settoken', authorization);
					_this.$router.push('/undergraduate');
					_this.notify(res.data.msg,'success');
				} else {
					_this.notify(res.data.msg,'error');
				}
			}).catch(err=> {
				_this.notify(err,'error');
			});
		},
		onSubmit() {
			//防止form冒泡
			return false;
		},
		register()
		{
			this.$router.push('/register')
		},
		findpwd()
		{
			this.$router.push('/findpwd')
		}
            
	},
	created() {
		//created 在模板渲染成html前调用，即通常初始化某些属性值，然后再渲染成视图
	},
	mounted() {
		//mounted 在模板渲染成html后调用，通常是初始化页面完成后，再对html的dom节点进行一些需要的操作
	}

}
</script>

<style >

 .auth-box .lead {
    margin-top: 5px;
    font-size: 18px;
    text-align: center; }

 .auth-box .right {
    float: right;
    width: 58%;
    height: 100%;
    position: relative;
    background-image: url("../assets/img/back.jpeg");
    background-repeat: no-repeat;
    background-size: cover; }
</style>
