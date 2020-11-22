package ex1.src;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class WGraph_DS implements weighted_graph, Serializable {

	private HashMap<Integer, node_info> nodes; // keys - the nodes - id, vakues - the nodes
	private int edgeSize;
	private int modeCount;

	public WGraph_DS() {
		this.nodes = new HashMap<Integer, node_info>();
		this.edgeSize = 0;
		this.modeCount = 0;
	}

	public WGraph_DS(weighted_graph other) {
		this.modeCount = other.getMC();
		this.edgeSize = other.edgeSize();
		this.nodes = new HashMap<Integer, node_info>();
		Iterator<node_info> it = other.getV().iterator();
		while (it.hasNext())// copying the nodes O(N)
		{
			node_info next = it.next();
			this.nodes.put(next.getKey(), new NodeInfo(next));
		}
	}

	@Override
	public node_info getNode(int key) {
		if (this.nodes.containsKey(key))
			return this.nodes.get(key);
		else
			return null;
	}

	@Override
	public boolean hasEdge(int node1, int node2) {
		if (this.nodes.containsKey(node1) && this.nodes.containsKey(node2)) {// if the nodes exist
			return ((NodeInfo) this.nodes.get(node1)).hasNei(this.nodes.get(node2))
					&& ((NodeInfo) this.nodes.get(node2)).hasNei(this.nodes.get(node1));
		} else
			return false;
	}

	@Override
	public double getEdge(int node1, int node2) {
		if (this.nodes.containsKey(node1) && this.nodes.containsKey(node2))
			if (this.hasEdge(node1, node2))
				return ((NodeInfo) this.getNode(node1)).getEdges().get(node2).getWeight();
		return -1;
	}

	@Override
	public void addNode(int key) {
		if (this.getNode(key) == null) {// if the node exist
			this.nodes.put(key, new NodeInfo(key));
			this.modeCount++;
		}
	}

	@Override
	public void connect(int node1, int node2, double w) {
		NodeInfo node11 = (NodeInfo) this.getNode(node1);
		NodeInfo node22 = (NodeInfo) this.getNode(node2);
		if (node11 == null || node22 == null)
			return;
		if (this.hasEdge(node1, node2)) {
			node11.edges.get(node2).setWeight(w);
		} else {
			node11.addNei(node22, w);
			node22.addNei(node11, w);
			this.edgeSize++;
			this.modeCount++;
		}

	}

	@Override
	public Collection<node_info> getV() {
		return (Collection<node_info>) this.nodes.values();
	}

	@Override
	public Collection<node_info> getV(int node_id) {
		NodeInfo n = (NodeInfo) this.getNode(node_id);
		ArrayList<node_info> l = new ArrayList<node_info>();
		for (Integer node : n.getEdges().keySet()) {
			l.add(this.getNode(node));
		}
		return (Collection<node_info>) l;
	}

	@Override
	public node_info removeNode(int key) {
		if (this.nodes.containsKey(key)) {
			Collection<node_info> coll = this.getV(key);// O(k)
			Iterator<node_info> it = coll.iterator();
			while (it.hasNext())//O(k)
				this.removeEdge(key, it.next().getKey());
			this.modeCount++;
			return this.nodes.remove(key);
		} else
			return null;
	}

	@Override
	public void removeEdge(int node1, int node2) {
		NodeInfo node11 = (NodeInfo) this.getNode(node1);
		NodeInfo node22 = (NodeInfo) this.getNode(node2);
		if (node11 == null || node22 == null)
			return;
		if (!this.hasEdge(node1, node2))
			return;
		node11.removeNei(node22);
		node22.removeNei(node11);

		this.edgeSize--;
		this.modeCount++;

	}

	@Override
	public int nodeSize() {
		return this.nodes.size();
	}

	@Override
	public int edgeSize() {
		return this.edgeSize;
	}

	@Override
	public int getMC() {
		// TODO Auto-generated method stub
		return this.modeCount;
	}

	public boolean equals(Object other) {
		//System.out.println("equalsssssssss");
		if (!(this.nodes.size() == ((WGraph_DS) other).getV().size() && this.modeCount == ((WGraph_DS) other).modeCount
				&& this.edgeSize == ((WGraph_DS) other).edgeSize))
			return false;
		for (Integer nodeKey : this.nodes.keySet()) {//O(|V|)
			if (((WGraph_DS) other).nodes.containsKey(nodeKey) == false)
				return false;
			if (((WGraph_DS) other).nodes.get(nodeKey).equals(this.nodes.get(nodeKey)) == false)
				return false;
		}
		return true;
	}

	public class NodeInfo implements Serializable, node_info, Comparable<node_info> {

		private int key;
		private String info;
		private double tag;

		private int pred;// parent
		private HashMap<Integer, Edge> edges;// the edges of the node. keys - the id of the other node in the edge.
												// values - the edges

		public NodeInfo(node_info other) {
			this.key = other.getKey();
			this.info = new String(other.getInfo());
			this.tag = other.getTag();
			this.edges = new HashMap<Integer, Edge>();
			for (Edge e : ((NodeInfo) other).getEdges().values()) {//O(|E|)
				this.edges.put(e.getNei(other.getKey()), new Edge(e));
			}
			this.pred = ((NodeInfo) other).getPred();
		}

		public NodeInfo(int key) {
			this.info = "";
			this.tag = Integer.MAX_VALUE;
			this.edges = new HashMap<Integer, Edge>();
			this.key = key;
			this.pred = Integer.MIN_VALUE;
		}

		public boolean equals(Object other) {
			if (this.key != ((NodeInfo) other).getKey())
				return false;
			if (this.info != ((NodeInfo) other).getInfo())
				return false;
			if (this.tag != ((NodeInfo) other).getTag())
				return false;
			if (this.pred != ((NodeInfo) other).getPred())
				return false;
			if (this.edges.size() != ((NodeInfo) other).getEdges().size())
				return false;
			for (Integer edgeKey : this.edges.keySet()) {//O(k)
				if (((NodeInfo) other).getEdges().containsKey(edgeKey) == false)
					return false;
				if (((NodeInfo) other).getEdges().get(edgeKey).equals(this.edges.get(edgeKey)) == false)
					return false;
			}
			return true;
		}

		/**
		 * 
		 * @return - the HashMap of the edges of the node
		 */
		public HashMap<Integer, Edge> getEdges() {
			return this.edges;
		}

		/**
		 * Add another neighbor to the node and connect them with an edge
		 * 
		 * @param n - the new neighbor
		 * @param w - the weight of the node
		 */
		public void addNei(node_info n, double w) {
			if ((this.edges.keySet().contains(n.getKey()) == false) && n.getKey() != this.key) {// if the node does not																				// the same node
				this.edges.put(n.getKey(), new Edge(this.key, n.getKey(), w));

			}
		}

		/**
		 * Removed a node from the neighbors
		 * 
		 * @param n - the node that we ant to remove
		 */
		public void removeNei(node_info n) {
			if (this.edges.containsKey(n.getKey()))
				edges.remove(n.getKey());
		}

		/*
		 * public node_info getNei(int key) { if (this.hasNei(key)) return
		 * WGraph_DS.this.getNode(key); else return null; }
		 */

		/**
		 * return true - if a node is in the neighbors, else - return false
		 * 
		 * @param key - the node that we want to check if he is a neighbor
		 * @return
		 */
		public boolean hasNei(node_info key) {
			return this.edges.containsKey(key.getKey());
		}

		@Override
		public int getKey() {
			return this.key;
		}

		@Override
		public String getInfo() {
			return this.info;
		}

		@Override
		public void setInfo(String s) {
			this.info = s;
		}

		@Override
		public double getTag() {
			return this.tag;
		}

		@Override
		public int compareTo(node_info arg0) {
			int ret = 0;
			if (this.tag - arg0.getTag() > 0)
				ret = 1;
			else if (this.tag - arg0.getTag() < 0)
				ret = -1;
			return ret;
		}

		@Override
		public void setTag(double t) {
			this.tag = t;
		}

		public int getPred() {
			return pred;
		}

		public void setPred(int pred) {
			this.pred = pred;
		}

		public void setKey(int key) {
			this.key = key;
		}

		public void setEdges(HashMap<Integer, Edge> edges) {
			this.edges = edges;
		}

	}

}
