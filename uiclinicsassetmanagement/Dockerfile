# Use Node 12 (stable)
FROM node:12-alpine

# Create app directory
WORKDIR /usr/src/app/

# Copy package.json and package-lock.json
COPY src/package*.json /usr/src/app/

# Install app dependencies
RUN npm install

# Bundle app source
COPY ./src .

# Expose npm on port 80
EXPOSE 80

CMD [ "npm", "start" ]
