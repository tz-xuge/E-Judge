const webpack = require("webpack")
const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({

  transpileDependencies: true,
  lintOnSave:false,
  
  configureWebpack: {
    plugins: [
      new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        "windows.jQuery": "jquery",
        Popper: ['popper.js', 'default']
      }),
    ],
  },
  devServer: {
    host: '0.0.0.0',
    port: 9527,
    client: {
      webSocketURL: 'ws://0.0.0.0:9527/ws',
    },
    headers: {
      'Access-Control-Allow-Origin': '*',
    }
  },

})