package br.usp.sin5009;

import org.apache.ibatis.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.extension.process_test_coverage.junit.rules.TestCoverageProcessEngineRuleBuilder;
import org.camunda.bpm.engine.test.Deployment;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import static org.camunda.bpm.engine.test.assertions.ProcessEngineTests.*;
import static org.junit.Assert.*;

/**
 * Test case starting an in-memory database-backed Process Engine.
 */
public class ProcessUnitTest {

	@ClassRule
	@Rule
	public static ProcessEngineRule processEngineRule = TestCoverageProcessEngineRuleBuilder.create().build();

	private static final String PROCESS_DEFINITION_KEY = "procIdCliente";

	static {
		LogFactory.useSlf4jLogging(); // MyBatis
	}

	@Before
	public void setup() {
		init(processEngineRule.getProcessEngine());
	}

	/*
	 * https://docs.camunda.org/manual/latest/user-guide/testing/
	 */

	/*
	 * Just tests if the process definition is deployable.
	 */
	@Test
	@Deployment(resources = "process2.bpmn")
	public void testParsingAndDeployment() {
		// nothing is done here, as we just want to check for exceptions during
		// deployment
	}

	@Test
	@Deployment(resources = "process2.bpmn")
	public void testHappyPath() {

		RuntimeService runtimeService = processEngineRule.getRuntimeService();
		runtimeService.startProcessInstanceByKey(PROCESS_DEFINITION_KEY);

		// HistoryService historyService = processEngineRule.getHistoryService();
		// requires history level >= "activity"
//	    HistoricVariableInstance variable = historyService
//	      .createHistoricVariableInstanceQuery()
//	      .singleResult();
//	      
		// assertEquals("value", variable.getValue());
		// Now: Drive the process by API and assert correct behavior by
		// camunda-bpm-assert
	}

}
