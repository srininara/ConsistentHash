package com.krishnaraajyam.consistentHash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class NodeManagerImpl implements NodeManger {

	private SortedMap<Integer, Node> circle = new TreeMap<Integer, Node>();

	private static final String delimiter = ":";

	private void initializeNodes(List<Node> nodes) {
		if (nodes == null || nodes.isEmpty())
			return;
		for (int i = 0; i < nodes.size(); i++) {
			initializeNode(nodes.get(i));
		}
	}

	private void initializeNode(Node node) {
		for (int j = 0; j < node.getSupportedNumberOfVNodes(); j++) {
			circle.put(getHash(getVNodeIdentifier(node.getIdentifier(), j)),
					node);
		}
	}

	private int getHash(String vNodeIdentifier) {
		return new MD5HashingFunction().hash(vNodeIdentifier);
	}

	private String getVNodeIdentifier(String identifier, int j) {
		return identifier + delimiter + j;
	}

	public NodeManagerImpl() {
		this(null);
	}

	public NodeManagerImpl(List<Node> nodes) {
		initializeNodes(nodes);
	}

	public int getNumberOfVNodes() {
		return circle.size();
	}

	public Set<Integer> getVNodeKeys() {
		return circle.keySet();
	}

	public Node getNode(Integer vNodeKey) {
		return circle.get(vNodeKey);
	}

	public void add(Node node) {
		initializeNode(node);
	}

	public void remove(Node node) {
		if (node == null)
			return;
		Set<Integer> removableKeys = new HashSet<Integer>();
		for (Integer key : getVNodeKeys()) {
			if (node.equals(getNode(key))) {
				removableKeys.add(key);
			}
		}
		for (Integer rKey : removableKeys) {
			circle.remove(rKey);
		}
	}

	public List<Node> findSuitableNodes(String key, int numberOfNodes) {
		Map<Integer,Node> vNodes = findSuitableVNodes(key, numberOfNodes);
		List<Node> nodes = new ArrayList<Node>();
		for (Integer vNodeKey: vNodes.keySet()) {
			nodes.add(vNodes.get(vNodeKey));
		}
		return nodes;
	}
	
	public Map<Integer,Node> findSuitableVNodes(String key, int numberOfNodes) {
		if (numberOfNodes <= 0)
			return null;
		Integer hashedKey = new MD5HashingFunction().hash(key);
		SortedMap<Integer, Node> tail = circle.tailMap(hashedKey);
		Map<Integer,Node> suitableNodes = new LinkedHashMap<Integer,Node>();
		int suitableNodeCount = 0;
		for (Integer suitableNodeKey : tail.keySet()) {
			suitableNodes.put(suitableNodeKey,tail.get(suitableNodeKey));
			++suitableNodeCount;
			if (suitableNodeCount >= numberOfNodes) {
				return suitableNodes;
			}
		}
		SortedMap<Integer, Node> head = circle.headMap(hashedKey);
		for (Integer suitableNodeKey : head.keySet()) {
			suitableNodes.put(suitableNodeKey,head.get(suitableNodeKey));
			++suitableNodeCount;
			if (suitableNodeCount >= numberOfNodes) {
				break;
			}
		}
		return suitableNodes;
	}

	public Set<Node> getAvailableNodes() {
		Collection<Node> nodeColl = circle.values();
		Set<Node> nonDupSet = new HashSet<Node>();
		for (Node node : nodeColl) {
			nonDupSet.add(node);
		}
		return nonDupSet;
	}

	public Map<Integer,Node> getAvailableVNodes() {
		return circle;
	}

}
