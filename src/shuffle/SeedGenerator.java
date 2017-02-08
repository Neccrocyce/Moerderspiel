package shuffle;

import java.util.Arrays;

public class SeedGenerator {
	int[] numbers;
	
	public SeedGenerator(int[] numbers) {
		this.numbers = numbers;		
		for (int i = 0; i < this.numbers.length; i++) {
			this.numbers[i] = Math.abs(this.numbers[i]);
		}
		Arrays.sort(this.numbers);
	}
	
	public boolean[] generateSeed () {
		int n;
		if (numbers.length < 5) {
			n = (int) (Math.random() * 16);
		} else {
			n = (numbers[numbers.length - 1] * (numbers[numbers.length - 2] / numbers[1])) >> (numbers[0] % 10);
			for (int i = 2; i < numbers.length - 2; i++) {
				n += numbers[i];
			}
		}
		n = Math.abs(n);
		return intToBoolean(n, numbers.length > 4 ? numbers.length : 4);
	}
	
	private boolean[] intToBoolean (int seed, int elements) {
		int seed2 = seed;
		boolean[] b = new boolean[elements];
		for (int i = 0; i < b.length; i++) {
			b[i] = ((seed2 & 0x1) > 0);
			seed2 = seed2 >> 1;
		}
		return b;
	}
}
