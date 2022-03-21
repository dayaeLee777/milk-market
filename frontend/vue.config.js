module.exports = {
  devServer: {
    https: false,
    port:8083,
    open:true,
    // proxy:{
    //   '/api' : {
    //     target: 'http://localhost:8080/'
    //   }
    // },
    historyApiFallback: true,
    hot:true
  },
  css: {
    requireModuleExtension:false
  },
  transpileDependencies:[
    'element-plus',
    'vuetify'
  ],
  lintOnSave:false,
  // outputDir: '../backend/src/main/resources/dist' //빌드 시 dist 파일 생성 경로 지정
};
