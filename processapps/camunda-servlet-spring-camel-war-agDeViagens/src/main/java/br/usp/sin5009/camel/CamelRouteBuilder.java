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
		final String msgStart_AgDeViagem = "{\"messageName\" : \"MsgDeSolicitacaoRecebida\", \"businessKey\" : \"123\",\"processVariables\" : {\"tipoDeCliente\" : {\"value\" : \"vip\", \"type\": \"String\",\"valueInfo\" : { \"transient\" : true } },\"nomeDeCliente\" : {\"value\" : \"Denise\", \"type\": \"String\", \"valueInfo\" : { \"transient\" : true } },\"emailDoCliente\" : {\"value\" : \"wagnermarques@usp.br\", \"type\": \"String\", \"valueInfo\" : { \"transient\" : true } },\"canal_de_comunicacao\" : {\"value\" : \"presencial\", \"type\": \"String\",\"valueInfo\" : { \"transient\" : true }}}}";

		// N O S S O S S E R V I C O S R E S T
		// configura backend para criacao dos nossos servicos rest
		// mais informacoes: https://tomd.xyz/articles/camel-rest/
		restConfiguration().component("restlet").host("localhost").port("8090");

		// Endpoint de servico rest cujo metodo post inicia processo do cliente
		rest("/processoCliente").post().to("direct:iniciaProcessoDoCliente");

		/**
		 * Codigo que inicia processo do cliente Pode ser invocado por qualquer end
		 * point de servico
		 */
		from("direct:iniciaProcessoDoCliente").routeId("direct_iniciaProcessoDoCliente")
				.setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http://localhost:8080/engine-rest/process-definition/key/" + processDefinitionKey_Cliente
						+ "/submit-form")

				.process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						LOGGER.info(" @@@ The response code is: {} : "
								+ exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
					}
				});

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
				.to("smtps://smtp.gmail.com:465?username=wagnerdocri@gmail.com&password=SenhaSecreta");

		/**
		 * Um arquivo com a msg de starta PROCESSO DO CLIENTE PARA O USE CASE DE
		 * ATENDIMENTO PRESENCIAL o arquivo, sendo colocado na pasta sin5009InputFolder,
		 * estarta automaticamente o processo /**
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
		from("file:" + sin5009InputFolder + "?noop=false&fileName=payloadMsg_startProcess_Cliente.json")
				.process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						String body = exchange.getIn().getBody(String.class);
						System.out.println(body);
					}

				}).to("direct:iniciaProcessoDoCliente");

		/**
		 * Um arquivo com a msg de starta PROCESSO DA AG DE VIAGEM Esse codigo nao faz
		 * parte de nenhum dos nossos use cases porque mesmo que o atendimento fosse
		 * presencial com o agente de viagens salvando um arquivo nesse diretorio o
		 * ideal seria iniciar o processo do cliente que por sua vez inicializa o
		 * processo da agencia Entretanto, interessa porque he um exemplo de como enviar
		 * uma mensagem para um processo via codigo
		 * 
		 */
		from("file:" + sin5009InputFolder + "?noop=false&fileName=starMsg_MsgDeSolicitacaoRecebida.json")
				.process(new Processor() {

					@Override
					public void process(Exchange exchange) throws Exception {
						String body = exchange.getIn().getBody(String.class);
						System.out.println(body);
					}

				}).setHeader(Exchange.HTTP_METHOD, constant("POST"))
				.setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
				.to("http://localhost:8080/engine-rest/message").process(new Processor() {
					@Override
					public void process(Exchange exchange) throws Exception {
						LOGGER.info(" @@@ The response code is: {} : "
								+ exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE));
					}
				});
	}

}
