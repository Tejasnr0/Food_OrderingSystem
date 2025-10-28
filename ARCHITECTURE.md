 Architecture Diagram (Sequence Diagram)
`mermaid
  erDiagram
      USER {
          Long Id PK
          String fullName
          String password
          String email
          UserRole role
      }

      ADDRESS {
          Long id PK
          String streetaddress
          String city
          String stateProvince
          String country
      }

      RESTAURANT {
          Long id PK
          String name
          String description
          String cuisineType
          String OpeningHourse
          boolean open
          LocalDateTime registerTime
      }

      CATEGORY {
          Long id PK
          String name
      }

      FOOD {
          Long id PK
          String name
          String description
          Long price
          Boolean avaible
          Boolean isVeg
          Boolean isSeasonal
          Date createdDate
      }

      INGREDIENTS {
          Long Id PK
          String name
          boolean Avaible
      }

      INGRED_CATEGORY {
          Long id PK
          String name
      }

      ORDERS {
          Long id PK
          Long totalAmount
          String orderStatus
          Date createdAt
          Integer totalItem
          Long totalPrice
      }

      ORDER_ITEM {
          Long id PK
          Integer quantity
          Long totalPrice
      }

      CART {
          Long id PK
          Long Total
      }

      CART_ITEM {
          Long id PK
          Integer qualtiy
          Long totalPrice
      }

      USER ||--o{ ADDRESS : has
      USER ||--o{ ORDERS : places
      USER ||--o{ CART : has
      RESTAURANT ||--o{ CATEGORY : has
      RESTAURANT ||--o{ FOOD : serves
      RESTAURANT ||--o{ INGRED_CATEGORY : has
      RESTAURANT ||--o{ ORDERS : receives
      RESTAURANT ||--o{ INGREDIENTS : has
      RESTAURANT {
          User owner FK
      }
      CATEGORY {
          Restaurant res FK
      }
      FOOD {
          Category cat FK
          Restaurant restaurant FK
      }
      FOOD ||--o{ INGREDIENTS : contains
      INGRED_CATEGORY {
          Restaurant res FK
      }
      INGREDIENTS {
          IngredCategory category FK
          Restaurant res FK
      }
      ORDERS {
          User customer FK
          Restaurant res FK
          Address deliveryAddress FK
      }
      ORDERS ||--o{ ORDER_ITEM : contains
      ORDER_ITEM {
          Food food FK
      }
      CART {
          User customer FK
      }
      CART ||--o{ CART_ITEM : contains
      CART_ITEM {
          Cart cart FK
          Food food FK
      }
      `
