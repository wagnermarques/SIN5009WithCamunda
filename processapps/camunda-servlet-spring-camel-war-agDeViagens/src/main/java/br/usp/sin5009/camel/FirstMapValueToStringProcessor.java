package br.usp.sin5009.camel;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class FirstMapValueToStringProcessor implements Processor {

	private final Logger LOGGER = Logger.getLogger(FirstMapValueToStringProcessor.class.getName());

	
	public void process(Exchange exchange) throws Exception {
		
		LOGGER.info(" \n\n [> [> CamelCamelCamelCamel :: FirstMapValueToStringProcessor...\n");
		String retStr = "";

		@SuppressWarnings("unchecked")
		Map<String, Object> map = exchange.getIn().getBody(Map.class);

		Iterator<String> itKeys = map.keySet().iterator();
		while (itKeys.hasNext()) {
			LOGGER.info("\n [> [> CamelCamelCamelCamel :: while(itKeys.hasNext()){...");
			String key = itKeys.next();

			LOGGER.info("\n [> [> CamelCamelCamelCamel :: String key = itKeys.next() -> " + key);
			String value = map.get(key).toString();

			LOGGER.info("\n [> [> CamelCamelCamelCamel :: map.get(key).toString() -> " + value);
			retStr += key + ":" + value + " | ";
		}

		LOGGER.info("\n [> [> CamelCamelCamelCamel :: returning... " + retStr + "\n\n");
		exchange.getIn().setBody(retStr);
	}

}
