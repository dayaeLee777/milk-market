# build stage
FROM node:lts-alpine as build-stage
WORKDIR /app
COPY package*.json ./
RUN npm install -g truffle 
RUN npm install @openzeppelin/contracts
COPY . .



# production stage