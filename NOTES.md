
# Sources

* Test
  * https://devops.com/13-best-practices-successful-software-testing-projects/
  * https://junit.org/junit4/javadoc/4.12/org/junit/rules/ExpectedException.html
  * https://stormpath.com/blog/7-tips-writing-unit-tests-java
  * https://www.vogella.com/tutorials/Mockito/article.html
  * https://powermock.github.io/
  * https://testng.org/doc/
* Logging
  * http://www.slf4j.org/manual.html
* Splunk Search Engine
  * https://www.splunk.com/en_us/download.html
  * https://www.splunk.com/en_us/about-us/why-splunk.html
  * https://www.splunk.com/themes/splunk_com/img/assets/pdfs/education/SplunkDeploymentGuide2_1.pdf
  * https://docs.splunk.com/Documentation/Splunk/8.0.1/SearchReference/ListOfSearchCommands
  * https://www.splunk.com/pdfs/solution-guides/splunk-quick-reference-guide.pdf
  * https://docs.splunk.com/Documentation/Forwarder/8.0.1/Forwarder/HowtoforwarddatatoSplunkEnterprise
  * http://localhost:8000/en-GB/help?location=learnmore.adddata.success.search
  * https://dev.splunk.com/enterprise/docs/java/logging-java/getstartedloggingjava/installloggingjava/
  * https://docs.splunk.com/Documentation/Splunk/8.0.1/Alert/DefineRealTimeAlerts
  * https://dev.splunk.com/enterprise/docs/java/logging-java/howtouseloggingjava/enableloghttpjava
  * https://www.splunk.com/en_us/blog/tips-and-tricks/splunk-sdk-for-java-now-has-maven-support.html
* Git
  * https://www.udacity.com/course/version-control-with-git--ud123
  * https://www.udacity.com/course/github-collaboration--ud456
* Docker
  * https://www.docker.com/why-docker
* Jenkins: https://jenkins.io
* AWS 
  * https://d1.awsstatic.com/whitepapers/AWS_Blue_Green_Deployments.pdf


# Jenkins

## Generate SSH Keys

```bash 
ssh-keygen -t rsa
```

## Workaround for java.security.InvalidAlgorithmParameterException: the trustAnchors parameter must be non-empty

https://gist.github.com/mikaelhg/527204e746984cf9a33f7910bb8b4cb6


# Splunk

```bash 
https://www.splunk.com/en_us/training/videos/installing-splunk-enterprise-on-linux.html
```

## Queries
```text
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" HHID="046612"
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | stats count by HHID

source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | head 10
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | tail 10
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | eval areturn=ACTIVHH / AGEHH1
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | where HHID="048527"
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | table HHID,ACTIVHH
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | search HHID="001*"
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | rename HHID as HouseID | rename ACTIVHH as ActiveHouse
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv"  | fields HHID| timechart avg(HHID)
source="505266201_T_ATS_HH_CHARACTERISTICS.csv" host="t480" sourcetype="csv" | top HHID | bucket _time span=1h
```
