package ex1.tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;

class Tests {

	public static WGraph_DS g = new WGraph_DS();

	// public static WGraph_DS gCopy = (WGraph_DS)new WGraph_Algo(g).copy();
	@BeforeAll
	public static void init() {

		for (int i = 1; i < 7; i++) {
			g.addNode(i);
		}
		g.connect(1, 2, 7);
		g.connect(2, 3, 10);
		g.connect(1, 6, 14);
		g.connect(1, 3, 9);
		g.connect(2, 4, 15);
		g.connect(3, 6, 2);
		g.connect(6, 5, 9);
		g.connect(5, 4, 6);
		g.connect(4, 3, 11);
	}

	@Test
	public void test_method_1() {
		assertTrue(g.edgeSize() == 9);
		assertTrue(g.nodeSize() == 6);
		System.out.println("@Test - test_method_1");

	}

	@Test
	public void test_method_2() {
		WGraph_Algo gAlgo = new WGraph_Algo(g);
		assertTrue(gAlgo.isConnected());
		assertTrue(gAlgo.shortestPathDist(1, 1) == 0);
		assertTrue(gAlgo.shortestPathDist(1, 2) == 7);
		assertTrue(gAlgo.shortestPathDist(1, 3) == 9);
		assertTrue(gAlgo.shortestPathDist(1, 4) == 20);
		assertTrue(gAlgo.shortestPathDist(1, 5) == 20);
		assertTrue(gAlgo.shortestPathDist(1, 6) == 11);
		gAlgo.getGraph().removeEdge(5, 6);
		gAlgo.getGraph().removeEdge(3, 6);
		gAlgo.getGraph().removeEdge(1, 3);
		gAlgo.getGraph().removeEdge(1, 2);
		assertFalse(gAlgo.isConnected());
		assertTrue(gAlgo.shortestPathDist(1, 3) == -1);
		assertTrue(gAlgo.shortestPath(1, 3) == null);
		System.out.println("@Test - test_method_2");

	}

	@Test
	public void test_method_3() {
		WGraph_Algo wg = new WGraph_Algo(new WGraph_DS());
		try {
			wg.save("graph.txt");
		} catch (Exception e) {
			fail("didnt save");
		}
		try {
			wg.load("graph.txt");
		} catch (Exception e) {
			fail("didnt load");
		}
		assertTrue(wg.getGraph().nodeSize() == 0);
		System.out.println("@Test - test_method_3");
	}

	@Test
	public void test_method_4() {
		g = new WGraph_DS();
		for (int i = 0; i < 1000000; i++) {
			g.addNode(i);
		}
		for (int i = 0; i < 3000000; i++) {
			Random rnum = new Random();
			int a = rnum.nextInt(100000);
			int b = rnum.nextInt(100000);
			g.connect(a, b, rnum.nextDouble());
		}
		//g.addNode(-5);
		WGraph_Algo wg = new WGraph_Algo(g);
		
		long startTime = System.currentTimeMillis();
		System.out.println(wg.isConnected());
		wg.copy();
		//wg.Dijkstra(wg.getGraph().getV().toArray(), wg.getGraph().getNode(0));
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println(totalTime);
	}

}
