# Task Description
Implement a program that monitors web sites and reports their availability. This tool is intended as
a monitoring tool for web-site administrators for detecting problems on their sites.
Main functions:
1. Reads a list of web pages (HTTP URLs) and corresponding page content requirements from a
   configuration file.
2. Periodically makes an HTTP request to each page.
3. Verifies that the page content received from the server matches the content requirements.
4. Measures the time it took for the web server to complete the whole request.
5. Writes a log file that shows the progress of the periodic checks.
6. (OPTIONAL) Implement a single-page HTTP server interface in the same process that shows
   (HTML) each monitored web site and their current (last check) status.
# Details:

1. The “content requirement” can for example be just a simple string that must be included in the
   response received from the server, e.g. one rule might be that the page at the
   URL http://www.foobar.com/login must contain the text “Please login:”.
2. The checking period must be configurable via a command-line option or by a setting in the
   configuration file.
3. The log file must contain the checked URLs, their status and the response times.
4. The program must distinguish between connection level problems (e.g. the web site is down) and
   content problems (e.g. the content requirements were not fulfilled).
5. Unit tests would be handy to have!
6. There is a lot of freedom to choose software technologies, tools and file formats to achieve the
   goal.


# Solution

- The program has necessary unit tests.

## Tech Stack
- Java 17
- Spring Boot 3
- Maven
- Junit 5
- Logback
- Lombok
- Quartz Scheduler

## Architecture
The solution is based on the following architecture:
1) Config.json - contains the configuration of the websites to be monitored
   - add website related or default configuration(applicable to all websites)
2) FileConfigurationReaderService - reads the configuration from the config.json file
   - stream reading can be used for large files
3) WebsiteMonitoringService - starts schedulers
4) ContentVerifierService - verifies the content of the website
5) WebsiteMonitoringJob - represents the job to be executed by the scheduler
6) QuartzWebsiteMonitoringScheduler - schedules the job to be executed
   - scheduler configuration can be tuned as per the requirement
7) RestHttpService - uses RestTemplate to make HTTP calls
   - RestTemplate config can be tuned 
8) AppConfig, QuartzConfig - configuration classes

## How to run
1) Clone the repository
2) Run the MonitorApplication.java file
   - Update `logback-spring.xml` file for changing the log file location or format
   - Update `config.json` file for changing the configuration of the websites to be monitored
3) The logs will be generated in the logs folder
   - all_logs.log - contains all logs
   - website_status.log - contains the status of the websites with errors as well


