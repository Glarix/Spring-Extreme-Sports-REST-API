# Extreme Sports REST API 
### Project by Codreanu Dan
### Developed in April 2022

---
# Description
This is a project made for Atta Systems internship recruitment process 2022.
The project consists of a REST API made with Java, Spring, Spring Data JPA and PostgreSQL.
It represents the backend for managing Extreme Sports practice locations.

Structure: The backend is modeled by a hierarchical data structure composed of Country,
Region, City and Sport models. The models are stored in a relational Database (PostgreSQL)
and the user can add, get, delete or update any of these models and I also implemented a
searching feature to search by sports and/or by periods.

---
# API Documentation
#### Every endpoint begins with: localhost:8080/api/v1
####I will continue referring to it as simple "localhost"
There are endpoints implemented for each model of the application.
* #### Country Model:
    * getCountries - A method to get a list with all the saved countries.
      * Endpoint: localhost/getcountries
      * Request: access endpoint, no JSON body needed
    * saveCountry - Method to save a new country into the database
      * Endpoint: localhost/addcountry
      * Request: access endpoint with JSON Body with:
      ```json
      {"id":0, "countryName":"nameOfCountry"}
      ```
    * getCountry - Method to get a country by name
      * Endpoint: localhost/getcountry/{name}
      * Request: access endpoint, {name} should be the name of the wanted country
    * deleteCountry - Method to delete a country from database
      * Endpoint: localhost/deletecountry/{country}
      * Request: access endpoint, {country} should be the name of the country to delete
* #### Region Model:
    * getRegions - Method to get a list with all existent regions
      * Endpoint: localhost/getregions
      * Request: access endpoint
    * saveRegion - Method to save a new region to database
      * Endpoint: localhost/addregion
      * Request: access endpoint with JSON Body:
      ```json
      {"id":0, "regionName":"nameOfRegion", "countryName":"nameOfCountryTheRegionIsFrom"}
      ```
    * getRegion - Method to get a region by name
      * Endpoint: localhost/getregion/{name}
      * Request: access endpoint, {name} should be the name of wanted region
    * deleteRegion - Method to delete a region from database
      * Endpoint: localhost/deleteregion/{region_countryName}
      * Request: access endpoint, {region_countryName} should be a combination between the regionName and 
      countryName separated with '_'
* #### City Model:
    * getCities - Method to get a list of all existing cities
      * Endpoint: localhost/getcities
      * Request: access endpoint
    * saveCity - Method to save a new city in database
      * Endpoint: localhost/addcity
      * Request: access endpoint with JSON Body:
      ```json
      {"id":0, "cityName":"nameOfCity", "regionName":"nameOfRegion"}
      ```
    * getCity - Method to get a city by name
      * Endpoint: localhost/getcity/{name}
      * Request: access endpoint, {name} should be the name of wanted city
    * deleteCity - Method to delete a city from database
      * Endpoint: localhost/deletecity/{city_regionName}
      * Request: access endpoint, {city_regionName} should be a combination between the cityName and
      regionName separated with '_'
    * deleteSportFromCity - Method to delete a sport-city relationship from the city_sport table
      * Endpoint: localhost/deletesportfromcity/{cityName_sportName}
      * Request: access endpoint, {cityName_sportName} should be a combination between the cityName and
        sportName separated with '_'
* #### Sport Model:
   * getSports - Method to get a list of all existing sports
     * Endpoint: localhost/getsports
     * Request: access endpoint
   * saveSport - Method to save a new sport to database
     * Endpoint: localhost/addsport
     * Request: access endpoint with JSON Body:
     ```json
     {"id": 0, "sportName": "nameOfTheSport", "cityName": "nameOfTheCity", 
     "startDate": "periodStartDate", "endDate": "periodEndDate", "price": 0.00}
     ```
     * Note: The dates must be in SQL format YYYY-MM-DD
   * getSport - Method to get a sport by name
     * Endpoint: localhost/getsport/{name}
     * Request: access endpoint, {name} should be the name of wanted sport
   * deleteSport - Method to delete a spport from database
     * Endpoint: localhost/deletesport/{name}
     * Request: access endpoint, {name} should be the name of the sport to be deleted
   * updateSport - Method to update the price or period of a sport
     * Endpoint: localhost/updatesport/{sportName_cityName}
     * Request: access endpoint with JSON Body:
     ```json
     {"id": 0, "sportName": "nameOfTheSport", "cityName": "nameOfTheCity", 
     "startDate": "newPeriodStartDate", "endDate": "newPeriodEndDate", "price": 1.00}
     ```   
   * selectSports - Method to get a list of available sports in a certain period
     * Endpoint: localhost/selectsports
     * Request: access endpoint with JSON Body:
     ```json
     {"sports": ["sport_name1", "sport_name2 .."], "startDate": "periodStartDate",
     "endDate": "periodEndDate"}
     ```
     * Note: Dates must be SQL format YYYY-MM-DD and any of JSON attributes might be empty
       
---
## Database Structure

The database contains 5 tables that are as following:

Country table:

| country_id | country_name |
|------------|--------------|
| id_1       | name_1       |

Region table:

| region_id | region_name | country_id |
|-----------|-------------|------------|
| id_1      | name_1      | C_id_1     |

City table:

| city_id | city_name | region_id |
|---------|-----------|-----------|
| id_1    | name_1    | R_id_1    |
Sport table:

| sport_id | sport_name |
|----------|------------|
| id_1     | name_1     |

City_sport table:

| city_sport_id | city_id | sport_id | start_date | end_date | price  |
|---------------|---------|----------|------------|----------|--------|
| id_1          | C_ID_1  | S_id_1   | startdate1 | enddate1 | price1 |

### Table Relationships
* Country - Region : OneToMany
* Region - City : OneToMany
* City - Sport : ManyToMany

---
## Dependencies
Necessary dependencies that I used:
* Spring Web
* Spring Data JPA
* PostgreSQL Driver

---
##### Codreanu Dan
