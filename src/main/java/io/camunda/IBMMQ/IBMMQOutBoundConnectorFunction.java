package io.camunda.IBMMQ;

import com.ibm.mq.MQException;
import io.camunda.connector.api.annotation.OutboundConnector;
import io.camunda.connector.api.outbound.OutboundConnectorContext;
import io.camunda.connector.api.outbound.OutboundConnectorFunction;
import io.camunda.connector.generator.java.annotation.ElementTemplate;
import io.camunda.IBMMQ.dto.IBMMQ;
import io.camunda.IBMMQ.dto.IBMMQOutboundConnectorRequest;
import io.camunda.IBMMQ.dto.IBMMQOutboundConnectorResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@OutboundConnector(
    name = "IBM MQ OutBound Connector",
    inputVariables = {"qmgr", "qname", "message"},
    type = "io.camunda:template:1")
@ElementTemplate(
    id = "ibmmqoutboundtemplate",
    name = "IBM MQ OutBound Connector",
    version = 1,
    description = "This custom connector links Camunda 8 to IBM MQ, allowing workflows to publish and consume messages for real-time, event-driven process automation.",
    inputDataClass = IBMMQOutboundConnectorRequest.class)
public class IBMMQOutBoundConnectorFunction implements OutboundConnectorFunction {

  private static final Logger LOGGER = LoggerFactory.getLogger(IBMMQOutBoundConnectorFunction.class);

  @Override
  public Object execute(OutboundConnectorContext context) throws MQException, IOException {
    final var connectorRequest = context.bindVariables(IBMMQOutboundConnectorRequest.class);
    return executeConnector(connectorRequest);
  }

  private IBMMQOutboundConnectorResult executeConnector(final IBMMQOutboundConnectorRequest connectorRequest) throws MQException, IOException {

    String Queue_Manager = connectorRequest.qmgr();
    String Queue_Name =  connectorRequest.qname();
    String Message = connectorRequest.message();
    IBMMQ ibmmq = new IBMMQ();
    String result=ibmmq.messagePublish(Queue_Manager,Queue_Name,Message);

    System.out.println("Queue_Manager : "+Queue_Manager);
    System.out.println("Queue_Name : "+Queue_Name);
    System.out.println("Message : "+Message);
    System.out.println("Message Published Status : " +result);

    return new IBMMQOutboundConnectorResult("Message Published Status : " +result);
  }
}
