# Use an official Tomcat image as a parent image
FROM tomcat:9.0-jdk11-openjdk-slim

# Set the working directory in the container
WORKDIR /usr/local/tomcat/webapps

# Copy the WAR file into the Tomcat webapps directory
# Ensure the WAR file path matches the actual location of the WAR file
COPY target/linux/stocks.war stocks.war

# Expose the port Tomcat runs on
EXPOSE 8080

