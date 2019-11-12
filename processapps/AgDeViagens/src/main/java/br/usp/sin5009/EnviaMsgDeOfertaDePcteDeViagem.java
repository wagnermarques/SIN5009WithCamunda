package br.usp.sin5009;


import org.apache.commons.logging.Log;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class EnviaMsgDeOfertaDePcteDeViagem implements JavaDelegate {

	private final Logger LOGGER = Logger.getLogger(EnviaMsgDeOfertaDePcteDeViagem.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("\n\n\n\n##### EnviaMsgDeOfertaDePcteDeViagem executing...");
				
		
		String canal_de_comunicacao = execution.getVariable("canal_de_comunicacao").toString();
		//Map <String,Object> variablesMsgPayload = new HashMap<String, Object>(); 
//		variablesMsgPayload.put("messageName", "OfertaDePcteDeViagemRecebida");
//		variablesMsgPayload.put("businesskey", "1");
//		variablesMsgPayload.put("canal_de_comunicacao", "email");
		
//		execution.getProcessEngineServices().getRuntimeService()
//		.createMessageCorrelation("OfertaDePcteDeViagemRecebida")
//		.setVariables(variablesMsgPayload)
//		.correlateWithResult();
	}

}
