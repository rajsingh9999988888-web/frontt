# Use Node.js 18 as the base image
FROM node:18-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy package.json and package-lock.json
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the application
RUN npm run build

# Install a static server to serve the built app
RUN npm install -g serve

# Expose the port the app runs on
EXPOSE 5173

# Start the application
CMD ["serve", "-s", "dist", "-l", "5173"]
