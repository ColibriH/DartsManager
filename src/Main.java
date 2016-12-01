import MainController.MainController;


// TODO On/Off Game interface
// Additional features
// ==================================
// TODO Create logging system
// TODO Go through try catch blocks
// TODO Game Save for unexpected exit
//===================================
// TODO How to style playing forms.... and use abstract class
// TODO Create documentation
// TODO check memory leak
// TODO Global End Refactor

public class Main
{
	public static void main (String[] args)
	{
		MainController.DEBUG_MODE = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
		MainController.openMenuGui ();
	}
}
