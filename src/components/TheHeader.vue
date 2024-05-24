<template>
  <div id="wrapper" v-cloak>
			<nav class="navbar navbar-default navbar-fixed-top">
				<div class="brand">
					<a href="javascript:void(0);" @click="toindex()"><img src="../assets/img/image.png" alt="Klorofil Logo" class="img-responsive logo"></a>
				</div>
				<div class="container-fluid">

					<div id="navbar-menu">
								<ul class="nav navbar-nav navbar-left" v-for="item in items">

									<li class="dropdown nav-a">
										<a href="javascript:void(0);" @click="onRoutes(item.index)"> <span slot="title" style="font-size: 18px;">{{ item.title }}</span> </a>
									</li>
								</ul>
								
						<ul class="nav navbar-nav navbar-right">					
							<li>
								<span style="float:right;margin-top:20px;">{{user.name}}</span> 
								<el-avatar class = "header" style="background:#21B1FF;margin:10px">{{getNameLast(user.name)}}</el-avatar>							
								
							</li>
						</ul>
					</div>
				</div>
			</nav>
		</div>
</template>

<script>

import { mixin } from '../assets/js/util'
import axios from 'axios'

export default {
	name:"Login",
   	mixins: [mixin],
	
	data() {
		return{
			user:{},
			role:"",
			notices:[],
			items: [
			{
			index: 'undergraduate',
			title: '本科生'
			},
			{
			index: 'postgraduate',
			title: '研究生'
			},
			{
			index: 'doctor',
			title: '博士生'
			},
      ]
			}
  },
	
	methods: {

		loadPage() {
			var _this = this;
			console.log(_this.$store.getters.uid)
			axios({
				method: 'get',
				url: _this.getURL() + 'user/profile',
				params: {
					uid: _this.$store.getters.uid
				},
				headers: {
					'Authorization': _this.$store.getters.token,
					'Content-Type': 'application/x-www-form-urlencoded'
				}
			}).then(res=>{
				console.log(res);

				if (res.data.status >= 0) {
					_this.$data.user = res.data.object;
				} else {
					_this.notify(res.data.msg,'warning');
				}

			}).catch(err=> {
				_this.notify(err,'error');
			});
		},
		getNameLast(str)
      	{
			if (str != null) {
				if (str.indexOf('(') > -1 || str.indexOf('（') > -1){
				const _str = str.split('(') || str.split('（');
				const newStr = _str[0].substring(_str[0].length - 1);
				return newStr;
				}
			else {
				return str.substring(str.length - 1)
			}
			}
      	},
		onRoutes(index)
		{
			this.$router.push(index);
		},
		toindex()
		{
			this.$router.push('/blog')
		},
		},
		
	created() {
		this.loadPage()

	},
	mounted() {
	}
	
}

</script>




<style>
.header {
  float: right;

  display: flex;
  justify-content: space-around;
  align-items: center;
}
</style>