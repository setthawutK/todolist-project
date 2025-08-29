# Stage 1: Build Angular
FROM node:22 AS builder
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npx ng build --configuration production --base-href /

# Stage 2: Serve with Nginx
FROM nginx:stable-alpine
COPY --from=builder /usr/src/app/dist/todo-list-webapp/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
