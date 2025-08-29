const PROXY_CONFIG = {
  '/todo-list-service': {
    target: 'http://localhost:30080',
    secure: false,
    changeOrigin: true,
  },
};

module.exports = PROXY_CONFIG;
