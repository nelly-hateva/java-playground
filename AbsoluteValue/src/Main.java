import java.util.Random;

public class Main {

	public static void main(String[] args) {
		final int[] numbers = generateNumbers();
		System.out.println(absoluteValue1(numbers));
		System.out.println(absoluteValue2(numbers));
		System.out.println(absoluteValue3(numbers));
		System.out.println(absoluteValue4(numbers));
		System.out.println(absoluteValue5(numbers));
		Benchmarker.benchmark(new Runnable() {
			int sum = 0;
			public void run() {
				sum = absoluteValue1(numbers);

			}
		});
		Benchmarker.benchmark(new Runnable() {
			int sum = 0;
			public void run() {
				sum = absoluteValue2(numbers);

			}
		});
		Benchmarker.benchmark(new Runnable() {
			int sum = 0;
			public void run() {
				sum = absoluteValue3(numbers);

			}
		});
		Benchmarker.benchmark(new Runnable() {
			int sum = 0;
			public void run() {
				sum = absoluteValue4(numbers);

			}
		});
		Benchmarker.benchmark(new Runnable() {
			int sum = 0;
			public void run() {
				sum = absoluteValue5(numbers);

			}
		});
	}

	static int absoluteValue1(int[] numbers)
	{
		int sum = 0;
		for( int i = 0; i < numbers.length; i++ )
			sum += Math.abs(numbers[i]);
	    return sum;
	}

	static int absoluteValue2(int[] numbers)
	{
		int sum = 0;
		for( int i = 0; i < numbers.length; i++ )
			sum += numbers[i] >= 0 ? numbers[i] : -numbers[i];
	    return sum;
	}

	static int absoluteValue3(int[] numbers)
	{
		int sum = 0, s, v;
		for( int i = 0; i < numbers.length; i++ )
		{
			v = numbers[i];
			s = v >> 31;
			sum += (v ^ s) - s;
		}
	    return sum;
	}

	static int absoluteValue4(int[] numbers)
	{
		int sum = 0;
		int v0, v1, v2, v3;
		int s0, s1, s2, s3;

		for( int i = 0; i < numbers.length; i = i + 4 )
		{
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

	static int absoluteValue5(int[] numbers)
	{
		int sum = 0;
		int v0, v1, v2, v3, v4, v5, v6, v7;
		int s0, s1, s2, s3, s4, s5, s6, s7;
		
		for( int i = 0; i < numbers.length; i = i + 8 )
		{
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

	static int[] generateNumbers()
	{
		int left = 2000, right = 4000, size = 5000, i;
		int[] numbers = new int[size];
		Random randomGenerator = new Random();
		for( i = 0; i < size; ++i )
			numbers[i] = randomGenerator.nextInt(right) - left;
	    return numbers;	
	}
}
