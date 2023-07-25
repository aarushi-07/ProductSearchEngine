package searchHistory;

public class SearchHistory {

	private static class TreeNode {

		String key;
		int frequency;
		TreeNode left;
		TreeNode right;

		public TreeNode(String key, int frequency) {
			this.key = key;
			this.frequency = frequency;
			this.left = null;
			this.right = null;
		}

	}

	private TreeNode root;
	private int maxSize;

	public SearchHistory(int maxSize) {
		this.maxSize = maxSize;
		this.root = null;
	}

	public void addSearch(String search) {
		root = addSearchHelper(root, search);
	}

	private TreeNode addSearchHelper(TreeNode node, String search) {
		if (node == null) {
			return new TreeNode(search, 1);
		}

		int cmp = search.compareTo(node.key);
		if (cmp == 0) {
			node.frequency++;
		} else if (cmp < 0) {
			node.left = addSearchHelper(node.left, search);
		} else {
			node.right = addSearchHelper(node.right, search);
		}
		return node;
	}

	public int getOrDefault(String search, int defaultValue) {
		return getOrDefaultHelper(root, search, defaultValue);
	}

	private int getOrDefaultHelper(TreeNode node, String search, int defaultValue) {
		if (node == null) {
			return defaultValue;
		}

		int cmp = search.compareTo(node.key);
		if (cmp == 0) {
			return node.frequency;
		} else if (cmp < 0) {
			return getOrDefaultHelper(node.left, search, defaultValue);
		} else {
			return getOrDefaultHelper(node.right, search, defaultValue);
		}
	}

	public void printHistory() {
		printHistoryHelper(root);
	}

	private void printHistoryHelper(TreeNode node) {
		if (node != null) {
			printHistoryHelper(node.left);
			System.out.println(node.key + " - " + node.frequency);
			printHistoryHelper(node.right);
		}
	}

}