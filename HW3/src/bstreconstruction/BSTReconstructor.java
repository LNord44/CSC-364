package bstreconstruction;

import java.util.ArrayList;
import java.util.List;

public class BSTReconstructor {
	static BST<Integer> originalBST;
	static BST<Integer> preOrderReconstructedBST = new BST<>();
	static BST<Integer> postOrderReconstructedBST = new BST<>();

	static List<Integer> inOrderOutput;
	static List<Integer> preOrderOutput;
	static List<Integer> postOrderOutput;

	static Integer[] input = { 35, 25, 65, 30, 15, 20, 95, 45, 40, 55, 32, 60, 75 };

	public static void main(String[] args) {

		// Create BST from input
		originalBST = new BST<>(input);

		// Generate in/pre/post order traversal lists
		generateBSTTraversalLists();

		// Reconstruct BST from traversal lists of original BST
		System.out.println("Reconstructing BSTs from pre and post order traversal lists.");
		preOrderReconstructor(preOrderOutput);
		postOrderReconstructor(postOrderOutput);

		reconstructionVerificationTests();

	}

	// Generate in/pre/post order traversal lists
	private static void generateBSTTraversalLists() {
		inOrderOutput = originalBST.inorder();
		System.out.println("In-order: " + inOrderOutput);

		preOrderOutput = originalBST.preorder();
		System.out.println("Pre-order: " + preOrderOutput);

		postOrderOutput = originalBST.postorder();
		System.out.println("Post-order: " + postOrderOutput);

	}

	private static void reconstructionVerificationTests() {

		System.out.println("preOrderReconstructedBST should be true: " + originalBST.isEqualTo(preOrderReconstructedBST));
		System.out.println("postOrderReconstructedBST should be true: " + originalBST.isEqualTo(postOrderReconstructedBST));

		System.out.println("Inserting and deleting 65 from original BST");
		originalBST.delete(65);
		originalBST.insert(65);

		System.out.println("preOrderReconstructedBST should be false: " + originalBST.isEqualTo(preOrderReconstructedBST));
		System.out.println("postOrderReconstructedBST should be false: " + originalBST.isEqualTo(postOrderReconstructedBST));

		System.out.println("Inserting and deleting 65 from preOrderReconstructed BST");
		preOrderReconstructedBST.delete(65);
		preOrderReconstructedBST.insert(65);

		System.out.println("preOrderReconstructedBST should be true: " + originalBST.isEqualTo(preOrderReconstructedBST));
		System.out.println("postOrderReconstructedBST should be false: " + originalBST.isEqualTo(postOrderReconstructedBST));

		System.out.println("Inserting and deleting 65 from postOrderReconstructed BST");
		postOrderReconstructedBST.delete(65);
		postOrderReconstructedBST.insert(65);

		System.out.println("preOrderReconstructedBST should be true: " + originalBST.isEqualTo(preOrderReconstructedBST));
		System.out.println("postOrderReconstructedBST should be true: " + originalBST.isEqualTo(postOrderReconstructedBST));
	}

	// Reconstruct BST from pre-order traversal lists of original BST.
	// This method will take an inputArray of pre-order traversal items
	// and re-create the original BST, and save reconstructed tree in the
	// preOrderReconstructedBST variable.
	private static void preOrderReconstructor(List<Integer> inputArray) {
		int size = inputArray.size(); //getting size of input array
		if (size==0) {
			return; //returning if size == 0;
		}
		BST.TreeNode<Integer> root = new BST.TreeNode(inputArray.get(0)); // defining root as the first element in inputArray
		preOrderReconstructedBST.insert(root.element); // adding root to preOrderReconstructedBST
		if (size == 1) { //checking if size == 1
			return;
		}
		int i;
		for (i = 1; i <= size - 1; i++) {
			if (inputArray.get(i) > root.element) {
				break; //recording index of i
			}
		}

		preOrderReconstructor(inputArray.subList(1, i));
		preOrderReconstructor(inputArray.subList(i, size));
	}

	// Reconstruct BST from post-order traversal lists of original BST.
	// This method will take an inputArray of post-order traversal items
	// and re-create the original BST, and save reconstructed tree in the
	// postOrderReconstructedBST variable.
	private static void postOrderReconstructor(List<Integer> inputArray) {
		int size = inputArray.size(); //getting size of input array
		if (size==0) {
			return; //returning if size == 0;
		}
		BST.TreeNode<Integer> root = new BST.TreeNode(inputArray.get(size - 1)); // defining root as the last element in inputArray
		postOrderReconstructedBST.insert(root.element); // adding root to postOrderReconstructedBST
		if (size == 1) { //checking if size == 1
			return;
		}
		int i;
		for (i = 0; i <= size - 2; i++) {
			if (inputArray.get(i) > root.element) {
				break; //getting instance of i
			}
		}

		postOrderReconstructor(inputArray.subList(0,i));
		postOrderReconstructor(inputArray.subList(i,size - 1));
	}


}
