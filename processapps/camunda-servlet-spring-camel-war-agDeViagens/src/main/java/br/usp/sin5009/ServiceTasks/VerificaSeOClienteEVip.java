package br.usp.sin5009.ServiceTasks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Logger;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import br.usp.sin5009.persistence.PostgresqlConnectionFactory;

public class VerificaSeOClienteEVip implements JavaDelegate {

	Logger LOGGER = Logger.getLogger(VerificaSeOClienteEVip.class.getName());

	@Override
	public void execute(DelegateExecution execution) throws Exception {

		LOGGER.info("\n\n\n\n %%%%%%%%%% public class VerificaSeOClienteEVip implements JavaDelegate {...");

		RuntimeService runtimeService = execution.getProcessEngineServices().getRuntimeService();

		Object variableTipoDeCliente = runtimeService.getVariable(execution.getId(), "tipoDeCliente");
		int intCliVip = -1; // 1 se o cliente for vip

		// Na apresentacao do trabalho a gente sempre comeca com o nome do cliente
		// por isso he que nao testamos se existe essa variavel ou se ela esta em branco
		String variablenomeDoCliente = (String) runtimeService.getVariable(execution.getId(), "nomeDoCliente");

		if (variableTipoDeCliente == null || variableTipoDeCliente.equals("")) {
			LOGGER.info("\n %%%%%%%%%% variableTipoDeCliente nao informado");
			LOGGER.info("\n %%%%%%%%%% vamos verificar se o cliente he vip");
			// O PROCESSO INICIOU SEM QUE SAIBAMOS SE O CLIENTE HE VIP
			// JA QUE A VARIAVEL DE PROCESSO "tipoDeCliente" NAO FOI FORNECIDA
			// NA OCASIAO DA INSTANCIACAO DO PROCESSO

			// neste caso vamos fazer uma pesquisa pra ver se temos o nome do cliente
			Connection connectionInstance = PostgresqlConnectionFactory.getConnectionInstance();
			LOGGER.info("\n %%%%%%%%%% verificando se o cliente "+ variablenomeDoCliente + " he vip!");

			//isso he so um exercicio, nao nos preocupamos com sqlinjectin aqui...
			PreparedStatement pstm = connectionInstance
					.prepareStatement(
							"select count(*) as clienteVip from clientes where nome='" 
									+ variablenomeDoCliente + "' and tipoDeCliente='vip';");		
			ResultSet rs = pstm.executeQuery();

			/*
			 * Vamos so ver se tem um cliente com o nome especifico e que seja vip
			 * obviamente me producao fariamos algo mais sofisticado
			 */
			while (rs.next()) {
				// Se intCliVip for pelo 1, o cliente he vip
				intCliVip = rs.getInt("clienteVip");
			}

			if (intCliVip == 1) {
				execution.setVariable("tipoDeCliente", "vip");
			}else {
				//cai aqui qdo o tipo de cliente e igual a null e nao e vip
				execution.setVariable("tipoDeCliente", "comum");
			}

		} else {
			LOGGER.info(
					"\n %%%%%%%%%% variableTipoDeCliente ja havia sido definida, entao nao foi necessario verificar se era vip");
			LOGGER.info("\n %%%%%%%%%% variableTipoDeCliente = " + variablenomeDoCliente);
		}
	}
}
