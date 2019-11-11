package br.usp.sin5009.commands;

import java.util.List;

import org.camunda.bpm.BpmPlatform;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RepositoryService;
import org.camunda.bpm.engine.repository.ProcessDefinition;

public class QueryProcessDefinitions {

	private ProcessEngine processEngine;
	private RepositoryService repositoryService;
	
	public QueryProcessDefinitions() {
		this.processEngine = BpmPlatform.getProcessEngineService().getProcessEngine("default");
		this.repositoryService = processEngine.getRepositoryService();
	}

	public List<ProcessDefinition> list() {
		List<ProcessDefinition> processDefinitions = repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey("invoice").orderByProcessDefinitionVersion().asc().list();
		return processDefinitions;
	}

	public static void main(String[] args) {
		new QueryProcessDefinitions().list();
	}

}
