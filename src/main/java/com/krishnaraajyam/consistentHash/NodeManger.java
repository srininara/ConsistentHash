package com.krishnaraajyam.consistentHash;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface NodeManger {

	int getNumberOfVNodes();
	
	Set<Node> getAvailableNodes();
	
	Map<Integer,Node> getAvailableVNodes();
	
	Set<Integer> getVNodeKeys();

	Node getNode(Integer vNodeKey);

	void add(Node node);

	void remove(Node node);

	List<Node> findSuitableNodes(String key, int numberOfNodes);
	
	Map<Integer,Node> findSuitableVNodes(String key, int numberOfNodes);


}
