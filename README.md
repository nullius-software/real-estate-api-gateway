# Real Estate API Gateway - Setup Guide

Welcome to the Real Estate API Gateway project. This guide provides step-by-step instructions to set up the development environment locally.

## About This Repository

The `real-estate-api-gateway` repository serves as the API Gateway for the Real Estate project. The API Gateway acts as a single entry point for all client requests. It routes requests to the appropriate microservices, handles cross-cutting concerns like authentication, authorization, and rate limiting, and simplifies communication between clients and microservices.

## Prerequisites

- Docker: Ensure Docker is installed and running on your machine. Download from [docker.com](https://www.docker.com) if needed.
- Access to a terminal (bash, cmd, PowerShell, etc.).
- Internet connection to pull necessary images.
- Keycloak: Ensure Keycloak is running locally. The guide for configuring Keycloak locally can be found [here](https://github.com/nullius-software/real-estate-keycloak-render).
- Discovery Service: Ensure the discovery service is running locally. The guide for setting up the discovery service can be found [here](https://github.com/nullius-software/real-estate-discovery-service).

## Step 1: Clone the Repository

First, clone the `real-estate-api-gateway` repository to your local machine:

```bash
git clone https://github.com/nullius-software/real-estate-api-gateway.git
cd real-estate-api-gateway
```

## Step 2: Build the Docker Image

Build the Docker image for the API Gateway:

```bash
docker build -t real-estate-api-gateway .
```

## Step 3: Run the Docker Container

Run the Docker container for the API Gateway:

```bash
docker run -p 8080:8080 real-estate-api-gateway
```

### Explanation:

- `-p 8080:8080`: Maps port 8080 from the container to your local machine.

Wait for the container to start. You’ll see logs indicating the API Gateway is ready when something like this appears:

```
Started ApiGatewayApplication in X.XXX seconds
```

## Step 4: Access the API Gateway

Open your browser and go to: [http://localhost:8080](http://localhost:8080).

You should see the API Gateway indicating that it is running.

## Step 5: Verify the Configuration

Ensure that the other microservices in the Real Estate project are configured to route through the API Gateway by checking their configuration files. They should have the API Gateway URL set to `http://localhost:8080`.
