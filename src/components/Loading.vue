<template>
    <div class="wrapper">
      <div class="loading">
        <span
          :style="{
            width: '20px',
            animationDuration:
              duration.indexOf('s') === -1 ? duration + 's' : duration,
            animationDelay: (parseInt(duration) / text.length) * index + 's',
          }"
          v-for="(char, index) in text"
          :key="index"
        >
          {{ char }}
        </span>
      </div>
    </div>
  </template>
  <script>
  export default {
    props: {
      text: {
        type: String,
        default: "我是一个粉刷匠",
      },
      duration: {
        type: String,
        default: "1s",
      },
    },
  };
  </script>
  
  <style scoped>
  .wrapper {
    display: flex;
    justify-content: center;
  }
  .loading {
    display: flex;
    text-align: center;
    padding-top: 30px;
    height: 50px;
    justify-content: space-between;
  }
  .loading span {
    margin-top: 0;
    animation: ease infinite move;
    display: block;
  }
  
  @keyframes move {
    0% {
      margin-top: -10px;
      border-bottom: 1px solid;
    }
    50% {
      margin-top: 10px;
      border-bottom: none;
    }
    100% {
      margin-top: -10px;
    }
  }
  </style>
  
  