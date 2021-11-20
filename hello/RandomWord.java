import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class RandomWord {
	public RandomWord() {}

	public static void main(String[] args) {

		String champion = StdIn.readString();
		int i = 1;
		while (!StdIn.isEmpty()) {
			i++;
			String word = StdIn.readString();

			if (StdRandom.bernoulli(1/(float)i)) {
				// StdOut.println(i);
				champion = word;
			} 
		}
		StdOut.println(champion);
	}
}