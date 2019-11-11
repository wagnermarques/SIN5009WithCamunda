package br.usp.sin5009;

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

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("##### -------------------------------------------");
		LOGGER.info("##### EnviaSolicitacaoDePcteDeViagem executing...");
		
		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();
	    //runtimeService.startProcessInstanceByMessage("SolicitacaoDePcteDeViagemRecebidaPelaAgDeViagem");

		MessageCorrelationResult correlateWithResult = execution.getProcessEngineServices().getRuntimeService()
		.createMessageCorrelation("SolicitacaoDePcteDeViagemRecebidaPelaAgDeViagem")
		.correlateWithResult();
		
		LOGGER.info("correlateWithResult.toString()...");
		LOGGER.info(correlateWithResult.toString());
		
		
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
