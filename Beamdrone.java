import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Beamdrone {

	static class Task {
		public static final String INPUT_FILE = "beamdrone.in";
		public static final String OUTPUT_FILE = "beamdrone.out";

		int N, M;
		int x_i, y_i, x_f, y_f;
		int[][] track;

		public static final int INF = (int) 1e9;

		public class DijkstraResult {
			List<Integer> d;

			DijkstraResult(List<Integer> _d) {
				d = _d;
			}
		};

		public class Pair implements Comparable<Pair> {
			public int destination;
			public int cost;
			public int neigh_x;
			public int neigh_y;
			public String prev_direction;

			Pair(int _destination, int _cost, int _neigh_x, int _neigh_y,
					String _prev_direction) {
				destination = _destination;
				cost = _cost;
				neigh_x = _neigh_x;
				neigh_y = _neigh_y;
				prev_direction = _prev_direction;
			}

			public int compareTo(Pair rhs) {
				return Integer.compare(cost, rhs.cost);
			}
		}

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				M = sc.nextInt();
				x_i = sc.nextInt();
				y_i = sc.nextInt();
				x_f = sc.nextInt();
				y_f = sc.nextInt();

				track = new int[N][M];
				int cnt = 0;
				for (int i = 0; i < N; i++) {
					String str = sc.next();
					String[] split = str.split("");

					for (int j = 0; j < M; j++) {
						if (!split[j].equals("W")) {
							track[i][j] = cnt;
							cnt++;
							continue;
						}
						track[i][j] = -100;
					}
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", result);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private int getResult() {
			return dijkstra(track[x_i][y_i], N * M);
		}

		/**
		 * Functia se ocupa cu implementarea logicii algoritmului Dijkstra.
		 * Se initializeaza distantele la infinit si, folosindu-ne de un
		 * priority queue vom genera toate perechile nod-vecin posibile,
		 * desi elementele noastre nu sunt tocmai muchii.
		 * Vom folosi field-ul de cost ca si distanta pana la nodul respectiv.
		 * Inseram nodul de plecare in pq si ii setam distanta la 0, apoi
		 * parcurgem queue si calculam la fiecare pas vecinii N, S, E, V
		 * daca e posibil si actualizam costul si distantele daca e necesar.
		 * 
		 * @param source - pozitia initiala din matrice data in input.
		 * @param nodes  - dimensiunea totala a matricii.
		 * @return - costul minim pentru distanta cautata.
		 */
		private int dijkstra(int source, int nodes) {
			List<Integer> d = new ArrayList<>();
			int result = INF;

			for (int node = 0; node < nodes; node++) {
				d.add(INF);
			}

			PriorityQueue<Pair> pq = new PriorityQueue<>();

			d.set(source, 0);
			pq.add(new Pair(source, 0, x_i, y_i, "initial"));

			while (!pq.isEmpty()) {
				int node = pq.peek().destination;
				int cost = pq.peek().cost;
				int current_x = pq.peek().neigh_x;
				int current_y = pq.peek().neigh_y;
				String prev_direction = pq.poll().prev_direction;

				if (current_x == x_f && current_y == y_f && result > cost) {
					result = cost;
					break;
				}

				if (cost > d.get(node)) {
					continue;
				}

				int north_neigh = INF;
				int east_neigh = INF;
				int west_neigh = INF;
				int south_neigh = INF;

				if (current_x - 1 >= 0) {
					north_neigh = track[current_x - 1][current_y];
				}

				if (current_x + 1 < N) {
					south_neigh = track[current_x + 1][current_y];
				}

				if (current_y - 1 >= 0) {
					west_neigh = track[current_x][current_y - 1];
				}

				if (current_y + 1 < M) {
					east_neigh = track[current_x][current_y + 1];
				}

				if (north_neigh != INF && north_neigh != -100) {
					if (prev_direction.equals("initial") || prev_direction.equals("north")) {
						if (cost <= d.get(north_neigh)) {
							d.set(north_neigh, cost);
							pq.add(new Pair(north_neigh, d.get(north_neigh), current_x - 1,
									current_y, "north"));
						}
					}

					if (prev_direction.equals("east") || prev_direction.equals("west")) {
						if (cost + 1 <= d.get(north_neigh)) {
							d.set(north_neigh, cost + 1);
							pq.add(new Pair(north_neigh, d.get(north_neigh), current_x - 1,
									current_y, "north"));
						}
					}

					if (prev_direction.equals("south")) {
						if (cost + 2 <= d.get(north_neigh)) {
							d.set(north_neigh, cost + 2);
							pq.add(new Pair(north_neigh, d.get(north_neigh), current_x - 1,
									current_y, "north"));
						}
					}
				}

				if (south_neigh != INF && south_neigh != -100) {
					if (prev_direction.equals("initial") || prev_direction.equals("south")) {
						if (cost <= d.get(south_neigh)) {
							d.set(south_neigh, cost);
							pq.add(new Pair(south_neigh, d.get(south_neigh), current_x + 1,
									current_y, "south"));
						}
					}
					if (prev_direction.equals("east") || prev_direction.equals("west")) {
						if (cost + 1 <= d.get(south_neigh)) {
							d.set(south_neigh, cost + 1);
							pq.add(new Pair(south_neigh, d.get(south_neigh), current_x + 1,
									current_y, "south"));
						}
					}

					if (prev_direction.equals("north")) {
						if (cost + 2 <= d.get(south_neigh)) {
							d.set(south_neigh, cost + 2);
							pq.add(new Pair(south_neigh, d.get(south_neigh), current_x + 1,
									current_y, "south"));
						}
					}
				}

				if (east_neigh != INF && east_neigh != -100) {
					if (prev_direction.equals("initial") || prev_direction.equals("east")) {
						if (cost <= d.get(east_neigh)) {
							d.set(east_neigh, cost);
							pq.add(new Pair(east_neigh, d.get(east_neigh), current_x,
									current_y + 1, "east"));
						}
					}
					if (prev_direction.equals("north") || prev_direction.equals("south")) {
						if (cost + 1 <= d.get(east_neigh)) {
							d.set(east_neigh, cost + 1);
							pq.add(new Pair(east_neigh, d.get(east_neigh), current_x,
									current_y + 1, "east"));
						}
					}

					if (prev_direction.equals("west")) {
						if (cost + 2 <= d.get(east_neigh)) {
							d.set(east_neigh, cost + 2);
							pq.add(new Pair(east_neigh, d.get(east_neigh), current_x,
									current_y + 1, "east"));
						}
					}
				}

				if (west_neigh != INF && west_neigh != -100) {
					if (prev_direction.equals("initial") || prev_direction.equals("west")) {
						if (cost <= d.get(west_neigh)) {
							d.set(west_neigh, cost);
							pq.add(new Pair(west_neigh, d.get(west_neigh), current_x,
									current_y - 1, "west"));
						}
					}
					if (prev_direction.equals("north") || prev_direction.equals("south")) {
						if (cost + 1 <= d.get(west_neigh)) {
							d.set(west_neigh, cost + 1);
							pq.add(new Pair(west_neigh, d.get(west_neigh), current_x,
									current_y - 1, "west"));
						}
					}

					if (prev_direction.equals("east")) {
						if (cost + 2 <= d.get(west_neigh)) {
							d.set(west_neigh, cost + 2);
							pq.add(new Pair(west_neigh, d.get(west_neigh), current_x,
									current_y - 1, "west"));
						}
					}
				}
			}
			return result;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}