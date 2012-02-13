package com.krishnaraajyam.consistentHash;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ConsistentHashTest extends TestUtil {

	@Test
	public void simpleTestWithAllNodes() {
		initializeNodeManager(16);
		int numberOfNodes = 4;
		String key = "Summa Key1";
		vNodeTest(numberOfNodes, key);
	}

	@Test
	public void findDefaultNodesForAKey() {
		initializeNodeManager();
		int numberOfNodes = 2;
		String key = "Summa Key1";
		println("Initiated with 4 nodes with default number of vnodes and requesting for 2 suitable nodes");
		multiScenarioTest(numberOfNodes, key);
	}

	@Test
	public void findThreeDefaultNodesForAKey() {
		initializeNodeManager();
		int numberOfNodes = 3;
		String key = "Summa Key1";
		println("Initiated with 4 nodes with default number of vnodes and requesting for 3 suitable nodes");
		multiScenarioTest(numberOfNodes, key);
	}

	@Test
	public void findNodesWith16VNodesForAKey() {
		initializeNodeManager(16);
		int numberOfNodes = 2;
		String key = "Summa Key1";
		println("Initiated with 4 nodes with max 16 vnodes per node and requesting for 2 suitable nodes");
		multiScenarioTest(numberOfNodes, key);
	}

	private void multiScenarioTest(int numberOfNodes, String key) {
		fullNodeTest(numberOfNodes, key);

		nodeManager.remove(node1);
		println("removed node1");
		fullNodeTest(numberOfNodes, key);

		nodeManager.add(new SimpleNode("exampleNode5"));
		println("added node5");
		fullNodeTest(numberOfNodes, key);

		nodeManager.remove(node4);
		println("removed node4");
		fullNodeTest(numberOfNodes, key);

		nodeManager.add(new SimpleNode("exampleNode7"));
		println("added node7");
		fullNodeTest(numberOfNodes, key);

		nodeManager.remove(node3);
		println("removed node3");
		fullNodeTest(numberOfNodes, key);

		nodeManager.remove(node2);
		println("removed node2");
		fullNodeTest(numberOfNodes, key);

		nodeManager.add(node3);
		println("added node3");
		fullNodeTest(numberOfNodes, key);

		nodeManager.add(node2);
		println("added node2");
		fullNodeTest(numberOfNodes, key);

		nodeManager.remove(node3);
		nodeManager.remove(node2);
		println("removed node3 and node2 and requesting for "+ (numberOfNodes+1) +" suitable nodes");
		fullNodeTest(numberOfNodes+1, key);
	}

	private void fullNodeTest(int numberOfNodes, String key) {
		nodeTest(numberOfNodes, key);
		vNodeTest(numberOfNodes, key);
		println("*****************************");
		println();
	}

	private void nodeTest(int numberOfNodes, String key) {
		List<Node> nodes = nodeManager.findSuitableNodes(key, numberOfNodes);
		assertEquals(numberOfNodes, nodes.size());
		println("available nodes: " + currentAvailableNodes());
		println("<key:hash> -" + getKeyHash(key));
		println("selected nodes: " + getNodeInfo(nodes));
	}

	private String getNodeInfo(List<Node> nodes) {
		StringBuffer buff = new StringBuffer();
		buff.append("[");
		for (Node node : nodes) {
			buff.append(node.getIdentifier());
			buff.append(",");
		}
		buff.deleteCharAt(buff.length() - 1);
		buff.append("]");
		return buff.substring(0, buff.length());
	}

	private void vNodeTest(int numberOfNodes, String key) {
		Map<Integer, Node> vnodes = nodeManager.findSuitableVNodes(key,
				numberOfNodes);
		println("available vnodes: " + currentAvailableVNodes());
		println("<key:hash> -" + getKeyHash(key));
		println("selected vnodes: " + getVNodeInfo(vnodes));
	}

	private String getKeyHash(String key) {
		StringBuffer sb = new StringBuffer();
		sb.append("<");
		sb.append(key);
		sb.append(":");
		sb.append(new MD5HashingFunction().hash(key));
		return sb.toString();
	}

	private String getVNodeInfo(Map<Integer, Node> vNodes) {
		StringBuffer buff = new StringBuffer();
		buff.append("[");
		for (Integer vNodeKey : vNodes.keySet()) {
			buff.append("<");
			buff.append(vNodeKey);
			buff.append(":");
			buff.append(nodeManager.getNode(vNodeKey).getIdentifier());
			buff.append(">");
		}
		buff.append("]");
		return buff.substring(0, buff.length());
	}

	private String currentAvailableVNodes() {
		return getVNodeInfo(nodeManager.getAvailableVNodes());
	}

	private String currentAvailableNodes() {
		return getNodeInfo(new ArrayList<Node>(nodeManager.getAvailableNodes()));
	}

}
