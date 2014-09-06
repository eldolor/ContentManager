package com.cm.util;

import java.util.logging.Logger;

public class Anglicizer {
	private static final Logger log = Logger.getLogger(Anglicizer.class
			.getName());

	public static final char[] A = new char[] { '�', '�', '�', '�', '�', '�',
			'�' };
	public static final char[] C = new char[] { '�' };
	public static final char[] D = new char[] { '�' };
	public static final char[] E = new char[] { '�', '�', '�', '�' };
	public static final char[] I = new char[] { '�', '�', '�', '�' };
	public static final char[] N = new char[] { '�' };
	public static final char[] O = new char[] { '�', '�', '�', '�', '�', '�' };
	public static final char[] S = new char[] { '�' };
	public static final char[] U = new char[] { '�', '�', '�', '�' };
	public static final char[] Y = new char[] { '�', '�' };
	public static final char[] Z = new char[] { '�' };

	public static final char[] a = new char[] { '�', '�', '�', '�', '�', '�',
			'�' };
	public static final char[] c = new char[] { '�' };
	public static final char[] d = new char[] { '�' };
	public static final char[] e = new char[] { '�', '�', '�', '�' };
	public static final char[] i = new char[] { '�', '�', '�', '�' };
	public static final char[] n = new char[] { '�' };
	public static final char[] o = new char[] { '�', '�', '�', '�', '�', '�',
			'�' };
	public static final char[] s = new char[] { '�', '�' };
	public static final char[] u = new char[] { '�', '�', '�', '�' };
	public static final char[] y = new char[] { '�', '�' };
	public static final char[] z = new char[] { '�' };

	public static String anglicize(String value) {
		log.info("Entering");
		log.info("Original Value => " + value);

		char[] charArray = value.toCharArray();

		boolean charMatch = false;

		for (int ii = 0; ii < charArray.length; ii++, charMatch = false) {

			if (!charMatch) {
				for (int j = 0; j < A.length; j++) {
					if (charArray[ii] == A[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'A');
						charArray[ii] = 'A';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < C.length; j++) {
					if (charArray[ii] == C[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'C');
						charArray[ii] = 'C';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < D.length; j++) {
					if (charArray[ii] == D[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'D');
						charArray[ii] = 'D';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < E.length; j++) {
					if (charArray[ii] == E[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'E');
						charArray[ii] = 'E';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < I.length; j++) {
					if (charArray[ii] == I[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'I');
						charArray[ii] = 'I';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < N.length; j++) {
					if (charArray[ii] == N[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'N');
						charArray[ii] = 'N';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < O.length; j++) {
					if (charArray[ii] == O[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'O');
						charArray[ii] = 'O';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < S.length; j++) {
					if (charArray[ii] == S[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'S');
						charArray[ii] = 'S';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < U.length; j++) {
					if (charArray[ii] == U[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'U');
						charArray[ii] = 'U';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < Y.length; j++) {
					if (charArray[ii] == Y[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'Y');
						charArray[ii] = 'Y';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < Z.length; j++) {
					if (charArray[ii] == Z[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'Z');
						charArray[ii] = 'Z';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < a.length; j++) {
					if (charArray[ii] == a[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'a');
						charArray[ii] = 'a';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < c.length; j++) {
					if (charArray[ii] == c[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'c');
						charArray[ii] = 'c';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < d.length; j++) {
					if (charArray[ii] == d[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'd');
						charArray[ii] = 'd';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < e.length; j++) {
					if (charArray[ii] == e[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'e');
						charArray[ii] = 'e';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < i.length; j++) {
					if (charArray[ii] == i[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'i');
						charArray[ii] = 'i';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < n.length; j++) {
					if (charArray[ii] == n[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'n');
						charArray[ii] = 'n';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < o.length; j++) {
					if (charArray[ii] == o[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'o');
						charArray[ii] = 'o';
						charMatch = true;
						break;
					}
				}
			}
			if (!charMatch) {
				for (int j = 0; j < s.length; j++) {
					if (charArray[ii] == s[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 's');
						charArray[ii] = 's';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < u.length; j++) {
					if (charArray[ii] == u[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'u');
						charArray[ii] = 'u';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < y.length; j++) {
					if (charArray[ii] == y[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'y');
						charArray[ii] = 'y';
						charMatch = true;
						break;
					}
				}

			}
			if (!charMatch) {
				for (int j = 0; j < z.length; j++) {
					if (charArray[ii] == z[j]) {
						log.info("Replacing " + charArray[ii] + " with " + 'z');
						charArray[ii] = 'z';
						charMatch = true;
						break;
					}
				}
			}
		}
		String rtn = new String(charArray);
		log.info("Anglicized Value => " + rtn);
		log.info("Exiting");

		return rtn;
	}
}