package com.krishnaraajyam.consistentHash;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class NodeTest {

	private static final String EXAMPLE_NODE_IDENTIFIER = "exampleNode1";
	private Node node;
	private static final Integer EXAMPLE_VNODE_SUPPORT_VALUE = 64;
	
	@Before
	public void setup() {
		node = new SimpleNode(EXAMPLE_NODE_IDENTIFIER);
	}
	
	@Test
	public void basicTest() {
		assertEquals(EXAMPLE_NODE_IDENTIFIER,node.getIdentifier());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void aNodeCannotHaveNullIdentifier() {
		new SimpleNode(null);
	}
	
	@Test
	public void aNodevNodesSupportValueIfPassedAsNullWillBeSetToDefaultValue() {
		node = new SimpleNode(EXAMPLE_NODE_IDENTIFIER,null);
		assertEquals(SimpleNode.DEFAULT_VNODES_VALUE,node.getSupportedNumberOfVNodes());
	}
	
	@Test
	public void aNodevNodesSupportValueIfPassedAsZeroWillBeSetToDefaultValue() {
		node = new SimpleNode(EXAMPLE_NODE_IDENTIFIER,0);
		assertEquals(SimpleNode.DEFAULT_VNODES_VALUE,node.getSupportedNumberOfVNodes());
	}
	
	
	@Test
	public void aNodevNodeSupportValueIfPassedProperlyWillBeSetToThatValue() {
		node = new SimpleNode(EXAMPLE_NODE_IDENTIFIER,EXAMPLE_VNODE_SUPPORT_VALUE);
		assertEquals(NodeTest.EXAMPLE_VNODE_SUPPORT_VALUE,node.getSupportedNumberOfVNodes());
	}
	
	@Test
	public void aNodeToStringMustContainNodeId() {
		node = new SimpleNode(EXAMPLE_NODE_IDENTIFIER,EXAMPLE_VNODE_SUPPORT_VALUE);
		assertTrue(node.toString().contains(EXAMPLE_NODE_IDENTIFIER));
	}
	
	@Test
	public void aNodesHashCodeIsSameAsItsIdentifiersHashCode() {
		node = new SimpleNode(EXAMPLE_NODE_IDENTIFIER);
		assertEquals(EXAMPLE_NODE_IDENTIFIER.hashCode(),node.hashCode());
	}

}
