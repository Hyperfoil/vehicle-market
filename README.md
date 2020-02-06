# Vehicle Market: A Microservices / Serverless benchmark

## Overview

A benchmark to provide end to end load testing of a microservice / serverless application. 
The application is based on the concept of an online vehicle brokerage platform.
The application allows traders to list vehicles for sale, place offers on vehicles and complete purchase transactions.
In addition to the main functionality, the application allows finance brokers to provide quotes to purchasers to help finance the purchase of their vehicles.

## Target Platforms
Micro Profile + JPA
Quarkus running on OCP

## Technologies
MP Config
MP Metrics
MP Opentracing
DI
JPA
HyperFoil
Mix Reactive/Imperative styles
Polyglot (potential alternative service implementations in different languages)
Quarkus
Compensating Transactions

## Infrastructure

### Load generator :red_circle:
Client side driver for driving load at the benchmark application. Based on Hyperfoil

### Random Data generator :red_circle:
Database populator to generate pre-populated databases for testing application against.

### Service Orchestration :red_circle:
Automated scripts for building, spinning up and terminating microservices in benchmark

## Benchmark Application Services :red_circle:

### Vehicle discovery service :red_circle:
A read-only service that provides a single point of contact regarding all available vehicles in the market. 

### User/Trader service :red_circle:
Service responsible for maintaining a list of authorized users of the application.

### Listing Service :red_circle:
All listings on the system are registered with the listing service.  Here sellers can add/remove/update their vehicle listings and buyers can search for and view listings of vehicles that are available to purchase.

### Bidding Service :red_circle:
A service that allows buyers to bid on/offer on listings.  This service manages the trading between the buyers and sellers of vehicles.

### Settlement Service :red_circle:
Once a bid has been accepted, this service completes the sale, by transferring funds between buyer seller, registering the vehicle to the new user, removing the listing and cancelling all other bids

### Authentication Service :red_circle:
A distributed service for authenticating actors within the application

### Finance Broker Service :red_circle: 
A finance broker service allow buyers to receive and accept finance offers on vehicles they are purchasing.

### Reporting Service :red_circle:
A finance broker service allow buyers to receive and accept finance offers on vehicles they are purchasing.

## Datasets

### Vehicle database :red_circle:
An extensive list of vehicle makes/models and emissions data is available from the US government fuel economy website [here](https://www.fueleconomy.gov/feg/ws/index.shtml);

The data is released under the `U.S. Public Domain license`, which does not restrict the usage of the data for our use case.

### Address database :red_circle:
A random address generator can be created using a dataset released by Intel under a XXX licensing agreement

### Name database :red_circle:
A random name generator can be created using a dataset released by Intel under a XXX licensing agreement
