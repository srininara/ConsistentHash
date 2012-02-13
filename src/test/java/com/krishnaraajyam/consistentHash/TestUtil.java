package com.krishnaraajyam.consistentHash;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

	protected NodeManger nodeManager;
	protected List<Node> nodes;
	protected Node node1;
	protected Node node2;
	protected Node node3;
	protected Node node4;

	protected void initializeNodeManager(Integer vNodeSupport) {
		node1 = new SimpleNode("exampleNode1", vNodeSupport);
		node2 = new SimpleNode("exampleNode2", vNodeSupport);
		node3 = new SimpleNode("exampleNode3", vNodeSupport);
		node4 = new SimpleNode("exampleNode4", vNodeSupport);

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		nodeManager = new NodeManagerImpl(nodes);
	}

	protected void initializeNodeManager() {
		node1 = new SimpleNode("exampleNode1");
		node2 = new SimpleNode("exampleNode2");
		node3 = new SimpleNode("exampleNode3");
		node4 = new SimpleNode("exampleNode4");

		List<Node> nodes = new ArrayList<Node>();
		nodes.add(node1);
		nodes.add(node2);
		nodes.add(node3);
		nodes.add(node4);
		nodeManager = new NodeManagerImpl(nodes);
	}
	
	protected void println(Object obj) {
		System.out.println(obj);
	}
	
	protected void println() {
		System.out.println();
	}

	protected void print(Object obj) {
		System.out.print(obj);
	}

}
