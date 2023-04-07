FROM node:alpine

WORKDIR ./app

COPY contracts/* ./contracts/
COPY package.json ./package.json
COPY deploy/deploy-stations.js ./
COPY .env ./

RUN npm install -g npm@9.6.3
RUN npm i

ENTRYPOINT ["node", "./deploy-stations.js"]