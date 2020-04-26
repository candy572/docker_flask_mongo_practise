#This is the Base image
FROM node:alpine
#This will the working directory
WORKDIR /usr/app/
#COpy the package.json to the working directory
#Copy instruction is divided into two commands to avoid npm installation every time code changes
COPY ./package.json ./
#This is the command that needs to be run in the base image
RUN npm install
#Copy the current files in the file system to container file system. 
#It will be copied to working directory if specified wlse it will get copied to root directory of the container
COPY ./ ./
#This is the startup command for our image
CMD ["npm","start"]
