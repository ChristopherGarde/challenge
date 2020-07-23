# Setup
Prerequisites:
* Have java 11 installed
* Run `./gradlew build` to install the version of gradle and build the application 
* Run `./gradlew bootRun` to start the application

## API Endpoints
### `/classes`
`GET` - Returns the current list of classes held in the array in memory

`POST` - Create a new class 

---

### `/bookings`
`GET` - Returns the current list of bookings held in the array in memory

`POST` - Create a new booking 

## Example Curl Commands
Create a class
```
curl -X POST 127.0.0.1:8080/classes -H 'Content-Type: application/json' -d '{"name": "testName", "startDate": "2020-07-22", "endDate": "2020-07-29", "maxCapacity": 20}'
```

Create a booking
```
curl -X POST 127.0.0.1:8080/bookings -H 'Content-Type: application/json' -d '{"day": "2020-07-23","classDetails": {"name": "testName","startDate": "2020-07-22","endDate": "2020-07-29","maxCapacity": 20},"member": {"name": "testName","email": "test@test.com"}}'
```

You can view all created classes by going to `127.0.0.1:8080/classes` in a browser

You can view all created bookings by going to `127.0.0.1:8080/bookings` in a browser
