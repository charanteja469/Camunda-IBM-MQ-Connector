package io.camunda.IBMMQ.dto;

import io.camunda.connector.generator.java.annotation.TemplateProperty;
import io.camunda.connector.generator.java.annotation.TemplateProperty.PropertyType;
import jakarta.validation.constraints.NotEmpty;

public record IBMMQOutboundConnectorRequest(
    @NotEmpty @TemplateProperty(group = "compose", type = PropertyType.Text)
    String qmgr,
    String qname,
    String message) {}
