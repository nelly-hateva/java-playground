import java.util.Random;

public class Absolute {

	public static void main(String[] args) {

		final int[] numbers = generateRandomNumbers();
		System.out.println(sumAbs1(numbers));
		System.out.println(sumAbs2(numbers));
		System.out.println(sumAbs3(numbers));
		System.out.println(sumAbs4(numbers));
		System.out.println(sumAbs5(numbers));
		System.out.println(sumAbs6(numbers));

		Benchmarker.benchmark(new Runnable() {
			@SuppressWarnings("unused")
			long sum = 0;

			public void run() {
				sum = sumAbs1(numbers);
			}
		});
		Benchmarker.benchmark(new Runnable() {
			@SuppressWarnings("unused")
			long sum = 0;

			public void run() {
				sum = sumAbs2(numbers);
			}
		});
		Benchmarker.benchmark(new Runnable() {
			@SuppressWarnings("unused")
			long sum = 0;

			public void run() {
				sum = sumAbs3(numbers);
			}
		});
		Benchmarker.benchmark(new Runnable() {
			@SuppressWarnings("unused")
			long sum = 0;

			public void run() {
				sum = sumAbs4(numbers);
			}
		});
		Benchmarker.benchmark(new Runnable() {
			@SuppressWarnings("unused")
			long sum = 0;

			public void run() {
				sum = sumAbs5(numbers);
			}
		});
		Benchmarker.benchmark(new Runnable() {
			@SuppressWarnings("unused")
			long sum = 0;

			public void run() {
				sum = sumAbs6(numbers);
			}
		});
	}

	static long sumAbs1(int[] numbers) {
		long sum = 0;
		for (int i = 0; i < numbers.length; ++i)
			sum += Math.abs(numbers[i]);
		return sum;
	}

	static long sumAbs2(int[] numbers) {
		long sum = 0;
		for (int i = 0; i < numbers.length; ++i)
			sum += numbers[i] >= 0 ? numbers[i] : -numbers[i];
		return sum;
	}

	static long sumAbs3(int[] numbers) {
		long sum = 0;
		for (int i = 0; i < numbers.length; i += 4) {
			sum += numbers[i] >= 0 ? numbers[i] : -numbers[i];
			sum += numbers[i + 1] >= 0 ? numbers[i + 1] : -numbers[i + 1];
			sum += numbers[i + 2] >= 0 ? numbers[i + 2] : -numbers[i + 2];
			sum += numbers[i + 3] >= 0 ? numbers[i + 3] : -numbers[i + 3];
		}
		return sum;
	}

	static long sumAbs4(int[] numbers) {
		long sum = 0;
		int s, v;
		for (int i = 0; i < numbers.length; ++i) {
			v = numbers[i];
			s = v >> 31;
			sum += (v ^ s) - s;
		}
		return sum;
	}

	static long sumAbs5(int[] numbers) {
		long sum = 0;
		int v0, v1, v2, v3;
		int s0, s1, s2, s3;

		for (int i = 0; i < numbers.length; i += 4) {
			v0 = numbers[i];
			v1 = numbers[i + 1];
			v2 = numbers[i + 2];
			v3 = numbers[i + 3];

			s0 = v0 >> 31;
			s1 = v1 >> 31;
			s2 = v2 >> 31;
			s3 = v3 >> 31;

			sum += (v0 ^ s0) - s0;
			sum += (v1 ^ s1) - s1;
			sum += (v2 ^ s2) - s2;
			sum += (v3 ^ s3) - s3;
		}
		return sum;
	}

	static long sumAbs6(int[] numbers) {
		long sum = 0;
		int v0, v1, v2, v3, v4, v5, v6, v7;
		int s0, s1, s2, s3, s4, s5, s6, s7;

		for (int i = 0; i < numbers.length; i += 8) {
			v0 = numbers[i];
			v1 = numbers[i + 1];
			v2 = numbers[i + 2];
			v3 = numbers[i + 3];
			v4 = numbers[i + 4];
			v5 = numbers[i + 5];
			v6 = numbers[i + 6];
			v7 = numbers[i + 7];

			s0 = v0 >> 31;
			s1 = v1 >> 31;
			s2 = v2 >> 31;
			s3 = v3 >> 31;
			s4 = v4 >> 31;
			s5 = v5 >> 31;
			s6 = v6 >> 31;
			s7 = v7 >> 31;

			sum += (v0 ^ s0) - s0;
			sum += (v1 ^ s1) - s1;
			sum += (v2 ^ s2) - s2;
			sum += (v3 ^ s3) - s3;
			sum += (v4 ^ s4) - s4;
			sum += (v5 ^ s5) - s5;
			sum += (v6 ^ s6) - s6;
			sum += (v7 ^ s7) - s7;
		}
		return sum;
	}

	static int[] generateRandomNumbers() {
		int count = 5000;
		int[] numbers = new int[count];
		Random randomGenerator = new Random();

		for (int i = 0; i < count; ++i)
			numbers[i] = randomGenerator.nextInt(1000000) - 50000;

		return numbers;
	}
}
