package algorithms.search;

import static org.junit.Assert.*;

import org.junit.Test;

import algorithms.demo.SearchableAdapter;
import algorithms.mazeGenerators.GrowingTreeGenerator;
import algorithms.mazeGenerators.Position;

public class BestFirstSearchTest {

	@Test
	public void testSearch() {
		BestFirstSearch<Position> tester = new BestFirstSearch<Position>();
		
		assertEquals("solution for null is null", null, tester.Search(null));
		assertNotNull("solution for a searchable is not null", tester.Search(new SearchableAdapter(new GrowingTreeGenerator().generate(3, 3, 3))));
	}

	@Test
	public void testGetNumOfNodesEvaluated() {
		BestFirstSearch<Position> tester = new BestFirstSearch<Position>();

		assertEquals("num of evaluated nodes should be 0 at the beginning", 0, tester.getNumOfNodesEvaluated());
		tester.Search(new SearchableAdapter(new GrowingTreeGenerator().generate(3, 3, 3)));
		if(tester.getNumOfNodesEvaluated() == 0){
			fail("At least one node evaluated (start/goal nodes)");
		}
	}

	@Test
	public void testBackTrace() {
		BestFirstSearch<Position> tester = new BestFirstSearch<Position>();

		assertEquals("bakTrace returns null if the state it gets is null", null, tester.backTrace(null));
	}

}
