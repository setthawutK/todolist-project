const todoListService = 'http://13.213.7.137:30081';

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
