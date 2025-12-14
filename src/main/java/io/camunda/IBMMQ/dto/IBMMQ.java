package io.camunda.IBMMQ.dto;


import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;

import java.io.IOException;


public class IBMMQ {
    public String messagePublish(String QMGR_NAME, String QUEUE_NAME, String message) throws MQException, IOException {

        MQQueueManager qmgr = new MQQueueManager(QMGR_NAME);
        MQQueue queue=qmgr.accessQueue( QUEUE_NAME, MQConstants.MQOO_OUTPUT | MQConstants.MQOO_FAIL_IF_QUIESCING);

        MQMessage msg = new MQMessage();
        msg.format = MQConstants.MQFMT_STRING;
        msg.writeString(message);

        queue.put(msg, new MQPutMessageOptions());

        return "OK";
    }




}
