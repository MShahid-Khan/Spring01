package Spring01;

public class RevString {

	public static void main(String[] args) {
		System.out.println(reverseString("A quick brown fox jumps over the lazy doog"));
	}
	
	private static String reverseString(String text) {
		String arrSplit[] = text.split(" ");
		StringBuilder builder = new StringBuilder();
		
		for(String splitText : arrSplit) {
			if(builder.length() > 0) builder.append(" ");
			builder.append(new StringBuilder(splitText).reverse());
		}
		return builder.toString();
	}
}
