const proxy = require('http-proxy-middleware')

module.exports = function(app){
    const useHttps = process.env.HTTPS
    const port = useHttps ? 8443 : 8080
    const endpoint = {target: (useHttps ? 'https': 'http') + '://localhost:' + port + '/', secure: false};
    app.use(proxy('/images', endpoint))
    app.use(proxy('/config', endpoint))
}