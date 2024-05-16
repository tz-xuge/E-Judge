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
								<a href="#"><img v-bind:src="(user.headimg)" class="img-circle" alt="headimg">
									 <span>{{user.name}}</span> 
								</a>
								
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
	},
	mounted() {
	}
	
}

</script>




<style>

</style>