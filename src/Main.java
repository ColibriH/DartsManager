import MainController.MainController;


// TODO 4 more than 2 people in one group -> little bit changes in rules check this! (for this need huge refactor and logic changes) AND Create more game types

// Additional features
// ==================================
// TODO Create logging system
// TODO Game Save for unexpected exit
// TODO all player result?
// TODO allPlayer places?
// TODO style configuration
// TODO In game menu
//===================================

// TODO Go through try catch blocks
// TODO How to style playing forms.... and use abstract class
// TODO Create documentation
// TODO Global Refactor: split logic
// TODO Global End Refactor

// TODO check memory leak

public class Main
{
	public static void main (String[] args)
	{
		MainController.DEBUG_MODE = java.lang.management.ManagementFactory.getRuntimeMXBean().getInputArguments().toString().indexOf("-agentlib:jdwp") > 0;
		MainController.openMenuGui ();
	}
}
