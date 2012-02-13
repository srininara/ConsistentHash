package com.krishnaraajyam.consistentHash;

public interface Node {

	public abstract String getIdentifier();

	public abstract Integer getSupportedNumberOfVNodes();

}