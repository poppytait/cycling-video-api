# Cycling Video API
A RESTful API to store, delete and search video data from GlobalCyclingNetwork and globalmtb Youtube channels.

## Install & Run
Run MySQL container:

`docker run -p 3306:3306 --name some-mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7`

Run application:

`./gradlew bootRun`
