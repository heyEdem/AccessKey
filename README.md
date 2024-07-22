
# Amalitech Access Key Manager

Access Key Manager is a web application designed for Micro-Focus Inc.'s school management platform. It allows school IT personnel to purchase and manage access keys to activate their school accounts. The application also enables Micro-Focus admins to oversee and manage all access keys across the platform. This project is tailored for schools using the platform and the administrative team at Micro-Focus Inc.


## Project Objective
Micro-Focus Inc. built a school management platform where various schools can set up and operate independently. To monetize the platform, an access key-based approach is used instead of integrating payment features. This project aims to develop an access key manager, a web application for schools to purchase access keys and activate their accounts.
## Requirements
- JDK 17 or later
- Maven 3.3.6 or later
- Springboot 3.0 or later
- PostgreSQL 15 or later

## Support
I'll try to keep up to date, but if you
- Notice something wrong
- Want me to make a release for a new version
- Want a minute of my time
  Feel free to reach out to me at ```akatoaugustine@gmail.com```


## Features

### School IT Personnel
- Signup & login with email and password, including account verification.
- Reset password feature to recover lost passwords.
- View a list of all access keys: active, expired, or revoked.
- View the status, procurement date, and expiry date for each access key.
- Restrict users from obtaining a new key if an active key already exists. Only one active key per user.

### Micro-Focus Admin
- Login with email and password.
- Manually revoke access keys.
- View all keys generated on the platform with their status, procurement date, and expiry date.
- Access an endpoint to verify the active key status of a school based on the provided school email.


## Installation
To install and run the Access Key Manager locally:

- Clone the repository:
   ```bash
   git clone https://github.com/heyEdem/AccessKey.git
   ```
   ```bash 
   cd AccessKey
   ```

## Configuration
- Ensure the environment variables for the database and mail server are configuration are set correctly.
- Update the ```application-dev.yaml``` file with the necessary infomation.

### Quick Run (Maven)
```bash 
 mvn clean install
 ``` 

## Usage
- Visit ```bash localhost:8080``` to access the application




## Documentation

Click [here](http://localhost:8080/swagger-ui.html)
to view all the endpoint documentation

## Run Locally


### Authentication
#### Signup
Request
 ```bash 
 POST http://localhost:8080/api/v1/signup
 {
    "name": "Ohene",
    "email": "yayol61671@stikezz.com",
    "password": "password"
}
 ```

Response
 ```bash 
 {
  "message": "Token sent, check your email"
}
 ```

#### Verify User

Request
 ``` bash 
 POST http://localhost:8080/api/v1/verify-user

 {
  "code": "116787",
  "email": "yayol61671@stikezz.com"
}
 ```

Response
 ```bash
 "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYXd5ZXIxQGdtYWlsLmNvbSIsImlhdCI6MTU5MTU5MDg5NCwiZXhwIjoxNTkyMTk1Njk0fQ.pavyfoIoZW-f-R07UG2aJjszP2ttycQZuFQiJeSJ-N1YMjK18M2_ciS2-sFaA_4OAK-UkySni_gTvAG77wtgJg",
  "name": "Ohene",
  "email": "yayol61671@stikezz.com"
  "role": "SCHOOL"
 ```

#### Login user
Request
```bash
POST http://localhost:8080/api/v1/login

{
  "email": "yayol61671@stikezz.com",
    "password": "password"
}
```
Response
 ```bash
 "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYXd5ZXIxQGdtYWlsLmNvbSIsImlhdCI6MTU5MTU5MDg5NCwiZXhwIjoxNTkyMTk1Njk0fQ.pavyfoIoZW-f-R07UG2aJjszP2ttycQZuFQiJeSJ-N1YMjK18M2_ciS2-sFaA_4OAK-UkySni_gTvAG77wtgJg",
  "name": "Ohene",
  "email": "yayol61671@stikezz.com"
  "role": "SCHOOL"
 ```

#### Resend Verification Token
Request
```bash
POST http://localhost:8080/api/v1/resend-otp

{
  "email": "yayol61671@stikezz.com",
    "type": "create"
}
```
Response
 ```bash 
 {
  "message": "Token sent, check your email"
}
 ```

#### Request password Reset
Request
```bash
POST http://localhost:8080/api/v1/request-password-reset

{
  "email": "yayol61671@stikezz.com",
}
```
Response
 ```bash 
 {
  "message": "Token sent, check your email"
}
 ```

#### Reset password

Request
```bash
POST http://localhost:8080/api/v1/reset-password

{
  "email": "yayol61671@stikezz.com",
    "type": "create"
}
```
Response
 ```bash 
 {
  "Password reset successfully, login in"
}
 ```

### Access Keys

#### Create New Key
Request
```bash
POST http://localhost:8080/api/v1/access-keys/new-key
Accept: */*
Cache-Control: no-cache
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYXd5ZXIxQGdtYWlsLmNvbSIsImlhdCI6MTU5MTU1ODg4MywiZXhwIjoxNTkyMTYzNjgzfQ.OKY-SJjgzmKAfKd0HbJyMIxWnJqsgnUayo2M2CDVWdSNzSzldOG8x3rGrrVBBl9K6Rjp_hGQWYFXLNOd2tqtpg

{
    "name": "First Key"
}
```
Response
 ```bash 
 {
  "message": ""New access key added"",
  "code": "415626001491436",
  "expiry": "2024-08-22",
  "name": "First Key",
  "status": "ACTIVE"
}
 ```

#### Get User Keys
Request
```bash
GET http://localhost:8080/api/v1/access-keys/my-keys
Accept: */*
Cache-Control: no-cache
Content-Type: application/json
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJsYXd5ZXIxQGdtYWlsLmNvbSIsImlhdCI6MTU5MTU1ODg4MywiZXhwIjoxNTkyMTYzNjgzfQ.OKY-SJjgzmKAfKd0HbJyMIxWnJqsgnUayo2M2CDVWdSNzSzldOG8x3rGrrVBBl9K6Rjp_hGQWYFXLNOd2tqtpg
```
Response
 ```bash 
 {
    "content": [
        {
            "name": "Slightly",
            "createdAt": "2024-07-21T14:06:38.097905",
            "status": "ACTIVE",
            "code": "8012059134311135",
            "expiry": "2024-08-20"
        }
    ],
    "page": {
        "size": 100,
        "number": 0,
        "totalElements": 1,
        "totalPages": 1
    }
}
 ```

## Authors

- [Edem Akato](https://www.github.com/heyEdem)


## Environment Variables

To run this project, you will need to add the following environment variables to your .env file


`POSTGRES_USER`
`DB_HOST`
`DB_PORT`
`DB`

`MAIL_USERNAME`
`MAIL_PASSWORD`
`MAIL_HOST`
`MAIL_PORT`

`JWT_SECRET`
`EXPIRATION`

## Tech Stack

Java, Springboot

