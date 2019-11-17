package br.usp.sin5009.camel;

import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import java.util.logging.Logger;
import java.util.*;

public class FirstMapValueToStringProcessor implements Processor {

    private final Logger LOGGER = Logger.getLogger(FirstMapValueToStringProcessor.class.getName());
    
  public void process(Exchange exchange) throws Exception {
    LOGGER.info(" \n\n [> [> CamelCamelCamelCamel :: FirstMapValueToStringProcessor...\n");
    String retStr = "";
    @SuppressWarnings("unchecked")
    Map<String, Object> map = exchange.getIn().getBody(Map.class);
    //exchange.getIn().setBody(map.values().iterator().next().toString());
    Iterator<String> itKeys = map.keySet().iterator();
    while(itKeys.hasNext()){
        String key = itKeys.next();
        String value = map.get(key).toString();
        retStr += key +":"+value+" | ";
    }
    LOGGER.info(" [> [> CamelCamelCamelCamel :: returning... " + retStr+"\n\n");
    exchange.getIn().setBody(map.toString());
  }

}
