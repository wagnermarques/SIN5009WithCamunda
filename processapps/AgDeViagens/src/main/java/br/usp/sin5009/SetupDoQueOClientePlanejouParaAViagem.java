package br.usp.sin5009;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import java.util.logging.Logger;

public class SetupDoQueOClientePlanejouParaAViagem implements JavaDelegate{

	private final Logger LOGGER = Logger.getLogger(SetupDoQueOClientePlanejouParaAViagem.class.getName());
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LOGGER.info("@Override\n" + 
				"	public void execute(DelegateExecution execution) throws Exception {...");		
	}

}
