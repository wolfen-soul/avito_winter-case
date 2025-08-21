# Avito Merch Shop API
Task: https://github.com/avito-tech/tech-internship/blob/main/Tech%20Internships/Backend/Backend-trainee-assignment-winter-2025/Backend-trainee-assignment-winter-2025.md

## Content
- [Development stack](#-development-stack)
- [How to start](#-how-to-start)
- [API Reference](#-api-reference)

## Development stack
- **Language**: Java 17
- **Database**: PostgreSQL 16
- **Authentication**: JWT
- **Сontainerization**: Docker, Docker Compose

## How to start
```
git clone https://github.com/wolfen-soul/avito_winter-case
cd avito_winter-case
docker-compose up -d
```

## API Reference
Base URL: `http://localhost:8080/api`

#### For non-authorized users

`POST /auth/signup` - creates new user with initial wallet (1000 coins)

`POST /auth/signin` - signs the registered user in and gives JWT-token

Body (JSON):
```
{
      "username":"your_username",
      "password":"your_password"
}
```

Copy received JWT token and set it: `Authorization -> Bearer token`

#### For authorized users:
`GET /info` - receive info about authorized user

`POST /buy/item?=quantity=number` - buy certain quantity of items ("item" should be replaced with the name of the item, "number" - with certain amount to buy)

`POST /sendCoin` - make a transaction to another registered user

Body (JSON):
```
{
      "user":"user_to_send",
      "amount":"amount_of_coins"
}
```

