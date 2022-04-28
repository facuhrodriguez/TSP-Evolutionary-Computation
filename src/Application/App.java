package Application;

import Helpers.TSPLIBHelper;

public class App {
	static TSPLIBHelper  fh;
	public static void main(String[] args) {
		String fileUrl = args[0];
		fh = new TSPLIBHelper();
		fh.readTSPInstanceFile(fileUrl);
	}

}
