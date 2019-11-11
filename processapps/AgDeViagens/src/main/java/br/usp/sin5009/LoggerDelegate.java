package br.usp.sin5009;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

/**
 * This is an easy adapter implementation 
 * illustrating how a Java Delegate can be used 
 * from within a BPMN 2.0 Service Task.
 */
public class LoggerDelegate implements JavaDelegate {
 
  private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());
  
  public void execute(DelegateExecution execution) throws Exception {
    
    LOGGER.info("##### \n\n  ... LoggerDelegate invoked by "
            + "\n\n processDefinitionId=" + execution.getProcessDefinitionId()
            + ", \n\n activtyId=" + execution.getCurrentActivityId()
            + ", \n\n activtyName='" + execution.getCurrentActivityName() + "'"
            + ", \n\n processInstanceId=" + execution.getProcessInstanceId()
            + ", \n\n businessKey=" + execution.getProcessBusinessKey()
            + ", \n\n executionId=" + execution.getId()
            + " \n\n");
    
  }

}
