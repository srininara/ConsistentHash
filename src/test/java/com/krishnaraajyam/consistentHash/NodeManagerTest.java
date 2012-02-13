package com.krishnaraajyam.consistentHash;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class NodeManagerTest extends TestUtil {

	@Before
	public void setup() {
		nodes = new ArrayList<Node>();
		nodes.add(new SimpleNode("exampleNode1"));

	}

	@Test
	public void aNodeManagerStartedWithNodesListAsNullWillHaveZeroVNodes() {
		nodeManager = new NodeManagerImpl(null);
		assertEquals(0, nodeManager.getNumberOfVNodes());
	}

	@Test
	public void aNodeManagerStartedWithoutAnyNodeListWillHaveZeroVNodes() {
		nodeManager = new NodeManagerImpl();
		assertEquals(0, nodeManager.getNumberOfVNodes());
	}

	@Test
	public void aNodeManagerStartedWithASingleNodeWithoutSpecifyingSupportedNumberOfVNodesWillHaveDefaultNoOfVNodes() {
		nodeManager = new NodeManagerImpl(nodes);
		inspectNodeDetails(32, 1);
	}

	@Test
	public void aNodeManagerStartedWithTwoNodes64andDefaultMustHave96VNodes() {
		Node node1 = new SimpleNode("exampleNode1", 64);
		Node node2 = new SimpleNode("exampleNode2");
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodeManager = new NodeManagerImpl(nodes);
		inspectNodeDetails(96, 2);
	}

	@Test
	public void aNodeManagerStartedWithADefaultNodeAndThenAddedWithADefaultNodeMustHave64VNodes() {
		Node node1 = new SimpleNode("exampleNode1");
		Node node2 = new SimpleNode("exampleNode2");
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodeManager = new NodeManagerImpl(nodes);
		inspectNodeDetails(32, 1);
		nodeManager.add(node2);
		inspectNodeDetails(64, 2);
	}

	@Test
	public void aNodeManagerStartedWithTwoDefaultNodeAndThenRemovedANodeMustHave32VNodes() {
		Node node1 = new SimpleNode("exampleNode1");
		Node node2 = new SimpleNode("exampleNode2");
		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodeManager = new NodeManagerImpl(nodes);
		inspectNodeDetails(64, 2);
		nodeManager.remove(node2);
		inspectNodeDetails(32, 1);
	}



	private void inspectNodeDetails(int expectedNoOfVNodes,
			int expectedNoOfNodes) {
		Map<Node, Integer> nodeDistribution = new HashMap<Node, Integer>();
		for (Integer vNodeId : nodeManager.getVNodeKeys()) {
			Node currNode = nodeManager.getNode(vNodeId);
			Integer existDist = nodeDistribution.get(currNode);
			Integer currDist = (existDist != null) ? ++existDist : 1;
			nodeDistribution.put(currNode, currDist);
		}
		assertEquals(expectedNoOfNodes, nodeDistribution.size());
		int calculatedNoOfVNodes = 0;
		for (Node node : nodeDistribution.keySet()) {
			assertEquals(node.getSupportedNumberOfVNodes(),
					nodeDistribution.get(node));
			calculatedNoOfVNodes = calculatedNoOfVNodes
					+ node.getSupportedNumberOfVNodes();
		}
		assertEquals(expectedNoOfVNodes, nodeManager.getNumberOfVNodes());
		assertEquals(expectedNoOfVNodes, calculatedNoOfVNodes);
	}

}
