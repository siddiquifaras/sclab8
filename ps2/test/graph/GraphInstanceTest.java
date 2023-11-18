package graph;

import static org.junit.Assert.*;


import java.util.Map;
import java.util.Set;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyGraphVertices() {
        Graph<String> graph = emptyInstance();
        assertTrue("Expected new graph to have no vertices", graph.vertices().isEmpty());
    }

    @Test
    public void testAddVertices() {
        Graph<String> graph = emptyInstance();
        assertTrue(graph.add("Vertex1"));
        assertTrue(graph.add("Vertex2"));
        assertTrue(graph.add("Vertex3"));

        Set<String> expectedVertices = Set.of("Vertex1", "Vertex2", "Vertex3");
        assertEquals("Vertices after adding", expectedVertices, graph.vertices());
    }

    @Test
    public void testSetEdgesAndQuerySourcesTargets() {
        Graph<String> graph = emptyInstance();
        graph.add("Vertex1");
        graph.add("Vertex2");
        graph.add("Vertex3");

        assertEquals("Previous weight of non-existing edge", 0, graph.set("Vertex1", "Vertex2", 5));
        assertEquals("Previous weight of non-existing edge", 0, graph.set("Vertex2", "Vertex3", 10));

        Map<String, Integer> sourcesMap = graph.sources("Vertex2");
        Map<String, Integer> targetsMap = graph.targets("Vertex1");

        assertEquals("Sources for Vertex2", Map.of("Vertex1", 5), sourcesMap);
        assertEquals("Targets for Vertex1", Map.of("Vertex2", 5), targetsMap);
    }

    @Test
    public void testRemoveVertices() {
        Graph<String> graph = emptyInstance();
        graph.add("Vertex1");
        graph.add("Vertex2");
        graph.add("Vertex3");

        assertTrue(graph.remove("Vertex1"));
        assertFalse(graph.remove("Vertex1")); // Removing again should return false

        Set<String> expectedVerticesAfterRemoval = Set.of("Vertex2", "Vertex3");
        assertEquals("Vertices after removal", expectedVerticesAfterRemoval, graph.vertices());
    }

    @Test
    public void testRemoveEdges() {
        Graph<String> graph = emptyInstance();
        graph.add("Vertex1");
        graph.add("Vertex2");
        graph.add("Vertex3");

        graph.set("Vertex1", "Vertex2", 5);
        graph.set("Vertex2", "Vertex3", 10);

        int previousWeight = graph.set("Vertex2", "Vertex3", 0); // Remove the edge
        assertEquals("Previous weight of removed edge", 10, previousWeight);

        Map<String, Integer> sourcesMap = graph.sources("Vertex2");
        Map<String, Integer> targetsMap = graph.targets("Vertex1");

        assertTrue("Sources for Vertex2 after removal", sourcesMap.isEmpty());
        assertTrue("Targets for Vertex1 after removal", targetsMap.isEmpty());
    }
}
