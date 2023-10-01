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

Note that the task is meant for evaluating both coding skills and software architecture design skills.
Pay attention to the design of applications you create. Be ready to defend your architectural
decisions in a discussion. It is necessary to personally write the source code, but it does not have
to be complete.
Deliverables:
The software is delivered with the full source code included. All used source code must be freely
distributable.
If necessary, include readme.txt to describe the software and how it meets the requirements.

# Solution

## Tech Stack
- Java 17
- Spring Boot 3
- Maven
- Junit 5
- Logback
- Lombok
- Quartz Scheduler

## Architecture
The solution is based on the following architecture(in below order):
1) Config.json - contains the configuration of the websites to be monitored
   - add website related or default configuration(applicable to all websites)
2) ConfigurationReaderService - reads the configuration from the config.json file
   - stream reading can be used for large files
3) ConfigCacheService - caches the configurations
   - can be replaced with a database
4) SchedulerService - schedules the monitoring of the websites
   - Quartz scheduler can be tuned
5) WebsiteMonitorJob - monitors a website
6) ContentVerifier - verifies the content of a website
7) CustomLogger - logs the monitoring results to a file with errors as well

## How to run
1) Clone the repository
2) Run the MonitorApplication.java file
   - Update `logback-spring.xml` file for changing the log file location or format
   - Update `config.json` file for changing the configuration of the websites to be monitored
3) The logs will be generated in the logs folder
   - all_logs.log - contains all the logs
   - website_status.log - contains the status of the websites with errors as well


