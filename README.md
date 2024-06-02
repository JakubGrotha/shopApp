# ShopApp

A playground project that serves as a backend application for e-commerce site.

The main aim of this project is to try out different technologies and learn how to implement various solutions and ideas.

### How to use

#### Docker containers

You can test the app by creating the required docker containers by running:

```bash
docker-compose up -d
```

Make sure you have Docker Desktop (or Colima) installed and running if you're not using Linux

#### Authentication

Before you can test any of the endpoints in the `order`, `product` or `promocode` packages, you first need to create an account. You can use any credentials, for example:

```json
{
  "email": "example@example.com",
  "password": "password",
  "postalCode": "1111",
  "city": "Warsaw",
  "country": "Poland"
}
```

Send the credentials as request body to `/register` endpoint, and you will receive the token (JWT), you need to pass with every request in the Authorization header (with the "Bearer " prefix).
