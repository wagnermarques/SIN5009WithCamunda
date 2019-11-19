package br.usp.sin5009.camel;

import java.util.logging.Logger;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

//import org.apache.camel.component.jackson.JacksonDataFormat;

public class CamelRouteBuilder extends RouteBuilder {

	private final Logger LOGGER = Logger.getLogger(CamelRouteBuilder.class.getName());

	@Override
	public void configure() throws Exception {

		String sin5009InputFolder = System.getProperty("user.home") + System.getProperty("file.separator")
				+ "sin5009InputFolder" + System.getProperty("file.separator");
		String processDefinitionKey_Cliente = "Process_Participant_Cliente";
		String processDefinitionKey_AgDeViagem = "Process_Participant_AgDeViagem";

		final String startProcessPostBodyAsJson = "{\"variables\": {\"tipoDeCliente\" : {\"value\" : \"vip\", \"type\": \"String\"}, \"nomeDoCliente\" : { \"value\" : \"denise\",\"type\": \"string\"},\"canal_de_comunicacao\" : {\"value\" : \"pessoalmente\",\"type\": \"string\"}},\"businessKey\" : \"bk123\"}";
		final String msgStart_AgDeViagem="{\"messageName\" : \"MsgDeSolicitacaoRecebida\", \"businessKey\" : \"123\",\"processVariables\" : {\"tipoDeCliente\" : {\"value\" : \"vip\", \"type\": \"String\",\"valueInfo\" : { \"transient\" : true } },\"nomeDeCliente\" : {\"value\" : \"Denise\", \"type\": \"String\", \"valueInfo\" : { \"transient\" : true } },\"emailDoCliente\" : {\"value\" : \"wagnermarques@usp.br\", \"type\": \"String\", \"valueInfo\" : { \"transient\" : true } },\"canal_de_comunicacao\" : {\"value\" : \"presencial\", \"type\": \"String\",\"valueInfo\" : { \"transient\" : true }}}}";
		
		// JSON Data Format
		// JacksonDataFormat jsonDataFormatAsHashMap = new
		// JacksonDataFormat(java.util.HashMap.class);

		/**
		 *
		 * Envia email de Solicitacao de Pacotes de Viagens
		 * ${camel.sendTo('direct:enviaEmailDeSolicitacaoDePctesDeViagem',
		 * 'tipoDeCliente,nomeDoCliente,')}
		 *
		 **/
		from("direct:enviaEmailDeSolicitacaoDePctesDeViagem").routeId("directEnviaEmailDeSolicitacaoDePctesDeViagem")
				.doTry().setHeader("subject", simple("Solicitacao De Pctes De Viagem Recebida"))
				.setHeader("to", simple("wagnerdocri@gmail.com"))
				.to("smtps://smtp.gmail.com:465?username=wagnerdocri@gmail.com&password=GooMt23-910*Email");

		/**
		 *
		 * Inicia Instancia de processo por meio de arquivo salvo na pasta String
		 * sin5009InputFolder=System.getProperty("user.home") +
		 * System.getProperty("file.separator") + "sin5009InputFolder" +
		 * System.getProperty("file.separator");
		 * ${camel.sendTo('direct:enviaEmailDeSolicitacaoDePctesDeViagem',
		 * 'tipoDeCliente,nomeDoCliente,')}
		 *
		 * Obviamente, no futuro, o ideal he que a gente lesse os dados do cliente e
		 * seus desejos para a viagem que ele quer fazer pra subsidiar a preparacao das
		 * ofertas de pctes de viagens
		 *
		 * Vai startar o processo do cliente setando o canal de comunicacao como sendo
		 * pessoalmente O cliente Recebe uma confirmacao
		 *
		 * noop=true Camel will set idempotent=true as well, to avoid consuming the same
		 * files over and over again
		 * https://camel.apache.org/components/latest/file-component.html
		 * https://fabian-kostadinov.github.io/2016/01/10/reading-from-and-writing-to-files-in-apache-camel/
		 *
		 * https://github.com/camunda/camunda-bpm-camel/blob/master/README.md
		 **/

//            from("file:"+sin5009InputFolder+"?noop=true")
		// .routeId("fileInSin5009InputFolder_To_StartProcess_Participant_Cliente")
		// .to("log:org.camunda.demo.camel?level=INFO&showAll=true&multiline=true")
		// .log(" @@@ from(\"file:\"+sin5009InputFolder+\"?noop=false\")...")
		// .process(exchange -> exchange.getIn().setBody(startProcessPostBodyAsJson))
//                .process(new Processor() {					
//					@Override
//					public void process(Exchange exchange) throws Exception {
//						exchange.getIn().setBody(startProcessPostBodyAsJson);						
//					}
//				});
//                .setHeader(Exchange.HTTP_METHOD, constant("POST"))
//                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
//                .to("http://localhost:8080/egine-rest/process-definition/"+processDefinitionKey_Cliente+"/start")
//                //.process(exchange -> log.info(" @@@ The response code is: {}", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE)));
//                .process(new Processor() {					
//					@Override
//					public void process(Exchange exchange) throws Exception {							
//						LOGGER.info(" @@@ The response code is: {} : " + exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
//					}
//				});

		// .setHeader(Exchange.HTTP_METHOD, simple("POST"))
		// .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
		// .to("http://localhost:8080/egine-rest/process-definition/"+processDefinitionKey_Cliente+"/start")
		// .process(new FileProcessorJsonContent())
		// .to("camunda-bpm:start?processDefinitionKey=Process_Participant_Cliente&copyBodyAsVariable");
		// .doTry().unmarshal(jsonDataFormatAsHashMap)
		// .process(new FileProcessorJsonContent())
		//

		/**
		 * 
		 */
		from("file:/home/wagner/sin5009InputFolder?noop=false").process(new Processor() {

			@Override
			public void process(Exchange exchange) throws Exception {
//					String body = exchange.getIn().getBody(String.class);
//					System.out.println("from(\"file:/home/wagner/sin5009InputFolder\").process(new Processor() {...");
//				

				//exchange.getIn().setBody(msgStart_AgDeViagem);

				
				String body = exchange.getIn().getBody(String.class);
				System.out.println(body);
			}

		}).setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http://localhost:8080/engine-rest/message")
				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						LOGGER.info(" @@@ The response code is: {} : "
								+ exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
					}
				});

		// .doTry().setHeader("subject", simple("Solicitacao De Pctes De Viagem
		// Recebida"))
		// .setHeader("to", simple("wagnerdocri@gmail.com"))
		// .to("smtps://smtp.gmail.com:465?username=wagnerdocri@gmail.com&password=GooMt23-910*Email")

		// .doTry().setHeader("subject", simple("Vc Deixou uma Solicitacao De Pctes De
		// Viagem em Nossa Agencia"))
		// .setHeader("to", simple("wagnermarques@usp.br"))
		// .to("smtps://smtp.gmail.com:465?username=wagnerdocri@gmail.com&password=GooMt23-910*Email");

		// from("file:C:/inboxPOST?noop=true").process(new
		// CreateEmployeeProcessor()).marshal(jsonDataFormat)
		// .setHeader(Exchange.HTTP_METHOD, simple("POST"))
		// .setHeader(Exchange.CONTENT_TYPE,
		// constant("application/json")).to("http://localhost:8080/employee")
		// .process(new MyProcessor());

		/**
		 *
		 * 
		 *
		 **/
		// from("imaps://imap.gmail.com:993?username=wagnerdocri@gmail.com&password=GooMt23-910*Email&delete=false&unseen=true&consumer.delay=6000")
		// .to("log:org.camunda.demo.camel?level=INFO&showAll=true&multiline=true")
		// .process(new InvoiceMailProcessor())
		// .to("camunda-bpm:start?processDefinitionKey=Process_Participant_Cliente");

		/*
		 * ${camel.sendTo('direct:variableToFile', 'tipoDeCliente')} Envia so uma
		 * variavel, a tipoDeCliente
		 */
		// from("direct:variableToFile").log(LoggingLevel.INFO, "\n ###### Escrevendo
		// variavel em arquivo de texto \n\n")
		// .process(new FirstMapValueToStringProcessor())
		// .to("file://" + System.getProperty("user.home") +
		// System.getProperty("file.separator")
		// + "camunda-bpm-demo-camel" + System.getProperty("file.separator") +
		// "variableToFile");

//    from("pop3://mail.example.com?username=camunda@example.com&password=camunda&delete=true&unseen=true&consumer.delay=60000")
//      .log(LoggingLevel.INFO, "Received email. Starting process...")
//      .process(new InvoiceMailProcessor())
//      .to("camunda-bpm:start?processDefinitionKey=InvoiceProcess");

		// from("direct:invoiceToDB").log(LoggingLevel.INFO, "Sending invoice to DB");
//      .to("sql:insert into INVOICE set foo=:#myProcessVariable?dataSource=dataSource");

	}

}
