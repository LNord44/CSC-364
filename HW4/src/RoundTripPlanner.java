
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoundTripPlanner {
	// user inputs for the source and destination
	private int startCityIndex;
	private int endCityIndex;

	// Graph created using the following vertices and edges
	private WeightedGraph<String> flightNetwork;

	// array of vertices
	private String[] cities;
	// array of weighted edges [source][dest][weight]
	private int[][] connections;

	// forward and return route cities lists and cost of trip
	private List<String> forwardRoute;
	private double forwardRouteCost;
	private List<String> returnRoute;
	private double returnRouteCost;

	/*
	 * Constructor:
	 * - Assigns class variables
	 * - Invokes generateRoundTrip() method
	 */
	public RoundTripPlanner(String[] cities, int[][] connections, int startCityIndex, int endCityIndex) {
		//applying these attributes to my object and calling generate round trip method
		this.cities = cities;
		this.connections = connections;
		this.startCityIndex = startCityIndex;
		this.endCityIndex = endCityIndex;
		generateRoundTrip();
	}

	/*
	 * Round trip generator:
	 * - Creates flight network graph
	 * - Updates forward trip path variable and forward trip cost
	 * - Performs necessary actions for return trip planning
	 * - Updates return trip path variable and return trip cost
	 */
	public void generateRoundTrip() {
		flightNetwork = new WeightedGraph<>(cities, connections);
		WeightedGraph<String>.ShortestPathTree forwardShortestPathTree = flightNetwork.getShortestPath(startCityIndex);
		// declaring forwardShortestPathTree
		forwardRoute = forwardShortestPathTree.getPath(endCityIndex);
		forwardRouteCost = forwardShortestPathTree.getCost(endCityIndex);
		// getting forward route and it's cost
		for(int j=0; j<forwardRoute.size(); j++){
			if(j < forwardRoute.size() - 1) {
				int firstIndex = flightNetwork.getIndex(forwardRoute.get(j));
				int secondIndex = flightNetwork.getIndex(forwardRoute.get(j + 1));
				//assigning our indexes
				for (int[] i : connections) {
					if (i[0] == firstIndex && i[1] == secondIndex){
						i[2] = Integer.MAX_VALUE;
					}
					if (i[0] == secondIndex && i[1] == firstIndex) {
						i[2] = Integer.MAX_VALUE;
					}
				}
				//finding the best destination path for our flight network
			}
		}
		flightNetwork = new WeightedGraph<>(cities, connections);
		WeightedGraph<String>.ShortestPathTree returnShortestPathTree = flightNetwork.getShortestPath(endCityIndex);
		// declaring returnShortestPathTree
		returnRoute = returnShortestPathTree.getPath(startCityIndex);
		returnRouteCost = returnShortestPathTree.getCost(startCityIndex);
		// getting return route and it's cost
	}


	/*
	 * Trip viewer:
	 * - prints forward trip in the format:
	 * "Forward trip from A to B: A �> P �> Q �> R �> B"
	 * - prints return trip in the same format:
	 * "Return trip from B to A: B �> S �> T �> U �> A"
	 * - prints the costs for the forward trip, return trip, and total trip in the format:
	 *  "Forward route cost: 200.0"
	 *  "Return route cost: 300.0"
	 *  "Total trip cost: 500.0"
	 */
	public void printRoundTrip() {
		List<String> fRoute = getForwardRoute();
		System.out.print("Forward trip from " + fRoute.get(0) + " to " + fRoute.get(fRoute.size() -1) + ": ");
		for (int i = 0; i < fRoute.size(); i++){
			if (i != (fRoute.size() -1)) {
				System.out.print(fRoute.get(i) + " -> ");
			}
			else {
				System.out.print(fRoute.get(i));
			}
		}
		// getting our forward route and printing
		List<String> rRoute = getReturnRoute();
		System.out.print("\nReturn trip from " + rRoute.get(0) + " to " + rRoute.get(rRoute.size() -1) + ": ");
		for (int i = 0; i < rRoute.size(); i++){
			if (i != (rRoute.size() -1)) {
				System.out.print(rRoute.get(i) + " -> ");
			}
			else {
				System.out.print(rRoute.get(i));
			}
		}
		// getting our return route and printing
		System.out.println("\nForward route cost: " + getForwardRouteCost());
		System.out.println("Return route cost: " + getReturnRouteCost());
		float totalCost = (float) (getForwardRouteCost() + getReturnRouteCost());
		System.out.println("Total trip cost: " + totalCost);
		// printing costs
	}

	// Returns the forwardRoute class variable
	public List<String> getForwardRoute() {
		return forwardRoute;
	}

	// Returns the returnRoute class variable
	public List<String> getReturnRoute() {
		return returnRoute;
	}

	// Returns the forwardRouteCost class variable
	public double getForwardRouteCost() {
		return forwardRouteCost;
	}

	// Returns the returnRouteCost class variable
	public double getReturnRouteCost() {
		return returnRouteCost;
	}
}
