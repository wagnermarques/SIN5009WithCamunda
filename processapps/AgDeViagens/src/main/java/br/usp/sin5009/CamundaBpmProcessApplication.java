package br.usp.sin5009;

import java.util.HashMap;
import java.util.Map;

import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;

import java.util.logging.Logger;

/**
 * Process Application exposing this application's resources the process engine.
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {


	private static final String PROCESS_DEFINITION_KEY = "AgDeViagens";
	private final Logger LOGGER = Logger.getLogger(CamundaBpmProcessApplication.class.getName());
	 
	
	/**
   * In a @PostDeploy Hook you can interact with the process engine and access 
   * the processes the application has deployed. 
   */
  @PostDeploy
  public void onDeploymentFinished(ProcessEngine processEngine) {
	  
	  LOGGER.info("@PostDeploy public void onDeploymentFinished(ProcessEngine processEngine) {... ");
	  
	  //String canal_de_comunicacao = 
	  // start an initial process instance
	  Map<String, Object> variables = new HashMap<String, Object>();
	  variables.put("canal_de_comunicacao", "email");	  
	  processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
  }

}
