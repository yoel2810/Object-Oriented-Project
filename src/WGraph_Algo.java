package ex1.src;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ex0.node_data;
import ex1.src.WGraph_DS.NodeInfo;

public class WGraph_Algo implements weighted_graph_algorithms {

	private weighted_graph graph;
	
	public WGraph_Algo() {
		this.graph = new WGraph_DS();
	}

	public WGraph_Algo(weighted_graph g) {
		this.graph = g;
	}

	@Override
	public void init(weighted_graph g) {
		this.graph = g;
		g.getMC();
	}

	@Override
	public weighted_graph getGraph() {
		return this.graph;
	}

	@Override
	public weighted_graph copy() {
		return new WGraph_DS(this.graph);
	}

	@Override
	public boolean isConnected() {
		Queue<Integer> q = new LinkedList<Integer>();
		HashMap<Integer, Boolean> visited = new HashMap<Integer, Boolean>();// checking if the node is visited
		for (node_info nd : this.graph.getV()) {// O(|V|)
			visited.put(nd.getKey(), false);
		}
		if (this.graph.nodeSize() == 0)
			return true;
		int s = this.graph.getV().iterator().next().getKey();
		q.add(s);
		visited.replace(s, true);// the first node is visited
		while (!q.isEmpty()) {
			int node = q.remove();
			Collection<node_info> neighbours = this.graph.getV(node);
			Iterator<node_info> it = neighbours.iterator();
			while (it.hasNext()) {
				node_info next = it.next();
				if (!visited.get(next.getKey())) {
					q.add(next.getKey());
					visited.replace(next.getKey(), true);// updating the visited array

				}
			}
		}
		if (visited.containsValue(false))// if there is a node that was not visited return false
			return false;
		return true;
	}

	/*
	 * private void isConnected(NodeInfo node) {// O(n) node.setInfo("visited"); //
	 * System.out.println("now: " + node.getKey()); for (Edge e :
	 * node.getEdges().values()) { if (e.getSrc() != node.getKey()) { //
	 * System.out.println("nei " + e.getNode1().getKey()); if
	 * (this.graph.getNode(e.getSrc()).getInfo() != "visited") {
	 * isConnected((NodeInfo) this.graph.getNode(e.getSrc())); } } else { //
	 * System.out.println("nei " + e.getNode2().getKey()); if
	 * (this.graph.getNode(e.getDst()).getInfo() != "visited") {
	 * isConnected((NodeInfo) this.graph.getNode(e.getDst())); } } } }
	 */

	@Override
	public double shortestPathDist(int src, int dest) {
		// if (this.prevModeCount == this.graph.getMC())
		weighted_graph initg = copy();// o(n^2)
		Dijkstra(this.graph.getV().toArray(), this.graph.getNode(src));
		double ret = this.graph.getNode(dest).getTag();
		this.init(initg);
		if (ret < Integer.MAX_VALUE)
			return ret;
		else
			return -1;

	}

	@Override
	public List<node_info> shortestPath(int src, int dest) {
		// if (this.prevModeCount == this.graph.getMC())
		weighted_graph initg = copy();// o(n^2)
		Dijkstra(this.graph.getV().toArray(), this.graph.getNode(src));
		ArrayList<node_info> path = new ArrayList<node_info>();
		while (dest != src) {
			NodeInfo node = ((NodeInfo) this.graph.getNode(dest));
			if (node == null)
				return null;
			dest = node.getPred();
			path.add(this.graph.getNode(dest));
		}
		ArrayList<node_info> rev = new ArrayList<node_info>();
		for (int i = 0; i < path.size(); i++) {
			rev.add(path.get(path.size() - 1 - i));
		}
		this.init(initg);
		return rev;
	}

	/**
	 * Get an array of all the nodes in the graph and do a Dijkstra algorithem
	 * 
	 * @param nodeArr - and array of all the nodes in the array
	 * @param s       - get the source node (drom where to start)
	 */
	public void Dijkstra(Object[] nodeArr, node_info s) {
		s.setTag(0);

		PriorityQueue<NodeInfo> que = new PriorityQueue<NodeInfo>();
		for (int i = 0; i < nodeArr.length; i++) {// O(|V|)
			que.add((NodeInfo) nodeArr[i]);

		}

		while (que.isEmpty() == false) {//O(|V|)
			NodeInfo u = que.poll();
			for (Integer neiKey : u.getEdges().keySet()) {//O(degree)
				node_info v = this.graph.getNode(neiKey);
				if (v.getInfo() != "visited") {
					double t = u.getTag() + u.getEdges().get(neiKey).getWeight();
					if (v.getTag() > t) {
						que.remove(v);//O(|V|)
						v.setTag(t);
						((NodeInfo) v).setPred(u.getKey());
						que.add((NodeInfo) v);//O(|V|*log|V|)

					}

				}

			}
			u.setInfo("visited");
		}

	}

	

	@Override
	public boolean save(String file) {

		try {
			FileOutputStream myFile = new FileOutputStream(file);
			ObjectOutputStream oos = new ObjectOutputStream(myFile);
			oos.writeObject(this.graph);
			oos.close();
			myFile.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean load(String file) {
		try {
			FileInputStream myFile = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(myFile);
			this.graph = (weighted_graph) ois.readObject();
			ois.close();
			myFile.close();
			return true;
		} catch (Exception error) {
			error.printStackTrace();
			return false;
		}
	}

}
