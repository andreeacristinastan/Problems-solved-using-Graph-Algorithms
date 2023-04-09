import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;

public class Curse {
	static class Task {
		public static final String INPUT_FILE = "curse.in";
		public static final String OUTPUT_FILE = "curse.out";

		int N, M, A;
		int[][] courses;
		Boolean[] check;

		public void solve() {
			readInput();
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				M = sc.nextInt();
				A = sc.nextInt();

				courses = new int[A][N];

				check = new Boolean[A];
				Arrays.fill(check, Boolean.FALSE);

				@SuppressWarnings("unchecked")
				ArrayList<Integer>[] adj = new ArrayList[M + 1];

				for (int i = 0; i < M + 1; i++) {
					adj[i] = new ArrayList<>();
				}

				for (int i = 0; i < A; i++) {
					for (int j = 0; j < N; j++) {
						courses[i][j] = sc.nextInt();
						if (i >= 1) {
							if (check[i] == false && courses[i - 1][j] != courses[i][j]) {
								check[i] = true;
								adj[courses[i - 1][j]].add(courses[i][j]);
							}
						}
					}
				}

				sc.close();
				writeOutput(getResult(adj));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(ArrayList<Integer> result) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				for (int i = result.size() - 1; i > 0; i--) {
					pw.printf("%d ", result.get(i));
				}
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/**
		 * Functie ajutatoare ce contribuie la realizarea sortarii
		 * topologice. Incep prin a marca nodul curent ca vizitat in
		 * vectorul destinat acestui lucru, apoi parcurg matricea de
		 * adiacenta prin toate nodurile adiacente cu nodul curent si
		 * adaug nodul in rezultat.
		 * 
		 * @param v       - nodul de vizitat
		 * @param visited - vectorul de vizitati
		 * @param stack   - rezultatul final
		 * @param adj     - matricea de adiacenta
		 */
		private void getResultHelper(Integer v, boolean[] visited,
				ArrayList<Integer> stack, ArrayList<Integer>[] adj) {

			visited[v] = true;
			Integer i;

			Iterator<Integer> it = adj[v].iterator();
			while (it.hasNext()) {
				i = it.next();
				if (!visited[i]) {
					getResultHelper(i, visited, stack, adj);
				}
			}

			stack.add(v);
		}

		/**
		 * Functia se ocupa cu initializarea vectorului de vizitati, asa
		 * incat fiecare nod sa fie nevizitat initial.
		 * Se apeleaza functia recursiva ce ajuta la realizarea sortarii
		 * topologice pentru fiecare nod nevizitat.
		 * 
		 * @param adj - matricea de adiacenta
		 * @return - rezultatul final
		 */
		private ArrayList<Integer> getResult(ArrayList<Integer>[] adj) {
			ArrayList<Integer> stack = new ArrayList<>();

			boolean[] visited = new boolean[M + 1];
			for (int i = 0; i < M + 1; i++) {
				visited[i] = false;
			}

			for (int i = 0; i < M + 1; i++) {
				if (visited[i] == false) {
					getResultHelper(i, visited, stack, adj);
				}
			}
			return stack;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
