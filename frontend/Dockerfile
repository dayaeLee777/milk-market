# # build stage
# FROM node:lts-alpine as build-stage
# WORKDIR /app
# COPY package*.json ./
# RUN npm install --production
# COPY . .
# RUN npm run build

# # production stage
# FROM nginx:stable-alpine as production-stage
# RUN rm -rf /etc/nginx/conf.d/default.conf
# COPY --from=build-stage /app/dist /usr/share/nginx/html
# EXPOSE 3000
# CMD ["nginx", "-g", "daemon off;"]

FROM node:lts-alpine
RUN npm install -g http-server

WORKDIR /app
COPY package*.json ./
RUN npm install

COPY . .
RUN npm run build
EXPOSE 3000

CMD [ "http-server", "dist" ]
CMD [ "npm", "run", "serve" ]