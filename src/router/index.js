import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    redirect: '/login'
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "about" */ '../views/login.vue')
  },
  {
    path: '/index',
    component: () => import(/* webpackChunkName: "about" */ '../components/index.vue'),
    children: [
        {
            path: '/undergraduate',
            name:'/undergraduate',
            component: () => import(/* webpackChunkName: "about" */ '../views/undergraduate.vue'),
            meta: { title: 'undergraduate' }
        },
        // {
        //   path: '/postgraduate',
        //   name:'/postgraduate',
        //   component: () => import(/* webpackChunkName: "about" */ '../views/postgraduate.vue'),
        //   meta: { title: 'postgraduate' }
        // },
        // {
        //   path: '/doctor',
        //   name:'doctor',
        //   component: () => import(/* webpackChunkName: "about" */ '../views/doctor.vue'),
        //   meta: { title: 'doctor' },
        //   props: route => ({param: route.query.param})
        // },
  
  ]
}
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
