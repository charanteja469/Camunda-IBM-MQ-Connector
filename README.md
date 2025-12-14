# Camunda 8 IBM mQ Custom Outbound Connector- Local(windows installation)

Camunda 8 IBM MQ Outbound Connector is a custom-built connector (developed using the Camunda Connector SDK, typically in Java) that seamlessly integrates Camunda 8 process automation with NSQ. It allows Camunda workflows to publish messages to NSQ topics as part of a service task, enabling asynchronous communication and decoupling of processes. Developers can easily configure message content and NSQ topic details directly within their BPMN models.





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
















