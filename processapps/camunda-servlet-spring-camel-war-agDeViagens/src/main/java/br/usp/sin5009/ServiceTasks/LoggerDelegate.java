package br.usp.sin5009.ServiceTasks;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.camel.spring.CamelServiceImpl;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This is an empty service implementation illustrating how to use a plain Java
 * class as a BPMN 2.0 Service Task delegate.
 */
public class LoggerDelegate implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

	public void execute(DelegateExecution execution) throws Exception {

		LOGGER.info("\n\n\n\n<<<<< public class LoggerDelegate implements JavaDelegate {...");
		LOGGER.info("\n <<<<< public void execute(DelegateExecution execution) throws Exception {...");

		String processDefinitionId = execution.getProcessDefinitionId();		
		String currentActivityId = execution.getCurrentActivityId();
		String currentActivityName = execution.getCurrentActivityName();
		String processInstanceId = execution.getProcessInstanceId();	
		String processBusinessKey = execution.getProcessBusinessKey();
		String executionId = execution.getId();
		
		LOGGER.info("processDefinitionId = " + processDefinitionId);
		LOGGER.info("processInstanceId = " + processInstanceId);
		LOGGER.info("processBusinessKey = " + processBusinessKey);
		LOGGER.info("executionId = " + executionId);
		LOGGER.info("currentActivityId = " + currentActivityId);
		LOGGER.info("currentActivityName = " + currentActivityName);
		
		
		Map<String, Object> variables = execution.getVariables();
		Iterator<String> iterator = variables.keySet().iterator();
		
		while(iterator.hasNext()) {
			String variableName = iterator.next();
			Object variableValue = variables.get(variableName);
			LOGGER.info(variableName +" = "+variableValue);
		}
			
		LOGGER.info("\n >>>>>");

	}

}
