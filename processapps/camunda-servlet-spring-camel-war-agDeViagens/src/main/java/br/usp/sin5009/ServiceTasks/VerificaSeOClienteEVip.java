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
							"select * from clientes where nome='" 
									+ variablenomeDoCliente + "' and tipoDeCliente='vip';");		
			ResultSet rs = pstm.executeQuery();

			/*
			 * Vamos so ver se tem um cliente com o nome especifico e que seja vip
			 * obviamente me producao fariamos algo mais sofisticado
			 */
			String nomeDoCliente = null;
			String emailDoCliente = null;
			String tipoDoCliente = null;
			while (rs.next()) {
				// Se intCliVip for pelo 1, o cliente he vip
				intCliVip = 1;
				nomeDoCliente = rs.getString("nome");
				emailDoCliente = rs.getString("emaildocliente");
				tipoDoCliente = rs.getString("tipodecliente"); 
			}

			
			//Se o emailDoCliente ja tiver sido informado a gente vai ficar com o que foi informado
			//caso contrario a gente usa o email que esta cadastrado no banco de dados
			//se nenhuma dessas alternativas funcionarem, nao temos email
			if (intCliVip == 1) {
				LOGGER.info("\n %%%%%%%%%% O cliente "+ variablenomeDoCliente + " he vip!");
				execution.setVariable("tipoDeCliente", "vip");			
				//pra efeitos desse trabalho, os clientes vips sempre terao emails cadastrados no banco
				execution.setVariable("emailDoCliente", emailDoCliente);
			}else {
				LOGGER.info("\n %%%%%%%%%% O cliente "+ variablenomeDoCliente + " N-A-O he vip!");
				//cai aqui qdo o tipo de cliente e igual a null e nao e vip
				execution.setVariable("tipoDeCliente", "comum");				
				
				//neste caso pode ser que no banco nao tenha e nem tenha sido informado
				if(execution.getVariable("emailDoCliente") != null || execution.getVariable("emailDoCliente").equals("")){
					//este he o caso em que o email do cliente foi informado
					//mesmo ele nao sendo vip
				}else {
					//bom, aqui o email nao foi informado anteriomente,
					//vamos ver se da pra usar algum que esteja cadastrado no bacno
					if(emailDoCliente != null || emailDoCliente.equals("")) {
						execution.setVariable("emailDoCliente", emailDoCliente);
					}else {
						//o cliente nao vai ter email 
					}
				}				
			}
			

		} else {
			LOGGER.info(
					"\n %%%%%%%%%% variableTipoDeCliente ja havia sido definida, entao nao foi necessario verificar se era vip");
			LOGGER.info("\n %%%%%%%%%% variableTipoDeCliente = " + variablenomeDoCliente);
		}
	}
}
