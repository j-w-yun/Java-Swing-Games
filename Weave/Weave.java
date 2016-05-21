class Weave
{
	public static void main(String... args)
	{
		MainFrame.getWindow("Weave"); // invoke static factory method
	}

	// TODO :
	//
	// implement MouseListener to DisplayPanel
	// add undo feature
	// add wall generation
		// for this I think we should cluster the walls so that they're not all over the place as a single wall
	// add recursive backtracking map generator
		// there should be two levels of backtracking--
		// one for a fast map validation (see if there are more than one deadend)
		// and another (presumably slower) backtracking one encased within that backtracking to see if the validated map is solvable by trial and error
	// implement difficulty level into map size and number of walls
	// add GUI for player selection of game settings (ie difficulty, size, walls)
}