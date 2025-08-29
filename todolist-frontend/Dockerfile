# Build Angular
FROM node:22 AS builder
WORKDIR /usr/src/app

COPY package*.json ./
RUN npm install --legacy-peer-deps

COPY . .
RUN npx ng build --prod --base-href /

# Stage 2: Serve with Nginx
FROM nginx:stable-alpine

# Copy build Angular output
COPY --from=builder /usr/src/app/dist/todo-list-webapp/browser /usr/share/nginx/html
COPY --from=builder /usr/src/app/nginx.conf /nginx.conf

# ENV for proxy
ENV SERVICE_URL=http://todo-backend-svc:8080

# Replace envs into nginx.conf at container start
CMD cat /nginx.conf | envsubst "$(env | awk -F = '{printf " $$%s", $1}')" \
    > /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'
