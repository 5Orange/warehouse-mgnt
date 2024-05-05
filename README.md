docker run --name pg1 -p 5432:5432 -e POSTGRES_USER=datvu -e POSTGRES_PASSWORD=asdasd -e POSTGRES_DB=mydb -d postgres:15-alpine

commonEntity
    - id
    - createDate
    ....

category
    - id
    - name
product
    - name
    - code
    - price
    - categoryID
    - providerID
OrderItem
    - orderId
    - productId
    - count
provider:
    - id
    - name
order
    - id
    - List<OrderItem>

quantity
    - productId
    - count

importLog:
    - 