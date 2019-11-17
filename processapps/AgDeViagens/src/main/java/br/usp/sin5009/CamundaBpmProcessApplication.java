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
import org.camunda.bpm.engine.cdi.BusinessProcessEvent;
import org.camunda.bpm.engine.cdi.annotation.event.StartActivity;
import org.camunda.bpm.engine.cdi.impl.event.CdiEventListener;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.camunda.bpm.engine.delegate.TaskListener;

import br.usp.sin5009.model.PacoteDeViagem;

import java.util.logging.Logger;

import javax.enterprise.event.Observes;

/**
 * Process Application exposing this application's resources the process engine.
 * https://docs.camunda.org/manual/7.4/user-guide/process-applications/the-process-application-class/
 */

/**
 * providing the name of the ProcessApplication: You can provide a custom name
 * for your process application using the annotation: @ProcessApplication("Loan
 * Approval App"). If no name is provided, a name is automatically detected. In
 * case of a ServletProcessApplication, the name of the ServletContext is used.
 * 
 *
 */
//@ProcessApplication("SIN5009AgDeViagem")
@ProcessApplication
public class CamundaBpmProcessApplication extends ServletProcessApplication {


	private static final String PROCESS_DEFINITION_KEY = "procIdCliente";
	private final Logger LOGGER = Logger.getLogger(CamundaBpmProcessApplication.class.getName());


	/*
	 * https://docs.camunda.org/manual/7.11/user-guide/cdi-java-ee-integration/the-cdi-event-bridge/
	 * https://docs.camunda.org/manual/7.4/user-guide/cdi-java-ee-integration/
	 */
	protected CdiEventListener cdiEventListener = new CdiEventListener();
	public ExecutionListener getExecutionListener() {
		return cdiEventListener;
	}
	public TaskListener getTaskListener() {
		return cdiEventListener;
	}
	/**
	 * This observer would be notified of all events. If we want to restrict the set
	 * of events the observer receives, we can add qualifier annotations:
	 * Demais listeners
	 * https://docs.camunda.org/manual/7.11/user-guide/cdi-java-ee-integration/the-cdi-event-bridge/
	 * @param businessProcessEvent
	 */
	public void onProcessEvent(@Observes BusinessProcessEvent businessProcessEvent) {
		LOGGER.info(" CDI.CDI.CDI.CDI.CDI.CDI  public void onProcessEvent(@Observes BusinessProcessEvent businessProcessEvent) {...");
	}
	public void onActivityEvent(@Observes @StartActivity("Logger") BusinessProcessEvent businessProcessEvent) {
		LOGGER.info(" CDI.CDI.CDI.CDI.CDI.CDI  public void onActivityEvent(@Observes @StartActivity(\"Logger\") BusinessProcessEvent businessProcessEvent) {....");
	}
	public void onActivityEventTask_Logar(@Observes @StartActivity("Task_Logar") BusinessProcessEvent businessProcessEvent) {
		LOGGER.info(" CDI.CDI.CDI.CDI.CDI.CDI  public void onActivityEvent(@Observes @StartActivity(\"Task_Logar\") BusinessProcessEvent businessProcessEvent) {....");
	}

	
	/**
	 * In a @PostDeploy Hook you can interact with the process engine and access the
	 * processes the application has deployed.
	 */
	@PostDeploy
	public void onDeploymentFinished(ProcessEngine processEngine) {

		LOGGER.info("\n\n\n(((((( @PostDeploy public void onDeploymentFinished(ProcessEngine processEngine) {... ");
		Set<PacoteDeViagem> pctesDeViagemSet = new HashSet<PacoteDeViagem>();
		List<String> tiposDeClientes = new ArrayList<String>();
		tiposDeClientes.add("vip");
		tiposDeClientes.add("normal");

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("canal_de_comunicacao", "email");
		variables.put("tipos_de_clientes", tiposDeClientes);
		variables.put("pctes_de_viagem", pctesDeViagemSet);
		variables.put("sin5009ApiKey", "AIzaSyDYuzCv1ReZ0vvQK7s2FEJSlR2Sa2u5uEM");

		processEngine.getRuntimeService().startProcessInstanceByKey(PROCESS_DEFINITION_KEY, variables);
		LOGGER.info("\n\n\n ))))))");
	}

}
