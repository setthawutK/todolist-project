const todoListService = 'http://localhost:30080';

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
