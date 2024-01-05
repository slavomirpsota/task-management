FROM nginx:latest

# Remove the default NGINX configuration
RUN rm -rf /etc/nginx/conf.d/*

# Copy new NFINX conf
COPY nginx.conf /etc/nginx/conf.d/

# Copy the Angular app build files to the NGINX default HTML directory
COPY dist/ /usr/share/nginx/html

# Expose port 4200
EXPOSE 4200

# Start NGINX to serve the Angular application
CMD ["nginx", "-g", "daemon off;"]
