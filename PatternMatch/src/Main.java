public class Main {

	static final String[] tests = {
		"", "",
		"a", "",
		"?", "",
		"*", "",
		"*?", "",
		"**", "",
		"", "a",
		"a", "a",
		"b", "a",
		"?", "a",
		"*", "a",
		"*?", "a",
		"**", "a",
		"ab", "aab",
		"a?b", "aab",
		"a*b", "aab",
		"a*?b", "aab",
		"a*a*b", "aab",
		"aa", "aaa",
		"a?a", "aaa",
		"a*a", "aaa",
		"a*?a", "aaa",
		"a*a*a", "aaa",
		"a*b*c", "abc",
		"a*b*c", "axbxc",
		"*a", "aaa",
		"*a", "aaab",
		"*a", "bbba",
		"*b", "aaa",
		"*?", "aaa",
		"a*", "aaa",
		"a*", "baaa",
		"b*", "aaa",
		"?*", "aaa",
		"a*?", "abba",
		"a**", "aa"
	};

	public static void main(String[] args) {

		String regex, pattern, string;
		for (int i = 0; i < tests.length; i = i + 2) {
			pattern = tests[i];
			string = tests[i + 1];
			regex = pattern.replaceAll("\\*", ".*").replaceAll("\\?", ".");
			if (matchsi(pattern, string) != string.matches(regex)) {
				System.out.println(pattern + " does not match " + string);
				System.out.println("was " + matchsi(pattern, string));
			}
		}

		Benchmarker.benchmark(new Runnable() {

			@Override
			public void run() {
				matchsi("a*a*a*b",
						"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaba");

			}
		});

		Benchmarker.benchmark(new Runnable() {

			@Override
			public void run() {
				matchpi("a*a*a*b",
						"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaba");

			}
		});

		Benchmarker.benchmark(new Runnable() {

			@Override
			public void run() {
				regexMatch("a*a*a*b",
						"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaba");

			}
		});
	}

	static boolean regexMatch(String pattern, String string) {
		return string.matches(pattern.replaceAll("\\*", ".*").replaceAll("\\?",
				"."));
	}

	static boolean matchsi(String pattern, String string) {
		int pattern_length = pattern.length();
		int string_length = string.length();
		char sc, pc;
		int pi;
		int si;
		boolean[][] matrix = new boolean[string_length + 1][pattern_length + 1];
		matrix[0][0] = true;
		int position = 0;
		while (position < pattern_length && pattern.charAt(position) == '*') {
			matrix[0][position + 1] = true;
			++position;
		}

		for (si = 0; si < string_length; ++si) {
			sc = string.charAt(si);
			for (pi = 0; pi < pattern_length; ++pi) {
				pc = pattern.charAt(pi);
				if (pc == '*')
					matrix[si + 1][pi + 1] = matrix[si + 1][pi]
							|| matrix[si][pi + 1];
				else
					matrix[si + 1][pi + 1] = (pc == '?' || pc == sc)
							&& matrix[si][pi];
			}
		}
		return matrix[string_length][pattern_length];
	}

	static boolean matchpi(String pattern, String string) {
		int pattern_length = pattern.length();
		int string_length = string.length();
		char pc;
		int pi;
		int si;
		boolean[][] matrix = new boolean[pattern_length + 1][string_length + 1];
		matrix[0][0] = true;
		for (pi = 0; pi < pattern_length; ++pi) {
			pc = pattern.charAt(pi);
			if (pc == '*') {
				matrix[pi + 1][0] = matrix[pi][0];
				for (si = 0; si < string_length; ++si)
					matrix[pi + 1][si + 1] = matrix[pi][si + 1]
							|| matrix[pi + 1][si];
			} else
				for (si = 0; si < string_length; ++si)
					matrix[pi + 1][si + 1] = (pc == '?' || pc == string
							.charAt(si)) && matrix[pi][si];
		}
		return matrix[pattern_length][string_length];
	}
}
