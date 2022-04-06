const webpack = require('webpack');

module.exports = {
  devServer: {
    https: false,
    port:8083,
    open:true,
    historyApiFallback: true,
    disableHostCheck: true,
    hot:true
  },
  configureWebpack: {
    plugins: [
      new webpack.optimize.LimitChunkCountPlugin({
        maxChunks: 6
      })
    ]
  },
  css: {
    // sourceMap: process.env.NODE_ENV !== 'production'
  },
  transpileDependencies:[
    'element-plus',
    'vuetify'
  ],
  lintOnSave:false
};
