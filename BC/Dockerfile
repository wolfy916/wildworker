FROM qww1552/base as truffle

COPY package.json ./
COPY package-lock.json ./

RUN npm install --quiet

COPY truffle-config.js ./
COPY contracts ./contracts
COPY migrations ./migrations/
COPY test ./test/

CMD ["truffle", "migrate"]