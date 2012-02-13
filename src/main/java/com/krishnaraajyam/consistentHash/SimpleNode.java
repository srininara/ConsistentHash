package com.krishnaraajyam.consistentHash;

public class SimpleNode implements Node {

	private static final String NODE_IDENTIFIER_CANNOT_BE_NULL_MSG = "Node identifier cannot be null";

	public static final Integer DEFAULT_VNODES_VALUE = 32;

	private String identifier;

	private Integer supportedNumberOfVNodes;

	public SimpleNode(String identifier) {
		this(identifier, null);
	}

	public SimpleNode(String identifier, Integer vNodesSupport) {
		if (identifier == null)
			throw new IllegalArgumentException(
					NODE_IDENTIFIER_CANNOT_BE_NULL_MSG);
		this.identifier = identifier;
		if (vNodesSupport == null || vNodesSupport.equals(0)) {
			this.supportedNumberOfVNodes = DEFAULT_VNODES_VALUE;
		} else {
			this.supportedNumberOfVNodes = vNodesSupport;
		}
	}

	public String getIdentifier() {
		return identifier;
	}

	public Integer getSupportedNumberOfVNodes() {
		return supportedNumberOfVNodes;
	}
	
	@Override
	public String toString() {
		return identifier;
	}

	@Override
	public int hashCode() {
		return identifier.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleNode other = (SimpleNode) obj;
		if (identifier == null) {
			if (other.identifier != null)
				return false;
		} else if (!identifier.equals(other.identifier))
			return false;
		return true;
	}
	
}
