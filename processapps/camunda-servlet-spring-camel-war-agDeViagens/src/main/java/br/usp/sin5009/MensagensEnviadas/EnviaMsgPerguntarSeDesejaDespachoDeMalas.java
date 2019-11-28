package br.usp.sin5009.MensagensEnviadas;

import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.MessageCorrelationResultType;

public class EnviaMsgPerguntarSeDesejaDespachoDeMalas implements JavaDelegate {

	Logger LOGGER = Logger.getLogger(EnviaMsgPerguntarSeDesejaDespachoDeMalas.class.getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("\n\n\n\n[[[[[[ -------------------------------------------");
		LOGGER.info("\n[[[[[[ public class EnviaMsgPerguntarSeDesejaDespachoDeMalas implements JavaDelegate {...");

		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();

		MessageCorrelationResult correlateWithResult = runtimeService.createMessageCorrelation("MsgPerguntarSeDesejaDespachoDeMalas")
				.processInstanceBusinessKey(execution.getBusinessKey())
				.correlateWithResult();
		
	}

}
