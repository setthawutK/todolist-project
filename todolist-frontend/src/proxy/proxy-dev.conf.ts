const todoListService = 'http://54.169.241.164:8081';

const PROXY_CONFIG = {
  '/todo-list-service': {
    target: todoListService,
    changeOrigin: true,
    secure: false,
    pathRewrite: {
      '^/todo-list-service': '',
    },
  },
};

module.exports = PROXY_CONFIG;
