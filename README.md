# HumanResourcesAssistant
An application for helping human resources department in managing existing employees, their documents and requirements. Also including functionality to manage CVs from external.

## Requirements
The application run on the following component version:

<pre><code>
 JAVA 1.8
 MAVEN 3.6.3
 VAADIN 14.1.24
 SPRING 2.2.5
 </code></pre>
 
##Installing
To run the application 

<pre><code>git clone https://github.com/EugenS21/HumanResourcesAssistant.git

mvn clean compile install spring-boot:run</code></pre>
 
Wait for all the dependencies to be resolved, UI and BE initialized. The applicatoin will be available at

<pre><code>localhost:80</code></pre>

##Deployment
Currently, deployment is not available, the only way to deploy is to manually checkout and run.

##Testing
There are available 3 types of tests:
* unit;
* integration;
* regression.

To run automated tests for this application
<pre><code>mvn clean test -P 'typeoftest'</code></pre>

###Reports
Generated test reports may be found in the report folder which will be added after each mvn test command. And will be cleared on mvn clean.

##Build with
<pre>
Maven - dependency management
Google Cloud - virtual machines and storage space
SSHJ - ftp client to transfer files remotely
PostreSQL - database solution
</pre>