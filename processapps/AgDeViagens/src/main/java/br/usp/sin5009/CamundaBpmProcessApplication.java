package br.usp.sin5009;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;


import org.camunda.bpm.application.PostDeploy;
import org.camunda.bpm.application.ProcessApplication;
import org.camunda.bpm.application.impl.ServletProcessApplication;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;

import br.usp.sin5009.model.PacoteDeViagem;

import java.util.logging.Logger;

/**
 * Process Application exposing this application's resources the process engine.
 */
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {


	private static final String PROCESS_DEFINITION_KEY = "procIdCliente";
	private final Logger LOGGER = Logger.getLogger(CamundaBpmProcessApplication.class.getName());
	 
	
	/**
   * In a @PostDeploy Hook you can interact with the process engine and access 
   * the processes the application has deployed. 
   */
  @PostDeploy


  public void onDeploymentFinished(ProcessEngine processEngine) {
	  
	  LOGGER.info("\n\n\n(((((( @PostDeploy public void onDeploymentFinished(ProcessEngine processEngine) {... ");
	  Set<PacoteDeViagem> pctesDeViagemSet = new HashSet<PacoteDeViagem>();
          List<String> tiposDeClientes = new ArrayList<String>();
	  tiposDeClientes.add("vip");
          tiposDeClientes.add("normal");
          
	  //String canal_de_comunicacao = 
	  // start an initial process instance
	  Map<String, Object> variables = new HashMap<String, Object>();
	  //variables.put("canal_de_comunicacao", "email");
          //variables.put("tipos_de_clientes",tiposDeClientes);
	  //variables.put("pctes_de_viagem", pctesDeViagemSet);
	  //variables.put("sin5009ApiKey", "AIzaSyDYuzCv1ReZ0vvQK7s2FEJSlR2Sa2u5uEM");
	  
	  processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
          LOGGER.info("\n\n\n ))))))"); 
  }

}
