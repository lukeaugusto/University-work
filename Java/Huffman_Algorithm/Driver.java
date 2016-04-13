
import java.util.LinkedList;

public class Driver {
	
	public static void main(String[] args) {

		
		String str = "aaaabbbccd";
		
		System.out.println("Original string: " + str);
		Huffman huffman = new Huffman();
		huffman.createHuffmanTree(str);
		
		LinkedList<Boolean> code = huffman.encode(str);
		System.out.println(code);
		
		System.out.println(huffman.getHuffmanTree());
		String strDecoded = huffman.decode(code);
		System.out.println("Decoded string: " + strDecoded);
		
		System.out.println("Number of bits to store string: " + str.length()*16);
		System.out.println("Number of bits to store code: " + code.size());
	}

}
