# Camunda 8 IBM MQ Custom Outbound Connector- Local(windows installation)

Camunda 8 IBM MQ Outbound Connector is a custom-built connector (developed using the Camunda Connector SDK, typically in Java) that seamlessly integrates Camunda 8 process automation with IBM MQ. It allows Camunda workflows to send and receive messages to/from IBM MQ queues as part of a service task, enabling asynchronous communication and decoupling of processes. Developers can easily configure queue manager details, host, port, channel, queue name, and message content directly within their BPMN models.


<img width="2546" height="1090" alt="workflow" src="https://github.com/user-attachments/assets/8b84a492-6991-4408-99ba-85af020f66e7" />




### IBM MQ



1. install the server which is 90 days trail version- https://www.ibm.com/resources/mrs/assets/DownloadList?source=ibmmqtrial&lang=en_US
2. set the path
3. run cmd in admistration mode(else you will face authorization error while creting queue manager)
   4.type dspmqver(which will give info about mq)
   C:\Windows\System32>dspmqver
   Name:        IBM MQ
   Version:     9.4.0.10
   Level:       p940-010-250212.1.TRIAL
   BuildType:   IKAP - (Production)
   Platform:    IBM MQ for Windows (x64 platform)
   Mode:        64-bit
   O/S:         Windows 11 Professional x64 Edition, Build 22621
   InstName:    Installation1
   InstDesc:
   Primary:     Yes
   InstPath:    C:\Program Files\IBM\MQ
   DataPath:    C:\ProgramData\IBM\MQ
   MaxCmdLevel: 940
   LicenseType: Trial
   ReleaseType: Long Term Support (LTS)

A)Creating the Queue Manger

1.crtmqm QM1 : creates a queue manager named QM1 with default config


C:\Windows\System32>crtmqm QM1
There are 17 days left in the trial period for this copy of IBM MQ.
IBM MQ queue manager 'QM1' created.
Directory 'C:\ProgramData\IBM\MQ\qmgrs\QM1' created.
The queue manager is associated with installation 'Installation1'.
Creating or replacing default objects for queue manager 'QM1'.
Default objects statistics : 86 created. 0 replaced. 0 failed.
Completing setup.
Setup completed.


2.strmqm QM1 : starts it; dspmq should show QM1 in RUNNING status

C:\Windows\System32>strmqm QM1
There are 17 days left in the trial period for this copy of IBM MQ.
IBM MQ queue manager 'QM1' starting.
The queue manager is associated with installation 'Installation1'.
6 log records accessed on queue manager 'QM1' during the log replay phase.
Log replay for queue manager 'QM1' complete.
Transaction manager state recovered for queue manager 'QM1'.
Plain text communication is enabled.
IBM MQ queue manager 'QM1' started using V9.4.0.10.

3. dspmq

C:\Windows\System32>dspmq
QMNAME(QM1)                                               STATUS(Running)


B)Create a LOCAL Queue

Command

runmqsc QM1
DEFINE QLOCAL(Charan)
END


C:\Windows\System32>runmqsc QM1
5724-H72 (C) Copyright IBM Corp. 1994, 2025.
Starting MQSC for queue manager QM1.


DEFINE QLOCAL(TEST.Q)
1 : DEFINE QLOCAL(TEST.Q)
AMQ8006I: IBM MQ queue created.
END
2 : END
One MQSC command read.
No commands have a syntax error.
All valid MQSC commands were processed.

C)Publish a Message

amqsput TEST.Q QM1

C:\Windows\System32>amqsput TEST.Q QM1
Sample AMQSPUT0 start
target queue is TEST.Q
hello this is Charan

D)Subscribe the Message

amqsget TEST.Q QM1

C:\Windows\System32>amqsget TEST.Q QM1
Sample AMQSGET0 start
message <hello this is charan>
no more messages
Sample AMQSGET0 end

## Test with SaaS and Self-Managed

#### Setting Up the Saas Environment:

1. Navigate to Camunda [SaaS](https://console.camunda.io).
2. Create a cluster using the latest version available.
3. Select your cluster, then go to the `API` section and click `Create new Client`.
4. Ensure the `zeebe` checkbox is selected, then click `Create`.
5. Copy the configuration details displayed under the `Spring Boot` tab.
6. Paste the copied configuration into your `application.properties` file within your project.

#### Setting Up the Self-Managed Environment:

1. Set up the Camunda 8 Self-Managed(https://docs.camunda.io/docs/self-managed/setup/deploy/local/docker-compose/).
2. Cluster endpoint is http://localhost:26500
3. uncomment the properties in test resource folder

   (camunda.client.zeebe.grpc-address=http://localhost:26500)
   
   (camunda.client.zeebe.rest-address=http://localhost:8088)
5. download desktop modeler if requires (https://camunda.com/download/modeler/)


### Launching Your Connector

1. Start your connector by executing `io.camunda.IBMMQ.LocalConnectorRuntime` in your development environment.
2. Access the Web Modeler or Desktop Modeler and create a new project.
3. Click on `Create new`, then select `Upload files`. Upload the connector template from the repository you have(https://github.com/charanteja469/Camunda-IBM-MQ-Connector/blob/master/element-templates/IBM%20MQ%20Outbound%20Connector.json)

 NOTE: if your using Desktop modeler--> go to modeler folder-->resources-->element-templates-->Past the above downloaded IBMMQ  
       Connector Template

4. In the same folder, create a new BPMN diagram.
5. Design and start a process that incorporates your new connector.


# STEP BY STEP Process to Configure and Use IBM MQ Outbound Connector

1. Create a workflow with Start event, Task, End Event
2. select the task and click on element change type and search for IBM MQ Outbound Connector
<img width="1760" height="1138" alt="IBMMQTemplateSelection" src="https://github.com/user-attachments/assets/2e36eb18-7b0b-4815-ae9f-f6ff8a577206" />

<img width="2546" height="1090" alt="workflow" src="https://github.com/user-attachments/assets/f938fe84-dbc5-41a1-9b6b-5c2d2a713cc1" />

3. Configure the Input like (Queue Manager, Queue Name, Message)
   <img width="2558" height="1060" alt="ConnectorInput" src="https://github.com/user-attachments/assets/00e89728-5e01-4acd-8e5a-78db2f0caecd" />
4. Configure the output Result Expression
   #### Result Expression :

   {"Status":result}

5. Deploy the process and Start the Process
6. Start the Connector Runtime
<img width="2536" height="868" alt="RunningConnectorCode" src="https://github.com/user-attachments/assets/c6bdd687-bd3a-4974-a8e0-61de6af169e5" />
<img width="2552" height="1336" alt="ConnectorCodeConsoleOutput" src="https://github.com/user-attachments/assets/28fef135-4713-4e20-99c1-f0f4eb028378" />
 7. IBM MQ Outbound Connector successfully published the message.

<img width="2550" height="1166" alt="OperatorData" src="https://github.com/user-attachments/assets/6b1f6670-0a50-405b-bc2e-459d99a00a69" />
<img width="2552" height="1336" alt="ConnectorCodeConsoleOutput" src="https://github.com/user-attachments/assets/0da60784-d3c5-47fa-9bd6-7921b5162cb5" />



   
8. Now you can open the IBM MQ consumer application and consume the message.

   here i'm using IBM MQ Consumer CLI. Run this Command and consume the message

   amqsget OK.NICE QM1

   <img width="1118" height="390" alt="CLImessagesub" src="https://github.com/user-attachments/assets/cb71b9db-cba6-4e9b-9292-082cd80145e3" />

## Refer Camunda BPMN File

you can refer the Camunda BPMN file here 👉🏻 https://github.com/charanteja469/Camunda-IBM-MQ-Connector/blob/master/src/main/resources/IBMMQConnector_Test.bpmn






















