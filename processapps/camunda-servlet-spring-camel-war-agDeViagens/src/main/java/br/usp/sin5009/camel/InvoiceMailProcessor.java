package br.usp.sin5009.camel;

import java.util.Map;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.mail.internet.MimeMultipart;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import br.usp.sin5009.ServiceTasks.LoggerDelegate;

public class InvoiceMailProcessor implements Processor {

	private final Logger LOGGER = Logger.getLogger(LoggerDelegate.class.getName());

    public void InvoiceMailProcessor(){
        LOGGER.info("\n\n ||| InvoiceMailProcessor |||-> public void InvoiceMailProcessor(){...\n");
    }
    
	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.info("\n\n <<<<InvoiceMailProcessor>>>> public void process(Exchange exchange) throws Exception {...\n");
		
		Map<String, Object> map = exchange.getIn().getHeaders();

		Object body = exchange.getIn().getBody();
                LOGGER.info("\n\n <<<<InvoiceMailProcessor>>>> Object body = exchange.getIn().getBody() : "+body.toString() +"\n");
		if (body instanceof MimeMultipart) {
			String emailBody = ((MimeMultipart) body).getBodyPart(0).getContent().toString();
			map.put("emailBody", emailBody);
		}

                /**
		Map<String, DataHandler> attachments = exchange.getIn().getAttachments();
		if (attachments.size() > 0) {
			for (String name : attachments.keySet()) {
				DataHandler dh = attachments.get(name);
				// get the file name
				String filename = dh.getName();

				// get the content and convert it to byte[]
				byte[] data = exchange.getContext().getTypeConverter().convertTo(byte[].class, dh.getInputStream());
				map.put("invoiceDocumentName", filename); // TODO use Camunda file variable
				map.put("invoiceDocument", data);
				break; // TODO handle multiple attachments
			}
		}
                **/
		exchange.getIn().setBody(map);
	}

}
