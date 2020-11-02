import java.io.*;
import java.util.*;


class find_route {
	public static void main(final String[] args) {
		final int number_of_args = args.length;
		final String start_city = args[1];
		final String goal_city = args[2];
		int expanded = 0;
		int generated = 0;
		int size = 0;
		BufferedReader reader;
		String line;
		final ArrayList<String> input = new ArrayList<String>();
		final Map<String, Float> heuristic = new HashMap<String, Float>();

		
		try {
			reader = new BufferedReader(new FileReader(args[0]));
			while (!(line = reader.readLine()).equals("END OF INPUT")) {
				input.add(line);
			}
			reader.close();
		} catch (final Exception exception) {
			exception.printStackTrace();
		}

		if (number_of_args == 4) {
			try {
				reader = new BufferedReader(new FileReader(args[3]));
				String[] strArray = new String[3];
				while (!(line = reader.readLine()).equals("END OF INPUT")) {
					strArray = line.split(" ");
					heuristic.put(strArray[0], Float.parseFloat(strArray[1]));
				}
				reader.close();
			} catch (final Exception exception) {
				exception.printStackTrace();
			}
		}

		
		final Node startNode = new Node(start_city, null, 0, 0);

		final PriorityQueue<Node> fringe = new PriorityQueue<Node>(new fringeComparator());
		final ArrayList<String> explored = new ArrayList<String>();
		Node sol = null;
		fringe.add(startNode);

		while (!(fringe.isEmpty())) {
			final Node work_node = fringe.peek();
			if (number_of_args == 4) {
				if (!(work_node.current.equals(start_city)))
					work_node.path_cost = work_node.path_cost - heuristic.get(work_node.current);
			}
			fringe.poll();
			expanded++;

			if (work_node.current.equals(goal_city)) {
				sol = work_node;
				break;
			} else {
				if (!explored.contains(work_node.current)) {
					explored.add(work_node.current);
					for (final String input1 : input) {
						if (input1.contains(work_node.current)) {
							final String[] str = input1.split(" ");
							final String beg_city = str[0];
							final String des_city = str[1];
							final Float cost = Float.parseFloat(str[2]);
							if (work_node.current.equals(beg_city)) {
								if (number_of_args == 4) {
									final Node addFringe = new Node(des_city, work_node,
											work_node.path_cost + cost + heuristic.get(des_city), work_node.depth + 1);
									fringe.add(addFringe);
								} else {
									final Node addFringe = new Node(des_city, work_node, work_node.path_cost + cost,
											work_node.depth + 1);
									fringe.add(addFringe);
								}
							} else {
								if (number_of_args == 4) {
									final Node addFringe = new Node(beg_city, work_node,
											work_node.path_cost + cost + heuristic.get(beg_city), work_node.depth + 1);
									fringe.add(addFringe);
								} else {
									final Node addFringe = new Node(beg_city, work_node, work_node.path_cost + cost,
											work_node.depth + 1);
									fringe.add(addFringe);
								}
							}
							generated++;
						}
					}

					if (size < fringe.size() || size == fringe.size()) {
						size = fringe.size();
					}
				}

			}
		}
		
		Printoutput print = new Printoutput(expanded,generated,size,sol);
		print.printfun();
	}
}

class Node {
	String current;
	Node parent;
	Node child;
	float path_cost;
	int depth;

	Node(final String cur, final Node par, final float pc, final int dep) {
		current = cur;
		parent = par;
		path_cost = pc;
		depth = dep;
	}
}

class fringeComparator implements Comparator<Node> {
	public int compare(final Node node1, final Node node2) {
		if (node1.path_cost > node2.path_cost)
			return 1;
		if (node1.path_cost < node2.path_cost)
			return -1;
		return 0;
	}
}


class Printoutput {
	int expanded = 0;
	int generated = 0;
	int size = 0;
	Node sol=null;
	Printoutput(int expanded,int generated,int size, Node sol)
	{
		this.expanded = expanded;
		this.generated = generated;
		this.size =size;
		this.sol =sol;
	}
	
	void printfun()
	{
		System.out.println("nodes expanded: " + expanded);
		System.out.println("nodes generated: " + generated);
		
				if (sol == null) {
			System.out.println("distance: infinity");
			System.out.println("route: " + "\nnone");
		} else {
			sol.child = null;
			System.out.println("Distance: " + sol.path_cost + " km" + "\nroute:");
			while (sol.parent != null) {
				sol.parent.child = sol;
				sol = sol.parent;
			}
			while (sol.child != null) {
				System.out.println(sol.current + " to " + sol.child.current + ", "
						+ (sol.child.path_cost - sol.path_cost) + " km");
				sol = sol.child;
			}
		}
	}

}


