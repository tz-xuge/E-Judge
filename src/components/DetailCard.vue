<template>
    <transition name="slide">
      <div v-if="visible" class="detail-card">
        <div class="detail-card-header">
          <span style="float:left;cursor: pointer;" @click="closeCard">关闭</span>
        </div>
        <div class="detail-card-content">
          <slot></slot>
        </div>
      </div>
    </transition>
  </template>

  <script>
  export default {
    props: {
      visible: {
        type: Boolean,
        required: true
      }
    },

    methods: {
      closeCard() {
        this.$emit('close');
      }
    },

  };
  </script>

  <style>
  .detail-card {
    position: fixed;
    top: 70px;
    right: 20px;
    width: 900px;
    height: 700px;
    background-color: white;
    box-shadow: -2px 0 5px rgba(0, 0, 0, 0.3);
    overflow: auto;
    z-index: 1000;
  }

  .detail-card-header {
    display: flex;
    /* justify-content: flex-end; */
    padding: 10px;
    background-color: #f5f5f5;
  }

  .detail-card-content {
    padding: 20px;
  }

  .slide-enter-active, .slide-leave-active {
    transition: transform 0.8s ease-in-out;
  }

  .slide-enter {
    transform: translateX(100%);
  }

  .slide-leave-to {
    transform: translateX(100%);
  }
  </style>
