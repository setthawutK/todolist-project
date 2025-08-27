# --- Build stage ---
FROM node:22 AS builder
WORKDIR /usr/src/app
COPY package*.json ./
RUN npm ci --legacy-peer-deps
COPY . .
RUN npm run build --prod

# --- Runtime stage (no Nginx) ---
FROM node:22-alpine
WORKDIR /srv
RUN npm i -g http-server
# ✅ ใช้ path ที่มี browser ตามที่ยืนยัน
COPY --from=builder /usr/src/app/dist/todo-list-webapp/browser /srv/dist
EXPOSE 8080
CMD ["http-server", "/srv/dist", "-p", "8080", "-a", "0.0.0.0"]
