package br.usp.sin5009;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * This is an easy adapter implementation illustrating how a Java Delegate can
 * be used from within a BPMN 2.0 Service Task.
 */
public class LoggerDelegate implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("\n\n\n\n<<<<< public class LoggerDelegate implements JavaDelegate {...");
		LOGGER.info("<<<<< public void execute(DelegateExecution execution) throws Exception {...");

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
			
//		LOGGER.info("\n\n  ... LoggerDelegate invoked by " + "\n\n processDefinitionId="
//				+ execution.getProcessDefinitionId() + ", \n\n activtyId=" + execution.getCurrentActivityId()
//				+ ", \n\n activtyName='" + execution.getCurrentActivityName() + "'" + ", \n\n processInstanceId="
//				+ execution.getProcessInstanceId() + ", \n\n businessKey=" + execution.getProcessBusinessKey()
//				+ ", \n\n executionId=" + execution.getId() + " \n\n");

		LOGGER.info(">>>>>");
	}

}
