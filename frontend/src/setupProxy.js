const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app){
    app.use(
        "/api",
      createProxyMiddleware( {
        target: 'http://61.33.142.222:8188/',
        //target: 'http://localhost:8188',
        changeOrigin: true
      })
    )
    
  };