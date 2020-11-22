package ex1.src;

import java.io.Serializable;

/**
 * This class represents the edge between 2 nodes (the source node) and the
 * destination node), it's also contains the weigh for the edge.
 * 
 * @author Yoel Hartman
 *
 */

public class Edge implements Serializable {

	private int src, dst;// src
	private double weight;

	public Edge(int src, int dst, double weight) {
		this.src = src;
		this.dst = dst;
		this.weight = weight;
	}

	public Edge(Edge e) {
		this.src = e.getSrc();
		this.dst = e.getDst();
		this.weight = e.getWeight();
	}

	/**
	 * return the node id in the other side of the edge
	 * 
	 * @param me - the current node id
	 * @return the other node id (the node in the other side of the node)
	 */
	public int getNei(int me) {
		if (this.src == me)
			return this.dst;
		else
			return this.src;
	}

	public int getSrc() {
		return src;
	}

	public void setSrc(int src) {
		this.src = src;
	}

	public int getDst() {
		return dst;
	}

	public void setDst(int dst) {
		this.dst = dst;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public boolean equals(Object other) {
		return this.dst == ((Edge)other).dst && this.src == ((Edge)other).src && this.weight == ((Edge)other).weight;//O(1)
	}

}
