package br.usp.sin5009.MensagensEnviadas;

import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;

public class EnviaMsgConfirmacaoDeRecebimentoDeSolicitacao implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(EnviaMsgConfirmacaoDeRecebimentoDeSolicitacao.class.getName());
	private final String MSG_NAME = "MsgConfirmacaoDeRecebimentoDeSolicitacao";

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		LOGGER.info("\n\n\n\n[[[[[[ -------------------------------------------");
		LOGGER.info("\n[[[[[[ public class EnviaMsgConfirmacaoDeRecebimentoDeSolicitacao implements JavaDelegate {...");

		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();

		MessageCorrelationResult correlateWithResult = runtimeService.createMessageCorrelation("MsgConfirmacaoDeRecebimentoDeSolicitacao")
			.processInstanceBusinessKey(execution.getBusinessKey())
			.setVariable("tipoDeCliente", execution.getVariable("tipoDeCliente"))			
			//.processInstanceId((String) execution.getVariable("cliProcessInstanceId"))
			.correlateWithResult();
		
		
			
	}

}
