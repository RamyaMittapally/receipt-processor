# Receipt Processor App Setup

## Step 1: Install Docker and a REST API client

* Install [Docker](https://www.docker.com/get-started)
* Use a REST API client like [Postman](https://www.postman.com/downloads/), or use `curl` in the terminal.

## Step 2: Log in to Docker Hub

* Open your terminal and run the following command to log in to Docker Hub:
* If port 8080 is already in use on your system, change the host port
```bash
docker login
docker pull ramyamittapally/receipt-processor:v1.0
docker run -p 8080:8080 ramyamittapally/receipt-processor:v1.0
```


## Step 3: Send a POST request to process a receipt

* URL: http://localhost:8080/receipts/process
* Example

Method: POST

Header: Content-Type = application/json
```
Body:
{
"retailer": "Target",
"purchaseDate": "2022-01-01",
"purchaseTime": "13:01",
"items": [
{"shortDescription": "Mountain Dew 12PK", "price": "6.49"},
{"shortDescription": "Emils Cheese Pizza", "price": "12.25"}
],
"total": "18.74"
}
```
You will receive a response with an ID like "3f5a8b4d"
* Use the ID to get reward points

* URL format: http://localhost:8080/receipts/{id}/points
* Method: GET




