package br.usp.sin5009.camel;

import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class CamelRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {

            String sin5009InputFolder=System.getProperty("user.home") + System.getProperty("file.separator") + "sin5009InputFolder" + System.getProperty("file.separator");

            /**
             *
             * Envia email de Solicitacao de Pacotes de Viagens
             * ${camel.sendTo('direct:enviaEmailDeSolicitacaoDePctesDeViagem', 'tipoDeCliente,nomeDoCliente,')}
             *
             **/
            from("direct:enviaEmailDeSolicitacaoDePctesDeViagem")
                .routeId("directEnviaEmailDeSolicitacaoDePctesDeViagem")
                .doTry().setHeader("subject", simple("Solicitacao De Pctes De Viagem Recebida"))
                .setHeader("to", simple("wagnerdocri@gmail.com,wagnermarques@usp.br"))
                .to("smtps://smtp.gmail.com:465?username=wagnerdocri@gmail.com&password=SenhaSecreta");


            /**
             *
             * Inicia Instancia de processo por meio de arquivo salvo na pasta 
             * String sin5009InputFolder=System.getProperty("user.home") + System.getProperty("file.separator") + "sin5009InputFolder" + System.getProperty("file.separator");
             * ${camel.sendTo('direct:enviaEmailDeSolicitacaoDePctesDeViagem', 'tipoDeCliente,nomeDoCliente,')}
             *
             * Obviamente, no futuro, o ideal he que a gente lesse os dados do cliente e seus desejos
             * para a viagem que ele quer fazer
             * pra subsidiar a preparacao das ofertas de pctes de viagens
             *
             * Vai startar o processo do cliente setando o canal de comunicacao como sendo pessoalmente
             * O cliente Recebe uma confirmacao
             **/
            from("file:"+sin5009InputFolder)
                .routeId("fileInSin5009InputFolder_To_StartProcess_Participant_Cliente")
                .to("camunda-bpm:start?processDefinitionKey=Process_Participant_Cliente");
                .doTry().setHeader("subject", simple("Vc Deixou uma Solicitacao De Pctes De Viagem Na nossa Ag de Viagem"))
                     //depois tem que trocar wagnermarques@usp.br pegando o email do cliente via variavel do processo
                .setHeader("to", simple("wagnermarques@usp.br"))
				.to("smtps://smtp.gmail.com:465?username=wagnerdocri@gmail.com&password=SenhaSecreta");

            /**
             *
             * 
             *
             **/
		from("imaps://imap.gmail.com:993?username=wagnerdocri@gmail.com&password=SenhaSecreta&delete=false&unseen=true&consumer.delay=6000")
				.to("log:org.camunda.demo.camel?level=INFO&showAll=true&multiline=true")
                                .process(new InvoiceMailProcessor())
				.to("camunda-bpm:start?processDefinitionKey=Process_Participant_Cliente");

            /*
             * ${camel.sendTo('direct:variableToFile', 'tipoDeCliente')} Envia so uma
             * variavel, a tipoDeCliente
             */
            //from("direct:variableToFile").log(LoggingLevel.INFO, "\n  ###### Escrevendo variavel em arquivo de texto \n\n")
		//		.process(new FirstMapValueToStringProcessor())
		//		.to("file://" + System.getProperty("user.home") + System.getProperty("file.separator")
		//				+ "camunda-bpm-demo-camel" + System.getProperty("file.separator") + "variableToFile");

                
//    from("pop3://mail.example.com?username=camunda@example.com&password=camunda&delete=true&unseen=true&consumer.delay=60000")
//      .log(LoggingLevel.INFO, "Received email. Starting process...")
//      .process(new InvoiceMailProcessor())
//      .to("camunda-bpm:start?processDefinitionKey=InvoiceProcess");

		//from("direct:invoiceToDB").log(LoggingLevel.INFO, "Sending invoice to DB");
//      .to("sql:insert into INVOICE set foo=:#myProcessVariable?dataSource=dataSource");

	}

}
