# Cycling Video API
A RESTful API to store, delete and search video data from GlobalCyclingNetwork and globalmtb Youtube channels.

## Install & Run
Run MySQL container:

`docker run -p 3306:3306 --name some-mysql -e MYSQL_ROOT_PASSWORD=123456 -d mysql:5.7`

Run application with api key environment variable:

`YOUTUBE_API_KEY={your-key-here} ./gradlew bootRun`

## API Docs
Can be accessed here: https://documenter.getpostman.com/view/10876522/TzRREURq

## Next Steps
- Handle several pages of YouTube search results (currently only first page is handled).
