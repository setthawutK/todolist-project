# Build Angular
FROM node:22 AS builder
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build --prod

# Serve Angular with Node.js
# ใช้ package serve (npm i -g serve)
FROM node:22
WORKDIR /usr/src/app
RUN npm install -g serve
COPY --from=builder /usr/src/app/dist/todo-list-webapp/browser ./dist
EXPOSE 3000
CMD ["serve", "-s", "dist", "-l", "3000"]
