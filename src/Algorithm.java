import java.util.List;


public interface Algorithm {
	
	public List<Solution> solve(ProgramGUI view);
	
	public Solution solve(Robot r, ProgramGUI view);

	public Boolean[][] getVisitedSquares();
	
}
