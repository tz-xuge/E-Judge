import { createStore } from 'vuex'

export default createStore({

  state: {
  
      token:'',
      uid:'',
      role:'',
      headimg:'',

      //emoji表情
    
  },
  
  getters: {
    token: state => {
      let token = state.token
      if (!token) {
        token = JSON.parse(window.localStorage.getItem('token') || null)
      }
      return token
    },
    uid: state => {
      let uid = state.uid
      if (!uid) {
        uid = JSON.parse(window.localStorage.getItem('uid') || null)
      }
      return uid
    },
    role: state => {
      let role = state.role
      if (!role) {
        role = JSON.parse(window.localStorage.getItem('role') || null)
      }
      return role
    },
    headimg: state => {
      let headimg = state.headimg
      if (!headimg) {
        headimg = JSON.parse(window.localStorage.getItem('headimg') || null)
      }
      return headimg
    },
  },


  mutations: {
    settoken: (state, token) => {
      state.token = token
      window.localStorage.setItem('token', JSON.stringify(token))
    },

    setuid: (state, uid) => {
      state.uid = uid
      window.localStorage.setItem('uid', JSON.stringify(uid))
    },

    setrole: (state, role) => {
      state.role = role
      window.localStorage.setItem('role', JSON.stringify(role))
    },
    setheadimg: (state, headimg) => {
      state.headimg = headimg
      window.localStorage.setItem('headimg', JSON.stringify(headimg))
    },

  },
  actions: {


  },
  modules: {


  }
})
