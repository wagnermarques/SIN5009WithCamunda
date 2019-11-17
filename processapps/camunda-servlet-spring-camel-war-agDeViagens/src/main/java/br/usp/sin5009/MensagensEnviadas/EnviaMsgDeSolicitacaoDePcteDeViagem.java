package br.usp.sin5009.MensagensEnviadas;

import java.util.HashMap;

import java.util.Map;
import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.MessageCorrelationResult;
import org.camunda.bpm.engine.runtime.MessageCorrelationResultType;
import org.camunda.bpm.engine.runtime.ProcessInstance;

public class EnviaMsgDeSolicitacaoDePcteDeViagem implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(EnviaMsgDeSolicitacaoDePcteDeViagem.class.getName());
    private final String MSG_NAME="MsgDeSolicitacaoRecebida";

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("\n\n\n\n[[[[[[ -------------------------------------------");
		LOGGER.info("[[[[[[ public class EnviaMsgDeSolicitacaoDePcteDeViagem implements JavaDelegate {...");

		String thisProcessExecutionBusinessKey = execution.getBusinessKey();
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
		MessageCorrelationResult correlateWithResult = null;

		// runtimeService.startProcessInstanceByMessage("SolicitacaoDePcteDeViagemRecebidaPelaAgDeViagem");

		/* 
		 * Se o nosso processo cliente tiver um business key a gente passa ele pra agencia de viagens via msg
		 */
		if (thisProcessExecutionBusinessKey != null) {
			correlateWithResult = runtimeService
					.createMessageCorrelation("SolicitacaoDePcteDeViagemRecebidaPelaAgDeViagem")
					.processInstanceBusinessKey(thisProcessExecutionBusinessKey)
					.correlateWithResult();
		} else {
			correlateWithResult = runtimeService
                            .createMessageCorrelation(this.MSG_NAME)
					.correlateWithResult();
		}
		
		LOGGER.info("**** entendendo correlateWithResult...");
		MessageCorrelationResultType resultType = correlateWithResult.getResultType();

		String businessKey = correlateWithResult.getProcessInstance().getBusinessKey();
		String caseInstanceId = correlateWithResult.getProcessInstance().getCaseInstanceId();
		String processDefinitionId = correlateWithResult.getProcessInstance().getProcessDefinitionId();
		String processInstanceId = correlateWithResult.getProcessInstance().getProcessInstanceId();
		LOGGER.info("correlateWithResult.getResultType().name() = " + correlateWithResult.getResultType().name());
		LOGGER.info("correlateWithResult.getProcessInstance().getBusinessKey() = "
				+ correlateWithResult.getProcessInstance().getBusinessKey());
		LOGGER.info("correlateWithResult.getProcessInstance().getCaseInstanceId() = "
				+ correlateWithResult.getProcessInstance().getCaseInstanceId());
		LOGGER.info("correlateWithResult.getProcessInstance().getProcessDefinitionId() = "
				+ correlateWithResult.getProcessInstance().getProcessDefinitionId());
		LOGGER.info("correlateWithResult.getProcessInstance().getProcessInstanceId() = "
				+ correlateWithResult.getProcessInstance().getProcessInstanceId());

//		String id = correlateWithResult.getExecution().getId();
//		boolean ended = correlateWithResult.getExecution().isEnded();
//		boolean suspended = correlateWithResult.getExecution().isSuspended();		
//		LOGGER.info("correlateWithResult.getExecution().isEnded() = "+ String.valueOf(correlateWithResult.getExecution().isEnded()));
//		LOGGER.info("correlateWithResult.getExecution().isEnded() = " + String.valueOf(correlateWithResult.getExecution().isEnded()));
//		LOGGER.info("correlateWithResult.getExecution().isSuspended() = " + String.valueOf(correlateWithResult.getExecution().isSuspended()));

		LOGGER.info("]]]]]] -------------------------------------------");

//		Map<String, Object> variablesMsgPayload = new HashMap<String, Object>();
//		variablesMsgPayload.put("messageName", "SolicitacaoDePcteDeViagemRecebidaPelaAgDeViagem");
//		variablesMsgPayload.put("businesskey", "1");
//                variablesMsgPayload.put("canal_de_comunicacao", "email");

//		String processInstanceId = correlateWithResult.getExecution().getProcessInstanceId();
//		ProcessInstance processInstance = correlateWithResult.getProcessInstance();
//		MessageCorrelationResultType resultType = correlateWithResult.getResultType();
//		String id = correlateWithResult.getExecution().getId();

//                LOGGER.info("##### correlateWithResult.getExecution().getProcessInstanceId() = "+processInstanceId);
//		LOGGER.info("##### correlateWithResult.getProcessInstance() = "+processInstance);
//		LOGGER.info("##### correlateWithResult.getResultType() = "+resultType);
//		LOGGER.info("##### correlateWithResult.getExecution().getId() = "+correlateWithResult);

	}

}
